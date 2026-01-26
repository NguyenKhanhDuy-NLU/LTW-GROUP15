<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Giỏ hàng của bạn</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/cart.css">
</head>
<body>

<div class="cart-container">
    <h2><i class="fas fa-shopping-cart"></i> Các phòng đã đặt</h2>

    <c:if test="${empty sessionScope.cart or empty sessionScope.cart.list}">
        <div style="text-align: center; padding: 40px;">
            <p>Không có phòng được đặt!</p>
            <a href="${pageContext.request.contextPath}/index" class="btn btn-update">Quay lại trang chủ</a>
        </div>
    </c:if>

    <c:if test="${not empty sessionScope.cart and not empty sessionScope.cart.list}">
        <div style="text-align: right; margin-bottom: 10px;">
            <a href="${pageContext.request.contextPath}/cart/remove-all" class="btn btn-warning" onclick="return confirm('Bạn có chắc muốn xóa tất cả?')">
                <i class="fas fa-trash"></i> Xóa hết
            </a>
        </div>

        <table class="table">
            <thead>
            <tr>
                <th>Phòng</th>
                <th>Giá</th>
                <th>Số lượng</th>
                <th>Thành tiền</th>
                <th>Thao tác</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${sessionScope.cart.list}">
                <tr>
                    <td>
                        <div style="display: flex; gap: 10px; align-items: center;">
                            <img src="${pageContext.request.contextPath}/assets/images/${item.room.thumbnail}" class="img-thumb" onerror="this.src='https://placehold.co/100x70'"/>
                            <div>
                                <strong>${item.room.roomName}</strong><br>
                                <small>${item.room.roomType}</small>
                            </div>
                        </div>
                    </td>
                    <td><fmt:formatNumber value="${item.room.basePrice}" type="currency" currencySymbol="đ"/></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/cart/update" method="post" style="display: inline-flex; gap: 5px;">
                            <input type="hidden" name="id" value="${item.room.id}">
                            <input type="number" name="quantity" value="${item.quantity}" min="1" max="${item.room.quantity}" class="quantity-input">
                            <button type="submit" class="btn btn-update"><i class="fas fa-sync"></i></button>
                        </form>
                    </td>
                    <td><fmt:formatNumber value="${item.totalPrice}" type="currency" currencySymbol="đ"/></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/cart/dell?id=${item.room.id}" class="btn btn-danger" onclick="return confirm('Xóa phòng này?')">
                            <i class="fas fa-times"></i> Xóa
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <td colspan="3" style="text-align: right; font-weight: bold;">Tổng cộng:</td>
                <td colspan="2" class="total-price">
                    <fmt:formatNumber value="${sessionScope.cart.totalMoney}" type="currency" currencySymbol="đ"/>
                </td>
            </tr>
            </tfoot>
        </table>


        <c:set var="continueLink" value="${pageContext.request.contextPath}/index.jsp"/>

        <c:if test="${not empty sessionScope.cart.list}">
            <c:set var="firstItem" value="${sessionScope.cart.list[0]}"/>
            <c:set var="continueLink" value="${pageContext.request.contextPath}/hotel-detail?id=${firstItem.room.hotelId}"/>
        </c:if>

        <div style="margin-top: 20px; display: flex; justify-content: space-between;">
            <a href="${continueLink}" class="btn btn-secondary">
                <i class="fas fa-arrow-left"></i> Tiếp tục xem
            </a>

            <a href="${pageContext.request.contextPath}/payment" class="btn btn-success">
                Thanh toán ngay <i class="fas fa-arrow-right"></i>
            </a>
        </div>
    </c:if>
</div>

</body>
</html>
