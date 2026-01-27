<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Quản lý Review - Admin</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin-dashboard.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
  <style>
    .star-rating {
      color: #ffc107;
      font-size: 1.1em;
    }
    .review-comment {
      max-width: 300px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
  </style>
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
    <a href="${pageContext.request.contextPath}/admin/reviews" class="menu-item active">
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
    <h1>Quản lý Review</h1>
    <div class="user-info">
      <span>Xin chào, ${sessionScope.user.fullName}</span>
    </div>
  </header>

  <section class="filter-section">
    <div class="stats-info">
      <p>Tổng: <strong>${totalReviews}</strong> review</p>
    </div>
  </section>

  <section class="data-section">
    <div class="card">
      <div class="card-header">
        <h3>Danh sách Review</h3>
      </div>
      <div class="card-body">
        <c:if test="${not empty param.success}">
          <div class="alert alert-success">
            <c:choose>
              <c:when test="${param.success eq 'delete'}">Xóa review thành công!</c:when>
              <c:otherwise>Thao tác thành công!</c:otherwise>
            </c:choose>
          </div>
        </c:if>

        <table class="data-table">
          <thead>
          <tr>
            <th>ID</th>
            <th>User</th>
            <th>Khách sạn</th>
            <th>Đánh giá</th>
            <th>Bình luận</th>
            <th>Ngày tạo</th>
            <th>Thao tác</th>
          </tr>
          </thead>
          <tbody>
          <c:choose>
            <c:when test="${empty reviews}">
              <tr>
                <td colspan="7" class="text-center">Không có review nào</td>
              </tr>
            </c:when>
            <c:otherwise>
              <c:forEach var="review" items="${reviews}">
                <tr>
                  <td>${review.id}</td>
                  <td>
                    <strong>${review.userFullName}</strong><br>
                    <small>${review.username}</small>
                  </td>
                  <td>${review.hotelName}</td>
                  <td>
                    <div class="star-rating">
                      <c:forEach begin="1" end="5" var="i">
                        <i class="fa${i <= review.rating ? 's' : 'r'} fa-star"></i>
                      </c:forEach>
                    </div>
                    <small>(${review.rating}/5)</small>
                  </td>
                  <td>
                    <div class="review-comment" title="${review.comment}">
                        ${review.comment}
                    </div>
                  </td>
                  <td>
                    <fmt:formatDate value="${review.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
                  </td>
                  <td>
                    <div class="action-buttons">
                      <a href="${pageContext.request.contextPath}/admin/reviews/view?id=${review.id}"
                         class="btn btn-sm btn-info" title="Xem chi tiết">
                        <i class="fas fa-eye"></i>
                      </a>
                      <a href="${pageContext.request.contextPath}/admin/reviews/delete?id=${review.id}"
                         class="btn btn-sm btn-danger"
                         onclick="return confirm('Bạn có chắc muốn xóa review này?')"
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
              <a href="?page=${currentPage - 1}">&laquo; Trước</a>
            </c:if>

            <c:forEach begin="1" end="${totalPages}" var="i">
              <a href="?page=${i}" class="${i == currentPage ? 'active' : ''}">${i}</a>
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
</body>
</html>
