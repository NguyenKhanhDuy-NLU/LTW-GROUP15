package vn.edu.nlu.fit.demo1.model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HotelDetail {
    private int id;
    private int cityId;
    private String hotelName;
    private String slug;
    private int starRating;
    private double minPrice;
    private String address;
    private String description;
    private String mainImage;
    private String coverImages;
    private boolean isFeatured;
    private Time checkInTime;
    private Time checkOutTime;
    private String cancellationPolicy;
    private String childPolicy;
    private String smokingPolicy;

    private List<Amenity> amenities = new ArrayList<>();

    public List<String> getGalleryImages() {
        if (coverImages == null || coverImages.length() < 3) return new ArrayList<>();
        String clean = coverImages.replace("[", "").replace("]", "").replace("\"", "");
        return Arrays.asList(clean.split(","));
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCityId() { return cityId; }
    public void setCityId(int cityId) { this.cityId = cityId; }
    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    public int getStarRating() { return starRating; }
    public void setStarRating(int starRating) { this.starRating = starRating; }
    public double getMinPrice() { return minPrice; }
    public void setMinPrice(double minPrice) { this.minPrice = minPrice; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getMainImage() { return mainImage; }
    public void setMainImage(String mainImage) { this.mainImage = mainImage; }
    public String getCoverImages() { return coverImages; }
    public void setCoverImages(String coverImages) { this.coverImages = coverImages; }
    public boolean isFeatured() { return isFeatured; }
    public void setFeatured(boolean featured) { isFeatured = featured; }
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
