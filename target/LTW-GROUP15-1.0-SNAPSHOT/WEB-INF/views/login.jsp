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
<style>
    body {
        margin: 0;
        padding: 0;
        font-family: 'Roboto', sans-serif;
        background-color: #F9FAFB;
    }

    .login-page-wrapper {
        min-height: 100vh;
        display: flex;
        align-items: center;
        justify-content: center;
        padding: 20px;
        background: linear-gradient(135deg, rgba(102, 126, 234, 0.1) 0%, rgba(118, 75, 162, 0.1) 100%);
    }

    .modal-content {
        background-color: var(--background-color);
        padding: 28px 32px;
        border-radius: var(--border-radius);
        box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
        max-width: 400px;
        width: 90%;
        position: relative;
        box-sizing: border-box;
    }

    .modal-close-btn {
        position: absolute;
        top: 10px;
        right: 16px;
        background: none;
        border: none;
        font-size: 1.8rem;
        color: var(--text-light);
        cursor: pointer;
        line-height: 1;
        transition: color 0.2s ease, transform 0.2s ease;
        width: 32px;
        height: 32px;
        display: flex;
        align-items: center;
        justify-content: center;
        border-radius: 50%;
        text-decoration: none;
    }

    .modal-close-btn:hover {
        color: var(--dark-blue);
        background-color: #F3F4F6;
        transform: rotate(90deg);
    }

    .modal-form h3 {
        text-align: center;
        color: var(--dark-blue);
        margin-top: 0;
        margin-bottom: 12px;
        font-size: 1.8rem;
        font-weight: 700;
        position: relative;
        padding-bottom: 12px;
    }

    .modal-form h3::after {
        content: '';
        position: absolute;
        bottom: 0;
        left: 50%;
        transform: translateX(-50%);
        width: 60px;
        height: 3px;
        background: linear-gradient(90deg, var(--primary-color), #0048CC);
        border-radius: 2px;
    }

    .form-group {
        display: flex;
        flex-direction: column;
        gap: 6px;
        margin-bottom: 16px;
    }

    .form-group label {
        font-size: 0.9rem;
        font-weight: 700;
        color: var(--dark-blue);
        margin: 0;
    }

    .form-group input {
        width: 100%;
        padding: 12px 14px;
        border: 1px solid var(--border-color);
        border-radius: 8px;
        font-size: 0.95rem;
        box-sizing: border-box;
        transition: all 0.3s ease;
    }

    .form-group input::placeholder {
        color: var(--text-light);
    }

    .form-group input:focus {
        outline: none;
        border-color: var(--primary-color);
        box-shadow: 0 0 0 2px rgba(0, 87, 255, 0.2);
    }

    .error-msg {
        color: red;
        font-size: 0.9rem;
        margin-top: 5px;
        margin-bottom: 10px;
        text-align: center;
        display: none;
    }

    .error-msg.show {
        display: block;
    }

    .form-submit-btn {
        background-color: var(--primary-color);
        color: var(--background-color);
        border: none;
        border-radius: 8px;
        padding: 14px;
        font-size: 1rem;
        font-weight: 700;
        cursor: pointer;
        transition: all 0.3s ease;
        margin-top: 8px;
        box-shadow: 0 2px 8px rgba(0, 87, 255, 0.2);
        width: 100%;
    }

    .form-submit-btn:hover {
        background-color: #0048CC;
        transform: translateY(-2px);
        box-shadow: 0 4px 12px rgba(0, 87, 255, 0.3);
    }

    .form-submit-btn:active {
        transform: translateY(0);
        box-shadow: 0 2px 6px rgba(0, 87, 255, 0.2);
    }

    .form-link-row {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-top: 16px;
    }

    .form-toggle-link {
        font-size: 0.85rem;
        color: var(--primary-color);
        text-decoration: none;
        font-weight: 500;
        cursor: pointer;
    }

    .form-toggle-link:hover {
        text-decoration: underline;
    }

</style>
<div class="login-page-wrapper">
    <div class="modal-content">
        <a href="${pageContext.request.contextPath}/" class="modal-close-btn" aria-label="Đóng">&times;</a>

        <div class="modal-form">
            <h3>Đăng nhập</h3>

            <form method="post" action="${pageContext.request.contextPath}/login">
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