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

<footer class="footer">
    <div class="container">
        <div class="footer-main">
            <div class="footer-col about">
                <div class="logo">
                    <img src="${pageContext.request.contextPath}/assets/images/logo.jpg" alt="logo">
                </div>
                <p>Chúng tôi giúp bạn tìm và đặt chỗ ở hoàn hảo - từ nhà khách ấm cúng đến khách sạn hạng đầu - một cách dễ dàng, tin cậy với ưu đãi tốt nhất.</p>
                <h3>Tải Ứng Dụng Của Chúng Tôi</h3>
                <div class="app-buttons">
                    <a href="#"><img src="https://placehold.co/135x40/4A4A4A/FFFFFF?text=App+Store" alt="App Store"></a>
                    <a href="#"><img src="https://placehold.co/135x40/4A4A4A/FFFFFF?text=Google+Play" alt="Google Play"></a>
                </div>
            </div>
            <div class="footer-col links">
                <h3>Khám phá</h3>
                <ul>
                    <li><a href="#">Điểm đến nổi bật</a></li>
                    <li><a href="#">Điểm nóng mùa hè</a></li>
                    <li><a href="#">Chọn nghỉ mùa đông</a></li>
                    <li><a href="#">Ưu đãi cuối tuần</a></li>
                    <li><a href="#">Chỗ ở cho gia đình</a></li>
                </ul>
            </div>
            <div class="footer-col links">
                <h3>Loại hình chỗ ở</h3>
                <ul>
                    <li><a href="#">Khách sạn</a></li>
                    <li><a href="#">Căn hộ</a></li>
                    <li><a href="#">Biệt thự</a></li>
                    <li><a href="#">Cabin</a></li>
                    <li><a href="#">Cắm trại cao cấp</a></li>
                    <li><a href="#">Nhà vòm</a></li>
                </ul>
            </div>
            <div class="footer-col links">
                <h3>Hỗ trợ</h3>
                <ul>
                    <li><a href="#">Trung tâm trợ giúp</a></li>
                    <li><a href="#">Hỗ trợ trò chuyện trực tiếp</a></li>
                    <li><a href="#">Câu hỏi thường gặp</a></li>
                    <li><a href="#">Liên hệ</a></li>
                </ul>
            </div>
            <div class="footer-col contact">
                <h3>Liên lạc</h3>
                <p>+1 (800) 123 456</p>
                <p>support@group15.com</p>
                <div class="social-icons">
                    <a href="#" aria-label="Facebook"><i class="fab fa-facebook-f"></i></a>
                    <a href="#" aria-label="Twitter"><i class="fab fa-twitter"></i></a>
                    <a href="#" aria-label="Instagram"><i class="fab fa-instagram"></i></a>
                    <a href="#" aria-label="LinkedIn"><i class="fab fa-linkedin-in"></i></a>
                </div>
            </div>
        </div>
        <hr class="footer-divider">
        <div class="footer-bottom">
            <p class="copyright">© 2025 Group15. Đã đăng ký bản quyền.</p>
            <div class="payment-icons">
                <img src="https://placehold.co/40x25/FFFFFF/000000?text=Visa" alt="Visa">
                <img src="https://placehold.co/40x25/FFFFFF/000000?text=MC" alt="Mastercard">
                <img src="https://placehold.co/40x25/FFFFFF/000000?text=AE" alt="American Express">
                <img src="https://placehold.co/40x25/FFFFFF/000000?text=Disc" alt="Discover">
                <img src="https://placehold.co/40x25/FFFFFF/000000?text=GPay" alt="Google Pay">
                <img src="https://placehold.co/40x25/FFFFFF/000000?text=APay" alt="Apple Pay">
            </div>
        </div>
    </div>
</footer>
</body>
</html>