<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý Khách sạn - Admin</title>
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
            <h1>Quản lý Khách sạn</h1>
            <p>Tổng số: ${totalHotels} khách sạn</p>
        </div>
        <a href="${pageContext.request.contextPath}/admin/hotels/add" class="btn btn-primary">
            <i class="fas fa-plus"></i> Thêm Khách sạn
        </a>
    </header>

    <c:if test="${param.success eq 'add'}">
        <div class="alert alert-success">
            <i class="fas fa-check-circle"></i> Thêm khách sạn thành công!
        </div>
    </c:if>
    <c:if test="${param.success eq 'update'}">
        <div class="alert alert-success">
            <i class="fas fa-check-circle"></i> Cập nhật khách sạn thành công!
        </div>
    </c:if>
    <c:if test="${param.success eq 'delete'}">
        <div class="alert alert-success">
            <i class="fas fa-check-circle"></i> Xóa khách sạn thành công!
        </div>
    </c:if>
    <c:if test="${param.success eq 'toggle'}">
        <div class="alert alert-success">
            <i class="fas fa-check-circle"></i> Thay đổi trạng thái khách sạn thành công!
        </div>
    </c:if>

    <section class="card">
        <div class="table-responsive">
            <table class="data-table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Tên khách sạn</th>
                    <th>Thành phố</th>
                    <th>Giá gốc</th>
                    <th>Giá KM</th>
                    <th>Sao</th>
                    <th>Trạng thái</th>
                    <th>Thao tác</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="hotel" items="${hotels}">
                    <tr>
                        <td>${hotel.id}</td>
                        <td>
                            <strong>${hotel.name}</strong><br>
                            <small class="text-muted">${hotel.address}</small>
                        </td>
                        <td>${hotel.cityName}</td>
                        <td>
                            <fmt:formatNumber value="${hotel.pricePerNight}" pattern="#,###" /> đ
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${hotel.hasDiscount()}">
                                    <span class="text-success">
                                        <fmt:formatNumber value="${hotel.discountPrice}" pattern="#,###" /> đ
                                    </span>
                                    <span class="badge badge-danger">-${hotel.discountPercent}%</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="text-muted">-</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:forEach begin="1" end="${hotel.starRating}">
                                <i class="fas fa-star text-warning"></i>
                            </c:forEach>
                        </td>
                        <td>
                            <span class="badge ${hotel.statusClass}">
                                    ${hotel.statusText}
                            </span>
                        </td>
                        <td>
                            <div class="action-buttons">
                                <a href="${pageContext.request.contextPath}/admin/rooms?hotelId=${hotel.id}"
                                   class="btn-icon btn-info"
                                   title="Quản lý phòng">
                                    <i class="fas fa-bed"></i>
                                </a>
                                <button onclick="toggleStatus(${hotel.id}, ${hotel.active})"
                                        class="btn-icon ${hotel.active ? 'btn-warning' : 'btn-success'}"
                                        title="${hotel.active ? 'Khóa' : 'Mở khóa'}">
                                    <i class="fas ${hotel.active ? 'fa-lock' : 'fa-lock-open'}"></i>
                                </button>
                                <button onclick="confirmDelete(${hotel.id}, '${hotel.name}')"
                                        class="btn-icon btn-delete"
                                        title="Xóa">
                                    <i class="fas fa-trash"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <c:if test="${totalPages > 1}">
            <div class="pagination">
                <c:if test="${currentPage > 1}">
                    <a href="?page=${currentPage - 1}" class="page-link">
                        <i class="fas fa-chevron-left"></i>
                    </a>
                </c:if>

                <c:forEach begin="1" end="${totalPages}" var="i">
                    <a href="?page=${i}" class="page-link ${i == currentPage ? 'active' : ''}">
                            ${i}
                    </a>
                </c:forEach>

                <c:if test="${currentPage < totalPages}">
                    <a href="?page=${currentPage + 1}" class="page-link">
                        <i class="fas fa-chevron-right"></i>
                    </a>
                </c:if>
            </div>
        </c:if>
    </section>
</main>

<script>
    function toggleStatus(id, currentActive) {
        const action = currentActive ? 'khóa' : 'mở khóa';
        if (confirm('Bạn có chắc muốn ' + action + ' khách sạn này?')) {
            window.location.href = '${pageContext.request.contextPath}/admin/hotels/toggle-status?id=' + id;
        }
    }
    function confirmDelete(id, name) {
        if (confirm('Bạn có chắc muốn xóa khách sạn "' + name + '"?')) {
            window.location.href = '${pageContext.request.contextPath}/admin/hotels/delete?id=' + id;
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
