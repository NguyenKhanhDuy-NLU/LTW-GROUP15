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
</head>
<body>
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
    <a href="${pageContext.request.contextPath}/admin/users" class="menu-item">
      <i class="fas fa-users"></i> Quản lý User
    </a>
    <a href="${pageContext.request.contextPath}/" class="menu-item">
      <i class="fas fa-home"></i> Về trang chủ
    </a>
    <a href="${pageContext.request.contextPath}/logout" class="menu-item">
      <i class="fas fa-sign-out-alt"></i> Đăng xuất
    </a>
  </nav>
</aside>

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

  <c:if test="${not empty errorMessage}">
    <div class="alert alert-danger">
      <i class="fas fa-exclamation-circle"></i> ${errorMessage}
    </div>
  </c:if>

  <form action="${pageContext.request.contextPath}/admin/hotels/${isEdit ? 'edit' : 'add'}"
        method="POST"
        enctype="multipart/form-data"
        class="hotel-form">

    <c:if test="${isEdit}">
      <input type="hidden" name="id" value="${hotel.id}">
    </c:if>

    <!-- THÔNG TIN CƠ BẢN -->
    <section class="card">
      <h2 class="section-title">
        <i class="fas fa-info-circle"></i> Thông tin cơ bản
      </h2>

      <div class="form-grid">
        <div class="form-group full-width">
          <label for="hotelName" class="required">Tên khách sạn</label>
          <input type="text"
                 id="hotelName"
                 name="hotelName"
                 class="form-control"
                 value="${hotel.hotelName}"
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
            <option value="3" ${hotel.starRating == 3 ? 'selected' : ''}>⭐⭐⭐ (3 sao)</option>
            <option value="4" ${hotel.starRating == 4 ? 'selected' : ''}>⭐⭐⭐⭐ (4 sao)</option>
            <option value="5" ${hotel.starRating == 5 ? 'selected' : ''}>⭐⭐⭐⭐⭐ (5 sao)</option>
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
          <label for="isFeatured">Trạng thái</label>
          <select id="isFeatured" name="isFeatured" class="form-control">
            <option value="1" ${hotel.featured ? 'selected' : ''}>Nổi bật</option>
            <option value="0" ${!hotel.featured ? 'selected' : ''}>Bình thường</option>
          </select>
        </div>
      </div>
    </section>

    <!-- MÔ TẢ CHI TIẾT - DÙNG TEXTAREA ĐƠN GIẢN -->
    <section class="card">
      <h2 class="section-title">
        <i class="fas fa-file-alt"></i> Mô tả chi tiết
      </h2>

      <div class="form-group">
        <label for="description">Nội dung mô tả</label>
        <textarea id="description"
                  name="description"
                  rows="8"
                  class="form-control"
                  placeholder="Nhập mô tả chi tiết về khách sạn...">${hotel.description}</textarea>
        <small class="form-text">Mô tả chi tiết về khách sạn, dịch vụ, vị trí...</small>
      </div>
    </section>

    <!-- TIỆN ÍCH - ĐÃ BỎ NHÀ HÀNG VÀ QUẦY BAR -->
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
        </div>
      </div>
    </section>

    <!-- QUẢN LÝ HÌNH ẢNH KHÁCH SẠN -->
    <section class="card">
      <h2 class="section-title">
        <i class="fas fa-images"></i> Quản lý hình ảnh khách sạn
      </h2>

      <!-- MỤC 1: ẢNH CHÍNH (1 ảnh) -->
      <div class="form-group">
        <label for="mainImage">
          <i class="fas fa-image"></i> Ảnh chính (hiển thị ở danh sách)
        </label>
        <input type="file"
               id="mainImage"
               name="mainImage"
               class="form-control"
               accept="image/*"
               onchange="previewMainImage(this)">
        <small class="form-text">
          <i class="fas fa-info-circle"></i>
          Ảnh này sẽ hiển thị khi người dùng xem danh sách khách sạn (index, search). Chỉ chọn 1 ảnh.
        </small>

        <c:if test="${isEdit && not empty hotel.mainImage}">
          <div class="current-image">
            <p>Ảnh hiện tại:</p>
            <img src="${pageContext.request.contextPath}${hotel.mainImage}"
                 alt="Main Image"
                 style="max-width: 300px; margin-top: 10px;">
          </div>
        </c:if>

        <div id="mainImagePreview"></div>
      </div>

      <hr style="margin: 30px 0;">

      <!-- MỤC 2: ẢNH COVER (3 ảnh) -->
      <div class="form-group">
        <label for="coverImages">
          <i class="fas fa-images"></i> Ảnh cover chi tiết (3 ảnh)
        </label>
        <input type="file"
               id="coverImages"
               name="coverImages"
               class="form-control"
               accept="image/*"
               multiple
               onchange="previewCoverImages(this)">
        <small class="form-text">
          <i class="fas fa-info-circle"></i>
          3 ảnh này kết hợp với ảnh phòng sẽ tạo thành cover images hiển thị ở trang chi tiết khách sạn. Chọn đúng 3 ảnh.
        </small>

        <c:if test="${isEdit && not empty hotel.coverImages}">
          <div class="current-images">
            <p>Ảnh cover hiện tại:</p>
            <div class="images-grid">
              <!-- Parse JSON array và hiển thị -->
              <c:forEach var="img" items="${coverImagesArray}">
                <img src="${pageContext.request.contextPath}${img}"
                     alt="Cover Image"
                     style="max-width: 200px; margin: 5px;">
              </c:forEach>
            </div>
          </div>
        </c:if>

        <div id="coverImagesPreview" class="images-grid"></div>
      </div>
    </section>

    <!-- FORM ACTIONS -->
    <div class="form-actions">
  <!-- QUẢN LÝ PHÒNG -->
  <div class="form-section" style="margin-top: 30px; padding: 20px; background: #f8f9fa; border-radius: 8px;">
    <h3 style="margin-bottom: 20px; color: #333;"><i class="fas fa-bed"></i> Phòng</h3>
    <div id="rooms-container"></div>
    <button type="button" onclick="addRoom()" class="btn btn-secondary" style="margin-top: 15px;">
      <i class="fas fa-plus"></i> Thêm Phòng
    </button>
  </div>

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
  // Preview ảnh chính
  function previewMainImage(input) {
    const preview = document.getElementById('mainImagePreview');
    preview.innerHTML = '';

    if (input.files && input.files[0]) {
      const reader = new FileReader();
      reader.onload = function(e) {
        preview.innerHTML = `
                    <div style="margin-top: 10px;">
                        <p><strong>Ảnh mới:</strong></p>
                        <img src="${e.target.result}" alt="Preview" style="max-width: 300px;">
                    </div>
                `;
      };
      reader.readAsDataURL(input.files[0]);
    }
  }

  // Preview ảnh cover (3 ảnh)
  function previewCoverImages(input) {
    const preview = document.getElementById('coverImagesPreview');
    preview.innerHTML = '';

    if (input.files && input.files.length > 0) {
      if (input.files.length !== 3) {
        alert('Vui lòng chọn đúng 3 ảnh cover!');
        input.value = '';
        return;
      }

      preview.innerHTML = '<p style="margin-top: 10px;"><strong>3 ảnh cover mới:</strong></p>';

      Array.from(input.files).forEach((file, index) => {
        const reader = new FileReader();
        reader.onload = function(e) {
          const img = document.createElement('img');
          img.src = e.target.result;
          img.alt = `Cover ${index + 1}`;
          img.style.maxWidth = '200px';
          img.style.margin = '5px';
          preview.appendChild(img);
        };
        reader.readAsDataURL(file);
      });
    }
  }

  // Xử lý submit form - convert amenities thành chuỗi
  document.querySelector('.hotel-form').addEventListener('submit', function(e) {
    const amenitiesChecked = Array.from(document.querySelectorAll('input[name="amenities"]:checked'))
            .map(cb => cb.value);

let roomIndex = 0;
function addRoom() {
  const container = document.getElementById("rooms-container");
  const roomDiv = document.createElement("div");
  roomDiv.className = "room-item";
  roomDiv.style.cssText = "background: white; padding: 15px; margin-bottom: 15px; border-radius: 5px; border: 1px solid #ddd;";
  roomDiv.innerHTML = `<div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 15px;"><h4 style="margin: 0;">Phòng ${roomIndex + 1}</h4><button type="button" onclick="removeRoom(this)" class="btn-icon btn-delete" style="padding: 5px 10px;"><i class="fas fa-trash"></i></button></div><div style="display: grid; grid-template-columns: 1fr 1fr; gap: 15px;"><div class="form-group"><label>Tên phòng *</label><input type="text" name="rooms[${roomIndex}].roomName" required placeholder="VD: Deluxe Room"></div><div class="form-group"><label>Loại phòng *</label><select name="rooms[${roomIndex}].roomType" required><option value="">-- Chọn --</option><option value="Standard">Standard</option><option value="Superior">Superior</option><option value="Deluxe">Deluxe</option><option value="Suite">Suite</option><option value="Presidential">Presidential</option></select></div><div class="form-group"><label>Giá phòng/đêm (VNĐ) *</label><input type="number" name="rooms[${roomIndex}].basePrice" required min="0" step="1000" placeholder="1000000"></div><div class="form-group"><label>Số người tối đa *</label><input type="number" name="rooms[${roomIndex}].maxPeople" required min="1" max="10" value="2"></div><div class="form-group"><label>Số lượng phòng *</label><input type="number" name="rooms[${roomIndex}].quantity" required min="1" value="1"></div><div class="form-group"><label>Diện tích</label><input type="text" name="rooms[${roomIndex}].size" placeholder="VD: 30m²"></div><div class="form-group"><label>View</label><input type="text" name="rooms[${roomIndex}].view" placeholder="VD: Hướng biển"></div><div class="form-group"><label>Ảnh phòng (URLs)</label><input type="text" name="rooms[${roomIndex}].images" placeholder="URL1, URL2, ..."></div></div><div class="form-group" style="margin-top: 10px;"><label>Mô tả phòng</label><textarea name="rooms[${roomIndex}].description" rows="2" placeholder="Mô tả chi tiết về phòng..."></textarea></div>`;
  container.appendChild(roomDiv);
  roomIndex++;
}
function removeRoom(button) {
  if (confirm("Xóa phòng này?")) {
    button.closest(".room-item").remove();
  }
}

    // Xóa các checkbox cũ
    document.querySelectorAll('input[name="amenities"]').forEach(cb => cb.remove());

    // Tạo input hidden với giá trị là chuỗi
    const input = document.createElement('input');
    input.type = 'hidden';
    input.name = 'amenities';
    input.value = amenitiesChecked.join(',');
    this.appendChild(input);
  });
</script>

<style>
  .amenities-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    gap: 15px;
    margin-top: 10px;
  }

  .checkbox-label {
    display: flex;
    align-items: center;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 5px;
    cursor: pointer;
    transition: all 0.3s;
  }

  .checkbox-label:hover {
    background-color: #f8f9fa;
    border-color: #007bff;
  }

  .checkbox-label input[type="checkbox"] {
    margin-right: 10px;
  }

  .checkbox-label i {
    margin-right: 5px;
    color: #007bff;
  }

  .images-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    gap: 10px;
    margin-top: 10px;
  }

  .current-image, .current-images {
    margin-top: 15px;
    padding: 15px;
    background-color: #f8f9fa;
    border-radius: 5px;
  }
</style>
</body>
</html>
