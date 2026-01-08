<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xác thực email - Group15</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/verification-sent.css">

    <link rel="icon" href="${pageContext.request.contextPath}/assets/images/logo.jpg">

</head>
<body>

<div class="login-container">
    <div class="login-wrapper" style="max-width: 600px;">
        <a href="${pageContext.request.contextPath}/" class="close-btn" title="Về trang chủ">
            <i class="fas fa-times"></i>
        </a>

        <div class="verification-box">
            <div class="success-icon">
                <i class="fas fa-envelope-circle-check"></i>
            </div>

            <h1>Kiểm tra email của bạn!</h1>
            <p>Chúng tôi đã gửi email xác thực đến:</p>

            <div class="email-display">
                <i class="fas fa-envelope"></i>
                <%= session.getAttribute("registeredEmail") != null ? session.getAttribute("registeredEmail") : "your-email@example.com" %>
            </div>

            <div class="info-box">
                <p><i class="fas fa-info-circle"></i> <strong>Vui lòng kiểm tra hộp thư và làm theo hướng dẫn để xác thực tài khoản.</strong></p>
                <p><i class="fas fa-clock"></i> Link xác thực có hiệu lực trong <strong>24 giờ</strong>.</p>
                <p><i class="fas fa-folder"></i> Nếu không thấy email, vui lòng kiểm tra trong thư mục <strong>Spam</strong> hoặc <strong>Junk</strong>.</p>
            </div>

            <div class="action-buttons">
                <a href="${pageContext.request.contextPath}/resend-verification" class="btn btn-secondary">
                    <i class="fas fa-paper-plane"></i> Gửi lại email
                </a>
                <a href="${pageContext.request.contextPath}/login" class="btn btn-primary">
                    <i class="fas fa-arrow-right"></i> Đến trang đăng nhập
                </a>
            </div>
        </div>
    </div>
</div>

</body>
</html>