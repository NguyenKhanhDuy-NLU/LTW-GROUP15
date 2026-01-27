package vn.edu.nlu.fit.demo1.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.demo1.dao.UserDAO;
import vn.edu.nlu.fit.demo1.dao.VerificationTokenDAO;
import vn.edu.nlu.fit.demo1.model.VerificationToken;

import java.io.IOException;

@WebServlet(name = "VerifyEmailController", urlPatterns = {"/verify-email"})
public class VerifyEmailController extends HttpServlet {
    private VerificationTokenDAO tokenDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        tokenDAO = new VerificationTokenDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = request.getParameter("token");

        if (token == null || token.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Link xác thực không hợp lệ");
            request.getRequestDispatcher("/WEB-INF/views/verification-result.jsp").forward(request, response);
            return;
        }

        VerificationToken verificationToken = tokenDAO.getTokenByString(token);

        if (verificationToken == null) {
            request.setAttribute("errorMessage", "Link xác thực không tồn tại hoặc đã được sử dụng");
            request.getRequestDispatcher("/WEB-INF/views/verification-result.jsp").forward(request, response);
            return;
        }

        if (verificationToken.isExpired()) {
            request.setAttribute("errorMessage", "Link xác thực đã hết hạn. Vui lòng yêu cầu gửi lại.");
            request.setAttribute("showResendLink", true);
            request.setAttribute("userId", verificationToken.getUserId());
            request.getRequestDispatcher("/WEB-INF/views/verification-result.jsp").forward(request, response);
            return;
        }

        if (verificationToken.isUsed()) {
            request.setAttribute("errorMessage", "Link xác thực đã được sử dụng");
            request.getRequestDispatcher("/WEB-INF/views/verification-result.jsp").forward(request, response);
            return;
        }
        boolean verified = userDAO.verifyUser(verificationToken.getUserId());

        if (verified) {
            tokenDAO.markTokenAsUsed(token);

            request.setAttribute("successMessage",
                    "Xác thực tài khoản thành công! Bạn có thể đăng nhập ngay bây giờ.");
            request.setAttribute("showLoginLink", true);
        } else {
            request.setAttribute("errorMessage", "Không thể xác thực tài khoản. Vui lòng thử lại.");
        }

        request.getRequestDispatcher("/WEB-INF/views/verification-result.jsp").forward(request, response);
    }
}