<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>${hotel.hotelName}</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/hotel-detail.css">
</head>
<body>

<header>
    <div class="header">
        <div class="header-left">
            <img src="${pageContext.request.contextPath}/assets/images/logo.png" alt="logo GROUP15" class="logo"
                 onclick="window.location.href='${pageContext.request.contextPath}/index.html'">
        </div>
        <div class="header-right">
            <div class="country"><img src="${pageContext.request.contextPath}/assets/images/Anh_VN.webp" alt="Việt Nam"> VND</div>
            <div class="user"><img src="https://ui-avatars.com/api/?name=User" alt="user"></div>
        </div>
    </div>
</header>

<section class="hotel-header container">
    <div class="hotel-info">
        <h1>${hotel.hotelName}</h1>
        <div class="hotel-sub">
            <div class="stars">
                <c:forEach begin="1" end="${hotel.starRating}">★</c:forEach>
            </div>
            <p>${hotel.address}</p>
        </div>
    </div>
    <div class="hotel-actions">
        <button class="icon-btn"><i class="far fa-heart"></i></button>
        <button class="icon-btn"><i class="fas fa-share-alt"></i></button>
    </div>
</section>

<section class="gallery">
    <div class="gallery-main">
        <img src="${pageContext.request.contextPath}/assets/images/${hotel.mainImage}" alt="${hotel.hotelName}"
             onerror="this.src='https://placehold.co/800x600?text=No+Image'"/>
    </div>
    <div class="gallery-side">
        <c:forEach var="img" items="${hotel.galleryImages}" end="3">
            <img src="${pageContext.request.contextPath}/assets/images/${img.trim()}" alt="Gallery"
                 onerror="this.style.display='none'"/>
        </c:forEach>
    </div>
</section>

<section class="description container">
    <h2>Giới thiệu</h2>
    <p>${hotel.description}</p>
</section>

<section class="amenities container">
    <h2>Tiện nghi</h2>
    <ul>
        <c:forEach var="a" items="${hotel.amenities}">
            <li><i class="${a.amenityIcon}"></i> ${a.amenityName}</li>
        </c:forEach>
    </ul>
</section>

<section class="location container">
    <h2>Vị trí</h2>
    <img src="${pageContext.request.contextPath}/assets/images/${hotel.mapImage}"
         alt="Bản đồ" class="location-img"
         onerror="this.src='https://placehold.co/1100x400?text=Map'"/>
</section>

<section class="rooms container">
    <h2>Danh sách phòng</h2>

    <c:forEach var="room" items="${rooms}">
        <a href="${pageContext.request.contextPath}/room-detail?id=${room.id}" class="room-item">

            <img src="${pageContext.request.contextPath}/assets/images/${room.thumbnail}"
                 alt="${room.roomName}"
                 onerror="this.src='https://placehold.co/260x170?text=No+Image'"/>

            <div class="room-info">
                <div class="room-name">${room.roomName}</div>
                <div class="room-price">
                    <fmt:formatNumber value="${room.basePrice}" type="currency" currencySymbol="đ"/> / đêm
                </div>
            </div>
        </a>
    </c:forEach>
</section>

<section class="reviews container">
    <h2>Đánh giá của khách</h2>

    <div class="review-box">
        <div class="review-score">
            ${hotel.averageStar}
        </div>

        <div class="review-details">
            <div class="review-title">Tuyệt vời</div>
            <div class="review-count">Dựa trên <strong>${hotel.reviewCount}</strong> nhận xét xác thực</div>
        </div>
    </div>
</section>

<section class="policy container">
    <h2>Chính sách khách sạn</h2>
    <ul>
        <li><strong>Nhận phòng:</strong> ${hotel.checkInTime}</li>
        <li><strong>Trả phòng:</strong> ${hotel.checkOutTime}</li>
        <li><strong>Huỷ phòng:</strong> ${hotel.cancellationPolicy}</li>
        <li><strong>Trẻ em:</strong> ${hotel.childPolicy}</li>
        <li><strong>Khác:</strong> ${hotel.smokingPolicy}</li>
    </ul>
</section>

<footer class="footer">
    <div class="container">
        <div class="footer-main">
            <div class="footer-col about">
                <div class="logo">
                    <img src="${pageContext.request.contextPath}/assets/images/logo.png" alt="logo">
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
<script src="${pageContext.request.contextPath}/js/hotel-detail.js"></script>
</body>
</html>
