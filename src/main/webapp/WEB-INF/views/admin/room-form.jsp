<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${room != null ? 'Sửa' : 'Thêm'} Phòng - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/hotel-form.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
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
        <a href="${pageContext.request.contextPath}/admin/hotels" class="menu-item">
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
            <h1>${room != null ? 'Sửa' : 'Thêm'} Phòng</h1>
        </div>
        <a href="${pageContext.request.contextPath}/admin/rooms${room != null ? '?hotelId=' += room.hotelId : ''}" class="btn btn-secondary">
            <i class="fas fa-arrow-left"></i> Quay lại
        </a>
    </header>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">
            <i class="fas fa-exclamation-circle"></i> ${error}
        </div>
    </c:if>

    <section class="card">
        <form action="${pageContext.request.contextPath}/admin/rooms/${room != null ? 'edit' : 'add'}" 
              method="post" class="hotel-form" enctype="multipart/form-data">
            
            <c:if test="${room != null}">
                <input type="hidden" name="id" value="${room.id}">
            </c:if>

            <div class="form-row">
                <div class="form-group">
                    <label for="hotelId">Khách sạn <span class="required">*</span></label>
                    <select id="hotelId" name="hotelId" required ${room != null ? 'disabled' : ''}>
                        <option value="">-- Chọn khách sạn --</option>
                        <c:forEach var="hotel" items="${hotels}">
                            <option value="${hotel.id}" 
                                    ${(room != null && room.hotelId == hotel.id) || (param.hotelId == hotel.id) ? 'selected' : ''}>
                                ${hotel.name}
                            </option>
                        </c:forEach>
                    </select>
                    <c:if test="${room != null}">
                        <input type="hidden" name="hotelId" value="${room.hotelId}">
                    </c:if>
                </div>

                <div class="form-group">
                    <label for="roomName">Tên phòng <span class="required">*</span></label>
                    <input type="text" id="roomName" name="roomName" required
                           placeholder="VD: Phòng Deluxe"
                           value="${room != null ? room.roomName : ''}">
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="roomType">Loại phòng <span class="required">*</span></label>
                    <select id="roomType" name="roomType" required>
                        <option value="">-- Chọn loại phòng --</option>
                        <option value="Standard" ${room != null && room.roomType == 'Standard' ? 'selected' : ''}>Standard</option>
                        <option value="Superior" ${room != null && room.roomType == 'Superior' ? 'selected' : ''}>Superior</option>
                        <option value="Deluxe" ${room != null && room.roomType == 'Deluxe' ? 'selected' : ''}>Deluxe</option>
                        <option value="Suite" ${room != null && room.roomType == 'Suite' ? 'selected' : ''}>Suite</option>
                        <option value="Presidential" ${room != null && room.roomType == 'Presidential' ? 'selected' : ''}>Presidential</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="maxPeople">Số người tối đa <span class="required">*</span></label>
                    <input type="number" id="maxPeople" name="maxPeople" required min="1" max="10"
                           value="${room != null ? room.maxPeople : '2'}">
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="basePrice">Giá phòng/đêm (VNĐ) <span class="required">*</span></label>
                    <input type="number" id="basePrice" name="basePrice" required min="0" step="1000"
                           placeholder="VD: 1000000"
                           value="${room != null ? room.basePrice : ''}">
                </div>

                <div class="form-group">
                    <label for="quantity">Số lượng phòng <span class="required">*</span></label>
                    <input type="number" id="quantity" name="quantity" required min="1" max="100"
                           value="${room != null ? room.quantity : '1'}">
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="size">Diện tích</label>
                    <input type="text" id="size" name="size"
                           placeholder="VD: 30m²"
                           value="${room != null ? room.size : ''}">
                </div>

                <div class="form-group">
                    <label for="view">View</label>
                    <input type="text" id="view" name="view"
                           placeholder="VD: Hướng biển"
                           value="${room != null ? room.view : ''}">
                </div>
            </div>

            <div class="form-group">
                <label for="mainImage">Ảnh phòng chính <span class="required">*</span></label>
                <input type="file" id="mainImage" name="mainImage" accept="image/*" 
                       ${room == null ? 'required' : ''} onchange="previewMainImage(this)">
                <small class="form-text">Upload 1 ảnh làm ảnh đại diện phòng</small>
                <div id="mainImagePreview" style="margin-top: 10px;"></div>
                <c:if test="${room != null && not empty room.images}">
                    <div style="margin-top: 10px;">
                        <strong>Ảnh hiện tại:</strong><br>
                        <img src="${pageContext.request.contextPath}${room.images.split(',')[0]}" 
                             style="max-width: 200px; margin: 5px;">
                    </div>
                </c:if>
            </div>

            <div class="form-group">
                <label for="otherImages">Ảnh phòng khác</label>
                <input type="file" id="otherImages" name="otherImages" accept="image/*" multiple 
                       onchange="previewOtherImages(this)">
                <small class="form-text">Có thể chọn nhiều ảnh (tối đa 5 ảnh)</small>
                <div id="otherImagesPreview" style="margin-top: 10px; display: grid; grid-template-columns: repeat(5, 1fr); gap: 10px;"></div>
                <c:if test="${room != null && not empty room.images}">
                    <div style="margin-top: 10px;">
                        <strong>Ảnh khác hiện tại:</strong><br>
                        <div style="display: grid; grid-template-columns: repeat(5, 1fr); gap: 10px;">
                            <c:forEach var="img" items="${room.images.split(',')}" begin="1">
                                <img src="${pageContext.request.contextPath}${img}" 
                                     style="width: 100%; height: 100px; object-fit: cover;">
                            </c:forEach>
                        </div>
                    </div>
                </c:if>
            </div>

            <div class="form-group">
                <label for="description">Mô tả phòng</label>
                <textarea id="description" name="description" rows="4"
                          placeholder="Mô tả chi tiết về phòng...">${room != null ? room.description : ''}</textarea>
            </div>

            <c:if test="${room != null}">
                <div class="form-group">
                    <label>
                        <input type="checkbox" name="isAvailable" value="true" 
                               ${room.available ? 'checked' : ''}>
                        Phòng đang khả dụng
                    </label>
                </div>
            </c:if>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-save"></i> ${room != null ? 'Cập nhật' : 'Thêm mới'}
                </button>
                <a href="${pageContext.request.contextPath}/admin/rooms${room != null ? '?hotelId=' += room.hotelId : ''}" 
                   class="btn btn-secondary">
                    <i class="fas fa-times"></i> Hủy
                </a>
            </div>
        </form>
    </section>
</main>

<script>
function previewMainImage(input) {
    const preview = document.getElementById('mainImagePreview');
    preview.innerHTML = '';
    
    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = function(e) {
            const img = document.createElement('img');
            img.src = e.target.result;
            img.style.maxWidth = '300px';
            img.style.borderRadius = '8px';
            preview.appendChild(img);
        };
        reader.readAsDataURL(input.files[0]);
    }
}

function previewOtherImages(input) {
    const preview = document.getElementById('otherImagesPreview');
    preview.innerHTML = '';
    
    if (input.files) {
        Array.from(input.files).slice(0, 5).forEach(file => {
            const reader = new FileReader();
            reader.onload = function(e) {
                const img = document.createElement('img');
                img.src = e.target.result;
                img.style.width = '100%';
                img.style.height = '100px';
                img.style.objectFit = 'cover';
                img.style.borderRadius = '8px';
                preview.appendChild(img);
            };
            reader.readAsDataURL(file);
        });
    }
}
</script>
</body>
</html>
