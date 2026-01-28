package vn.edu.nlu.fit.demo1.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;

public class Hotel implements Serializable {
    private static final long serialVersionUID = 1L;

    // Fields theo database schema mới
    private int id;
    private int cityId;
    private String cityName;
    private String hotelName;
    private String slug;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String address;
    private String description;
    private String mainImage;
    private String coverImages;
    private Time checkInTime;
    private Time checkOutTime;
    private int totalRooms;
    private String cancellationPolicy;
    private String childPolicy;
    private String smokingPolicy;
    private int starRating;
    private boolean isFeatured;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    private BigDecimal pricePerNight;
    private BigDecimal discountPrice;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private boolean isActive;
    private int viewCount;
    private int imageCount;

    private String amenities;

    public Hotel() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCityId() { return cityId; }
    public void setCityId(int cityId) { this.cityId = cityId; }

    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }

    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }

    public void setName(String name) { this.hotelName = name; }
    public String getName() { return hotelName; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public BigDecimal getMinPrice() { return minPrice; }
    public void setMinPrice(BigDecimal minPrice) { this.minPrice = minPrice; }

    public BigDecimal getMaxPrice() { return maxPrice; }
    public void setMaxPrice(BigDecimal maxPrice) { this.maxPrice = maxPrice; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

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

    public int getTotalRooms() { return totalRooms; }
    public void setTotalRooms(int totalRooms) { this.totalRooms = totalRooms; }

    public String getCancellationPolicy() { return cancellationPolicy; }
    public void setCancellationPolicy(String cancellationPolicy) { this.cancellationPolicy = cancellationPolicy; }

    public String getChildPolicy() { return childPolicy; }
    public void setChildPolicy(String childPolicy) { this.childPolicy = childPolicy; }

    public String getSmokingPolicy() { return smokingPolicy; }
    public void setSmokingPolicy(String smokingPolicy) { this.smokingPolicy = smokingPolicy; }

    public int getStarRating() { return starRating; }
    public void setStarRating(int starRating) { this.starRating = starRating; }

    public boolean isFeatured() { return isFeatured; }
    public void setFeatured(boolean featured) { isFeatured = featured; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    public String getAmenities() { return amenities; }
    public void setAmenities(String amenities) { this.amenities = amenities; }


    public BigDecimal getPricePerNight() {
        return minPrice != null ? minPrice : pricePerNight;
    }
    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
        if (this.minPrice == null) {
            this.minPrice = pricePerNight;
        }
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }
    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;

    }

    public BigDecimal getLatitude() {
        return latitude;
    }
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;

    }

    public BigDecimal getLongitude() {
        return longitude;
    }
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;

    }

    public boolean isActive() {
        return isFeatured;
    }
    public void setActive(boolean active) {
        this.isActive = active;
        this.isFeatured = active;
    }
    public boolean getIsActive() {
        return isFeatured;
    }
    public void setIsActive(boolean active) {
        this.isActive = active;
        this.isFeatured = active;
    }

    public int getViewCount() { return viewCount; }
    public void setViewCount(int viewCount) { this.viewCount = viewCount; }

    public int getImageCount() { return imageCount; }
    public void setImageCount(int imageCount) { this.imageCount = imageCount; }


    public String getFormattedMinPrice() {
        if (minPrice == null) return "0";
        return String.format("%,.0f", minPrice).replace(",", ".");
    }

    public String getFormattedMaxPrice() {
        if (maxPrice == null) return "0";
        return String.format("%,.0f", maxPrice).replace(",", ".");
    }

    public String getFormattedPrice() {
        return getFormattedMinPrice();
    }

    public String getFormattedDiscountPrice() {
        if (discountPrice == null) return null;
        return String.format("%,.0f", discountPrice).replace(",", ".");
    }

    public String getStatusText() {
        return isFeatured ? "Nổi bật" : "Bình thường";
    }

    public String getStatusClass() {
        return isFeatured ? "badge-success" : "badge-secondary";
    }

    public String[] getAmenitiesArray() {
        if (amenities == null || amenities.isEmpty()) {
            return new String[0];
        }
        return amenities.split(",");
    }

    public String getStarDisplay() {
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < starRating; i++) {
            stars.append("⭐");
        }
        return stars.toString();
    }

    public boolean hasDiscount() {
        return discountPrice != null && discountPrice.compareTo(pricePerNight != null ? pricePerNight : minPrice) < 0;
    }

    public int getDiscountPercent() {
        if (!hasDiscount()) return 0;
        BigDecimal price = pricePerNight != null ? pricePerNight : minPrice;
        if (price == null || price.compareTo(BigDecimal.ZERO) == 0) return 0;

        BigDecimal discount = price.subtract(discountPrice);
        return discount.multiply(new BigDecimal(100))
                .divide(price, 0, BigDecimal.ROUND_HALF_UP)
                .intValue();
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", hotelName='" + hotelName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", starRating=" + starRating +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", isFeatured=" + isFeatured +
                '}';
    }
}