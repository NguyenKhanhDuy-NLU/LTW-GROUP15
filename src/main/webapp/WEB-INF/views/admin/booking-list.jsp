<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý Booking - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin-dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
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
        <a href="${pageContext.request.contextPath}/admin/hotels" class="menu-item">
            <i class="fas fa-hotel"></i> Quản lý Khách sạn
        </a>
        <a href="${pageContext.request.contextPath}/admin/bookings" class="menu-item active">
            <i class="fas fa-calendar-check"></i> Quản lý Booking
        </a>
        <a href="${pageContext.request.contextPath}/admin/users" class="menu-item">
            <i class="fas fa-users"></i> Quản lý User
        </a>
        <a href="${pageContext.request.contextPath}/admin/reviews" class="menu-item">
            <i class="fas fa-star"></i> Quản lý Review
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
        <h1>Quản lý Booking</h1>
        <div class="user-info">
            <span>Xin chào, ${sessionScope.user.fullName}</span>
        </div>
    </header>

    <!-- Filter Section -->
    <section class="filter-section">
        <div class="filter-group">
            <label>Lọc theo trạng thái:</label>
            <select id="statusFilter" onchange="filterByStatus(this.value)">
                <option value="all" ${currentStatus eq 'all' ? 'selected' : ''}>Tất cả</option>
                <option value="pending" ${currentStatus eq 'pending' ? 'selected' : ''}>Chờ xác nhận</option>
                <option value="confirmed" ${currentStatus eq 'confirmed' ? 'selected' : ''}>Đã xác nhận</option>
                <option value="completed" ${currentStatus eq 'completed' ? 'selected' : ''}>Hoàn thành</option>
                <option value="cancelled" ${currentStatus eq 'cancelled' ? 'selected' : ''}>Đã hủy</option>
            </select>
        </div>
        <div class="stats-info">
            <p>Tổng: <strong>${totalBookings}</strong> booking</p>
        </div>
    </section>

    <!-- Bookings Table -->
    <section class="data-section">
        <div class="card">
            <div class="card-header">
                <h3>Danh sách Booking</h3>
            </div>
            <div class="card-body">
                <c:if test="${not empty param.success}">
                    <div class="alert alert-success">
                        <c:choose>
                            <c:when test="${param.success eq 'delete'}">Xóa booking thành công!</c:when>
                            <c:otherwise>Thao tác thành công!</c:otherwise>
                        </c:choose>
                    </div>
                </c:if>

                <c:if test="${not empty param.error}">
                    <div class="alert alert-danger">Có lỗi xảy ra!</div>
                </c:if>

                <table class="data-table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Khách hàng</th>
                        <th>Khách sạn</th>
                        <th>Check-in</th>
                        <th>Check-out</th>
                        <th>Số khách</th>
                        <th>Tổng tiền</th>
                        <th>Trạng thái</th>
                        <th>Thanh toán</th>
                        <th>Thao tác</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${empty bookings}">
                            <tr>
                                <td colspan="10" class="text-center">Không có booking nào</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="booking" items="${bookings}">
                                <tr>
                                    <td>${booking.id}</td>
                                    <td>
                                        <strong>${booking.userFullName}</strong><br>
                                        <small>${booking.userEmail}</small>
                                    </td>
                                    <td>${booking.hotelName}</td>
                                    <td><fmt:formatDate value="${booking.checkInDate}" pattern="dd/MM/yyyy"/></td>
                                    <td><fmt:formatDate value="${booking.checkOutDate}" pattern="dd/MM/yyyy"/></td>
                                    <td>${booking.numGuests}</td>
                                    <td><fmt:formatNumber value="${booking.totalPrice}" pattern="#,###"/>đ</td>
                                    <td>
                                            <span class="badge badge-${booking.statusBadgeClass}">
                                                    ${booking.statusText}
                                            </span>
                                    </td>
                                    <td>
                                            <span class="badge badge-${booking.paymentStatus eq 'paid' ? 'success' : 'warning'}">
                                                    ${booking.paymentStatusText}
                                            </span>
                                    </td>
                                    <td>
                                        <div class="action-buttons">
                                            <a href="${pageContext.request.contextPath}/admin/bookings/view?id=${booking.id}"
                                               class="btn btn-sm btn-info" title="Xem chi tiết">
                                                <i class="fas fa-eye"></i>
                                            </a>
                                            <button onclick="changeStatus(${booking.id})"
                                                    class="btn btn-sm btn-primary" title="Đổi trạng thái">
                                                <i class="fas fa-edit"></i>
                                            </button>
                                            <a href="${pageContext.request.contextPath}/admin/bookings/delete?id=${booking.id}"
                                               class="btn btn-sm btn-danger"
                                               onclick="return confirm('Bạn có chắc muốn xóa booking này?')"
                                               title="Xóa">
                                                <i class="fas fa-trash"></i>
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>

                <!-- Pagination -->
                <c:if test="${totalPages > 1}">
                    <div class="pagination">
                        <c:if test="${currentPage > 1}">
                            <a href="?page=${currentPage - 1}&status=${currentStatus}">&laquo; Trước</a>
                        </c:if>

                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <a href="?page=${i}&status=${currentStatus}"
                               class="${i == currentPage ? 'active' : ''}">${i}</a>
                        </c:forEach>

                        <c:if test="${currentPage < totalPages}">
                            <a href="?page=${currentPage + 1}&status=${currentStatus}">Sau &raquo;</a>
                        </c:if>
                    </div>
                </c:if>
            </div>
        </div>
    </section>
</main>

<script>
    function filterByStatus(status) {
        window.location.href = '${pageContext.request.contextPath}/admin/bookings?status=' + status;
    }

    function changeStatus(bookingId) {
        const status = prompt('Nhập trạng thái mới (pending/confirmed/completed/cancelled):');
        if (status) {
            fetch('${pageContext.request.contextPath}/admin/bookings/update-status', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: 'id=' + bookingId + '&status=' + status
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        alert('Cập nhật thành công!');
                        location.reload();
                    } else {
                        alert('Lỗi: ' + data.message);
                    }
                });
        }
    }
</script>
</body>
</html>
