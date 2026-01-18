package vn.edu.nlu.fit.demo1.model;
import java.io.Serializable;
import java.math.BigDecimal;
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
    private int guests;
    private String roomType;
    private BigDecimal totalPrice;
    private String status;
    private String paymentStatus;
    private String notes;

    private String hotelName;
    private String hotelImage;
    private String cityName;

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

    public int getGuests() { return guests; }
    public void setGuests(int guests) { this.guests = guests; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }

    public String getHotelImage() { return hotelImage; }
    public void setHotelImage(String hotelImage) { this.hotelImage = hotelImage; }

    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }

    public String getFormattedPrice() {
        if (totalPrice == null) return "0";
        return String.format("%,.0f", totalPrice);
    }

    public String getStatusBadgeClass() {
        switch (status) {
            case "completed":
                return "success";
            case "confirmed":
                return "pending";
            case "cancelled":
                return "cancel";
            default:
                return "pending";
        }
    }

    public String getStatusText() {
        switch (status) {
            case "completed":
                return "Hoàn thành";
            case "confirmed":
                return "Sắp tới";
            case "cancelled":
                return "Đã hủy";
            case "pending":
                return "Chờ xác nhận";
            default:
                return status;
        }
    }
}