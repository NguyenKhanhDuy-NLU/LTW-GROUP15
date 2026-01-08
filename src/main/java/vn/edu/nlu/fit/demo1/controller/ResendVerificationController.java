package vn.edu.nlu.fit.demo1.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.demo1.dao.UserDAO;
import vn.edu.nlu.fit.demo1.dao.VerificationTokenDAO;
import vn.edu.nlu.fit.demo1.model.User;
import vn.edu.nlu.fit.demo1.model.VerificationToken;
import vn.edu.nlu.fit.demo1.util.EmailUtil;
import vn.edu.nlu.fit.demo1.util.TokenUtil;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "ResendVerificationController", urlPatterns = {"/resend-verification"})
public class ResendVerificationController extends HttpServlet {
    private UserDAO userDAO;
    private VerificationTokenDAO tokenDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO();
        tokenDAO = new VerificationTokenDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");

        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Vui lòng nhập email");
            request.getRequestDispatcher("/WEB-INF/views/resend-verification.jsp").forward(request, response);
            return;
        }

        User user = userDAO.getUserByEmail(email.trim());

        if (user == null) {
            request.setAttribute("errorMessage", "Email không tồn tại trong hệ thống");
            request.setAttribute("email", email);
            request.getRequestDispatcher("/WEB-INF/views/resend-verification.jsp").forward(request, response);
            return;
        }

        if (userDAO.isUserVerified(user.getId())) {
            request.setAttribute("errorMessage", "Tài khoản đã được xác thực");
            request.getRequestDispatcher("/WEB-INF/views/resend-verification.jsp").forward(request, response);
            return;
        }

        if (!userDAO.canResendVerification(user.getId())) {
            request.setAttribute("errorMessage", "Vui lòng đợi 5 phút trước khi gửi lại email xác thực");
            request.setAttribute("email", email);
            request.getRequestDispatcher("/WEB-INF/views/resend-verification.jsp").forward(request, response);
            return;
        }

        String token = TokenUtil.generateToken();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(24);

        VerificationToken verificationToken = new VerificationToken(
                user.getId(),
                token,
                "email_verification",
                expiryDate
        );

        if (tokenDAO.createToken(verificationToken)) {
            String verificationLink = request.getRequestURL().toString()
                    .replace("/resend-verification", "/verify-email?token=" + token);

            boolean emailSent = EmailUtil.sendVerificationEmail(
                    user.getEmail(),
                    user.getFullName(),
                    verificationLink
            );

            if (emailSent) {
                HttpSession session = request.getSession(true);
                session.setAttribute("successMessage",
                        "Email xác thực đã được gửi lại. Vui lòng kiểm tra hộp thư.");
                response.sendRedirect(request.getContextPath() + "/verification-sent");
            } else {
                request.setAttribute("errorMessage", "Không thể gửi email. Vui lòng thử lại sau.");
                request.setAttribute("email", email);
                request.getRequestDispatcher("/WEB-INF/views/resend-verification.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Không thể tạo mã xác thực. Vui lòng thử lại.");
            request.setAttribute("email", email);
            request.getRequestDispatcher("/WEB-INF/views/resend-verification.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/resend-verification.jsp").forward(request, response);
    }
}