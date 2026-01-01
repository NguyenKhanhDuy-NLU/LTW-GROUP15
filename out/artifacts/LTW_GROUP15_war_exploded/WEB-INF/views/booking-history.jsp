<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="vn.edu.nlu.fit.demo1.model.User" %>
<%
    User user = (User) session.getAttribute("user");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lịch sử đặt phòng - Group15</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style_home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/history.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
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
            </div>
        </nav>
    </div>
</header>

<main class="profile-main">
    <div class="container">
        <div class="profile-layout">
            <aside class="user-sidebar">
                <div class="sidebar-header">
                    <div class="sidebar-avatar">
                        <img src="https://ui-avatars.com/api/?name=<%= user.getFullName().substring(0,1) %>&background=4285F4&color=fff&size=128" alt="user">
                    </div>
                    <h3><%= user.getFullName() %></h3>
                    <p>Thành viên</p>
                </div>
                <nav class="sidebar-menu">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/user"><i class="fas fa-user"></i> Thông tin tài khoản</a></li>
                        <li><a href="${pageContext.request.contextPath}/bookings" class="active"><i class="fas fa-history"></i> Lịch sử đặt phòng</a></li>
                        <li><a href="${pageContext.request.contextPath}/change-password"><i class="fas fa-lock"></i> Đổi mật khẩu</a></li>
                        <li><a href="${pageContext.request.contextPath}/logout" class="logout"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a></li>
                    </ul>
                </nav>
            </aside>

            <section class="user-content">
                <div class="content-title">
                    <h2>Lịch sử đặt phòng</h2>
                    <p>Xem lại các chuyến đi và trạng thái đặt phòng của bạn</p>
                </div>

                <div class="history-list">

                    <!-- Booking Card 1 - Hoàn thành -->
                    <div class="booking-card">
                        <div class="booking-img">
                            <img src="${pageContext.request.contextPath}/assets/images/img1.png" alt="Hotel Image">
                        </div>
                        <div class="booking-info">
                            <div class="booking-header">
                                <h3>The Five Residences Hanoi</h3>
                                <span class="status-badge success">Hoàn thành</span>
                            </div>
                            <div class="booking-details">
                                <p><i class="fas fa-calendar-alt"></i> 15/11/2025 - 18/11/2025</p>
                                <p><i class="fas fa-map-marker-alt"></i> Quận Ba Đình, Hà Nội</p>
                                <p><i class="fas fa-bed"></i> 1 Phòng giường đôi</p>
                            </div>
                        </div>
                        <div class="booking-price">
                            <p class="total-price">4.500.000 VND</p>
                            <button class="btn-detail" onclick="alert('Chức năng đang phát triển')">Xem chi tiết</button>
                        </div>
                    </div>

                    <!-- Booking Card 2 - Sắp tới -->
                    <div class="booking-card">
                        <div class="booking-img">
                            <img src="${pageContext.request.contextPath}/assets/images/img6.png" alt="Hotel Image">
                        </div>
                        <div class="booking-info">
                            <div class="booking-header">
                                <h3>La Vela Saigon Hotel</h3>
                                <span class="status-badge pending">Sắp tới</span>
                            </div>
                            <div class="booking-details">
                                <p><i class="fas fa-calendar-alt"></i> 20/12/2025 - 22/12/2025</p>
                                <p><i class="fas fa-map-marker-alt"></i> Quận 3, TP.HCM</p>
                                <p><i class="fas fa-bed"></i> 1 Phòng VIP</p>
                            </div>
                        </div>
                        <div class="booking-price">
                            <p class="total-price">3.200.000 VND</p>
                            <button class="btn-detail" onclick="alert('Chức năng đang phát triển')">Xem chi tiết</button>
                        </div>
                    </div>

                    <!-- Booking Card 3 - Đã hủy -->
                    <div class="booking-card">
                        <div class="booking-img">
                            <img src="${pageContext.request.contextPath}/assets/images/img37.png" alt="Hotel Image">
                        </div>
                        <div class="booking-info">
                            <div class="booking-header">
                                <h3>JW Marriott Phú Quốc</h3>
                                <span class="status-badge cancel">Đã hủy</span>
                            </div>
                            <div class="booking-details">
                                <p><i class="fas fa-calendar-alt"></i> 01/10/2025 - 03/10/2025</p>
                                <p><i class="fas fa-map-marker-alt"></i> An Thới, Phú Quốc</p>
                                <p><i class="fas fa-bed"></i> 1 Biệt thự biển</p>
                            </div>
                        </div>
                        <div class="booking-price">
                            <p class="total-price">10.000.000 VND</p>
                            <button class="btn-rebook" onclick="alert('Chức năng đang phát triển')">Đặt lại</button>
                        </div>
                    </div>

                </div>
            </section>
        </div>
    </div>
</main>

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
                    <li><a href="#">Điểm đến nổi bật</a></li>
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

</body>
</html>