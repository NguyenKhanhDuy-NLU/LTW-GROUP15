package vn.edu.nlu.fit.demo1.model;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.List;

public class Hotel {
    private int id;
    private int cityId;
    private String hotelName;
    private String slug;
    private String cityName;
    private String address;
    private String description;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private double averageStar;
    private int reviewCount;
    private String mainImage;
    private List<String> coverImages;
    private List<String> amenities;
    private Time checkInTime;
    private Time checkOutTime;
    private int totalRooms;
    private String cancellationPolicy;

    public Hotel() {}

    public Hotel(int id, String hotelName) {
        this.id = id;
        this.hotelName = hotelName;
    }

    // ============================================
    // GETTERS & SETTERS
    // ============================================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getAverageStar() {
        return averageStar;
    }

    public void setAverageStar(double averageStar) {
        this.averageStar = averageStar;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public List<String> getCoverImages() {
        return coverImages;
    }

    public void setCoverImages(List<String> coverImages) {
        this.coverImages = coverImages;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<String> amenities) {
        this.amenities = amenities;
    }

    public Time getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Time checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Time getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Time checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public int getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(int totalRooms) {
        this.totalRooms = totalRooms;
    }

    public String getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    public boolean[] getStarDisplay() {
        boolean[] stars = new boolean[5];
        int fullStars = (int) Math.round(this.averageStar);

        for (int i = 0; i < 5; i++) {
            stars[i] = (i < fullStars);
        }

        return stars;
    }

    public String getDetailUrl(String contextPath, String checkin, String checkout, String guests) {
        try {
            return contextPath + "/hotel-detail?id=" + this.id +
                    "&checkin=" + java.net.URLEncoder.encode(checkin != null ? checkin : "", "UTF-8") +
                    "&checkout=" + java.net.URLEncoder.encode(checkout != null ? checkout : "", "UTF-8") +
                    "&guests=" + java.net.URLEncoder.encode(guests != null ? guests : "", "UTF-8");
        } catch (Exception e) {
            return contextPath + "/hotel-detail?id=" + this.id;
        }
    }
    public String getReviewUrl(String contextPath) {
        return contextPath + "/reviews?hotelId=" + this.id;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", hotelName='" + hotelName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", address='" + address + '\'' +
                ", minPrice=" + minPrice +
                ", averageStar=" + averageStar +
                ", reviewCount=" + reviewCount +
                '}';
    }
}