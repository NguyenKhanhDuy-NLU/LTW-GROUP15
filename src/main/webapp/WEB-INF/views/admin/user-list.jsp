<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý User - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin-dashboard.css">
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
        <a href="${pageContext.request.contextPath}/admin/users" class="menu-item active">
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
            <h1>Quản lý User</h1>
            <p>Tổng số: ${totalUsers} người dùng</p>
        </div>
    </header>

    <c:if test="${param.success eq 'toggle'}">
        <div class="alert alert-success">
            <i class="fas fa-check-circle"></i> Thay đổi trạng thái user thành công!
        </div>
    </c:if>
    <c:if test="${param.success eq 'delete'}">
        <div class="alert alert-success">
            <i class="fas fa-check-circle"></i> Xóa user thành công!
        </div>
    </c:if>

    <section class="data-section">
        <div class="card">
            <div class="card-header">
                <h3>Danh sách User</h3>
            </div>
            <div class="card-body">
                <table class="data-table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Role</th>
                        <th>Trạng thái</th>
                        <th>Thao tác</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${empty users}">
                            <tr>
                                <td colspan="5" class="text-center">Không có user nào</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="user" items="${users}">
                                <tr>
                                    <td>${user.id}</td>
                                    <td>
                                        <strong>${user.username}</strong>
                                        <c:if test="${user.verified}">
                                            <i class="fas fa-check-circle text-success" title="Đã xác thực"></i>
                                        </c:if>
                                    </td>
                                    <td>
                                        <span class="badge ${user.admin ? 'badge-danger' : 'badge-primary'}">
                                                ${user.admin ? 'Admin' : 'Customer'}
                                        </span>
                                    </td>
                                    <td>
                                        <span class="badge ${user.statusClass}">
                                                ${user.statusText}
                                        </span>
                                    </td>
                                    <td>
                                        <div class="action-buttons">
                                            <a href="${pageContext.request.contextPath}/admin/users/view?id=${user.id}"
                                               class="btn btn-sm btn-info" title="Xem chi tiết">
                                                <i class="fas fa-eye"></i>
                                            </a>
                                            <button onclick="toggleStatus(${user.id}, ${user.active})"
                                                    class="btn btn-sm ${user.active ? 'btn-warning' : 'btn-success'}"
                                                    title="${user.active ? 'Khóa' : 'Mở khóa'}">
                                                <i class="fas ${user.active ? 'fa-lock' : 'fa-lock-open'}"></i>
                                            </button>
                                            <button onclick="confirmDelete(${user.id}, '${user.username}')"
                                                    class="btn btn-sm btn-danger"
                                                    title="Xóa">
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

                <c:if test="${totalPages > 1}">
                    <div class="pagination">
                        <c:if test="${currentPage > 1}">
                            <a href="?page=${currentPage - 1}">&laquo; Trước</a>
                        </c:if>

                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <a href="?page=${i}"
                               class="${i == currentPage ? 'active' : ''}">${i}</a>
                        </c:forEach>

                        <c:if test="${currentPage < totalPages}">
                            <a href="?page=${currentPage + 1}">Sau &raquo;</a>
                        </c:if>
                    </div>
                </c:if>
            </div>
        </div>
    </section>
</main>

<script>
    function toggleStatus(id, currentActive) {
        const action = currentActive ? 'khóa' : 'mở khóa';
        if (confirm('Bạn có chắc muốn ' + action + ' user này?')) {
            window.location.href = '${pageContext.request.contextPath}/admin/users/toggle-status?id=' + id;
        }
    }

    function confirmDelete(id, username) {
        if (confirm('Bạn có chắc muốn xóa user "' + username + '"?')) {
            window.location.href = '${pageContext.request.contextPath}/admin/users/delete?id=' + id;
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
