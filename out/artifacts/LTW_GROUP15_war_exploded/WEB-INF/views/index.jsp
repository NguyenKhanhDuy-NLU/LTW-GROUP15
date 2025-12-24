<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Object user = session.getAttribute("user");
    boolean isLoggedIn = (user != null);
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Group15 - Đặt phòng khách sạn</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style_home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/all.min.css">
</head>
<body>

<header class="header">
    <div class="container">
        <nav class="navbar">
            <div class="logo">
                <a href="#"><img src="${pageContext.request.contextPath}/assets/images/logo.jpg" alt="logo GROUP15"></a>
            </div>
            <div class="nav-right">
                <div class="language-currency">
                    <img src="${pageContext.request.contextPath}/assets/images/Anh_VN.webp" alt="Việt Nam">
                    <span>VND</span>
                    <i class="fas fa-chevron-down"></i>
                </div>

                <% if (isLoggedIn) { %>
                <a href="${pageContext.request.contextPath}/assets/html/user.html" class="nav-user-icon">
                    <i class="fas fa-user"></i>
                </a>
                <% } else { %>
                <a href="#" class="nav-login-btn">Đăng nhập</a>
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
        <h1>Chuyến Đi Của Bạn Bắt Đầu Từ Đây</h1>
        <p>Tìm kiếm chỗ ở độc đáo từ khách sạn, biệt thự, và hơn thế nữa.</p>

        <div class="search-container">
            <div class="search-tabs">
                <button class="tab-btn active"><i class="fas fa-hotel"></i> Khách sạn</button>
                <button class="tab-btn"><i class="fas fa-home"></i> Căn hộ</button>
                <button class="tab-btn"><i class="fas fa-user-friends"></i> Nhà khách</button>
                <button class="tab-btn"><i class="fas fa-campground"></i> Cabin</button>
                <button class="tab-btn"><i class="fas fa-tent"></i> Cắm trại</button>
                <button class="tab-btn"><i class="fas fa-water"></i> Resort</button>
            </div>

            <form class="search-form" action="${pageContext.request.contextPath}/assets/html/search.html" method="GET">
                <div class="form-group">
                    <label for="location">Địa điểm</label>
                    <input type="text" id="location" name="location" placeholder="Bạn muốn đến đâu?">
                </div>
                <div class="form-group">
                    <label for="checkin">Ngày nhận</label>
                    <input type="text" id="checkin" name="checkin" placeholder="Thêm ngày">
                </div>
                <div class="form-group">
                    <label for="checkout">Ngày trả</label>
                    <input type="text" id="checkout" name="checkout" placeholder="Thêm ngày">
                </div>
                <div class="form-group">
                    <label for="guests">Phòng và khách</label>
                    <input type="text" id="guests" name="guests" placeholder="1 phòng, 1 người lớn, 2 trẻ em">
                </div>
                <button type="submit" class="search-btn"><i class="fas fa-search"></i> Tìm kiếm</button>
            </form>
        </div>
    </div>
</section>

<section class="features">
    <div class="container">
        <h2>Vì Sao Khách Hàng Tin Tưởng GROUP15</h2>
        <div class="features-grid">
            <div class="feature-item">
                <div class="icon-wrapper blue">
                    <i class="fas fa-dollar-sign"></i>
                </div>
                <h3>Không có phí ẩn</h3>
                <p>Giá cả minh bạch, không có chi phí ẩn.</p>
            </div>
            <div class="feature-item">
                <div class="icon-wrapper blue">
                    <i class="fas fa-shield-alt"></i>
                </div>
                <h3>Đặt phòng tức thì</h3>
                <p>Nhận xác nhận ngay sau khi bạn đặt.</p>
            </div>
            <div class="feature-item">
                <div class="icon-wrapper blue">
                    <i class="fas fa-file-alt"></i>
                </div>
                <h3>Linh hoạt</h3>
                <p>Nhiều lựa chọn linh hoạt với chính sách hủy phòng miễn phí.</p>
            </div>
        </div>
    </div>
</section>

<section class="destinations">
    <div class="container">
        <h2>Các Điểm Đến Nổi Bật</h2>
        <div class="destination-filters">
            <button class="filter-btn active"><i class="fas fa-leaf"></i> Lựa chọn mùa xuân</button>
            <button class="filter-btn"><i class="fas fa-sun"></i> Điểm nóng mùa hè</button>
            <button class="filter-btn"><i class="fas fa-canadian-maple-leaf"></i> Khám phá mùa thu</button>
            <button class="filter-btn"><i class="fas fa-snowflake"></i> Chốn nghỉ mùa đông</button>
        </div>
        <div class="destination-grid">

            <a href="${pageContext.request.contextPath}/assets/html/hotel-detail1.html" class="destination-card-link">
                <div class="destination-card">
                    <img src="${pageContext.request.contextPath}/assets/images/img1.png" alt="Hà Nội">
                    <div class="card-content">
                        <h3>Hà Nội</h3>
                        <p class="price">Từ <span>2,000,000/đêm</span></p>
                        <p class="description">Lãng mạn, nghệ thuật, và cà phê nằm ngay trung tâm thủ đô.</p>
                    </div>
                </div>
            </a>

            <a href="${pageContext.request.contextPath}/assets/html/hotel-detail11.html" class="destination-card-link">
                <div class="destination-card">
                    <img src="${pageContext.request.contextPath}/assets/images/img2.png" alt="Vịnh Hạ Long">
                    <div class="card-content">
                        <h3>Vịnh Hạ Long</h3>
                        <p class="price">Từ <span>2,500,000/đêm</span></p>
                        <p class="description">Tầm nhìn ra biển, đại dương, và sự yên bình.</p>
                    </div>
                </div>
            </a>

            <a href="${pageContext.request.contextPath}/assets/html/hotel-detail13.html" class="destination-card-link">
                <div class="destination-card">
                    <img src="${pageContext.request.contextPath}/assets/images/img3.png" alt="Nha Trang">
                    <div class="card-content">
                        <h3>Nha Trang</h3>
                        <p class="price">Từ <span>500,000/đêm</span></p>
                        <p class="description">Biệt thự, thiên nhiên, và không khí an nhiên.</p>
                    </div>
                </div>
            </a>

            <a href="${pageContext.request.contextPath}/assets/html/hotel-detail16.html" class="destination-card-link">
                <div class="destination-card">
                    <img src="${pageContext.request.contextPath}/assets/images/img4.png" alt="Huế">
                    <div class="card-content">
                        <h3>Huế</h3>
                        <p class="price">Từ <span>900,000/đêm</span></p>
                        <p class="description">Phố cổ Hội An và nhiều khu văn hóa đa dạng khác.</p>
                    </div>
                </div>
            </a>
        </div>
    </div>
</section>

<section class="content-section">
    <div class="container">
        <h2>Du lịch nhiều hơn, chi tiêu ít hơn</h2>
        <div class="promo-grid">
            <div class="feature-card">
                <h3>Giảm giá 10% khi lưu trú</h3>
                <p>Tận hưởng giảm giá tại các cơ sở lưu trú tham gia trên toàn thế giới.</p>
            </div>
            <div class="feature-card">
                <h3>Du lịch trái mùa</h3>
                <p>Tránh giờ cao điểm và tận hưởng mức giá thấp hơn, ít đông đúc hơn.</p>
            </div>
            <div class="feature-card">
                <h3>Ưu đãi độc quyền</h3>
                <p>Tận hưởng giảm giá tại các cơ sở lưu trú tham gia trên toàn thế giới.</p>
            </div>
            <div class="feature-card">
                <h3>Đặc biệt cuối tuần</h3>
                <p>Tận hưởng giảm giá 12% cho các kỳ nghỉ cuối tuần.</p>
            </div>
        </div>
    </div>
</section>

<section class="content-section" style="background-color: #F9FAFB;">
    <div class="container">
        <h2>Những Điểm Tham Quan Hàng Đầu</h2>
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
                <p>Chúng tôi giúp bạn tìm và đặt chỗ ở hoàn hảo - từ nhà khách ấm cúng đến khách sạn hàng đầu - một cách dễ dàng, tin cậy với ưu đãi tốt nhất.</p>
                <h3>Tải Ứng Dụng Của Chúng Tôi</h3>
                <div class="app-buttons">
                    <a href="#"><img src="https://placehold.co/135x40/4A4A4A/FFFFFF?text=App+Store" alt="App Store"></a>
                    <a href="#"><img src="https://placehold.co/135x40/4A4A4A/FFFFFF?text=Google+Play" alt="Google Play"></a>
                </div>
            </div>

            <div class="footer-col links">
                <h3>Khám phá</h3>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/assets/html/hotel-detail1.html">Điểm đến nổi bật</a></li>
                    <li><a href="#">Điểm nóng mùa hè</a></li>
                    <li><a href="#">Chốn nghỉ mùa đông</a></li>
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

<script src="${pageContext.request.contextPath}/assets/js/home_js.js"></script>
</body>
</html>