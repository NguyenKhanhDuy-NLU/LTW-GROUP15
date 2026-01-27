<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>${isEdit ? 'Sửa' : 'Thêm'} Khách sạn - Admin</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin-dashboard.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

  <!-- CKEditor 5 -->
  <script src="https://cdn.ckeditor.com/ckeditor5/40.0.0/classic/ckeditor.js"></script>
</head>
<body>
<!-- Sidebar -->
<aside class="sidebar">
  <div class="sidebar-header">
    <img src="${pageContext.request.contextPath}/assets/images/logo.jpg" alt="Logo" class="logo">
    <h2>Admin Panel</h2>
  </div>
  <nav class="sidebar-menu">
    <a href="${pageContext.request.contextPath}/admin/dashboard" class="menu-item">
      <i class="fas fa-tachometer-alt"></i> Dashboard
    </a>
    <a href="${pageContext.request.contextPath}/admin/hotels" class="menu-item active">
      <i class="fas fa-hotel"></i> Quản lý Khách sạn
    </a>
    <a href="${pageContext.request.contextPath}/admin/bookings" class="menu-item">
      <i class="fas fa-calendar-check"></i> Quản lý Booking
    </a>
    <a href="${pageContext.request.contextPath}/" class="menu-item">
      <i class="fas fa-home"></i> Về trang chủ
    </a>
    <a href="${pageContext.request.contextPath}/logout" class="menu-item">
      <i class="fas fa-sign-out-alt"></i> Đăng xuất
    </a>
  </nav>
</aside>

<!-- Main Content -->
<main class="main-content">
  <header class="content-header">
    <div>
      <h1>${isEdit ? 'Sửa' : 'Thêm'} Khách sạn</h1>
      <p>${isEdit ? 'Cập nhật thông tin khách sạn' : 'Thêm khách sạn mới vào hệ thống'}</p>
    </div>
    <a href="${pageContext.request.contextPath}/admin/hotels" class="btn btn-secondary">
      <i class="fas fa-arrow-left"></i> Quay lại
    </a>
  </header>

  <!-- Error Message -->
  <c:if test="${not empty errorMessage}">
  <div class="alert alert-danger">
    <i class="fas fa-exclamation-circle"></i> ${errorMessage}
  </div>
  </c:if>

  <!-- Form -->
  <form action="${pageContext.request.contextPath}/admin/hotels/${isEdit ? 'edit' : 'add'}"
        method="POST"
        enctype="multipart/form-data"
        class="hotel-form">

    <c:if test="${isEdit}">
    <input type="hidden" name="id" value="${hotel.id}">
    </c:if>

    <section class="card">
      <h2 class="section-title">
        <i class="fas fa-info-circle"></i> Thông tin cơ bản
      </h2>

      <div class="form-grid">
        <div class="form-group full-width">
          <label for="name" class="required">Tên khách sạn</label>
          <input type="text"
                 id="name"
                 name="name"
                 class="form-control"
                 value="${hotel.name}"
                 placeholder="VD: Grand Hotel Saigon"
                 required>
        </div>

        <div class="form-group">
          <label for="cityId" class="required">Thành phố</label>
          <select id="cityId" name="cityId" class="form-control" required>
            <option value="">-- Chọn thành phố --</option>
            <option value="1" ${hotel.cityId == 1 ? 'selected' : ''}>Hồ Chí Minh</option>
            <option value="2" ${hotel.cityId == 2 ? 'selected' : ''}>Hà Nội</option>
            <option value="3" ${hotel.cityId == 3 ? 'selected' : ''}>Đà Nẵng</option>
            <option value="4" ${hotel.cityId == 4 ? 'selected' : ''}>Nha Trang</option>
            <option value="5" ${hotel.cityId == 5 ? 'selected' : ''}>Phú Quốc</option>
          </select>
        </div>

        <div class="form-group">
          <label for="starRating" class="required">Hạng sao</label>
          <select id="starRating" name="starRating" class="form-control" required>
            <option value="">-- Chọn --</option>
            <option value="3" ${hotel.starRating == 3 ? 'selected' : ''}>* * * (3 sao) </option>
            <option value="4" ${hotel.starRating == 4 ? 'selected': ''}>* * * * (4 sao) </option>
            <option value="5" ${hotel.starRating == 5 ? 'selected': ''}>* * * * *(5 sao) </option>
          </select>
        </div>
        <div class="form-group full-width">
          <label for="address" class="required">Địa chỉ</label>
          <input type="text"
                 id="address"
                 name="address"
                 class="form-control"
                 value="${hotel.address}"
                 placeholder="VD: 141 Nguyễn Huệ, Quận 1"
                 required>
        </div>

        <div class="form-group">
          <label for="pricePerNight" class="required">Giá gốc (VNĐ/đêm)</label>
          <input type="number"
                 id="pricePerNight"
                 name="pricePerNight"
                 class="form-control"
                 value="${hotel.pricePerNight}"
                 min="0"
                 step="1000"
                 placeholder="VD: 1500000"
                 required>
        </div>

        <div class="form-group">
          <label for="discountPrice">Giá khuyến mãi (VNĐ/đêm)</label>
          <input type="number"
                 id="discountPrice"
                 name="discountPrice"
                 class="form-control"
                 value="${hotel.discountPrice}"
                 min="0"
                 step="1000"
                 placeholder="VD: 1200000">
          <small class="form-text">Để trống nếu không có khuyến mãi</small>
        </div>

        <div class="form-group">
          <label for="latitude">Vĩ độ (Latitude)</label>
          <input type="text"
                 id="latitude"
                 name="latitude"
                 class="form-control"
                 value="${hotel.latitude}"
                 placeholder="VD: 10.762622">
        </div>

        <div class="form-group">
          <label for="longitude">Kinh độ (Longitude)</label>
          <input type="text"
                 id="longitude"
                 name="longitude"
                 class="form-control"
                 value="${hotel.longitude}"
                 placeholder="VD: 106.660172">
        </div>

        <div class="form-group">
          <label for="isActive">Trạng thái</label>
          <select id="isActive" name="isActive" class="form-control">
            <option value="1" ${hotel.isActive ? 'selected' : ''}>Hoạt động</option>
            <option value="0" ${!hotel.isActive ? 'selected' : ''}>Ẩn</option>
          </select>
        </div>
      </div>
    </section>

    <section class="card">
      <h2 class="section-title">
        <i class="fas fa-file-alt"></i> Mô tả chi tiết
      </h2>

      <div class="form-group">
        <label for="description" class="required">Nội dung mô tả</label>
        <textarea id="description"
                  name="description"
                  rows="10"
                  class="form-control">${hotel.description}</textarea>
        <small class="form-text">Sử dụng editor để định dạng văn bản</small>
      </div>
    </section>

    <section class="card">
      <h2 class="section-title">
        <i class="fas fa-concierge-bell"></i> Tiện ích
      </h2>

      <div class="form-group">
        <label>Chọn tiện ích khách sạn</label>
        <div class="amenities-grid">
          <label class="checkbox-label">
            <input type="checkbox" name="amenities" value="WiFi"
            ${hotel.amenities != null && hotel.amenities.contains('WiFi') ? 'checked' : ''}>
            <i class="fas fa-wifi"></i> WiFi miễn phí
          </label>
          <label class="checkbox-label">
            <input type="checkbox" name="amenities" value="Pool"
            ${hotel.amenities != null && hotel.amenities.contains('Pool') ? 'checked' : ''}>
            <i class="fas fa-swimming-pool"></i> Hồ bơi
          </label>
          <label class="checkbox-label">
            <input type="checkbox" name="amenities" value="Parking"
            ${hotel.amenities != null && hotel.amenities.contains('Parking') ? 'checked' : ''}>
            <i class="fas fa-parking"></i> Bãi đỗ xe
          </label>
          <label class="checkbox-label">
            <input type="checkbox" name="amenities" value="Free breakfast"
            ${hotel.amenities != null && hotel.amenities.contains('Free breakfast') ? 'checked' : ''}>
            <i class="fas fa-coffee"></i> Bữa sáng miễn phí
          </label>
          <label class="checkbox-label">
            <input type="checkbox" name="amenities" value="Gym"
            ${hotel.amenities != null && hotel.amenities.contains('Gym') ? 'checked' : ''}>
            <i class="fas fa-dumbbell"></i> Phòng gym
          </label>
          <label class="checkbox-label">
            <input type="checkbox" name="amenities" value="Spa"
            ${hotel.amenities != null && hotel.amenities.contains('Spa') ? 'checked' : ''}>
            <i class="fas fa-spa"></i> Spa
          </label>
          <label class="checkbox-label">
            <input type="checkbox" name="amenities" value="Restaurant"
            ${hotel.amenities != null && hotel.amenities.contains('Restaurant') ? 'checked' : ''}>
            <i class="fas fa-utensils"></i> Nhà hàng
          </label>
          <label class="checkbox-label">
            <input type="checkbox" name="amenities" value="Bar"
            ${hotel.amenities != null && hotel.amenities.contains('Bar') ? 'checked' : ''}>
            <i class="fas fa-glass-martini"></i> Quầy bar
          </label>
        </div>
      </div>
    </section>

    <section class="card">
      <h2 class="section-title">
        <i class="fas fa-images"></i> Quản lý hình ảnh
      </h2>

      <!-- Upload nhiều ảnh -->
      <div class="form-group">
        <label for="hotelImages">Tải lên hình ảnh khách sạn</label>
        <input type="file"
               id="hotelImages"
               name="hotelImages"
               class="form-control"
               accept="image/*"
               multiple>
        <small class="form-text">
          <i class="fas fa-info-circle"></i>
          Chọn nhiều ảnh cùng lúc (Ctrl/Cmd + Click). Định dạng: JPG, PNG, WEBP. Tối đa 5MB/ảnh.
        </small>
      </div>

      <div id="imagePreview" class="image-preview-grid"></div>

      <!-- Danh sách ảnh hiện có (chỉ hiển thị khi edit) -->
      <c:if test="${isEdit && not empty images}">
        <div class="existing-images">
          <h3>Ảnh hiện có (${images.size()})</h3>
          <div class="images-grid">
            <c:forEach var="image" items="${images}">
              <div class="image-item" data-image-id="${image.id}">
                <img src="${pageContext.request.contextPath}${image.imageUrl}"
                     alt="Hotel Image">
                <div class="image-actions">
                  <button type="button"
                          class="btn-thumbnail ${image.thumbnail ? 'active' : ''}"
                          onclick="setThumbnail(${hotel.id}, ${image.id})"
                          title="Chọn làm ảnh đại diện">
                    <i class="fas fa-star"></i>
                  </button>
                  <button type="button"
                          class="btn-delete-img"
                          onclick="deleteImage(${image.id})"
                          title="Xóa ảnh">
                    <i class="fas fa-trash"></i>
                  </button>
                </div>
                <c:if test="${image.thumbnail}">
                                    <span class="thumbnail-badge">
                                        <i class="fas fa-star"></i> Ảnh đại diện
                                    </span>
                </c:if>
              </div>
            </c:forEach>
          </div>
        </div>
      </c:if>
    </section>

    <!-- Form Actions -->
    <div class="form-actions">
      <button type="submit" class="btn btn-primary btn-lg">
        <i class="fas fa-save"></i> ${isEdit ? 'Cập nhật' : 'Thêm mới'}
      </button>
      <a href="${pageContext.request.contextPath}/admin/hotels" class="btn btn-secondary btn-lg">
        <i class="fas fa-times"></i> Hủy
      </a>
    </div>
  </form>
</main>

<script>
  ClassicEditor
          .create(document.querySelector('#description'), {
            toolbar: ['heading', '|', 'bold', 'italic', 'link', 'bulletedList', 'numberedList', '|',
              'outdent', 'indent', '|', 'blockQuote', 'insertTable', 'undo', 'redo'],
            language: 'vi'
          })
          .catch(error => {
            console.error(error);
          });

  // Preview ảnh được chọn
  document.getElementById('hotelImages').addEventListener('change', function(e) {
    const preview = document.getElementById('imagePreview');
    preview.innerHTML = '';

    const files = Array.from(e.target.files);

    files.forEach(file => {
      if (file.type.startsWith('image/')) {
        const reader = new FileReader();
        reader.onload = function(e) {
          const div = document.createElement('div');
          div.className = 'preview-item';
          div.innerHTML = `
                        <img src="${e.target.result}" alt="Preview">
                        <div class="preview-name">${file.name}</div>
                    `;
          preview.appendChild(div);
        };
        reader.readAsDataURL(file);
      }
    });
  });

  // Set thumbnail
  function setThumbnail(hotelId, imageId) {
    if (!confirm('Đặt ảnh này làm ảnh đại diện?')) return;

    fetch('${pageContext.request.contextPath}/admin/hotels/set-thumbnail', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: 'hotelId=' + hotelId + '&imageId=' + imageId
    })
            .then(response => response.json())
            .then(data => {
              if (data.success) {
                alert('Đã set ảnh đại diện thành công!');
                location.reload();
              } else {
                alert('Lỗi: ' + data.message);
              }
            })
            .catch(error => {
              console.error('Error:', error);
              alert('Có lỗi xảy ra!');
            });
  }

  function deleteImage(imageId) {
    if (!confirm('Bạn có chắc muốn xóa ảnh này?')) return;

    fetch('${pageContext.request.contextPath}/admin/hotels/delete-image', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: 'imageId=' + imageId
    })
            .then(response => response.json())
            .then(data => {
              if (data.success) {
                alert('Đã xóa ảnh thành công!');
                location.reload();
              } else {
                alert('Lỗi: ' + data.message);
              }
            })
            .catch(error => {
              console.error('Error:', error);
              alert('Có lỗi xảy ra!');
            });
  }

  document.querySelector('.hotel-form').addEventListener('submit', function(e) {
    const amenitiesChecked = Array.from(document.querySelectorAll('input[name="amenities"]:checked'))
            .map(cb => cb.value);

    document.querySelectorAll('input[name="amenities"]').forEach(cb => cb.remove());

    const input = document.createElement('input');
    input.type = 'hidden';
    input.name = 'amenities';
    input.value = amenitiesChecked.join(',');
    this.appendChild(input);
  });
</script>