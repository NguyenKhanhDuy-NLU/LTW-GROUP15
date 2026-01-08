<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập - Group15</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login.css">
    <link rel="icon" href="${pageContext.request.contextPath}/assets/images/logo.jpg">
</head>
<body>

<div class="login-container">
    <div class="login-wrapper">
        <a href="${pageContext.request.contextPath}/" class="close-btn" title="Về trang chủ">
            <i class="fas fa-times"></i>
        </a>

        <div class="login-header">
            <h1>Chào mừng trở lại!</h1>
            <p>Vui lòng đăng nhập để quản lý đặt phòng</p>
        </div>

        <% if (request.getAttribute("successMessage") != null) { %>
        <div class="alert-error" style="display:flex; background-color: #d4edda; border: 1px solid #c3e6cb; color: #155724;">
            <i class="fas fa-check-circle"></i>
            <span><%= request.getAttribute("successMessage") %></span>
        </div>
        <% } %>

        <div class="alert-error" style="<%= request.getAttribute("errorMessage") != null ? "display:flex;" : "display:none;" %>">
            <i class="fas fa-exclamation-circle"></i>
            <span><%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "" %></span>
        </div>

        <% if (request.getAttribute("showResendLink") != null) { %>
        <div style="text-align: center; margin: 15px 0;">
            <a href="${pageContext.request.contextPath}/resend-verification"
               style="color: #0057FF; text-decoration: none; font-weight: 600; font-size: 14px;">
                <i class="fas fa-paper-plane"></i> Gửi lại email xác thực
            </a>
        </div>
        <% } %>

        <form action="${pageContext.request.contextPath}/login" method="POST" class="login-form">

            <div class="input-group">
                <label for="username">Tên đăng nhập / Email</label>
                <div class="input-field">
                    <i class="fas fa-user"></i>
                    <input
                            type="text"
                            id="username"
                            name="username"
                            placeholder="Nhập tên đăng nhập / email"
                            value="<%= request.getAttribute("username") != null ? request.getAttribute("username") : "" %>"
                            required
                            autofocus>
                </div>
            </div>

            <div class="input-group">
                <label for="password">Mật khẩu</label>
                <div class="input-field">
                    <i class="fas fa-lock"></i>
                    <input
                            type="password"
                            id="password"
                            name="password"
                            placeholder="Nhập mật khẩu"
                            required>
                </div>
            </div>

            <div class="form-actions">
                <div class="remember-me">
                    <input type="checkbox" id="remember" name="remember">
                    <label for="remember">Ghi nhớ tôi</label>
                </div>
                <a href="${pageContext.request.contextPath}/forgot-password" class="forgot-pass">Quên mật khẩu?</a>
            </div>

            <button type="submit" class="btn-login">
                Đăng Nhập <i class="fas fa-arrow-right"></i>
            </button>

            <div class="register-link">
                Bạn chưa có tài khoản?
                <a href="${pageContext.request.contextPath}/register">Đăng ký ngay</a>
            </div>
        </form>
    </div>
</div>

</body>
</html>