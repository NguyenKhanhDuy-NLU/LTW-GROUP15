package vn.edu.nlu.fit.demo1.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.demo1.model.User;
import vn.edu.nlu.fit.demo1.service.UserService;

import java.io.IOException;
@WebServlet(name = "UserController", urlPatterns = {"/user"})
public class UserController extends HttpServlet {
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
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        request.setAttribute("user", user);

        request.getRequestDispatcher("/WEB-INF/views/user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User currentUser = (User) session.getAttribute("user");

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");

        if (fullName == null || fullName.trim().isEmpty() ||
                email == null || email.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Vui lòng điền đầy đủ thông tin bắt buộc");
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("/WEB-INF/views/user.jsp").forward(request, response);
            return;
        }

        if (phone != null && !phone.trim().isEmpty() && !phone.matches("\\d+")) {
            request.setAttribute("errorMessage", "Số điện thoại chỉ được chứa chữ số");
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("/WEB-INF/views/user.jsp").forward(request, response);
            return;
        }

        currentUser.setFullName(fullName.trim());
        currentUser.setEmail(email.trim());
        currentUser.setPhone(phone);
        currentUser.setGender(gender);
        currentUser.setAddress(address);

        if (userService.updateUser(currentUser)) {
            session.setAttribute("user", currentUser);
            session.setAttribute("fullName", currentUser.getFullName());
            session.setAttribute("successMessage", "Cập nhật thông tin thành công!");
            response.sendRedirect(request.getContextPath() + "/user");
        } else {
            request.setAttribute("errorMessage", "Cập nhật thất bại. Vui lòng thử lại.");
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("/WEB-INF/views/user.jsp").forward(request, response);
        }
    }
}