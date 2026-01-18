package vn.edu.nlu.fit.demo1.dao;

import vn.edu.nlu.fit.demo1.config.DatabaseConfig;
import vn.edu.nlu.fit.demo1.model.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    public static class RatingStats {
        private double averageRating;
        private int totalReviews;
        private int fiveStars;
        private int fourStars;
        private int threeStars;
        private int twoStars;
        private int oneStar;

        public double getAverageRating() { return averageRating; }
        public void setAverageRating(double averageRating) { this.averageRating = averageRating; }

        public int getTotalReviews() { return totalReviews; }
        public void setTotalReviews(int totalReviews) { this.totalReviews = totalReviews; }

        public int getFiveStars() { return fiveStars; }
        public void setFiveStars(int fiveStars) { this.fiveStars = fiveStars; }

        public int getFourStars() { return fourStars; }
        public void setFourStars(int fourStars) { this.fourStars = fourStars; }

        public int getThreeStars() { return threeStars; }
        public void setThreeStars(int threeStars) { this.threeStars = threeStars; }

        public int getTwoStars() { return twoStars; }
        public void setTwoStars(int twoStars) { this.twoStars = twoStars; }

        public int getOneStar() { return oneStar; }
        public void setOneStar(int oneStar) { this.oneStar = oneStar; }
    }

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

            stmt.setDouble(4, review.getRating());
            stmt.setString(5, review.getComment());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        review.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;

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
                    reviews.add(extractReviewFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public List<Review> getReviewsByHotelIdPaginated(int hotelId, int page, int pageSize) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        int offset = (page - 1) * pageSize;

        String sql = "SELECT r.*, u.full_name, u.avatar " +
                "FROM reviews r " +
                "JOIN users u ON r.user_id = u.id " +
                "WHERE r.hotel_id = ? " +
                "ORDER BY r.created_at DESC " +
                "LIMIT ? OFFSET ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, hotelId);
            stmt.setInt(2, pageSize);
            stmt.setInt(3, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reviews.add(extractReviewFromResultSet(rs));
                }
            }
        }
        return reviews;
    }

    public RatingStats getRatingStats(int hotelId) throws SQLException {
        RatingStats stats = new RatingStats();

        String sql = "SELECT " +
                "AVG(rating) as avg_rating, " +
                "COUNT(*) as total_reviews, " +
                "SUM(CASE WHEN rating = 5 THEN 1 ELSE 0 END) as five_stars, " +
                "SUM(CASE WHEN rating = 4 THEN 1 ELSE 0 END) as four_stars, " +
                "SUM(CASE WHEN rating = 3 THEN 1 ELSE 0 END) as three_stars, " +
                "SUM(CASE WHEN rating = 2 THEN 1 ELSE 0 END) as two_stars, " +
                "SUM(CASE WHEN rating = 1 THEN 1 ELSE 0 END) as one_star " +
                "FROM reviews WHERE hotel_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, hotelId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    stats.setAverageRating(rs.getDouble("avg_rating"));
                    stats.setTotalReviews(rs.getInt("total_reviews"));
                    stats.setFiveStars(rs.getInt("five_stars"));
                    stats.setFourStars(rs.getInt("four_stars"));
                    stats.setThreeStars(rs.getInt("three_stars"));
                    stats.setTwoStars(rs.getInt("two_stars"));
                    stats.setOneStar(rs.getInt("one_star"));
                }
            }
        }
        return stats;
    }

    public int countReviewsByHotelId(int hotelId) throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM reviews WHERE hotel_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, hotelId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }
        return 0;
    }

    public boolean hasUserReviewedHotel(int userId, int hotelId) throws SQLException {
        String sql = "SELECT COUNT(*) as count FROM reviews WHERE user_id = ? AND hotel_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, hotelId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count") > 0;
                }
            }
        }
        return false;
    }

    public Review getReviewById(int reviewId) throws SQLException {
        String sql = "SELECT r.*, u.full_name, u.avatar " +
                "FROM reviews r " +
                "JOIN users u ON r.user_id = u.id " +
                "WHERE r.id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reviewId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractReviewFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public boolean createReview(Review review) throws SQLException {
        String sql = "INSERT INTO reviews (user_id, hotel_id, booking_id, rating, comment, created_at) " +
                "VALUES (?, ?, ?, ?, ?, NOW())";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, review.getUserId());
            stmt.setInt(2, review.getHotelId());

            if (review.getBookingId() > 0) {
                stmt.setInt(3, review.getBookingId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }

            stmt.setDouble(4, review.getRating());
            stmt.setString(5, review.getComment());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        review.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean updateReview(Review review) throws SQLException {
        String sql = "UPDATE reviews SET rating = ?, comment = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, review.getRating());
            stmt.setString(2, review.getComment());
            stmt.setInt(3, review.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteReview(int reviewId) throws SQLException {
        String sql = "DELETE FROM reviews WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reviewId);
            return stmt.executeUpdate() > 0;
        }
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

    private Review extractReviewFromResultSet(ResultSet rs) throws SQLException {
        Review review = new Review();
        review.setId(rs.getInt("id"));
        review.setUserId(rs.getInt("user_id"));
        review.setHotelId(rs.getInt("hotel_id"));
        review.setBookingId(rs.getInt("booking_id"));
        review.setRating(rs.getDouble("rating"));
        review.setComment(rs.getString("comment"));
        review.setCreatedAt(rs.getTimestamp("created_at"));
        review.setUserName(rs.getString("full_name"));
        review.setUserAvatar(rs.getString("avatar"));
        return review;
    }
}