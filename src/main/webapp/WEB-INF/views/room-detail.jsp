<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${room.roomName} - Chi tiết</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/room.css">
</head>
<body>
<header class="main-header">
    <div class="container header">
        <button class="btn-back" onclick="window.history.back()">
            <i class="fas fa-arrow-left"></i> Quay về khách sạn
        </button>
    </div>
</header>

<section class="room-top container">
    <div class="room-title">
        <h1>${room.roomName}</h1>
        <p class="room-type">${room.roomType} · Được yêu thích nhất</p>
    </div>

    <div class="price-box">
        <div class="price-line">
            <span class="price">
                <fmt:formatNumber value="${room.basePrice}" type="currency" currencySymbol="đ"/>
            </span>
            <span class="per-night">/ đêm</span>
        </div>
        <form action="${pageContext.request.contextPath}/cart/add" method="post" style="margin: 0;">
            <input type="hidden" name="id" value="${room.id}">

            <input type="hidden" name="quantity" value="1">

            <button type="submit" class="btn-book"
            ${room.quantity <= 0 ? 'disabled style="opacity: 0.6; cursor: not-allowed;"' : ''}>
                ${room.quantity > 0 ? 'Đặt phòng ngay' : 'Hết phòng'}
            </button>
        </form>
        <small class="note">Đã bao gồm thuế & phí</small>
    </div>
</section>

<section class="room-content container">

    <div class="gallery">
        <img src="${pageContext.request.contextPath}/assets/images/${room.thumbnail}"
             class="main-img"
             alt="${room.roomName}"
             onerror="this.src='https://placehold.co/800x400?text=No+Image'"/>
    </div>

    <div class="info-card">
        <h3>Thông tin phòng</h3>
        <ul>
            <li>
                <i class="fas fa-user-friends"></i>
                Tối đa: <strong>${room.maxPeople} người</strong>
            </li>
            <li>
                <i class="fas fa-ruler-combined"></i>
                Diện tích: <strong>${room.size}</strong>
            </li>
            <li>
                <i class="fas fa-eye"></i>
                Hướng nhìn: <strong>${room.view}</strong>
            </li>
            <li>
                <i class="fas fa-door-open"></i>
                Tình trạng:
                <strong style="color: ${room.quantity > 0 ? '#00bfa5' : '#ff4d4f'}">
                    ${room.quantity > 0 ? 'Còn phòng' : 'Hết phòng'}
                </strong>
            </li>
        </ul>
    </div>
</section>

<section class="description container">
    <h2>Mô tả phòng</h2>
    <p>${room.description}</p>
</section>

<section class="amenities-section container">
    <h2>Tiện nghi</h2> <ul class="amenities-list">
    <c:if test="${empty room.amenities}">
        <li style="color: #888;">Đang cập nhật...</li>
    </c:if>
    <c:forEach var="a" items="${room.amenities}">
        <li>
            <i class="${a.amenityIcon}"></i> ${a.amenityName}
        </li>
    </c:forEach>
</ul>
</section>


</body>
</html>
