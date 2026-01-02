package vn.edu.nlu.fit.demo1.model;
import java.io.Serializable;
import java.math.BigDecimal;

public class Hotel implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private int cityId;
    private String cityName;
    private String address;
    private int starRating;
    private BigDecimal pricePerNight;
    private String description;
    private String mainImage;
    private String amenities;
    private BigDecimal latitude;
    private BigDecimal longitude;

    // Constructors
    public Hotel() {}

    // Getters and Setters
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

    public String[] getAmenitiesArray() {
        if (amenities == null || amenities.isEmpty()) {
            return new String[0];
        }
        return amenities.split(",");
    }

    public String getFormattedPrice() {
        if (pricePerNight == null) return "0";
        return String.format("%,.0f", pricePerNight);
    }
}