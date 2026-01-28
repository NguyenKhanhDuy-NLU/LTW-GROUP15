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
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("AdminAuthFilter initialized - Protecting /admin/* URLs");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        User user = (session != null) ? (User) session.getAttribute("user") : null;

        System.out.println("AdminAuthFilter checking access:");
        System.out.println("  Requested URI: " + httpRequest.getRequestURI());

        if (user != null) {
            System.out.println("  User logged in: " + user.getUsername());
            System.out.println("  User role: " + user.getRole());
        } else {
            System.out.println("  No user in session");
        }

        if (user == null) {
            System.out.println("  -> Access DENIED: No user logged in");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        if (!"admin".equals(user.getRole())) {
            System.out.println("  -> Access DENIED: User is not admin (role=" + user.getRole() + ")");
            System.out.println("403 page redirect...");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/403.jsp");
            return;
        }

        System.out.println("  -> Access GRANTED: Admin user authorized");
        System.out.println("Cho phép yêu cầu: " + httpRequest.getRequestURI());
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("AdminAuthFilter destroyed");
    }
}