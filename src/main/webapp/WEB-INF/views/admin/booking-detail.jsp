<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="vn.edu.nlu.fit.demo1.model.Booking" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%
    Booking booking = (Booking) request.getAttribute("booking");
    String mode = (String) request.getAttribute("mode"); // "view" hoặc "edit"
    boolean isEditMode = "edit".equals(mode);

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi tiết đặt phòng - Admin Panel</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/booking-detail.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

</head>
<body>

<div class="detail-container">
    <div class="detail-header">
        <h2>
            <i class="fas fa-calendar-check"></i>
            <%= isEditMode ? "Chỉnh sửa đặt phòng" : "Chi tiết đặt phòng" %>
        </h2>
        <div class="detail-actions">
            <% if (!isEditMode) { %>
            <a href="${pageContext.request.contextPath}/admin/bookings/edit?id=<%= booking.getId() %>"
               class="btn btn-primary">
                <i class="fas fa-edit"></i> Chỉnh sửa
            </a>
            <a href="${pageContext.request.contextPath}/admin/bookings/delete?id=<%= booking.getId() %>"
               class="btn btn-danger"
               onclick="return confirm('Bạn có chắc muốn xóa đặt phòng này?')">
                <i class="fas fa-trash"></i> Xóa
            </a>
            <% } %>
            <a href="${pageContext.request.contextPath}/admin/bookings" class="btn btn-secondary">
                <i class="fas fa-arrow-left"></i> Quay lại
            </a>
        </div>
    </div>

    <div class="detail-content">
        <form method="post" action="${pageContext.request.contextPath}/admin/bookings/update" id="bookingForm">
            <input type="hidden" name="id" value="<%= booking.getId() %>">

            <div class="info-grid">
                <div class="hotel-info">
                    <% if (booking.getHotelImage() != null && !booking.getHotelImage().isEmpty()) { %>
                    <img src="<%= booking.getHotelImage() %>" alt="Hotel" class="hotel-image">
                    <% } %>
                    <div class="hotel-details">
                        <h3><%= booking.getHotelName() %></h3>
                        <p><i class="fas fa-map-marker-alt"></i> <%= booking.getCityName() %></p>
                        <p><i class="fas fa-bed"></i> Loại phòng: <%= booking.getRoomType() %></p>
                        <p><i class="fas fa-users"></i> Số khách: <%= booking.getGuests() %></p>
                    </div>
                </div>

                <div class="info-group">
                    <div class="info-label">Mã đặt phòng</div>
                    <div class="info-value"><strong><%= booking.getBookingCode() %></strong></div>
                </div>

                <!-- User ID -->
                <div class="info-group">
                    <div class="info-label">ID Người dùng</div>
                    <div class="info-value">#<%= booking.getUserId() %></div>
                </div>

                <div class="info-group">
                    <div class="info-label">Ngày nhận phòng</div>
                    <div class="info-value">
                        <i class="fas fa-calendar"></i>
                        <%= sdf.format(booking.getCheckInDate()) %>
                    </div>
                </div>

                <div class="info-group">
                    <div class="info-label">Ngày trả phòng</div>
                    <div class="info-value">
                        <i class="fas fa-calendar"></i>
                        <%= sdf.format(booking.getCheckOutDate()) %>
                    </div>
                </div>

                <div class="info-group">
                    <div class="info-label">Tổng tiền</div>
                    <div class="info-value" style="color: #28a745; font-size: 20px;">
                        <strong><%= currencyFormat.format(booking.getTotalPrice()) %></strong>
                    </div>
                </div>

                <div class="info-group">
                    <div class="info-label">Trạng thái đặt phòng</div>
                    <div class="info-value">
                        <% if (isEditMode) { %>
                        <select name="status" class="form-control">
                            <option value="pending" <%= "pending".equals(booking.getStatus()) ? "selected" : "" %>>Chờ xác nhận</option>
                            <option value="confirmed" <%= "confirmed".equals(booking.getStatus()) ? "selected" : "" %>>Đã xác nhận</option>
                            <option value="completed" <%= "completed".equals(booking.getStatus()) ? "selected" : "" %>>Hoàn thành</option>
                            <option value="cancelled" <%= "cancelled".equals(booking.getStatus()) ? "selected" : "" %>>Đã hủy</option>
                        </select>
                        <% } else { %>
                        <span class="status-badge status-<%= booking.getStatus() %>">
                            <%= booking.getStatus() %>
                        </span>
                        <% } %>
                    </div>
                </div>

                <div class="info-group">
                    <div class="info-label">Trạng thái thanh toán</div>
                    <div class="info-value">
                        <% if (isEditMode) { %>
                        <select name="paymentStatus" class="form-control">
                            <option value="pending" <%= "pending".equals(booking.getPaymentStatus()) ? "selected" : "" %>>Chờ thanh toán</option>
                            <option value="paid" <%= "paid".equals(booking.getPaymentStatus()) ? "selected" : "" %>>Đã thanh toán</option>
                            <option value="failed" <%= "failed".equals(booking.getPaymentStatus()) ? "selected" : "" %>>Thất bại</option>
                        </select>
                        <% } else { %>
                        <span class="status-badge payment-<%= booking.getPaymentStatus() %>">
                            <%= booking.getPaymentStatus() %>
                        </span>
                        <% } %>
                    </div>
                </div>

                <div class="info-group full-width">
                    <div class="info-label">Ghi chú</div>
                    <div class="info-value">
                        <% if (isEditMode) { %>
                        <textarea name="notes" class="form-control"><%= booking.getNotes() != null ? booking.getNotes() : "" %></textarea>
                        <% } else { %>
                        <%= booking.getNotes() != null && !booking.getNotes().isEmpty() ? booking.getNotes() : "<em style='color: #999;'>Không có ghi chú</em>" %>
                        <% } %>
                    </div>
                </div>
            </div>

            <% if (isEditMode) { %>
            <div class="form-actions">
                <button type="submit" class="btn btn-success">
                    <i class="fas fa-save"></i> Lưu thay đổi
                </button>
                <a href="${pageContext.request.contextPath}/admin/bookings/view?id=<%= booking.getId() %>"
                   class="btn btn-secondary">
                    <i class="fas fa-times"></i> Hủy
                </a>
            </div>
            <% } %>
        </form>
    </div>
</div>

</body>
</html>
