<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Đặt phòng thành công - ${hotel.hotelName}</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/all.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/booking-success.css">
</head>
<body>
<div class="main-container">
  <div id="success-message" class="success-message" style="display: none;">
    <i class="fas fa-check-circle"></i>
    <span>Hủy đặt phòng thành công! Tiền sẽ được hoàn trả sớm cho bạn.</span>
    <button onclick="closeSuccessMessage()" class="close-btn">×</button>
  </div>

  <div class="success-header">
    <i class="fas fa-check-circle success-icon"></i>
    <h1>Đặt phòng thành công!</h1>
    <p>
    <div class="sub-title">Chúc mừng bạn đã hoàn tất đặt phòng tại:</div>
    <div class="sub-item">${hotel.hotelName}</div><br>
    <div class="sub-title">Email xác nhận + hóa đơn đã gửi đến:</div>
    <div class="sub-item">${user.email}</div><br>
    </p>
    <div class="booking-code">
      <span>Mã đặt phòng của bạn</span>
      <strong id="bookingCode">${booking.bookingCode}</strong>
    </div>

    <div class="action-buttons">
      <a href="${pageContext.request.contextPath}/" class="btn-primary">
        <i class="fas fa-home"></i> Về trang chủ
      </a>
      <c:if test="${canCancel}">
        <button class="btn-cancel" onclick="openCancelModal()">
          <i class="fas fa-times-circle"></i> Hủy đặt phòng
        </button>
      </c:if>
    </div>
  </div>

  <div class="hotel-card">
    <div class="hotel-image">
      <img src="${pageContext.request.contextPath}/assets/images/${hotel.mainImage}"
           alt="${hotel.hotelName}">
    </div>
    <div class="hotel-details">
      <div class="hotel-header">${hotel.hotelName}</div>
      <div class="info-grid">
        <div class="info-item">
          <i class="fas fa-calendar-check"></i>
          <div>
            <strong>Nhận phòng</strong>
            <p>${checkInFormatted} từ ${hotel.checkInTime != null ? hotel.checkInTime : '14:00'}</p>
          </div>
        </div>
        <div class="info-item">
          <i class="fas fa-calendar-times"></i>
          <div>
            <strong>Trả phòng</strong>
            <p>${checkOutFormatted} trước ${hotel.checkOutTime != null ? hotel.checkOutTime : '12:00'}</p>
          </div>
        </div>
        <div class="info-item">
          <i class="fas fa-moon"></i>
          <div>
            <strong>Thời gian lưu trú</strong>
            <p>${nights} đêm · ${booking.guests} khách</p>
          </div>
        </div>
        <div class="info-item">
          <i class="fas fa-map-marker-alt"></i>
          <div>
            <strong>Địa chỉ</strong>
            <p>${hotel.address}</p>
          </div>
        </div>
      </div>
      <div class="payment-summary">
        <div class="summary-row">
          <span>Phòng & dịch vụ</span>
          <span><fmt:formatNumber value="${booking.totalPrice}" type="number" groupingUsed="true"/> VND</span>
        </div>
        <div class="summary-row total">
          <span>Tổng cộng</span>
          <strong class="price-final">
            <fmt:formatNumber value="${booking.totalPrice}" type="number" groupingUsed="true"/> VND
          </strong>
        </div>
        <div class="payment-method">
          <div>Phương thức thanh toán: </div>
          <div>${booking.paymentStatus != null ? booking.paymentStatus : 'Đã thanh toán'}</div>
        </div>
      </div>
    </div>
  </div>


  <c:if test="${canCancel}">
    <div id="cancel-modal" class="modal" style="display: none;">
      <div class="modal-content">
        <div class="modal-header">
          <h3>Hủy đặt phòng</h3>
          <span class="close" onclick="closeCancelModal()">×</span>
        </div>
        <div class="modal-body">
          <p><strong>Mã đặt phòng:</strong> <span id="booking-code-display">${booking.bookingCode}</span></p>
          <p>Vui lòng chọn lý do hủy:</p>

          <div class="reason-options">
            <label><input type="radio" name="reason" value="Thay đổi kế hoạch"> Thay đổi kế hoạch</label>
            <label><input type="radio" name="reason" value="Tìm được nơi tốt hơn"> Tìm được nơi tốt hơn</label>
            <label><input type="radio" name="reason" value="Lỗi đặt phòng"> Lỗi đặt phòng</label>
            <label><input type="radio" name="reason" value="Lý do khác" id="other-reason-radio"> Lý do khác</label>
          </div>

          <textarea id="other-reason-text"
                    placeholder="Vui lòng nhập lý do khác..."
                    style="display: none; width: 100%; margin-top: 10px; padding: 10px; border: 1px solid #ddd; border-radius: 8px;"
                    rows="3"></textarea>
        </div>
        <div class="modal-footer">
          <button class="btn-secondary" onclick="closeCancelModal()">Hủy bỏ</button>
          <button class="btn-danger" onclick="confirmCancel()">Xác nhận hủy</button>
        </div>
      </div>
    </div>
  </c:if>
</div>

<footer class="footer">
  <div class="container">
    <div class="footer-main">
      <div class="footer-col about">
        <div class="logo">
          <img src="${pageContext.request.contextPath}/assets/images/logo.jpg" alt="Logo">
        </div>
        <p>Chúng tôi giúp bạn tìm và đặt chỗ ở hoàn hảo - từ nhà khách ấm cúng đến khách sạn hàng đầu - một cách dễ dàng, tin cậy với ưu đãi tốt nhất.</p>
        <h3>Tải Ứng Dụng Của Chúng Tôi</h3>
        <div class="app-buttons">
          <a href="#"><img src="https://placehold.co/135x40/1A1A1A/FFFFFF?text=App+Store&font=roboto" alt="App Store"></a>
          <a href="#"><img src="https://placehold.co/135x40/1A1A1A/FFFFFF?text=Google+Play&font=roboto" alt="Google Play"></a>
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

<script>
  const bookingId = ${booking.id};
  const contextPath = '${pageContext.request.contextPath}';
</script>
<script src="${pageContext.request.contextPath}/assets/js/booking-success.js"></script>
</body>
</html>