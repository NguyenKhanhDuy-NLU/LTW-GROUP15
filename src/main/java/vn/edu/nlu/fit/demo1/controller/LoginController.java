package vn.edu.nlu.fit.demo1.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.demo1.model.User;
import vn.edu.nlu.fit.demo1.service.UserService;

import java.io.IOException;

@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        if (session != null && session.getAttribute("successMessage") != null) {
            request.setAttribute("successMessage", session.getAttribute("successMessage"));
            session.removeAttribute("successMessage");
        }

        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Vui lòng nhập đầy đủ thông tin đăng nhập");
            request.setAttribute("username", username);
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }
        System.out.println("=== LOGIN ATTEMPT ===");
        System.out.println("Username: " + username);
        System.out.println("Password length: " + password.length());

        User user = userService.authenticate(username.trim(), password);

        if (user != null) {
            System.out.println("✓ Authentication successful for: " + username);


            if (!user.isActive()) {
                System.out.println("✗ User account is inactive: " + username);
                request.setAttribute("errorMessage",
                        "Tài khoản của bạn đã bị khóa. Vui lòng liên hệ admin để được hỗ trợ.");
                request.setAttribute("username", username);
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
                return;
            }
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("fullName", user.getFullName());
            session.setAttribute("userRole", user.getRole()); // admin hoặc customer
            session.setMaxInactiveInterval(30 * 60); // 30 phút

            System.out.println("✓ Login successful! User ID: " + user.getId());
            System.out.println("✓ User role: " + user.getRole());

            if ("admin".equalsIgnoreCase(user.getRole())) {
                System.out.println("Admin login - Redirecting to admin dashboard...");
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                System.out.println("User login - Redirecting to home page...");
                response.sendRedirect(request.getContextPath() + "/");
            }
        } else {
            System.out.println("✗ Authentication failed for: " + username);
            request.setAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng");
            request.setAttribute("username", username);
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}