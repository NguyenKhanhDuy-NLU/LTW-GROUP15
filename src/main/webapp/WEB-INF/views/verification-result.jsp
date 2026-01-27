<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kết quả xác thực - Group15</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/verification-result.css">
    <link rel="icon" href="${pageContext.request.contextPath}/assets/images/logo.jpg">
    
</head>
<body>

<div class="login-container">
    <div class="login-wrapper" style="max-width: 600px;">
        <a href="${pageContext.request.contextPath}/" class="close-btn" title="Về trang chủ">
            <i class="fas fa-times"></i>
        </a>

        <div class="result-box">
            <% if (request.getAttribute("successMessage") != null) { %>
            <div class="result-icon success">
                <i class="fas fa-check-circle"></i>
            </div>
            <h1>Xác thực thành công!</h1>
            <p><%= request.getAttribute("successMessage") %></p>
            <% } else { %>
            <div class="result-icon error">
                <i class="fas fa-times-circle"></i>
            </div>
            <h1>Xác thực thất bại</h1>
            <p><%= request.getAttribute("errorMessage") %></p>
            <% } %>

            <div class="action-buttons">
                <% if (request.getAttribute("showLoginLink") != null) { %>
                <a href="${pageContext.request.contextPath}/login" class="btn btn-primary">
                    <i class="fas fa-sign-in-alt"></i> Đăng nhập ngay
                </a>
                <% } %>

                <% if (request.getAttribute("showResendLink") != null) { %>
                <a href="${pageContext.request.contextPath}/resend-verification" class="btn btn-secondary">
                    <i class="fas fa-paper-plane"></i> Gửi lại email xác thực
                </a>
                <% } %>

                <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">
                    <i class="fas fa-home"></i> Về trang chủ
                </a>
            </div>
        </div>
    </div>
</div>

</body>
</html>