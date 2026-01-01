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

        <form action="${pageContext.request.contextPath}/register" method="POST" class="login-form">

            <div class="input-group">
                <label for="fullName">Họ và tên <span style="color: red;">*</span></label>
                <div class="input-field">
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
            </div>

            <div class="form-row">
                <div class="input-group">
                    <label for="username">Tên đăng nhập <span style="color: red;">*</span></label>
                    <div class="input-field">
                        <i class="fas fa-user-circle"></i>
                        <input
                                type="text"
                                id="username"
                                name="username"
                                placeholder="Tên đăng nhập"
                                value="<%= request.getAttribute("username") != null ? request.getAttribute("username") : "" %>"
                                required>
                    </div>
                </div>

                <div class="input-group">
                    <label for="phone">Số điện thoại</label>
                    <div class="input-field">
                        <i class="fas fa-phone"></i>
                        <input
                                type="tel"
                                id="phone"
                                name="phone"
                                placeholder="Số điện thoại"
                                value="<%= request.getAttribute("phone") != null ? request.getAttribute("phone") : "" %>">
                    </div>
                </div>
            </div>

            <div class="input-group">
                <label for="email">Email <span style="color: red;">*</span></label>
                <div class="input-field">
                    <i class="fas fa-envelope"></i>
                    <input
                            type="email"
                            id="email"
                            name="email"
                            placeholder="Nhập email"
                            value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>"
                            required>
                </div>
            </div>

            <div class="form-row">
                <div class="input-group">
                    <label for="password">Mật khẩu <span style="color: red;">*</span></label>
                    <div class="input-field">
                        <i class="fas fa-lock"></i>
                        <input
                                type="password"
                                id="password"
                                name="password"
                                placeholder="Mật khẩu (tối thiểu 6 ký tự)"
                                required>
                    </div>
                </div>

                <div class="input-group">
                    <label for="confirmPassword">Xác nhận mật khẩu <span style="color: red;">*</span></label>
                    <div class="input-field">
                        <i class="fas fa-lock"></i>
                        <input
                                type="password"
                                id="confirmPassword"
                                name="confirmPassword"
                                placeholder="Nhập lại mật khẩu"
                                required>
                    </div>
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
    document.querySelector('.login-form').addEventListener('submit', function(e) {
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;

        if (password !== confirmPassword) {
            e.preventDefault();
            alert('Mật khẩu xác nhận không khớp!');
            document.getElementById('confirmPassword').focus();
        }
    });
</script>

</body>
</html>