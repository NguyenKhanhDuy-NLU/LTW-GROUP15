package vn.edu.nlu.fit.demo1.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.demo1.model.User;
import vn.edu.nlu.fit.demo1.service.UserService;
import vn.edu.nlu.fit.demo1.util.ValidationUtil;

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

        User refreshedUser = userService.getUserById(user.getId());
        if (refreshedUser != null) {
            session.setAttribute("user", refreshedUser);
            user = refreshedUser;
        }

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

        fullName = ValidationUtil.sanitizeInput(fullName);
        email = ValidationUtil.sanitizeInput(email);
        phone = ValidationUtil.sanitizeInput(phone);
        gender = ValidationUtil.sanitizeInput(gender);
        address = ValidationUtil.sanitizeInput(address);


        if (fullName == null || fullName.trim().isEmpty() ||
                email == null || email.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Họ tên và Email là thông tin bắt buộc");
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("/WEB-INF/views/user.jsp").forward(request, response);
            return;
        }

        if (!ValidationUtil.isValidFullName(fullName)) {
            request.setAttribute("errorMessage", "Họ và tên phải từ 2-100 ký tự");
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("/WEB-INF/views/user.jsp").forward(request, response);
            return;
        }

        if (!ValidationUtil.isValidEmail(email)) {
            request.setAttribute("errorMessage", "Email không đúng định dạng");
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("/WEB-INF/views/user.jsp").forward(request, response);
            return;
        }

        if (phone != null && !phone.trim().isEmpty() && !ValidationUtil.isValidPhone(phone)) {
            request.setAttribute("errorMessage", "Số điện thoại không đúng định dạng. VD: 0912345678");
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("/WEB-INF/views/user.jsp").forward(request, response);
            return;
        }

        if (!email.trim().equalsIgnoreCase(currentUser.getEmail())) {
            if (userService.isEmailTakenByOtherUser(currentUser.getId(), email.trim())) {
                request.setAttribute("errorMessage", "Email '" + email.trim() + "' đã được sử dụng bởi tài khoản khác");
                request.setAttribute("user", currentUser);
                request.getRequestDispatcher("/WEB-INF/views/user.jsp").forward(request, response);
                return;
            }
        }

        if (phone != null && !phone.trim().isEmpty()) {
            String currentPhone = currentUser.getPhone();
            if (currentPhone == null || !phone.trim().equals(currentPhone.trim())) {
                if (userService.isPhoneTakenByOtherUser(currentUser.getId(), phone.trim())) {
                    request.setAttribute("errorMessage", "Số điện thoại '" + phone.trim() + "' đã được sử dụng bởi tài khoản khác");
                    request.setAttribute("user", currentUser);
                    request.getRequestDispatcher("/WEB-INF/views/user.jsp").forward(request, response);
                    return;
                }
            }
        }
        currentUser.setFullName(fullName.trim());
        currentUser.setEmail(email.trim());
        currentUser.setPhone(phone != null && !phone.trim().isEmpty() ? phone.trim() : null);
        currentUser.setGender(gender);
        currentUser.setAddress(address != null && !address.trim().isEmpty() ? address.trim() : null);

        if (userService.updateUser(currentUser)) {
            User updatedUser = userService.getUserById(currentUser.getId());
            session.setAttribute("user", updatedUser);
            session.setAttribute("fullName", updatedUser.getFullName());
            session.setAttribute("successMessage", "Cập nhật thông tin thành công!");
            response.sendRedirect(request.getContextPath() + "/user");
        } else {
            request.setAttribute("errorMessage", "Cập nhật thất bại. Vui lòng thử lại.");
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("/WEB-INF/views/user.jsp").forward(request, response);
        }
    }
}