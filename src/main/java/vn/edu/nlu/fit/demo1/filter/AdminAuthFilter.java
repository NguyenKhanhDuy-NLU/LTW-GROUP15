package vn.edu.nlu.fit.demo1.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.nlu.fit.demo1.model.User;

import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/*"})
public class AdminAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String requestURI = httpRequest.getRequestURI();
        System.out.println("=== ADMIN AUTH FILTER ===");
        System.out.println("Request URI: " + requestURI);

        if (session == null || session.getAttribute("user") == null) {
            System.out.println("Không có phiên hoặc người dùng nào chưa đăng nhập");
            System.out.println("Chuyển hướng tới login page...");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login?redirect=admin");
            return;
        }

        User user = (User) session.getAttribute("user");
        System.out.println("User  in: " + user.getUsername());
        System.out.println("User  ID: " + user.getRoleId());

        if (user.getRoleId() != 1) {
            System.out.println("Quyền truy cập bị từ chối! Người dùng không phải là admin (roleId != 1)");
            System.out.println("403 page...");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/403.jsp");
            return;
        }

        System.out.println("Admin quyền truy cập được cấp!");
        System.out.println("Cho phép yêu cầu: " + requestURI);
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("AdminAuthFilter initialized - Protecting /admin/* URLs");
    }

    @Override
    public void destroy() {
        System.out.println("AdminAuthFilter destroyed");
    }
}