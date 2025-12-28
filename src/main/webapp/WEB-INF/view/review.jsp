<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="vn.edu.nlu.fit.demo1.dao.ReviewDAO.RatingStats" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đánh giá - ${hotel.hotelName}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/reviews.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
<header class="header">
    <div class="container">
        <div class="header-content">
            <a href="${pageContext.request.contextPath}/" class="logo">
                <img src="${pageContext.request.contextPath}/assets/images/logo.jpg" alt="Group15">
            </a>
            <h1>Đánh giá khách sạn</h1>

            <div class="header-user">
                <c:choose>
                    <c:when test="${isLoggedIn}">
                        <div class="user-menu">
                            <a href="#" class="nav-user-icon" title="${displayName}">
                                <i class="fas fa-user"></i>
                            </a>
                            <div class="user-dropdown">
                                <a href="${pageContext.request.contextPath}/user">
                                    <i class="fas fa-user-circle"></i> Tài khoản
                                </a>
                                <a href="${pageContext.request.contextPath}/history">
                                    <i class="fas fa-history"></i> Lịch sử đặt phòng
                                </a>
                                <a href="${pageContext.request.contextPath}/logout">
                                    <i class="fas fa-sign-out-alt"></i> Đăng xuất
                                </a>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login" class="nav-login-btn">
                        <i class="fas fa-sign-in-alt"></i> Đăng nhập
                    </a>
                    <a href="${pageContext.request.contextPath}/register" class="nav-register-btn">
                        <i class="fas fa-user-plus"></i> Đăng ký
                    </a>
                </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</header>

<main class="main-container">

    <section class="hotel-info">
        <img src="${pageContext.request.contextPath}/assets/images/${hotel.mainImage}"
             alt="${hotel.hotelName}">
        <div class="hotel-details">
            <h2>${hotel.hotelName}</h2>
            <p>
                <i class="fas fa-map-marker-alt"></i>
                ${hotel.address}
            </p>
        </div>
    </section>

    <section class="rating-summary">
        <div class="rating-overview">
            <div class="average-rating">
                <div class="rating-number">
                    <fmt:formatNumber value="${stats.averageRating}" maxFractionDigits="1"/>
                </div>

                <div class="rating-stars">
                    <%
                        RatingStats stats = (RatingStats) request.getAttribute("stats");
                        int avgStars = (int) Math.round(stats.averageRating);
                        pageContext.setAttribute("avgStars", avgStars);
                    %>
                    <c:forEach begin="1" end="5" var="i">
                        <span class="${i <= avgStars ? 'filled' : 'empty'}">★</span>
                    </c:forEach>
                </div>

                <div class="rating-count">
                    ${totalReviews} đánh giá
                </div>
            </div>

            <div class="rating-breakdown">
                <%
                    for (int star = 5; star >= 1; star--) {
                        int percent = stats.getStarPercentage(star);
                        pageContext.setAttribute("star", star);
                        pageContext.setAttribute("percent", percent);
                %>
                <div class="rating-bar">
                    <span class="star-label">${star} <i class="fas fa-star"></i></span>
                    <div class="bar">
                        <div class="bar-fill" style="width:${percent}%"></div>
                    </div>
                    <span class="percentage">${percent}%</span>
                </div>
                <% } %>
            </div>
        </div>

        <c:choose>
            <c:when test="${isLoggedIn and canReview}">
                <button class="btn-write-review" onclick="openReviewModal()">
                    <i class="fas fa-pen"></i> Viết đánh giá
                </button>
            </c:when>
            <c:when test="${not isLoggedIn}">
                <a href="${pageContext.request.contextPath}/login" class="btn-write-review">
                    <i class="fas fa-sign-in-alt"></i> Đăng nhập để đánh giá
                </a>
            </c:when>
        </c:choose>
    </section>

    <section class="reviews-list">
        <h3>Tất cả đánh giá (${totalReviews})</h3>

        <c:choose>
            <c:when test="${empty reviews}">
                <div class="no-reviews">
                    <i class="fas fa-comment-slash"></i>
                    <p>Chưa có đánh giá nào cho khách sạn này</p>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="review" items="${reviews}">
                    <div class="review-card">
                        <div class="review-header">
                            <div class="user-info">
                                <c:choose>
                                    <c:when test="${not empty review.userAvatar}">
                                        <img src="${pageContext.request.contextPath}${review.userAvatar}"
                                             alt="${review.userName}" class="user-avatar">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="user-avatar-placeholder">
                                                ${fn:toUpperCase(fn:substring(review.userName,0,1))}
                                        </div>
                                    </c:otherwise>
                                </c:choose>

                                <div>
                                    <div class="user-name">${review.userName}</div>
                                    <div class="review-time">${review.timeAgo}</div>
                                </div>
                            </div>

                            <div class="review-rating">
                                <c:forEach var="filled" items="${review.starDisplay}">
                                    <span class="${filled ? 'filled' : 'empty'}">★</span>
                                </c:forEach>
                            </div>
                        </div>

                        <div class="review-content">
                                ${review.comment}
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>

        <c:if test="${totalPages > 1}">
            <div class="pagination">
                <c:if test="${currentPage > 1}">
                    <a href="?hotelId=${hotel.id}&page=${currentPage - 1}" class="page-link">
                        <i class="fas fa-chevron-left"></i>
                    </a>
                </c:if>

                <c:forEach begin="1" end="${totalPages}" var="i">
                    <a href="?hotelId=${hotel.id}&page=${i}"
                       class="page-link ${i == currentPage ? 'active' : ''}">
                            ${i}
                    </a>
                </c:forEach>

                <c:if test="${currentPage < totalPages}">
                    <a href="?hotelId=${hotel.id}&page=${currentPage + 1}" class="page-link">
                        <i class="fas fa-chevron-right"></i>
                    </a>
                </c:if>
            </div>
        </c:if>
    </section>

</main>

<div id="reviewModal" class="modal" style="display:none">
    <div class="modal-content">
        <div class="modal-header">
            <h3>Viết đánh giá</h3>
            <span class="close" onclick="closeReviewModal()">&times;</span>
        </div>

        <form id="reviewForm" class="modal-body">
            <input type="hidden" name="hotelId" value="${hotel.id}">

            <div class="form-group">
                <label>Đánh giá của bạn <span class="required">*</span></label>
                <div class="star-rating">
                    <% for (int s = 5; s >= 1; s--) { %>
                    <input type="radio" name="rating" value="<%= s %>" id="star<%= s %>">
                    <label for="star<%= s %>">★</label>
                    <% } %>
                </div>
                <span id="ratingError" class="error-message"></span>
            </div>

            <div class="form-group">
                <label for="comment">Nội dung đánh giá <span class="required">*</span></label>
                <textarea id="comment" name="comment" rows="5"
                          placeholder="Chia sẻ trải nghiệm của bạn về khách sạn này..."
                          required minlength="10" maxlength="1000"></textarea>
                <span class="char-count">0 / 1000</span>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn-secondary" onclick="closeReviewModal()">
                    Hủy
                </button>
                <button type="submit" class="btn-primary">
                    <i class="fas fa-paper-plane"></i> Gửi đánh giá
                </button>
            </div>
        </form>
    </div>
</div>

<script src="${pageContext.request.contextPath}/assets/js/reviews.js"></script>
</body>
</html>