package vn.edu.nlu.fit.demo1.model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HotelDetail {
    private int id;
    private String hotelName;
    private int starRating;
    private double averageStar;
    private int reviewCount;
    private String address;
    private String mapImage;
    private String description;
    private String mainImage;
    private String coverImages; // JSON String
    private Time checkInTime;
    private Time checkOutTime;
    private String cancellationPolicy;
    private String childPolicy;
    private String smokingPolicy;

    private List<Amenity> amenities = new ArrayList<>();

    // Helper xử lý ảnh
    public List<String> getGalleryImages() {
        if (coverImages == null || coverImages.length() < 3) return new ArrayList<>();
        String clean = coverImages.replace("[", "").replace("]", "").replace("\"", "");
        return Arrays.asList(clean.split(","));
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }
    public int getStarRating() { return starRating; }
    public void setStarRating(int starRating) { this.starRating = starRating; }
    public double getAverageStar() { return averageStar; }
    public void setAverageStar(double averageStar) { this.averageStar = averageStar; }
    public int getReviewCount() { return reviewCount; }
    public void setReviewCount(int reviewCount) { this.reviewCount = reviewCount; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getMapImage() { return mapImage; }
    public void setMapImage(String mapImage) { this.mapImage = mapImage; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getMainImage() { return mainImage; }
    public void setMainImage(String mainImage) { this.mainImage = mainImage; }
    public String getCoverImages() { return coverImages; }
    public void setCoverImages(String coverImages) { this.coverImages = coverImages; }
    public Time getCheckInTime() { return checkInTime; }
    public void setCheckInTime(Time checkInTime) { this.checkInTime = checkInTime; }
    public Time getCheckOutTime() { return checkOutTime; }
    public void setCheckOutTime(Time checkOutTime) { this.checkOutTime = checkOutTime; }
    public String getCancellationPolicy() { return cancellationPolicy; }
    public void setCancellationPolicy(String cancellationPolicy) { this.cancellationPolicy = cancellationPolicy; }
    public String getChildPolicy() { return childPolicy; }
    public void setChildPolicy(String childPolicy) { this.childPolicy = childPolicy; }
    public String getSmokingPolicy() { return smokingPolicy; }
    public void setSmokingPolicy(String smokingPolicy) { this.smokingPolicy = smokingPolicy; }
    public List<Amenity> getAmenities() { return amenities; }
    public void setAmenities(List<Amenity> amenities) { this.amenities = amenities; }
}
