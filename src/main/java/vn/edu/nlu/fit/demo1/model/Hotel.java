package vn.edu.nlu.fit.demo1.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Hotel implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private int cityId;
    private String cityName;
    private String address;
    private int starRating;
    private BigDecimal pricePerNight;
    private BigDecimal discountPrice;
    private String description;
    private String mainImage;
    private String amenities;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private boolean isActive;
    private int viewCount;
    private int imageCount;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Hotel() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCityId() { return cityId; }
    public void setCityId(int cityId) { this.cityId = cityId; }

    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public int getStarRating() { return starRating; }
    public void setStarRating(int starRating) { this.starRating = starRating; }

    public BigDecimal getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(BigDecimal pricePerNight) { this.pricePerNight = pricePerNight; }

    public BigDecimal getDiscountPrice() { return discountPrice; }
    public void setDiscountPrice(BigDecimal discountPrice) { this.discountPrice = discountPrice; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getMainImage() { return mainImage; }
    public void setMainImage(String mainImage) { this.mainImage = mainImage; }

    public String getAmenities() { return amenities; }
    public void setAmenities(String amenities) { this.amenities = amenities; }

    public BigDecimal getLatitude() { return latitude; }
    public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }

    public BigDecimal getLongitude() { return longitude; }
    public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public int getViewCount() { return viewCount; }
    public void setViewCount(int viewCount) { this.viewCount = viewCount; }

    public int getImageCount() { return imageCount; }
    public void setImageCount(int imageCount) { this.imageCount = imageCount; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    public String[] getAmenitiesArray() {
        if (amenities == null || amenities.isEmpty()) {
            return new String[0];
        }
        return amenities.split(",");
    }

    public String getFormattedPrice() {
        if (pricePerNight == null) return "0";
        return String.format("%,.0f", pricePerNight).replace(",", ".");
    }

    public String getFormattedDiscountPrice() {
        if (discountPrice == null) return null;
        return String.format("%,.0f", discountPrice).replace(",", ".");
    }

    public String getStarDisplay() {
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < starRating; i++) {
            stars.append("⭐");
        }
        return stars.toString();
    }

    public boolean hasDiscount() {
        return discountPrice != null && discountPrice.compareTo(pricePerNight) < 0;
    }

    public int getDiscountPercent() {
        if (!hasDiscount()) return 0;
        BigDecimal discount = pricePerNight.subtract(discountPrice);
        return discount.multiply(new BigDecimal(100))
                .divide(pricePerNight, 0, BigDecimal.ROUND_HALF_UP)
                .intValue();
    }

    public String getStatusText() {
        return isActive ? "Hoạt động" : "Đã ẩn";
    }

    public String getStatusClass() {
        return isActive ? "badge-success" : "badge-danger";
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cityName='" + cityName + '\'' +
                ", starRating=" + starRating +
                ", pricePerNight=" + pricePerNight +
                ", discountPrice=" + discountPrice +
                ", isActive=" + isActive +
                '}';
    }
}