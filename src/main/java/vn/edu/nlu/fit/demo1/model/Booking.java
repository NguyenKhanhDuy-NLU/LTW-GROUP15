package vn.edu.nlu.fit.demo1.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int userId;
    private int hotelId;
    private int roomId;              // ✅ THÊM
    private Date checkIn;
    private Date checkOut;
    private int guests;
    private String roomType;         // ✅ THÊM
    private BigDecimal finalPrice;
    private BigDecimal totalPrice;   // Alias
    private String bookingCode;
    private String status;
    private String paymentStatus;
    private String notes;
    private String specialRequests;
    private Timestamp bookingDate;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // For display
    private String userFullName;
    private String userEmail;
    private String username;
    private String hotelName;
    private String hotelImage;
    private String cityName;

    public Booking() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getHotelId() { return hotelId; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public Date getCheckIn() { return checkIn; }
    public void setCheckIn(Date checkIn) { this.checkIn = checkIn; }
    public Date getCheckInDate() { return checkIn; }
    public void setCheckInDate(Date d) { this.checkIn = d; }

    public Date getCheckOut() { return checkOut; }
    public void setCheckOut(Date checkOut) { this.checkOut = checkOut; }
    public Date getCheckOutDate() { return checkOut; }
    public void setCheckOutDate(Date d) { this.checkOut = d; }

    public int getGuests() { return guests; }
    public void setGuests(int guests) { this.guests = guests; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public BigDecimal getFinalPrice() { return finalPrice; }
    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
        this.totalPrice = finalPrice;
    }

    public BigDecimal getTotalPrice() {
        return finalPrice != null ? finalPrice : totalPrice;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        this.finalPrice = totalPrice;
    }

    public String getBookingCode() { return bookingCode; }
    public void setBookingCode(String bookingCode) { this.bookingCode = bookingCode; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    public String getUserFullName() { return userFullName; }
    public void setUserFullName(String userFullName) { this.userFullName = userFullName; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }

    public String getHotelImage() { return hotelImage; }
    public void setHotelImage(String hotelImage) { this.hotelImage = hotelImage; }

    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }

    // Utility methods
    public String getFormattedPrice() {
        BigDecimal price = finalPrice != null ? finalPrice : totalPrice;
        if (price == null) return "0";
        return String.format("%,.0f", price).replace(",", ".");
    }

    public String getStatusText() {
        if (status == null) return "Chưa xác định";
        switch (status.toLowerCase()) {
            case "completed": return "Hoàn thành";
            case "confirmed": return "Đã xác nhận";
            case "cancelled": return "Đã hủy";
            case "pending": return "Chờ xác nhận";
            default: return status;
        }
    }

    public String getStatusClass() {
        if (status == null) return "badge-warning";
        switch (status.toLowerCase()) {
            case "completed": return "badge-success";
            case "confirmed": return "badge-info";
            case "cancelled": return "badge-danger";
            case "pending":
            default: return "badge-warning";
        }
    }

    @Override
    public String toString() {
        return "Booking{id=" + id + ", bookingCode='" + bookingCode + "', status='" + status + "'}";
    }
}