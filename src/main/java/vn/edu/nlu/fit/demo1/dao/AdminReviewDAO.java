package vn.edu.nlu.fit.demo1.dao;

import vn.edu.nlu.fit.demo1.config.DatabaseConfig;
import vn.edu.nlu.fit.demo1.model.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminReviewDAO {

    public List<Review> getAllReviews(int page, int pageSize, Integer hotelId) {
        List<Review> reviews = new ArrayList<>();
        int offset = (page - 1) * pageSize;

        StringBuilder sql = new StringBuilder(
                "SELECT r.*, u.username, u.full_name, h.name as hotel_name " +
                        "FROM reviews r " +
                        "JOIN users u ON r.user_id = u.id " +
                        "JOIN hotels h ON r.hotel_id = h.id ");

        if (hotelId != null && hotelId > 0) {
            sql.append("WHERE r.hotel_id = ? ");
        }

        sql.append("ORDER BY r.created_at DESC LIMIT ? OFFSET ?");

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (hotelId != null && hotelId > 0) {
                stmt.setInt(paramIndex++, hotelId);
            }
            stmt.setInt(paramIndex++, pageSize);
            stmt.setInt(paramIndex, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Review review = extractReviewFromResultSet(rs);
                    reviews.add(review);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading reviews: " + e.getMessage());
            e.printStackTrace();
        }
        return reviews;
    }

    public int countReviews(Integer hotelId) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM reviews");

        if (hotelId != null && hotelId > 0) {
            sql.append(" WHERE hotel_id = ?");
        }

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            if (hotelId != null && hotelId > 0) {
                stmt.setInt(1, hotelId);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Review getReviewById(int id) {
        String sql = "SELECT r.*, u.username, u.full_name, u.email, " +
                "h.name as hotel_name " +
                "FROM reviews r " +
                "JOIN users u ON r.user_id = u.id " +
                "JOIN hotels h ON r.hotel_id = h.id " +
                "WHERE r.id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractReviewFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteReview(int id) {
        String sql = "DELETE FROM reviews WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting review: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateReview(int id, int rating, String comment) {
        String sql = "UPDATE reviews SET rating = ?, comment = ?, updated_at = NOW() WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, rating);
            stmt.setString(2, comment);
            stmt.setInt(3, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating review: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private Review extractReviewFromResultSet(ResultSet rs) throws SQLException {
        Review review = new Review();
        review.setId(rs.getInt("id"));
        review.setUserId(rs.getInt("user_id"));
        review.setHotelId(rs.getInt("hotel_id"));
        review.setBookingId(rs.getInt("booking_id"));
        review.setRating(rs.getInt("rating"));
        review.setComment(rs.getString("comment"));
        review.setCreatedAt(rs.getTimestamp("created_at"));
        review.setUpdatedAt(rs.getTimestamp("updated_at"));

        // Additional info from joins
        try {
            review.setUsername(rs.getString("username"));
            review.setUserFullName(rs.getString("full_name"));
            review.setHotelName(rs.getString("hotel_name"));
        } catch (SQLException e) {
            // These fields might not always be present
        }

        return review;
    }
}