<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="vn.edu.nlu.fit.demo1.model.User" %>
<%
    User user = (User) request.getAttribute("user");
    if (user == null) {
        user = (User) session.getAttribute("user");
    }
    String successMessage = (String) session.getAttribute("successMessage");
    if (successMessage != null) {
        session.removeAttribute("successMessage");
    }
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông tin tài khoản - Group15</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/user.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/all.min.css">
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
                        <li><a href="${pageContext.request.contextPath}/user" class="active"><i class="fas fa-user"></i> Thông tin tài khoản</a></li>
                        <li><a href="${pageContext.request.contextPath}/bookings"><i class="fas fa-history"></i> Lịch sử đặt phòng</a></li>
                        <li><a href="${pageContext.request.contextPath}/change-password"><i class="fas fa-lock"></i> Đổi mật khẩu</a></li>
                        <li><a href="${pageContext.request.contextPath}/logout" class="logout"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a></li>
                    </ul>
                </nav>
            </aside>

            <section class="user-content">
                <div class="content-title">
                    <h2>Hồ sơ của tôi</h2>
                    <p>Quản lý thông tin hồ sơ để bảo mật tài khoản</p>
                </div>

                <% if (successMessage != null) { %>
                <div class="alert-success" style="background-color: #d4edda; border: 1px solid #c3e6cb; color: #155724; padding: 10px 15px; border-radius: 6px; margin-bottom: 20px; display: flex; align-items: center; gap: 8px;">
                    <i class="fas fa-check-circle"></i>
                    <span><%= successMessage %></span>
                </div>
                <% } %>

                <% if (request.getAttribute("errorMessage") != null) { %>
                <div class="alert-error" style="background-color: #ffebee; border: 1px solid #ffcdd2; color: #d32f2f; padding: 10px 15px; border-radius: 6px; margin-bottom: 20px; display: flex; align-items: center; gap: 8px;">
                    <i class="fas fa-exclamation-circle"></i>
                    <span><%= request.getAttribute("errorMessage") %></span>
                </div>
                <% } %>

                <form class="user-form" id="info-form" action="${pageContext.request.contextPath}/user" method="POST">
                    <div class="form-avatar-upload">
                        <div class="avatar-edit-box">
                            <img src="https://ui-avatars.com/api/?name=<%= user.getFullName().substring(0,1) %>&background=4285F4&color=fff&size=128" alt="user">
                            <label for="file-upload" class="btn-upload-icon"><i class="fas fa-camera"></i></label>
                            <input type="file" id="file-upload" hidden disabled class="editable-file">
                        </div>
                    </div>

                    <div class="form-layout-grid">
                        <div class="form-group">
                            <label>Tên đăng nhập</label>
                            <input type="text" value="<%= user.getUsername() %>" disabled>
                        </div>
                        <div class="form-group">
                            <label>Họ và tên</label>
                            <input type="text" name="fullName" value="<%= user.getFullName() %>" disabled class="editable-input" required>
                        </div>
                        <div class="form-group">
                            <label>Email</label>
                            <input type="email" name="email" value="<%= user.getEmail() %>" disabled class="editable-input" required>
                        </div>
                        <div class="form-group">
                            <label>
                                Số điện thoại
                                <span id="phone-error" class="error-text" style="display: none;">(Chỉ được nhập số)</span>
                            </label>
                            <input type="text" id="phone" name="phone" value="<%= user.getPhone() != null ? user.getPhone() : "" %>" disabled class="editable-input">
                        </div>
                        <div class="form-group">
                            <label>Giới tính</label>
                            <div class="radio-options">
                                <label><input type="radio" name="gender" value="Nam" <%= "Nam".equals(user.getGender()) ? "checked" : "" %> disabled class="editable-input"> Nam</label>
                                <label><input type="radio" name="gender" value="Nữ" <%= "Nữ".equals(user.getGender()) ? "checked" : "" %> disabled class="editable-input"> Nữ</label>
                                <label><input type="radio" name="gender" value="Khác" <%= "Khác".equals(user.getGender()) ? "checked" : "" %> disabled class="editable-input"> Khác</label>
                            </div>
                        </div>
                        <div class="form-group full-width">
                            <label>Địa chỉ</label>
                            <input type="text" name="address" value="<%= user.getAddress() != null ? user.getAddress() : "" %>" disabled class="editable-input">
                        </div>
                    </div>

                    <div class="form-actions">
                        <button type="button" id="btn-edit" class="u-btn u-btn-primary">Sửa</button>
                        <button type="submit" id="btn-save" class="u-btn u-btn-success" disabled>Thay đổi</button>
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
    document.addEventListener('DOMContentLoaded', function() {
        const btnEdit = document.getElementById('btn-edit');
        const btnSave = document.getElementById('btn-save');
        const editableInputs = document.querySelectorAll('.editable-input');
        const fileInput = document.getElementById('file-upload');
        const phoneInput = document.getElementById('phone');
        const phoneError = document.getElementById('phone-error');

        let isPhoneValid = true;

        if (btnEdit && btnSave) {
            btnEdit.addEventListener('click', function() {
                editableInputs.forEach(input => {
                    input.removeAttribute('disabled');
                });

                if(fileInput) {
                    fileInput.removeAttribute('disabled');
                }

                btnSave.removeAttribute('disabled');
                btnEdit.style.display = 'none';

                if(editableInputs.length > 0) editableInputs[0].focus();
            });
        }

        if (phoneInput) {
            phoneInput.addEventListener('input', function() {
                const value = this.value;
                const hasNonNumber = /\D/.test(value);

                if (hasNonNumber) {
                    isPhoneValid = false;
                    phoneError.style.display = 'inline';
                    this.classList.add('input-error');
                    btnSave.setAttribute('disabled', true);
                } else {
                    isPhoneValid = true;
                    phoneError.style.display = 'none';
                    this.classList.remove('input-error');

                    if (btnEdit.style.display === 'none') {
                        btnSave.removeAttribute('disabled');
                    }
                }
            });
        }
    });
</script>

</body>
</html>