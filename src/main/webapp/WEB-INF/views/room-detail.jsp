<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết phòng</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/room.css">
</head>
<body>

<header class="main-header">
    <div class="container header">
        <button class="btn-back" onclick="history.back()">
            <i class="fas fa-arrow-left"></i> Quay về khách sạn
        </button>
    </div>
</header>

<section class="room-top container">
    <div>
        <h1>${room.roomName}</h1>
        <p class="room-type">
            ${room.roomType}
        </p>
    </div>

    <div class="price-box">
        <div class="price-line">
            <span class="price">
                <fmt:formatNumber value="${room.basePrice}" type="number"/>đ
            </span>
            <span class="per-night">/ đêm</span>
        </div>

        <button class="btn-book">Đặt phòng ngay</button>
        <small class="note">Đã bao gồm thuế & phí</small>
    </div>
</section>

<section class="room-content container">

    <div class="gallery">
        <img src="${pageContext.request.contextPath}/assets/images/pkshn1.1.jpg"
             class="main-img"
             alt="${room.roomName}">
    </div>

    <div class="info-card">
        <h3>Thông tin phòng</h3>
        <ul>
            <li>
                <i class="fas fa-user-friends"></i>
                Tối đa ${room.maxPeople} người
            </li>
            <li>
                <i class="fas fa-expand"></i>
                Diện tích: ${room.size}
            </li>
            <li>
                <i class="fas fa-eye"></i>
                View: ${room.view}
            </li>
            <li>
                <i class="fas fa-door-open"></i>
                Còn ${room.quantity} phòng
            </li>
        </ul>
    </div>
</section>

<section class="description container">
    <h2>Mô tả phòng</h2>
    <p>
        ${room.description}
    </p>
</section>

</body>
</html>
