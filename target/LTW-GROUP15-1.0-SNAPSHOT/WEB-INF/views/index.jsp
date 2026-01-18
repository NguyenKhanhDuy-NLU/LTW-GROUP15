<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Group15 - Đặt phòng khách sạn</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style_home.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="icon" href="${pageContext.request.contextPath}/assets/images/logo.jpg">
</head>
<body>

<header class="header">
    <div class="container">
        <nav class="navbar">
            <div class="logo">
                <a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/assets/images/logo.jpg" alt="logo GROUP15"></a>
            </div>
            <div class="nav-right">
                <div class="language-currency">
                    <img src="${pageContext.request.contextPath}/assets/images/Anh_VN.webp" alt="Việt Nam">
                    <span>VND</span>
                    <i class="fas fa-chevron-down"></i>
                </div>

                <% if (session.getAttribute("user") != null) { %>
                <!-- User logged in -->
                <div class="user-menu">
                    <a href="${pageContext.request.contextPath}/user" class="nav-user-icon">
                        <i class="fas fa-user"></i>
                    </a>
                    <div class="user-dropdown">
                        <a href="${pageContext.request.contextPath}/user"><i class="fas fa-user"></i> Hồ sơ</a>
                        <a href="${pageContext.request.contextPath}/bookings"><i class="fas fa-calendar-check"></i> Đặt phòng của tôi</a>
                        <a href="${pageContext.request.contextPath}/change-password"><i class="fas fa-lock"></i> Đổi mật khẩu</a>
                        <a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
                    </div>
                </div>
                <% } else { %>
                <!-- User not logged in -->
                <a href="${pageContext.request.contextPath}/login" class="nav-login-btn">Đăng nhập</a>
                <% } %>
            </div>
        </nav>
    </div>
</header>

<section class="hero">
    <div class="hero-background">
        <img src="${pageContext.request.contextPath}/assets/images/Hero%20Header.png" alt="ảnh bìa chính">
    </div>
    <div class="container hero-content">
        <h1>Tìm Khách Sạn Hoàn Hảo</h1>
        <p>Đặt phòng nhanh chóng, giá tốt nhất thị trường</p>

        <div class="search-container">
            <form class="search-form" action="${pageContext.request.contextPath}/assets/html/search.html" method="GET">
                <div class="form-group">
                    <label for="location"><i class="fas fa-map-marker-alt"></i> Địa điểm</label>
                    <input type="text" id="location" name="location" placeholder="Bạn muốn đến đâu?" required>
                </div>
                <div class="form-group">
                    <label for="checkin"><i class="fas fa-calendar-alt"></i> Ngày nhận phòng</label>
                    <input type="date" id="checkin" name="checkin" required>
                </div>
                <div class="form-group">
                    <label for="checkout"><i class="fas fa-calendar-alt"></i> Ngày trả phòng</label>
                    <input type="date" id="checkout" name="checkout" required>
                </div>
                <div class="form-group">
                    <label for="guests"><i class="fas fa-user"></i> Số khách</label>
                    <input type="number" id="guests" name="guests" placeholder="1" min="1" value="1" required>
                </div>
                <button type="submit" class="search-btn">
                    <i class="fas fa-search"></i> Tìm kiếm
                </button>
            </form>
        </div>
    </div>
</section>

<section class="features">
    <div class="container">
        <h2>Vì Sao Chọn GROUP15</h2>
        <div class="features-grid">
            <div class="feature-item">
                <div class="icon-wrapper blue">
                    <i class="fas fa-dollar-sign"></i>
                </div>
                <h3>Giá Tốt Nhất</h3>
                <p>Cam kết giá cả minh bạch, không phí ẩn</p>
            </div>
            <div class="feature-item">
                <div class="icon-wrapper blue">
                    <i class="fas fa-shield-alt"></i>
                </div>
                <h3>Đặt Phòng An Toàn</h3>
                <p>Bảo mật thông tin và thanh toán</p>
            </div>
            <div class="feature-item">
                <div class="icon-wrapper blue">
                    <i class="fas fa-headset"></i>
                </div>
                <h3>Hỗ Trợ 24/7</h3>
                <p>Đội ngũ chăm sóc khách hàng luôn sẵn sàng</p>
            </div>
        </div>
    </div>
</section>

<section class="content-section" style="background-color: #F9FAFB;">
    <div class="container">
        <h2>Khám Phá Việt Nam</h2>
        <div class="sights-grid">
            <a href="${pageContext.request.contextPath}/assets/html/hotel-detail11.html" class="sight-card-link span-3">
                <div class="sight-card">
                    <img src="${pageContext.request.contextPath}/assets/images/img5.png" alt="Vịnh Hạ Long">
                    <div class="sight-content">
                        <h3>Vịnh Hạ Long</h3>
                    </div>
                </div>
            </a>

            <a href="${pageContext.request.contextPath}/assets/html/hotel-detail12.html" class="sight-card-link span-3">
                <div class="sight-card">
                    <img src="${pageContext.request.contextPath}/assets/images/img6.png" alt="Hội An">
                    <div class="sight-content">
                        <h3>Hội An</h3>
                    </div>
                </div>
            </a>

            <a href="${pageContext.request.contextPath}/assets/html/hotel-detail13.html" class="sight-card-link span-2">
                <div class="sight-card">
                    <img src="${pageContext.request.contextPath}/assets/images/img7.png" alt="Nha Trang">
                    <div class="sight-content">
                        <h3>Nha Trang</h3>
                    </div>
                </div>
            </a>

            <a href="${pageContext.request.contextPath}/assets/html/hotel-detail14.html" class="sight-card-link span-2">
                <div class="sight-card">
                    <img src="${pageContext.request.contextPath}/assets/images/img8.png" alt="Phú Quốc">
                    <div class="sight-content">
                        <h3>Phú Quốc</h3>
                    </div>
                </div>
            </a>

            <a href="${pageContext.request.contextPath}/assets/html/hotel-detail16.html" class="sight-card-link span-2">
                <div class="sight-card">
                    <img src="${pageContext.request.contextPath}/assets/images/img9.png" alt="Huế">
                    <div class="sight-content">
                        <h3>Huế</h3>
                    </div>
                </div>
            </a>
        </div>
    </div>
</section>

<footer class="footer">
    <div class="container">
        <div class="footer-main">
            <div class="footer-col about">
                <div class="logo">
                    <img src="${pageContext.request.contextPath}/assets/images/logo.jpg" alt="logo">
                </div>
                <p>Nền tảng đặt phòng khách sạn trực tuyến hàng đầu Việt Nam. Giúp bạn tìm kiếm và đặt chỗ ở hoàn hảo với giá tốt nhất.</p>
                <h3>Tải Ứng Dụng</h3>
                <div class="app-buttons">
                    <a href="#"><img src="https://placehold.co/135x40/4A4A4A/FFFFFF?text=App+Store" alt="App Store"></a>
                    <a href="#"><img src="https://placehold.co/135x40/4A4A4A/FFFFFF?text=Google+Play" alt="Google Play"></a>
                </div>
            </div>

            <div class="footer-col links">
                <h3>Về Chúng Tôi</h3>
                <ul>
                    <li><a href="#">Giới thiệu</a></li>
                    <li><a href="#">Tuyển dụng</a></li>
                    <li><a href="#">Đối tác</a></li>
                    <li><a href="#">Tin tức</a></li>
                </ul>
            </div>

            <div class="footer-col links">
                <h3>Điều Khoản</h3>
                <ul>
                    <li><a href="#">Chính sách bảo mật</a></li>
                    <li><a href="#">Điều khoản sử dụng</a></li>
                    <li><a href="#">Quy định thanh toán</a></li>
                    <li><a href="#">Chính sách hủy phòng</a></li>
                </ul>
            </div>

            <div class="footer-col links">
                <h3>Hỗ Trợ</h3>
                <ul>
                    <li><a href="#">Trung tâm trợ giúp</a></li>
                    <li><a href="#">Câu hỏi thường gặp</a></li>
                    <li><a href="#">Liên hệ</a></li>
                    <li><a href="#">Đánh giá</a></li>
                </ul>
            </div>

            <div class="footer-col contact">
                <h3>Liên Hệ</h3>
                <p><i class="fas fa-phone"></i> +84 123 456 789</p>
                <p><i class="fas fa-envelope"></i> support@group15.com</p>
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
            <p class="copyright">© 2025 Group15. All rights reserved.</p>
            <div class="payment-icons">
                <img src="https://placehold.co/40x25/FFFFFF/000000?text=Visa" alt="Visa">
                <img src="https://placehold.co/40x25/FFFFFF/000000?text=MC" alt="Mastercard">
                <img src="https://placehold.co/40x25/FFFFFF/000000?text=AE" alt="American Express">
                <img src="https://placehold.co/40x25/FFFFFF/000000?text=Momo" alt="Momo">
                <img src="https://placehold.co/40x25/FFFFFF/000000?text=ZaloPay" alt="ZaloPay">
            </div>
        </div>
    </div>
</footer>

<script>
    // Set min date for check-in (today)
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('checkin').setAttribute('min', today);

    // Update checkout min date when checkin changes
    document.getElementById('checkin').addEventListener('change', function() {
        const checkinDate = new Date(this.value);
        checkinDate.setDate(checkinDate.getDate() + 1);
        const minCheckout = checkinDate.toISOString().split('T')[0];
        document.getElementById('checkout').setAttribute('min', minCheckout);
    });
</script>

</body>
</html>