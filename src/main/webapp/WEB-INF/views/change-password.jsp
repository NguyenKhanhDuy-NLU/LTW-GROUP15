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
  <title>Đổi mật khẩu - Group15</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/udPassw.css">
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
        <a href="${pageContext.request.contextPath}/user" class="nav-user-icon">
          <i class="fas fa-user"></i>
        </a>
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
            <li><a href="${pageContext.request.contextPath}/bookings"><i class="fas fa-history"></i> Lịch sử đặt phòng</a></li>
            <li><a href="${pageContext.request.contextPath}/change-password" class="active"><i class="fas fa-lock"></i> Đổi mật khẩu</a></li>
            <li><a href="${pageContext.request.contextPath}/logout" class="logout"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a></li>
          </ul>
        </nav>
      </aside>

      <section class="user-content">
        <div class="content-title">
          <h2>Đổi Mật Khẩu</h2>
          <p>Để bảo mật tài khoản, vui lòng không chia sẻ mật khẩu cho người khác</p>
        </div>

        <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="alert-error" style="background-color: #ffebee; border: 1px solid #ffcdd2; color: #d32f2f; padding: 10px 15px; border-radius: 6px; margin-bottom: 20px; display: flex; align-items: center; gap: 8px;">
          <i class="fas fa-exclamation-circle"></i>
          <span><%= request.getAttribute("errorMessage") %></span>
        </div>
        <% } %>

        <form class="user-form" action="${pageContext.request.contextPath}/change-password" method="POST">
          <div class="form-layout-grid" style="grid-template-columns: 1fr; max-width: 600px;">
            <div class="form-group">
              <label>Mật khẩu hiện tại</label>
              <input type="password" name="currentPassword" placeholder="Nhập mật khẩu hiện tại" required>
            </div>
            <div class="form-group">
              <label>Mật khẩu mới</label>
              <input type="password" name="newPassword" id="newPassword" placeholder="Nhập mật khẩu mới (tối thiểu 6 ký tự)" required>
            </div>
            <div class="form-group">
              <label>Xác nhận mật khẩu mới</label>
              <input type="password" name="confirmPassword" id="confirmPassword" placeholder="Nhập lại mật khẩu mới" required>
            </div>
          </div>

          <div class="form-actions">
            <a href="${pageContext.request.contextPath}/user" class="u-btn u-btn-secondary">Hủy bỏ</a>
            <button type="submit" class="u-btn u-btn-success">Xác nhận đổi</button>
          </div>
        </form>
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

<script>
  document.querySelector('.user-form').addEventListener('submit', function(e) {
    const newPassword = document.getElementById('newPassword').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    if (newPassword !== confirmPassword) {
      e.preventDefault();
      alert('Mật khẩu xác nhận không khớp!');
      return false;
    }

    if (newPassword.length < 6) {
      e.preventDefault();
      alert('Mật khẩu mới phải có ít nhất 6 ký tự!');
      return false;
    }
  });
</script>

</body>
</html>