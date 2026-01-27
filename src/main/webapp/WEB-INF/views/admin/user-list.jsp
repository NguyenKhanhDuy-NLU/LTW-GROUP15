<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý User - Admin</title>
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
        <a href="${pageContext.request.contextPath}/admin/hotels" class="menu-item">
            <i class="fas fa-hotel"></i> Quản lý Khách sạn
        </a>
        <a href="${pageContext.request.contextPath}/admin/bookings" class="menu-item">
            <i class="fas fa-calendar-check"></i> Quản lý Booking
        </a>
        <a href="${pageContext.request.contextPath}/admin/users" class="menu-item active">
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
        <h1>Quản lý User</h1>
        <div class="user-info">
            <span>Xin chào, ${sessionScope.user.fullName}</span>
        </div>
    </header>

    <!-- Search Section -->
    <section class="filter-section">
        <form action="${pageContext.request.contextPath}/admin/users" method="get" class="search-form">
            <input type="text" name="search" value="${searchQuery}"
                   placeholder="Tìm theo username, tên hoặc email..." class="search-input">
            <button type="submit" class="btn btn-primary">
                <i class="fas fa-search"></i> Tìm kiếm
            </button>
        </form>
        <div class="stats-info">
            <p>Tổng: <strong>${totalUsers}</strong> user</p>
        </div>
    </section>

    <section class="data-section">
        <div class="card">
            <div class="card-header">
                <h3>Danh sách User</h3>
            </div>
            <div class="card-body">
                <c:if test="${not empty param.success}">
                    <div class="alert alert-success">
                        <c:choose>
                            <c:when test="${param.success eq 'delete'}">Xóa user thành công!</c:when>
                            <c:otherwise>Thao tác thành công!</c:otherwise>
                        </c:choose>
                    </div>
                </c:if>

                <table class="data-table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Họ tên</th>
                        <th>Email</th>
                        <th>SĐT</th>
                        <th>Role</th>
                        <th>Trạng thái</th>
                        <th>Xác thực</th>
                        <th>Booking</th>
                        <th>Thao tác</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${empty users}">
                            <tr>
                                <td colspan="10" class="text-center">Không có user nào</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="user" items="${users}">
                                <tr>
                                    <td>${user.id}</td>
                                    <td><strong>${user.username}</strong></td>
                                    <td>${user.fullName}</td>
                                    <td>${user.email}</td>
                                    <td>${user.phone}</td>
                                    <td>
                                            <span class="badge badge-${user.roleId == 1 ? 'danger' : 'info'}">
                                                    ${user.roleText}
                                            </span>
                                    </td>
                                    <td>
                                            <span class="badge badge-${user.statusBadgeClass}">
                                                    ${user.statusText}
                                            </span>
                                    </td>
                                    <td>
                                            <span class="badge badge-${user.verifiedBadgeClass}">
                                                    ${user.verifiedText}
                                            </span>
                                    </td>
                                    <td>${user.bookingCount}</td>
                                    <td>
                                        <div class="action-buttons">
                                            <a href="${pageContext.request.contextPath}/admin/users/view?id=${user.id}"
                                               class="btn btn-sm btn-info" title="Xem chi tiết">
                                                <i class="fas fa-eye"></i>
                                            </a>
                                            <button onclick="toggleUserStatus(${user.id}, ${!user.active})"
                                                    class="btn btn-sm btn-${user.active ? 'warning' : 'success'}"
                                                    title="${user.active ? 'Khóa' : 'Mở khóa'}">
                                                <i class="fas fa-${user.active ? 'lock' : 'unlock'}"></i>
                                            </button>
                                            <c:if test="${!user.verified}">
                                                <button onclick="verifyUser(${user.id})"
                                                        class="btn btn-sm btn-primary" title="Xác thực">
                                                    <i class="fas fa-check"></i>
                                                </button>
                                            </c:if>
                                            <c:if test="${user.roleId != 1}">
                                                <a href="${pageContext.request.contextPath}/admin/users/delete?id=${user.id}"
                                                   class="btn btn-sm btn-danger"
                                                   onclick="return confirm('Bạn có chắc muốn xóa user này?')"
                                                   title="Xóa">
                                                    <i class="fas fa-trash"></i>
                                                </a>
                                            </c:if>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>

                <c:if test="${totalPages > 1}">
                    <div class="pagination">
                        <c:if test="${currentPage > 1}">
                            <a href="?page=${currentPage - 1}<c:if test='${not empty searchQuery}'>&search=${searchQuery}</c:if>">&laquo; Trước</a>
                        </c:if>

                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <a href="?page=${i}<c:if test='${not empty searchQuery}'>&search=${searchQuery}</c:if>"
                               class="${i == currentPage ? 'active' : ''}">${i}</a>
                        </c:forEach>

                        <c:if test="${currentPage < totalPages}">
                            <a href="?page=${currentPage + 1}<c:if test='${not empty searchQuery}'>&search=${searchQuery}</c:if>">Sau &raquo;</a>
                        </c:if>
                    </div>
                </c:if>
            </div>
        </div>
    </section>
</main>

<script>
    function toggleUserStatus(userId, activate) {
        const action = activate ? 'mở khóa' : 'khóa';
        if (confirm('Bạn có chắc muốn ' + action + ' user này?')) {
            fetch('${pageContext.request.contextPath}/admin/users/update-status', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: 'id=' + userId + '&isActive=' + activate
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

    function verifyUser(userId) {
        if (confirm('Xác thực user này?')) {
            fetch('${pageContext.request.contextPath}/admin/users/verify', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: 'id=' + userId
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        alert('Xác thực thành công!');
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
