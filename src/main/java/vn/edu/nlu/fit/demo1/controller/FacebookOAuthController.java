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
import vn.edu.nlu.fit.demo1.util.FacebookOAuthUtil;
import vn.edu.nlu.fit.demo1.util.ValidationUtil;

import java.io.IOException;

@WebServlet(name = "FacebookOAuthController", urlPatterns = {"/oauth/facebook", "/oauth/facebook/callback"})
public class FacebookOAuthController extends HttpServlet {

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

        if ("/oauth/facebook".equals(path)) {
            String authUrl = FacebookOAuthUtil.getAuthorizationUrl();
            response.sendRedirect(authUrl);

        } else if ("/oauth/facebook/callback".equals(path)) {
            handleFacebookCallback(request, response);
        }
    }

    private void handleFacebookCallback(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String code = request.getParameter("code");
        String error = request.getParameter("error");

        if (error != null) {
            request.setAttribute("errorMessage", "Đăng nhập Facebook thất bại: " + error);
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        if (code == null) {
            request.setAttribute("errorMessage", "Không nhận được mã xác thực từ Facebook");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        try {
            String accessToken = FacebookOAuthUtil.getAccessToken(code);

            JsonObject userInfo = FacebookOAuthUtil.getUserInfo(accessToken);

            String facebookId = userInfo.get("id").getAsString();
            String email = userInfo.has("email") ? userInfo.get("email").getAsString() : null;
            String name = userInfo.has("name") ? userInfo.get("name").getAsString() : "Facebook User";

            String picture = null;
            if (userInfo.has("picture") && userInfo.getAsJsonObject("picture").has("data")) {
                picture = userInfo.getAsJsonObject("picture")
                        .getAsJsonObject("data")
                        .get("url").getAsString();
            }

            User user = null;
            if (email != null) {
                user = userService.getUserByEmail(email);
            }

            if (user == null) {
                user = new User();
                user.setUsername("facebook_" + facebookId);
                user.setEmail(email != null ? email : "fb_" + facebookId + "@facebook.com");
                user.setFullName(name);
                user.setPassword(ValidationUtil.generateRandomPassword());
                user.setVerified(true); // Facebook users are pre-verified
                user.setAvatar(picture);

                boolean registered = userService.register(user);

                if (!registered) {
                    request.setAttribute("errorMessage", "Không thể tạo tài khoản. Vui lòng thử lại.");
                    request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
                    return;
                }

                user = userService.getUserByEmail(user.getEmail());
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