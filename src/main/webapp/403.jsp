<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>403 - Truy cập bị từ chối</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            color: #333;
        }

        .error-container {
            background: white;
            padding: 60px 40px;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
            text-align: center;
            max-width: 600px;
            width: 90%;
        }

        .error-code {
            font-size: 120px;
            font-weight: bold;
            color: #667eea;
            margin-bottom: 20px;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
        }

        .error-icon {
            font-size: 80px;
            margin-bottom: 20px;
        }

        h1 {
            font-size: 32px;
            color: #333;
            margin-bottom: 15px;
        }

        p {
            font-size: 18px;
            color: #666;
            margin-bottom: 30px;
            line-height: 1.6;
        }

        .btn-container {
            display: flex;
            gap: 15px;
            justify-content: center;
            flex-wrap: wrap;
        }

        .btn {
            padding: 15px 30px;
            font-size: 16px;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            transition: all 0.3s ease;
            font-weight: 600;
        }

        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(102, 126, 234, 0.3);
        }

        .btn-secondary {
            background: #f0f0f0;
            color: #333;
        }

        .btn-secondary:hover {
            background: #e0e0e0;
            transform: translateY(-2px);
        }

        .info-box {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 10px;
            margin-top: 30px;
            text-align: left;
        }

        .info-box h3 {
            color: #667eea;
            margin-bottom: 10px;
            font-size: 18px;
        }

        .info-box ul {
            list-style-position: inside;
            color: #666;
        }

        .info-box li {
            margin: 8px 0;
        }
    </style>
</head>
<body>
<div class="error-container">
    <div class="error-icon">🚫</div>
    <div class="error-code">403</div>
    <h1>Truy cập bị từ chối!</h1>
    <p>Xin lỗi, bạn không có quyền truy cập vào trang này.<br>
        Trang này chỉ dành cho quản trị viên (Admin).</p>

    <div class="btn-container">
        <a href="${pageContext.request.contextPath}/" class="btn btn-primary">
            Về trang chủ
        </a>
        <a href="${pageContext.request.contextPath}/logout" class="btn btn-secondary">
            Đăng xuất
        </a>
    </div>

    <div class="info-box">
        <h3>💡 Thông tin:</h3>
        <ul>
            <li>Chỉ tài khoản Admin mới có thể truy cập trang này</li>
            <li>Nếu bạn là Admin, vui lòng đăng nhập lại</li>
            <li>Nếu cần hỗ trợ, vui lòng liên hệ quản trị viên</li>
        </ul>
    </div>
</div>
</body>
</html>
