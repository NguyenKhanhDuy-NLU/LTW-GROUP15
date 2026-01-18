package vn.edu.nlu.fit.demo1.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Review implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int userId;
    private int hotelId;
    private int bookingId;
    private double rating;
    private String comment;
    private Timestamp createdAt;

    private String userName;
    private String userAvatar;

    public Review() {
    }

    public Review(int userId, int hotelId, int bookingId, double rating, String comment) {
        this.userId = userId;
        this.hotelId = hotelId;
        this.bookingId = bookingId;
        this.rating = rating;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getFormattedRating() {
        return String.format("%.1f", rating);
    }

    public int getFullStars() {
        return (int) Math.floor(rating);
    }

    public boolean hasHalfStar() {
        return (rating - Math.floor(rating)) >= 0.5;
    }

    public int getRatingAsInt() {
        return (int) Math.round(rating);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", userId=" + userId +
                ", hotelId=" + hotelId +
                ", bookingId=" + bookingId +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", userName='" + userName + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}