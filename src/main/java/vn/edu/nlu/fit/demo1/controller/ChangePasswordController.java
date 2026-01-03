package vn.edu.nlu.fit.demo1.controller;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.demo1.model.User;
import vn.edu.nlu.fit.demo1.service.UserService;
import java.io.IOException;

@WebServlet(name = "ChangePasswordController", urlPatterns = {"/change-password"})
public class ChangePasswordController extends HttpServlet {
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
        request.getRequestDispatcher("/WEB-INF/views/change-password.jsp").forward(request, response);
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
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        if (currentPassword == null || currentPassword.trim().isEmpty() ||
                newPassword == null || newPassword.trim().isEmpty() ||
                confirmPassword == null || confirmPassword.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Vui lòng điền đầy đủ thông tin");
            request.getRequestDispatcher("/WEB-INF/views/change-password.jsp").forward(request, response);
            return;
        }
        if (!currentUser.getPassword().equals(currentPassword)) {
            request.setAttribute("errorMessage", "Mật khẩu hiện tại không đúng");
            request.getRequestDispatcher("/WEB-INF/views/change-password.jsp").forward(request, response);
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Mật khẩu mới không khớp");
            request.getRequestDispatcher("/WEB-INF/views/change-password.jsp").forward(request, response);
            return;
        }
        if (newPassword.length() < 6) {
            request.setAttribute("errorMessage", "Mật khẩu mới phải có ít nhất 6 ký tự");
            request.getRequestDispatcher("/WEB-INF/views/change-password.jsp").forward(request, response);
            return;
        }
        if (newPassword.equals(currentPassword)) {
            request.setAttribute("errorMessage", "Mật khẩu mới phải khác mật khẩu hiện tại");
            request.getRequestDispatcher("/WEB-INF/views/change-password.jsp").forward(request, response);
            return;
        }
        if (userService.changePassword(currentUser.getUsername(), currentPassword, newPassword)) {
            currentUser.setPassword(newPassword);
            session.setAttribute("user", currentUser);
            session.setAttribute("successMessage", "Đổi mật khẩu thành công!");
            response.sendRedirect(request.getContextPath() + "/user");
        } else {
            request.setAttribute("errorMessage", "Đổi mật khẩu thất bại. Vui lòng thử lại.");
            request.getRequestDispatcher("/WEB-INF/views/change-password.jsp").forward(request, response);
        }
    }
}