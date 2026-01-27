package vn.edu.nlu.fit.demo1.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Room {
    private int id;
    private int hotelId;
    private String roomName;
    private String roomType;
    private int maxPeople;
    private double basePrice;
    private int quantity;
    private String images;
    private String description;
    private String size;
    private String view;

    public String getThumbnail() {
        List<String> imgs = getGalleryImages();
        return imgs.isEmpty() ? "placeholder.jpg" : imgs.get(0).trim();
    }

    public List<String> getGalleryImages() {
        if (images == null || images.length() < 3) return new ArrayList<>();
        String clean = images.replace("[", "").replace("]", "").replace("\"", "");
        return Arrays.asList(clean.split(","));
    }


    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getHotelId() { return hotelId; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }
    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }
    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }
    public int getMaxPeople() { return maxPeople; }
    public void setMaxPeople(int maxPeople) { this.maxPeople = maxPeople; }
    public double getBasePrice() { return basePrice; }
    public void setBasePrice(double basePrice) { this.basePrice = basePrice; }
    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    public String getView() { return view; }
    public void setView(String view) { this.view = view; }
}
