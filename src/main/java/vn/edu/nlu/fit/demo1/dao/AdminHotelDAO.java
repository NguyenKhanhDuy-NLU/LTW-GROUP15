package vn.edu.nlu.fit.demo1.dao;

import vn.edu.nlu.fit.demo1.config.DatabaseConfig;
import vn.edu.nlu.fit.demo1.model.Hotel;
import vn.edu.nlu.fit.demo1.model.DashboardStats;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AdminHotelDAO {

    public DashboardStats getDashboardStats() {
        System.out.println("AdminHotelDAO.getDashboardStats() - Loading statistics...");
        DashboardStats stats = new DashboardStats();

        try (Connection conn = DatabaseConfig.getConnection()) {

            String sqlHotels = "SELECT COUNT(*) as total FROM hotels WHERE is_active = 1";
            try (PreparedStatement stmt = conn.prepareStatement(sqlHotels);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    stats.setTotalHotels(rs.getInt("total"));
                    System.out.println("  Total Hotels: " + stats.getTotalHotels());
                }
            }

            String sqlBookings = "SELECT COUNT(*) as total FROM bookings";
            try (PreparedStatement stmt = conn.prepareStatement(sqlBookings);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    stats.setTotalBookings(rs.getInt("total"));
                    System.out.println("  Total Bookings: " + stats.getTotalBookings());
                }
            }

            String sqlPending = "SELECT COUNT(*) as total FROM bookings WHERE status = 'pending'";
            try (PreparedStatement stmt = conn.prepareStatement(sqlPending);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    stats.setPendingBookings(rs.getInt("total"));
                    System.out.println("  Pending Bookings: " + stats.getPendingBookings());
                }
            }

            String sqlConfirmed = "SELECT COUNT(*) as total FROM bookings WHERE status = 'confirmed'";
            try (PreparedStatement stmt = conn.prepareStatement(sqlConfirmed);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    stats.setConfirmedBookings(rs.getInt("total"));
                    System.out.println("  Confirmed Bookings: " + stats.getConfirmedBookings());
                }
            }

            String sqlUsers = "SELECT COUNT(*) as total FROM users WHERE is_active = 1";
            try (PreparedStatement stmt = conn.prepareStatement(sqlUsers);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    stats.setTotalUsers(rs.getInt("total"));
                    System.out.println("  Total Users: " + stats.getTotalUsers());
                }
            }

            String sqlRevenue = "SELECT COALESCE(SUM(total_price), 0) as total FROM bookings WHERE payment_status = 'paid'";
            try (PreparedStatement stmt = conn.prepareStatement(sqlRevenue);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    stats.setTotalRevenue(rs.getBigDecimal("total"));
                    System.out.println("  Total Revenue: " + stats.getTotalRevenue());
                }
            }

            String sqlMonthRevenue = "SELECT COALESCE(SUM(total_price), 0) as total " +
                    "FROM bookings " +
                    "WHERE payment_status = 'paid' " +
                    "AND MONTH(booking_date) = MONTH(CURRENT_DATE()) " +
                    "AND YEAR(booking_date) = YEAR(CURRENT_DATE())";
            try (PreparedStatement stmt = conn.prepareStatement(sqlMonthRevenue);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    stats.setMonthRevenue(rs.getBigDecimal("total"));
                    System.out.println("  Month Revenue: " + stats.getMonthRevenue());
                }
            }

            System.out.println("AdminHotelDAO.getDashboardStats() - Statistics loaded successfully");

        } catch (SQLException e) {
            System.err.println("AdminHotelDAO.getDashboardStats() - Error: " + e.getMessage());
            e.printStackTrace();

            stats.setTotalHotels(0);
            stats.setTotalBookings(0);
            stats.setPendingBookings(0);
            stats.setConfirmedBookings(0);
            stats.setTotalUsers(0);
            stats.setTotalRevenue(BigDecimal.ZERO);
            stats.setMonthRevenue(BigDecimal.ZERO);
        }

        return stats;
    }

    public List<Hotel> getAllHotels(int page, int pageSize) {
        List<Hotel> hotels = new ArrayList<>();
        int offset = (page - 1) * pageSize;

        String sql = "SELECT h.*, " +
                "(SELECT COUNT(*) FROM hotel_images WHERE hotel_id = h.id) as image_count " +
                "FROM hotels h " +
                "ORDER BY h.created_at DESC " +
                "LIMIT ? OFFSET ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pageSize);
            stmt.setInt(2, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Hotel hotel = extractHotelFromResultSet(rs);
                    hotels.add(hotel);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading hotels: " + e.getMessage());
            e.printStackTrace();
        }
        return hotels;
    }

    public int countHotels() {
        String sql = "SELECT COUNT(*) FROM hotels";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean addHotel(Hotel hotel) {
        String sql = "INSERT INTO hotels (name, city_id, address, star_rating, " +
                "price_per_night, discount_price, description, main_image, amenities, " +
                "latitude, longitude, is_active) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, hotel.getName());
            stmt.setInt(2, hotel.getCityId());
            stmt.setString(3, hotel.getAddress());
            stmt.setInt(4, hotel.getStarRating());
            stmt.setBigDecimal(5, hotel.getPricePerNight());
            stmt.setBigDecimal(6, hotel.getDiscountPrice());
            stmt.setString(7, hotel.getDescription());
            stmt.setString(8, hotel.getMainImage());
            stmt.setString(9, hotel.getAmenities());
            stmt.setBigDecimal(10, hotel.getLatitude());
            stmt.setBigDecimal(11, hotel.getLongitude());
            stmt.setBoolean(12, hotel.isActive());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        hotel.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error adding hotel: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateHotel(Hotel hotel) {
        String sql = "UPDATE hotels SET name = ?, city_id = ?, address = ?, " +
                "star_rating = ?, price_per_night = ?, discount_price = ?, " +
                "description = ?, main_image = ?, amenities = ?, latitude = ?, longitude = ?, " +
                "is_active = ?, updated_at = NOW() " +
                "WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hotel.getName());
            stmt.setInt(2, hotel.getCityId());
            stmt.setString(3, hotel.getAddress());
            stmt.setInt(4, hotel.getStarRating());
            stmt.setBigDecimal(5, hotel.getPricePerNight());
            stmt.setBigDecimal(6, hotel.getDiscountPrice());
            stmt.setString(7, hotel.getDescription());
            stmt.setString(8, hotel.getMainImage());
            stmt.setString(9, hotel.getAmenities());
            stmt.setBigDecimal(10, hotel.getLatitude());
            stmt.setBigDecimal(11, hotel.getLongitude());
            stmt.setBoolean(12, hotel.isActive());
            stmt.setInt(13, hotel.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating hotel: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteHotel(int id) {
        String sql = "UPDATE hotels SET is_active = 0, updated_at = NOW() WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting hotel: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean permanentDeleteHotel(int id) {
        String sql = "DELETE FROM hotels WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Hotel extractHotelFromResultSet(ResultSet rs) throws SQLException {
        Hotel hotel = new Hotel();
        hotel.setId(rs.getInt("id"));
        hotel.setName(rs.getString("name"));
        try {
            hotel.setCityId(rs.getInt("city_id"));
        } catch (SQLException e) {
        }

        hotel.setAddress(rs.getString("address"));

        try {
            hotel.setStarRating(rs.getInt("star_rating"));
        } catch (SQLException e) {
        }

        hotel.setPricePerNight(rs.getBigDecimal("price_per_night"));
        hotel.setDiscountPrice(rs.getBigDecimal("discount_price"));
        hotel.setDescription(rs.getString("description"));
        hotel.setMainImage(rs.getString("main_image"));
        hotel.setAmenities(rs.getString("amenities"));

        try {
            hotel.setLatitude(rs.getBigDecimal("latitude"));
            hotel.setLongitude(rs.getBigDecimal("longitude"));
        } catch (SQLException e) {
        }

        hotel.setActive(rs.getBoolean("is_active"));

        try {
            hotel.setImageCount(rs.getInt("image_count"));
        } catch (SQLException e) {
        }

        return hotel;
    }
}