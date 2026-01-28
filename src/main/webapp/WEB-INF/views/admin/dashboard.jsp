<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Group15</title>
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
        <a href="${pageContext.request.contextPath}/admin/dashboard" class="menu-item active">
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
        <h1>Dashboard</h1>
        <div class="user-info">
            <span>Xin chào, ${sessionScope.user.fullName}</span>
        </div>
    </header>

    <section class="stats-grid">
        <div class="stat-card blue">
            <div class="stat-icon">
                <i class="fas fa-hotel"></i>
            </div>
            <div class="stat-info">
                <h3>Tổng Khách sạn</h3>
                <p class="stat-number">${stats.totalHotels}</p>
            </div>
        </div>

        <div class="stat-card green">
            <div class="stat-icon">
                <i class="fas fa-calendar-check"></i>
            </div>
            <div class="stat-info">
                <h3>Tổng Booking</h3>
                <p class="stat-number">${stats.totalBookings}</p>
                <small>${stats.pendingBookings} chờ xử lý</small>
            </div>
        </div>

        <div class="stat-card orange">
            <div class="stat-icon">
                <i class="fas fa-users"></i>
            </div>
            <div class="stat-info">
                <h3>Tổng User</h3>
                <p class="stat-number">${stats.totalUsers}</p>
            </div>
        </div>

        <div class="stat-card purple">
            <div class="stat-icon">
                <i class="fas fa-dollar-sign"></i>
            </div>
            <div class="stat-info">
                <h3>Doanh thu tháng</h3>
                <p class="stat-number">${stats.formattedMonthRevenue}</p>
                <small>Tổng: ${stats.formattedTotalRevenue}</small>
            </div>
        </div>
    </section>

    <section class="quick-actions">
        <h2>Thao tác nhanh</h2>
        <div class="action-buttons">
            <a href="${pageContext.request.contextPath}/admin/hotels/add" class="btn btn-primary">
                <i class="fas fa-plus"></i> Thêm Khách sạn
            </a>
            <a href="${pageContext.request.contextPath}/admin/bookings?status=pending" class="btn btn-warning">
                <i class="fas fa-clock"></i> Xử lý Booking chờ
            </a>
            <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-info">
                <i class="fas fa-user-plus"></i> Quản lý User
            </a>
        </div>
    </section>
</main>

<script src="${pageContext.request.contextPath}/assets/js/admin-dashboard.js"></script>
</body>
</html>