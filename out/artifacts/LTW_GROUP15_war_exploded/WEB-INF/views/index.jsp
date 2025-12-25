<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
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

                <div class="user-menu">
                    <a href="javascript:void(0)" class="nav-user-icon">
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
                <p>Chúng tôi giúp bạn tìm và đặt chỗ ở hoàn hảo.</p>
                <h3>Tải Ứng Dụng</h3>
                <div class="app-buttons">
                    <a href="#"><img src="https://placehold.co/135x40/4A4A4A/FFFFFF?text=App+Store" alt="App Store"></a>
                    <a href="#"><img src="https://placehold.co/135x40/4A4A4A/FFFFFF?text=Google+Play" alt="Google Play"></a>
                </div>
            </div>

            <div class="footer-col links">
                <h3>Khám phá</h3>
                <ul>
                    <li><a href="#">Điểm đến nổi bật</a></li>
                    <li><a href="#">Ưu đãi cuối tuần</a></li>
                </ul>
            </div>

            <div class="footer-col links">
                <h3>Hỗ trợ</h3>
                <ul>
                    <li><a href="#">Trung tâm trợ giúp</a></li>
                    <li><a href="#">Liên hệ</a></li>
                </ul>
            </div>

            <div class="footer-col contact">
                <h3>Liên lạc</h3>
                <p>support@group15.com</p>
                <div class="social-icons">
                    <a href="#"><i class="fab fa-facebook-f"></i></a>
                    <a href="#"><i class="fab fa-twitter"></i></a>
                </div>
            </div>
        </div>

        <hr class="footer-divider">

        <div class="footer-bottom">
            <p class="copyright">© 2025 Group15. Đã đăng ký bản quyền.</p>
        </div>
    </div>
</footer>

<script src="${pageContext.request.contextPath}/assets/js/home_js.js"></script>
</body>
</html>