package vn.edu.nlu.fit.demo1.dao;

import vn.edu.nlu.fit.demo1.config.DatabaseConfig;
import vn.edu.nlu.fit.demo1.model.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    public boolean insertReview(Review review) {
        String sql = "INSERT INTO reviews (user_id, hotel_id, booking_id, rating, comment, created_at) VALUES (?, ?, ?, ?, ?, NOW())";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, review.getUserId());
            stmt.setInt(2, review.getHotelId());

            if (review.getBookingId() > 0) {
                stmt.setInt(3, review.getBookingId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }

            stmt.setInt(4, review.getRating());
            stmt.setString(5, review.getComment());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Review> getReviewsByHotelId(int hotelId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT r.*, u.full_name, u.avatar " +
                "FROM reviews r " +
                "JOIN users u ON r.user_id = u.id " +
                "WHERE r.hotel_id = ? " +
                "ORDER BY r.created_at DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, hotelId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Review review = new Review();
                    review.setId(rs.getInt("id"));
                    review.setUserId(rs.getInt("user_id"));
                    review.setHotelId(rs.getInt("hotel_id"));
                    review.setBookingId(rs.getInt("booking_id"));
                    review.setRating(rs.getInt("rating"));
                    review.setComment(rs.getString("comment"));
                    review.setCreatedAt(rs.getTimestamp("created_at"));

                    review.setUserName(rs.getString("full_name"));
                    review.setUserAvatar(rs.getString("avatar"));

                    reviews.add(review);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public double getAverageRating(int hotelId) {
        String sql = "SELECT AVG(rating) as avg_rating FROM reviews WHERE hotel_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, hotelId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("avg_rating");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public int countReviews(int hotelId) {
        String sql = "SELECT COUNT(*) as total FROM reviews WHERE hotel_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, hotelId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}