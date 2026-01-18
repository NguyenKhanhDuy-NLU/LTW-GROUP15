package vn.edu.nlu.fit.demo1.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class HotelImage implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int hotelId;
    private String imageUrl;
    private boolean isThumbnail;
    private int displayOrder;
    private String caption;
    private Timestamp createdAt;

    public HotelImage() {}

    public HotelImage(int hotelId, String imageUrl, boolean isThumbnail) {
        this.hotelId = hotelId;
        this.imageUrl = imageUrl;
        this.isThumbnail = isThumbnail;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getHotelId() { return hotelId; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public boolean isThumbnail() { return isThumbnail; }
    public void setThumbnail(boolean thumbnail) { isThumbnail = thumbnail; }

    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }

    public String getCaption() { return caption; }
    public void setCaption(String caption) { this.caption = caption; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "HotelImage{" +
                "id=" + id +
                ", hotelId=" + hotelId +
                ", imageUrl='" + imageUrl + '\'' +
                ", isThumbnail=" + isThumbnail +
                ", displayOrder=" + displayOrder +
                '}';
    }
}