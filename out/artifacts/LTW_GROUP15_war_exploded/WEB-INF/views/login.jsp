<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập - Group15</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/all.min.css">

</head>

<body>
<div class="login-page-wrapper">
    <div class="modal-content">
        <a href="${pageContext.request.contextPath}/" class="modal-close-btn" aria-label="Đóng">&times;</a>

        <div class="modal-form">
            <h3>Đăng nhập</h3>

            <form method="post" action="login">
                <div class="form-group">
                    <label for="login-email">Gmail/SĐT</label>
                    <input
                            type="text"
                            id="login-email"
                            name="username"
                            placeholder="Nhập gmail hoặc SĐT của bạn"
                            value="<%= request.getAttribute("username") != null ? request.getAttribute("username") : "" %>"
                            required
                            autofocus>
                </div>

                <div class="form-group">
                    <label for="login-pass">Mật khẩu</label>
                    <input
                            type="password"
                            id="login-pass"
                            name="password"
                            placeholder="Nhập mật khẩu"
                            required>
                </div>

                <p class="error-msg <%= request.getAttribute("errorMessage") != null ? "show" : "" %>">
                    <%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "Hãy nhập tên đăng nhập/mật khẩu" %>
                </p>

                <button type="submit" class="form-submit-btn">Đăng nhập</button>

                <div class="form-link-row">
                    <a href="${pageContext.request.contextPath}/forgot-password" class="form-toggle-link">Quên mật khẩu?</a>
                    <a href="${pageContext.request.contextPath}/register" class="form-toggle-link">Đăng ký</a>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>