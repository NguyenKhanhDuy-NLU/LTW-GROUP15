package vn.edu.nlu.fit.demo1.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.demo1.dao.VerificationTokenDAO;
import vn.edu.nlu.fit.demo1.model.User;
import vn.edu.nlu.fit.demo1.model.VerificationToken;
import vn.edu.nlu.fit.demo1.service.UserService;
import vn.edu.nlu.fit.demo1.util.EmailUtil;
import vn.edu.nlu.fit.demo1.util.TokenUtil;
import vn.edu.nlu.fit.demo1.util.ValidationUtil;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;
    private VerificationTokenDAO tokenDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
        tokenDAO = new VerificationTokenDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                fullName == null || fullName.trim().isEmpty() ||
                email == null || email.trim().isEmpty()) {

            request.setAttribute("errorMessage", "Vui lòng điền đầy đủ thông tin bắt buộc (*)");
            setFormData(request, username, fullName, email, phone);
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        if (!ValidationUtil.isValidUsername(username)) {
            request.setAttribute("errorMessage", "Tên đăng nhập phải từ 3-20 ký tự, chỉ chứa chữ cái, số và dấu gạch dưới (_)");
            setFormData(request, username, fullName, email, phone);
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        if (!ValidationUtil.isValidEmail(email)) {
            request.setAttribute("errorMessage", "Email không đúng định dạng. VD: example@gmail.com");
            setFormData(request, username, fullName, email, phone);
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        if (phone != null && !phone.trim().isEmpty() && !ValidationUtil.isValidPhone(phone)) {
            request.setAttribute("errorMessage", "Số điện thoại không đúng định dạng. VD: 0912345678 hoặc +84912345678");
            setFormData(request, username, fullName, email, phone);
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        if (!ValidationUtil.isValidFullName(fullName)) {
            request.setAttribute("errorMessage", "Họ và tên phải từ 2-100 ký tự");
            setFormData(request, username, fullName, email, phone);
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        if (!ValidationUtil.isValidPassword(password)) {
            request.setAttribute("errorMessage", "Mật khẩu phải từ 6-50 ký tự");
            setFormData(request, username, fullName, email, phone);
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Mật khẩu xác nhận không khớp. Vui lòng nhập lại");
            setFormData(request, username, fullName, email, phone);
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        if (userService.isUsernameExists(username.trim())) {
            request.setAttribute("errorMessage", "Tên đăng nhập '" + username.trim() + "' đã tồn tại. Vui lòng chọn tên khác");
            setFormData(request, username, fullName, email, phone);
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        if (userService.isEmailExists(email.trim())) {
            request.setAttribute("errorMessage", "Email '" + email.trim() + "' đã được đăng ký. Vui lòng sử dụng email khác hoặc <a href='" +
                    request.getContextPath() + "/login'>đăng nhập</a>");
            setFormData(request, username, fullName, email, phone);
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        if (phone != null && !phone.trim().isEmpty() && userService.isPhoneExists(phone.trim())) {
            request.setAttribute("errorMessage", "Số điện thoại '" + phone.trim() + "' đã được đăng ký. Vui lòng sử dụng số khác");
            setFormData(request, username, fullName, email, phone);
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        User newUser = new User(
                username.trim(),
                password,
                fullName.trim(),
                email.trim(),
                phone != null && !phone.trim().isEmpty() ? phone.trim() : null
        );

        if (userService.register(newUser)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("successMessage", "Đăng ký thành công! Bạn có thể đăng nhập ngay.");
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            request.setAttribute("errorMessage", "Đăng ký thất bại. Vui lòng thử lại sau hoặc liên hệ admin nếu vấn đề vẫn tiếp diễn.");
            setFormData(request, username, fullName, email, phone);
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        }
    }

    private void setFormData(HttpServletRequest request, String username, String fullName, String email, String phone) {
        request.setAttribute("username", username);
        request.setAttribute("fullName", fullName);
        request.setAttribute("email", email);
        request.setAttribute("phone", phone);
    }
}