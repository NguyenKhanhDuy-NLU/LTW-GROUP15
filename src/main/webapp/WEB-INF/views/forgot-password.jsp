<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quên mật khẩu - Group15</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login.css">
    <link rel="icon" href="${pageContext.request.contextPath}/assets/images/logo.jpg">
</head>
<body>

<div class="login-container">
    <div class="login-wrapper">
        <a href="${pageContext.request.contextPath}/login" class="close-btn" title="Quay lại đăng nhập">
            <i class="fas fa-arrow-left"></i>
        </a>

        <div class="login-header">
            <h1>Quên mật khẩu?</h1>
            <p>Nhập email của bạn để đặt lại mật khẩu</p>
        </div>

        <div class="alert-error" style="<%= request.getAttribute("errorMessage") != null ? "display:flex;" : "display:none;" %>">
            <i class="fas fa-exclamation-circle"></i>
            <span><%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "" %></span>
        </div>

        <form action="${pageContext.request.contextPath}/forgot-password" method="POST" class="login-form">

            <div class="input-group">
                <label for="email">Email đã đăng ký</label>
                <div class="input-field">
                    <i class="fas fa-envelope"></i>
                    <input
                            type="email"
                            id="email"
                            name="email"
                            placeholder="Nhập email của bạn"
                            value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>"
                            required
                            autofocus>
                </div>
            </div>

            <button type="submit" class="btn-login">
                Đặt Lại <i class="fas fa-paper-plane"></i>
            </button>

            <div class="register-link">
                <a href="${pageContext.request.contextPath}/login">
                    <i class="fas fa-arrow-left"></i> Quay lại đăng nhập
                </a>
            </div>

            <div class="register-link" style="margin-top: 10px;">
                Chưa có tài khoản?
                <a href="${pageContext.request.contextPath}/register">Đăng ký ngay</a>
            </div>
        </form>
    </div>
</div>

</body>
</html>