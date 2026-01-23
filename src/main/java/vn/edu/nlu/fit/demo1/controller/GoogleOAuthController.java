package vn.edu.nlu.fit.demo1.controller;

import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.nlu.fit.demo1.model.User;
import vn.edu.nlu.fit.demo1.service.UserService;
import vn.edu.nlu.fit.demo1.util.GoogleOAuthUtil;

import java.io.IOException;

@WebServlet(name = "GoogleOAuthController", urlPatterns = {"/oauth/google", "/oauth/google/callback"})
public class GoogleOAuthController extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if ("/oauth/google".equals(path)) {
            String authUrl = GoogleOAuthUtil.getAuthorizationUrl();
            response.sendRedirect(authUrl);

        } else if ("/oauth/google/callback".equals(path)) {
            handleGoogleCallback(request, response);
        }
    }

    private void handleGoogleCallback(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String code = request.getParameter("code");
        String error = request.getParameter("error");

        if (error != null) {
            request.setAttribute("errorMessage", "Đăng nhập Google thất bại: " + error);
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        if (code == null) {
            request.setAttribute("errorMessage", "Không nhận được mã xác thực từ Google");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        try {
            String accessToken = GoogleOAuthUtil.getAccessToken(code);

            JsonObject userInfo = GoogleOAuthUtil.getUserInfo(accessToken);

            String googleId = userInfo.get("id").getAsString();
            String email = userInfo.has("email") ? userInfo.get("email").getAsString() : null;
            String name = userInfo.has("name") ? userInfo.get("name").getAsString() : "Google User";
            String picture = userInfo.has("picture") ? userInfo.get("picture").getAsString() : null;

            User user = userService.getUserByEmail(email);

            if (user == null) {
                user = new User();
                user.setUsername("google_" + googleId);
                user.setEmail(email);
                user.setFullName(name);
                user.setPassword(ValidationUtil.generateRandomPassword());
                user.setVerified(true);
                user.setAvatar(picture);

                boolean registered = userService.register(user);

                if (!registered) {
                    request.setAttribute("errorMessage", "Không thể tạo tài khoản. Vui lòng thử lại.");
                    request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
                    return;
                }

                user = userService.getUserByEmail(email);
            }

            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            session.setAttribute("username", user.getUsername());
            session.setAttribute("fullName", user.getFullName());
            session.setMaxInactiveInterval(30 * 60);

            response.sendRedirect(request.getContextPath() + "/");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}