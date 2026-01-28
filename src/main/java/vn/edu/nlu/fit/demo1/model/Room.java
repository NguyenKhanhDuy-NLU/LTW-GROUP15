package vn.edu.nlu.fit.demo1.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Room implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int hotelId;
    private String hotelName;
    private String roomName;
    private String roomType;
    private int maxPeople;
    private BigDecimal basePrice;
    private int quantity;
    private boolean isAvailable;
    private String images;
    private String description;
    private String size;
    private String view;
    private Timestamp createdAt;

    public Room() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getHotelId() { return hotelId; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }

    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }

    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public int getMaxPeople() { return maxPeople; }
    public void setMaxPeople(int maxPeople) { this.maxPeople = maxPeople; }

    public BigDecimal getBasePrice() { return basePrice; }
    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getView() { return view; }
    public void setView(String view) { this.view = view; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public String getFormattedPrice() {
        if (basePrice == null) return "0";
        return String.format("%,.0f", basePrice).replace(",", ".");
    }

    public String getStatusText() {
        return isAvailable ? "Còn trống" : "Hết phòng";
    }

    public String getStatusClass() {
        return isAvailable ? "badge-success" : "badge-danger";
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", hotelId=" + hotelId +
                ", roomName='" + roomName + '\'' +
                ", roomType='" + roomType + '\'' +
                ", basePrice=" + basePrice +
                ", maxPeople=" + maxPeople +
                ", quantity=" + quantity +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
