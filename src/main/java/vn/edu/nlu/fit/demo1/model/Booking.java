package vn.edu.nlu.fit.demo1.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int userId;
    private int hotelId;
    private int roomId;
    private String bookingCode;
    private Date checkInDate;
    private Date checkOutDate;
    private int numGuests;
    private String roomType;
    private BigDecimal totalPrice;
    private String status;
    private String paymentStatus;
    private String specialRequests;
    private Timestamp bookingDate;
    private Timestamp updatedAt;

    private String hotelName;
    private String hotelImage;
    private String cityName;
    private String username;
    private String userFullName;
    private String userEmail;

    public Booking() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getHotelId() { return hotelId; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public String getBookingCode() { return bookingCode; }
    public void setBookingCode(String bookingCode) { this.bookingCode = bookingCode; }

    public Date getCheckInDate() { return checkInDate; }
    public void setCheckInDate(Date checkInDate) { this.checkInDate = checkInDate; }

    public Date getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(Date checkOutDate) { this.checkOutDate = checkOutDate; }

    public int getNumGuests() { return numGuests; }
    public void setNumGuests(int numGuests) { this.numGuests = numGuests; }

    public int getGuests() { return numGuests; }
    public void setGuests(int guests) { this.numGuests = guests; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getSpecialRequests() { return specialRequests; }
    public void setSpecialRequests(String specialRequests) { this.specialRequests = specialRequests; }

    public String getNotes() { return specialRequests; }
    public void setNotes(String notes) { this.specialRequests = notes; }

    public Timestamp getBookingDate() { return bookingDate; }
    public void setBookingDate(Timestamp bookingDate) { this.bookingDate = bookingDate; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }

    public String getHotelImage() { return hotelImage; }
    public void setHotelImage(String hotelImage) { this.hotelImage = hotelImage; }

    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getUserFullName() { return userFullName; }
    public void setUserFullName(String userFullName) { this.userFullName = userFullName; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getFormattedPrice() {
        if (totalPrice == null) return "0";
        return String.format("%,.0f", totalPrice);
    }

    public String getStatusBadgeClass() {
        if (status == null) return "pending";
        switch (status.toLowerCase()) {
            case "completed":
                return "success";
            case "confirmed":
                return "info";
            case "cancelled":
                return "danger";
            case "pending":
            default:
                return "warning";
        }
    }

    public String getStatusText() {
        if (status == null) return "Chưa xác định";
        switch (status.toLowerCase()) {
            case "completed":
                return "Hoàn thành";
            case "confirmed":
                return "Đã xác nhận";
            case "cancelled":
                return "Đã hủy";
            case "pending":
                return "Chờ xác nhận";
            default:
                return status;
        }
    }

    public String getPaymentStatusText() {
        if (paymentStatus == null) return "Chưa thanh toán";
        switch (paymentStatus.toLowerCase()) {
            case "paid":
                return "Đã thanh toán";
            case "refunded":
                return "Đã hoàn tiền";
            case "pending":
            default:
                return "Chờ thanh toán";
        }
    }
}