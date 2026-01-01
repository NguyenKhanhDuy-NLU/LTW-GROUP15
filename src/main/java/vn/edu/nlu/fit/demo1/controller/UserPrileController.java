package vn.edu.nlu.fit.demo1.controller;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.demo1.model.User;
import vn.edu.nlu.fit.demo1.service.UserService;

import java.io.IOException;

@WebServlet(name = "UserProfileController", urlPatterns = {"/user-profile"})
public class UserProfileController extends HttpServlet {

    private final UserService userService = new UserService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User currentUser = (User) session.getAttribute("user");
        boolean isLoggedIn = true;
        String displayName = getUserDisplayName(currentUser);

        request.setAttribute("isLoggedIn", isLoggedIn);
        request.setAttribute("displayName", displayName);
        request.setAttribute("user", currentUser);

        request.getRequestDispatcher("/WEB-INF/views/user-profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    private String getUserDisplayName(User user) {
        if (user == null) return "";
        return (user.getFullName() != null && !user.getFullName().isEmpty())
                ? user.getFullName() : user.getUsername();
    }
}