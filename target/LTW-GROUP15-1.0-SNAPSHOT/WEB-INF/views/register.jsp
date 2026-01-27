<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký - Group15</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/register.css">
    <link rel="icon" href="${pageContext.request.contextPath}/assets/images/logo.jpg">

</head>
<body>

<div class="login-container">
    <div class="login-wrapper">
        <a href="${pageContext.request.contextPath}/" class="close-btn" title="Về trang chủ">
            <i class="fas fa-times"></i>
        </a>

        <div class="login-header">
            <h1>Tạo tài khoản mới</h1>
            <p>Đăng ký để quản lý đặt phòng của bạn</p>
        </div>

        <div class="alert-error" style="<%= request.getAttribute("errorMessage") != null ? "display:flex;" : "display:none;" %>">
            <i class="fas fa-exclamation-circle"></i>
            <span><%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "" %></span>
        </div>

        <form action="${pageContext.request.contextPath}/register" method="POST" class="login-form" id="registerForm" novalidate>

            <div class="input-group">
                <label for="fullName">Họ và tên <span style="color: red;">*</span></label>
                <div class="input-field" id="fullNameField">
                    <i class="fas fa-user"></i>
                    <input
                            type="text"
                            id="fullName"
                            name="fullName"
                            placeholder="Nhập họ và tên"
                            value="<%= request.getAttribute("fullName") != null ? request.getAttribute("fullName") : "" %>"
                            required
                            autofocus>
                </div>
                <span class="error-message" id="fullNameError">Họ tên phải từ 2-100 ký tự</span>
            </div>

            <div class="form-row">
                <div class="input-group">
                    <label for="username">Tên đăng nhập <span style="color: red;">*</span></label>
                    <div class="input-field" id="usernameField">
                        <i class="fas fa-user-circle"></i>
                        <input
                                type="text"
                                id="username"
                                name="username"
                                placeholder="Tên đăng nhập"
                                value="<%= request.getAttribute("username") != null ? request.getAttribute("username") : "" %>"
                                required>
                    </div>
                    <span class="error-message" id="usernameError">Tên đăng nhập phải từ 3-20 ký tự, chỉ chữ cái, số và _</span>
                </div>

                <div class="input-group">
                    <label for="phone">Số điện thoại</label>
                    <div class="input-field" id="phoneField">
                        <i class="fas fa-phone"></i>
                        <input
                                type="tel"
                                id="phone"
                                name="phone"
                                placeholder="Số điện thoại"
                                value="<%= request.getAttribute("phone") != null ? request.getAttribute("phone") : "" %>">
                    </div>
                    <span class="error-message" id="phoneError">Số điện thoại không đúng định dạng</span>
                </div>
            </div>

            <div class="input-group">
                <label for="email">Email <span style="color: red;">*</span></label>
                <div class="input-field" id="emailField">
                    <i class="fas fa-envelope"></i>
                    <input
                            type="email"
                            id="email"
                            name="email"
                            placeholder="Nhập email"
                            value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>"
                            required>
                </div>
                <span class="error-message" id="emailError">Email không đúng định dạng</span>
            </div>

            <div class="form-row">
                <div class="input-group">
                    <label for="password">Mật khẩu <span style="color: red;">*</span></label>
                    <div class="input-field" id="passwordField">
                        <i class="fas fa-lock"></i>
                        <input
                                type="password"
                                id="password"
                                name="password"
                                placeholder="Mật khẩu (6-50 ký tự)"
                                required>
                    </div>
                    <span class="error-message" id="passwordError">Mật khẩu phải từ 6-50 ký tự</span>
                </div>

                <div class="input-group">
                    <label for="confirmPassword">Xác nhận mật khẩu <span style="color: red;">*</span></label>
                    <div class="input-field" id="confirmPasswordField">
                        <i class="fas fa-lock"></i>
                        <input
                                type="password"
                                id="confirmPassword"
                                name="confirmPassword"
                                placeholder="Nhập lại mật khẩu"
                                required>
                    </div>
                    <span class="error-message" id="confirmPasswordError">Mật khẩu xác nhận không khớp</span>
                </div>
            </div>

            <button type="submit" class="btn-login">
                Đăng Ký <i class="fas fa-user-plus"></i>
            </button>

            <div class="register-link">
                Đã có tài khoản?
                <a href="${pageContext.request.contextPath}/login">Đăng nhập ngay</a>
            </div>
        </form>
    </div>
</div>

<script>
    const patterns = {
        username: /^[a-zA-Z0-9_]{3,20}$/,
        email: /^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/,
        phone: /^(0|\+84)(\s|\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\d)(\s|\.)?((\d{3})(\s|\.)?(\d{3}))$/,
        fullName: /^.{2,100}$/,
        password: /^.{6,50}$/
    };

    const form = document.getElementById('registerForm');
    const fullNameInput = document.getElementById('fullName');
    const usernameInput = document.getElementById('username');
    const emailInput = document.getElementById('email');
    const phoneInput = document.getElementById('phone');
    const passwordInput = document.getElementById('password');
    const confirmPasswordInput = document.getElementById('confirmPassword');

    function validateFullName() {
        const value = fullNameInput.value.trim();
        const field = document.getElementById('fullNameField');
        const error = document.getElementById('fullNameError');

        if (!value) {
            showError(field, error, 'Họ tên không được để trống');
            return false;
        }
        if (!patterns.fullName.test(value)) {
            showError(field, error, 'Họ tên phải từ 2-100 ký tự');
            return false;
        }
        showSuccess(field, error);
        return true;
    }

    function validateUsername() {
        const value = usernameInput.value.trim();
        const field = document.getElementById('usernameField');
        const error = document.getElementById('usernameError');

        if (!value) {
            showError(field, error, 'Tên đăng nhập không được để trống');
            return false;
        }
        if (!patterns.username.test(value)) {
            showError(field, error, 'Tên đăng nhập phải từ 3-20 ký tự, chỉ chữ cái, số và _');
            return false;
        }
        showSuccess(field, error);
        return true;
    }

    function validateEmail() {
        const value = emailInput.value.trim();
        const field = document.getElementById('emailField');
        const error = document.getElementById('emailError');

        if (!value) {
            showError(field, error, 'Email không được để trống');
            return false;
        }
        if (!patterns.email.test(value)) {
            showError(field, error, 'Email không đúng định dạng');
            return false;
        }
        showSuccess(field, error);
        return true;
    }

    function validatePhone() {
        const value = phoneInput.value.trim();
        const field = document.getElementById('phoneField');
        const error = document.getElementById('phoneError');

        if (!value) {
            hideError(field, error);
            return true; // Phone is optional
        }

        const cleanPhone = value.replace(/\s/g, '');
        if (!patterns.phone.test(cleanPhone)) {
            showError(field, error, 'Số điện thoại không đúng định dạng');
            return false;
        }
        showSuccess(field, error);
        return true;
    }

    function validatePassword() {
        const value = passwordInput.value;
        const field = document.getElementById('passwordField');
        const error = document.getElementById('passwordError');

        if (!value) {
            showError(field, error, 'Mật khẩu không được để trống');
            return false;
        }
        if (!patterns.password.test(value)) {
            showError(field, error, 'Mật khẩu phải từ 6-50 ký tự');
            return false;
        }
        showSuccess(field, error);
        return true;
    }

    function validateConfirmPassword() {
        const password = passwordInput.value;
        const confirmPassword = confirmPasswordInput.value;
        const field = document.getElementById('confirmPasswordField');
        const error = document.getElementById('confirmPasswordError');

        if (!confirmPassword) {
            showError(field, error, 'Vui lòng xác nhận mật khẩu');
            return false;
        }
        if (password !== confirmPassword) {
            showError(field, error, 'Mật khẩu xác nhận không khớp');
            return false;
        }
        showSuccess(field, error);
        return true;
    }

    function showError(field, errorElement, message) {
        field.classList.add('error');
        errorElement.textContent = message;
        errorElement.classList.add('show');
    }

    function showSuccess(field, errorElement) {
        field.classList.remove('error');
        errorElement.classList.remove('show');
    }

    function hideError(field, errorElement) {
        field.classList.remove('error');
        errorElement.classList.remove('show');
    }

    fullNameInput.addEventListener('blur', validateFullName);
    usernameInput.addEventListener('blur', validateUsername);
    emailInput.addEventListener('blur', validateEmail);
    phoneInput.addEventListener('blur', validatePhone);
    passwordInput.addEventListener('blur', validatePassword);
    confirmPasswordInput.addEventListener('blur', validateConfirmPassword);
    confirmPasswordInput.addEventListener('input', validateConfirmPassword);

    form.addEventListener('submit', function(e) {
        e.preventDefault();

        const isValidFullName = validateFullName();
        const isValidUsername = validateUsername();
        const isValidEmail = validateEmail();
        const isValidPhone = validatePhone();
        const isValidPassword = validatePassword();
        const isValidConfirmPassword = validateConfirmPassword();

        if (isValidFullName && isValidUsername && isValidEmail &&
            isValidPhone && isValidPassword && isValidConfirmPassword) {
            form.submit();
        } else {
            // Scroll to first error
            const firstError = document.querySelector('.input-field.error');
            if (firstError) {
                firstError.scrollIntoView({ behavior: 'smooth', block: 'center' });
            }
        }
    });
</script>

</body>
</html>