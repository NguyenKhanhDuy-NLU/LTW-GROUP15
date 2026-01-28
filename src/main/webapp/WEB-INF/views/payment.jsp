<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Thanh toán - ${hotel.hotelName}</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/payment.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>

<header class="site-header">
  <div class="container header-inner">
    <div class="brand">
      <img src="${pageContext.request.contextPath}/assets/images/logo.jpg"
           alt="Group15" class="logo-header"
           onclick="window.location.href='${pageContext.request.contextPath}/'">
    </div>
    <nav class="booking-steps">
      <div class="step done">
        <div class="circle">✓</div>
        <span>Chọn phòng</span>
      </div>
      <div class="line"></div>
      <div class="step active">
        <div class="circle">2</div>
        <span>Thanh toán</span>
      </div>
      <div class="line"></div>
      <div class="step">
        <div class="circle">3</div>
        <span>Hoàn tất</span>
      </div>
    </nav>
  </div>
</header>

<main class="container page">
  <section class="main-col">
    <div class="card payment-card">
      <h2 class="title">Thanh toán</h2>
      <p class="subtitle">Hãy hoàn tất thanh toán để giữ chỗ. Dữ liệu thẻ được mã hóa an toàn.</p>

      <div class="section">
        <h3 class="section-title">Chọn phương thức thanh toán</h3>
        <div class="pay-methods">
          <label class="pm">
            <input type="radio" name="paymentMethod" value="card" checked>
            <div class="pm-body">
              <div class="pm-left">
                <img src="${pageContext.request.contextPath}/assets/images/visa.png"
                     alt="Card" class="pm-icon">
                <span>Thẻ tín dụng / thẻ ghi nợ</span>
              </div>
            </div>
          </label>

          <label class="pm">
            <input type="radio" name="paymentMethod" value="bank">
            <div class="pm-body">
              <div class="pm-left">
                <img src="${pageContext.request.contextPath}/assets/images/atm.png"
                     alt="Bank" class="pm-icon">
                <span>Chuyển khoản ngân hàng</span>
              </div>
            </div>
          </label>

          <label class="pm">
            <input type="radio" name="paymentMethod" value="momo">
            <div class="pm-body">
              <div class="pm-left">
                <img src="${pageContext.request.contextPath}/assets/images/fpx.png"
                     alt="Ewallet" class="pm-icon">
                <span>Ví điện tử</span>
              </div>
            </div>
          </label>
        </div>
      </div>

      <form id="paymentForm" class="section">
        <input type="hidden" name="hotelId" value="${hotel.id}">
        <input type="hidden" name="roomId" value="${room.id}">
        <input type="hidden" name="checkin" value="${checkin}">
        <input type="hidden" name="checkout" value="${checkout}">
        <input type="hidden" name="guests" value="${guests}">
        <input type="hidden" name="roomType" value="${room.roomType}">
        <input type="hidden" name="totalPrice" value="${totalPrice}">

        <h3 class="section-title">Thông tin thẻ</h3>
        <div class="field-row">
          <label class="field">
            <span class="label required">Tên trên thẻ</span>
            <input type="text" id="cardname" name="cardName"
                   placeholder="NGUYEN VAN A" required>
          </label>
          <label class="field">
            <span class="label required">Số thẻ</span>
            <input type="text" id="cardnumber" name="cardNumber"
                   inputmode="numeric"
                   pattern="\d{13,19}"
                   maxlength="19"
                   placeholder="1234 5678 9012 3456" required>
          </label>
        </div>

        <div class="field-row">
          <label class="field small">
            <span class="label required">Ngày hết hạn</span>
            <input type="text" id="exp" name="expiry"
                   pattern="(0[1-9]|1[0-2])\/20[2-9][0-9]"
                   placeholder="MM/YYYY" required>
          </label>
          <label class="field small">
            <span class="label required">CVV</span>
            <input type="text" id="cvv" name="cvv"
                   inputmode="numeric"
                   pattern="\d{3,4}"
                   maxlength="4"
                   placeholder="123" required>
          </label>
          <label class="field small">
            <span class="label">Ghi chú</span>
            <input type="text" name="notes" placeholder="Ghi chú (tùy chọn)">
          </label>
        </div>

        <div class="footer-actions">
          <div class="terms">
            <label class="required">
              <input type="checkbox" id="terms" required>
              Tôi đồng ý với các <a href="#">điều khoản, chính sách</a> của khách sạn.
            </label>
          </div>
          <div class="pay-action">
            <button type="submit" class="btn-pay">Hoàn tất đặt phòng</button>
          </div>
        </div>
      </form>

      <div id="qr-atm" class="qr atm" style="display:none;">
        <img src="${pageContext.request.contextPath}/assets/images/QRATM.png" alt="QR ATM">
        <div class="qr-tt">Hãy quét mã này để thanh toán</div>
      </div>
      <div id="qr-momo" class="qr momo" style="display:none;">
        <img src="${pageContext.request.contextPath}/assets/images/QRmomo.png" alt="QR Momo">
        <div class="qr-tt">Hãy quét mã này để thanh toán</div>
      </div>
    </div>
  </section>

  <aside>
    <div class="card card-grid">
      <div class="card-imgg">
        <img src="${pageContext.request.contextPath}/assets/images/${hotel.mainImage}"
             alt="${hotel.hotelName}">
      </div>
      <div class="card-body">
        <div>
          <div class="card-title">
            <h4>${hotel.hotelName}</h4>
            <div class="stars">
              <c:forEach begin="1" end="5" var="i">
                <c:choose>
                  <c:when test="${i <= Math.round(hotel.averageStar)}">★</c:when>
                  <c:otherwise>☆</c:otherwise>
                </c:choose>
              </c:forEach>
            </div>
          </div>
          <div class="card-meta">
            <div class="meta-chip">
              <i class="fas fa-map-marker-alt"></i> ${hotel.address}
            </div>
            <div class="meta-chip">
              <i class="fas fa-bed"></i> ${room.roomType}
            </div>
            <div class="meta-chip">
              <i class="fas fa-users"></i> ${guests} khách
            </div>
            <div class="meta-chip">
              <i class="fas fa-moon"></i> ${nights} đêm
            </div>
          </div>
        </div>
        <div class="card-right-grid">
          <div class="price-box">
            <div class="price">${totalPriceFormatted}đ</div>
            <c:if test="${not empty oldPriceFormatted}">
              <div class="old-price">${oldPriceFormatted}đ</div>
            </c:if>
          </div>
        </div>
      </div>
    </div>
  </aside>
</main>

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

<script src="${pageContext.request.contextPath}/assets/js/payment.js"></script>
</body>
</html>
