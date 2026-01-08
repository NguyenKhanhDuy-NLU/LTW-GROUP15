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

        username = ValidationUtil.sanitizeInput(username);
        fullName = ValidationUtil.sanitizeInput(fullName);
        email = ValidationUtil.sanitizeInput(email);
        phone = ValidationUtil.sanitizeInput(phone);

        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                fullName == null || fullName.trim().isEmpty() ||
                email == null || email.trim().isEmpty()) {

            request.setAttribute("errorMessage", "Vui lòng điền đầy đủ thông tin bắt buộc");
            setFormData(request, username, fullName, email, phone);
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        if (!ValidationUtil.isValidUsername(username)) {
            request.setAttribute("errorMessage", "Tên đăng nhập phải từ 3-20 ký tự, chỉ chứa chữ cái, số và dấu gạch dưới");
            setFormData(request, username, fullName, email, phone);
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        if (!ValidationUtil.isValidEmail(email)) {
            request.setAttribute("errorMessage", "Email không đúng định dạng");
            setFormData(request, username, fullName, email, phone);
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        if (phone != null && !phone.trim().isEmpty() && !ValidationUtil.isValidPhone(phone)) {
            request.setAttribute("errorMessage", "Số điện thoại không đúng định dạng");
            setFormData(request, username, fullName, email, phone);
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        if (!ValidationUtil.isValidFullName(fullName)) {
            request.setAttribute("errorMessage", "Họ tên phải từ 2-100 ký tự");
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
            request.setAttribute("errorMessage", "Mật khẩu xác nhận không khớp");
            setFormData(request, username, fullName, email, phone);
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        if (userService.isUsernameExists(username.trim())) {
            request.setAttribute("errorMessage", "Tên đăng nhập đã tồn tại");
            setFormData(request, username, fullName, email, phone);
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        if (userService.isEmailExists(email.trim())) {
            request.setAttribute("errorMessage", "Email đã được sử dụng");
            setFormData(request, username, fullName, email, phone);
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        User newUser = new User(username.trim(), password, fullName.trim(), email.trim(),
                phone != null ? phone.trim() : null);

        if (userService.register(newUser)) {
            String token = TokenUtil.generateToken();
            LocalDateTime expiryDate = LocalDateTime.now().plusHours(24);

            VerificationToken verificationToken = new VerificationToken(
                    newUser.getId(),
                    token,
                    "email_verification",
                    expiryDate
            );

            if (tokenDAO.createToken(verificationToken)) {
                String verificationLink = request.getRequestURL().toString()
                        .replace("/register", "/verify-email?token=" + token);

                boolean emailSent = EmailUtil.sendVerificationEmail(
                        newUser.getEmail(),
                        newUser.getFullName(),
                        verificationLink
                );

                if (emailSent) {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("registeredEmail", newUser.getEmail());
                    session.setAttribute("successMessage",
                            "Đăng ký thành công! Vui lòng kiểm tra email để xác thực tài khoản.");
                    response.sendRedirect(request.getContextPath() + "/verification-sent");
                } else {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("warningMessage",
                            "Đăng ký thành công nhưng không thể gửi email xác thực. Vui lòng liên hệ admin.");
                    response.sendRedirect(request.getContextPath() + "/login");
                }
            } else {
                request.setAttribute("errorMessage", "Không thể tạo mã xác thực. Vui lòng thử lại.");
                setFormData(request, username, fullName, email, phone);
                request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Đăng ký thất bại. Vui lòng thử lại.");
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