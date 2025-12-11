<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập - Group15</title>
    <link rel="stylesheet" href="../../assets/css/all.min.css">

</head>
<body>

<div class="login-container">
    <div class="login-left">
        <h1>Chào mừng đến với Group15!</h1>
        <p>Đăng nhập để quản lý đặt phòng khách sạn của bạn. Truy cập vào hệ thống quản lý toàn diện với giao diện thân thiện và dễ sử dụng.</p>
    </div>

    <div class="login-right">
        <div class="login-header">
            <h2>Đăng Nhập</h2>
            <p>Vui lòng nhập thông tin đăng nhập</p>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">
                <i class="fas fa-exclamation-circle"></i> ${error}
            </div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="alert alert-success">
                <i class="fas fa-check-circle"></i> ${success}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/auth/login" method="post">
            <div class="form-group">
                <label for="username">Tên đăng nhập</label>
                <div class="input-wrapper">
                    <i class="fas fa-user"></i>
                    <input type="text" id="username" name="username" placeholder="Nhập tên đăng nhập" required>
                </div>
            </div>

            <div class="form-group">
                <label for="password">Mật khẩu</label>
                <div class="input-wrapper">
                    <i class="fas fa-lock"></i>
                    <input type="password" id="password" name="password" placeholder="Nhập mật khẩu" required>
                </div>
            </div>

            <div class="remember-forgot">
                <label class="remember-me">
                    <input type="checkbox" id="remember" name="remember">
                    <span>Ghi nhớ đăng nhập</span>
                </label>
                <a href="#" class="forgot-link">Quên mật khẩu?</a>
            </div>

            <button type="submit" class="login-btn">Đăng Nhập</button>

            <div class="divider">
                <span>HOẶC</span>
            </div>

            <p class="signup-link">
                Chưa có tài khoản? <a href="${pageContext.request.contextPath}/auth/register">Đăng ký ngay</a>
            </p>
        </form>
    </div>
</div>

</body>
</html>