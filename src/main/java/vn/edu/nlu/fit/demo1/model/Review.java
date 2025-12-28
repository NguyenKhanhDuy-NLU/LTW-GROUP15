package vn.edu.nlu.fit.demo1.model;

import java.sql.Timestamp;

public class Review {
    private int id;
    private int userId;
    private int hotelId;
    private int bookingId;
    private double star;
    private String comment;
    private Timestamp createdAt;

    // Additional fields from JOIN
    private String userName;
    private String userAvatar;
    private String hotelName;

    public Review() {}

    public Review(int userId, int hotelId, int bookingId, double star, String comment) {
        this.userId = userId;
        this.hotelId = hotelId;
        this.bookingId = bookingId;
        this.star = star;
        this.comment = comment;
    }

    // Getters and Setters
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

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
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

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public boolean[] getStarDisplay() {
        boolean[] stars = new boolean[5];
        int fullStars = (int) Math.round(this.star);

        for (int i = 0; i < 5; i++) {
            stars[i] = (i < fullStars);
        }

        return stars;
    }
    public String getTimeAgo() {
        if (createdAt == null) return "";

        long diff = System.currentTimeMillis() - createdAt.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long months = days / 30;
        long years = days / 365;

        if (years > 0) return years + " năm trước";
        if (months > 0) return months + " tháng trước";
        if (days > 0) return days + " ngày trước";
        if (hours > 0) return hours + " giờ trước";
        if (minutes > 0) return minutes + " phút trước";
        return "Vừa xong";
    }
}