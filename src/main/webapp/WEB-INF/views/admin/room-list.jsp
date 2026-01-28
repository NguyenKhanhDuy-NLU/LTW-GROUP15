<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Phòng - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/hotel-list.css">
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
            <h1>Phòng</h1>
            <c:if test="${not empty hotel}">
                <p>Khách sạn: <strong>${hotel.name}</strong></p>
            </c:if>
            <p>Tổng số: ${rooms.size()} phòng</p>
        </div>
        <div>
            <c:if test="${not empty hotelId}">
                <a href="${pageContext.request.contextPath}/admin/rooms/add?hotelId=${hotelId}" class="btn btn-primary">
                    <i class="fas fa-plus"></i> Thêm Phòng
                </a>
                <a href="${pageContext.request.contextPath}/admin/hotels" class="btn btn-secondary">
                    <i class="fas fa-arrow-left"></i> Quay lại
                </a>
            </c:if>
        </div>
    </header>

    <c:if test="${param.success eq 'add'}">
        <div class="alert alert-success">
            <i class="fas fa-check-circle"></i> Thêm phòng thành công!
        </div>
    </c:if>
    <c:if test="${param.success eq 'update'}">
        <div class="alert alert-success">
            <i class="fas fa-check-circle"></i> Cập nhật phòng thành công!
        </div>
    </c:if>
    <c:if test="${param.success eq 'delete'}">
        <div class="alert alert-success">
            <i class="fas fa-check-circle"></i> Xóa phòng thành công!
        </div>
    </c:if>

    <section class="card">
        <div class="table-responsive">
            <table class="data-table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Khách sạn</th>
                    <th>Tên phòng</th>
                    <th>Loại</th>
                    <th>Giá/đêm</th>
                    <th>Số khách</th>
                    <th>Số lượng</th>
                    <th>Trạng thái</th>
                    <th>Thao tác</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${empty rooms}">
                        <tr>
                            <td colspan="9" class="text-center">Chưa có phòng nào</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="room" items="${rooms}">
                            <tr>
                                <td>${room.id}</td>
                                <td>${room.hotelName}</td>
                                <td>
                                    <strong>${room.roomName}</strong><br>
                                    <small class="text-muted">
                                        ${not empty room.size ? room.size : ''}
                                        ${not empty room.view ? ' | ' += room.view : ''}
                                    </small>
                                </td>
                                <td><span class="badge badge-info">${room.roomType}</span></td>
                                <td><fmt:formatNumber value="${room.basePrice}" pattern="#,###" /> đ</td>
                                <td>${room.maxPeople} người</td>
                                <td>${room.quantity} phòng</td>
                                <td>
                                    <span class="badge ${room.statusClass}">
                                        ${room.statusText}
                                    </span>
                                </td>
                                <td>
                                    <div class="action-buttons">
                                        <a href="${pageContext.request.contextPath}/admin/rooms/edit?id=${room.id}"
                                           class="btn-icon btn-edit" title="Sửa">
                                            <i class="fas fa-edit"></i>
                                        </a>
                                        <button onclick="confirmDelete(${room.id}, '${room.roomName}', ${room.hotelId})"
                                                class="btn-icon btn-delete" title="Xóa">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </section>
</main>

<script>
    function confirmDelete(id, name, hotelId) {
        if (confirm('Bạn có chắc muốn xóa phòng "' + name + '"?')) {
            window.location.href = '${pageContext.request.contextPath}/admin/rooms/delete?id=' + id + '&hotelId=' + hotelId;
        }
    }

    setTimeout(function() {
        const alerts = document.querySelectorAll('.alert');
        alerts.forEach(alert => {
            alert.style.opacity = '0';
            setTimeout(() => alert.remove(), 300);
        });
    }, 5000);
</script>
</body>
</html>
