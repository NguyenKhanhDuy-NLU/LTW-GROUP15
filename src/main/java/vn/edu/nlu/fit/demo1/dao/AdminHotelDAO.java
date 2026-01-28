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

            // ĐÃ SỬA: hotels → hotel, is_active → is_featured (hoặc đếm tất cả)
            String sqlHotels = "SELECT COUNT(*) as total FROM hotel";
            try (PreparedStatement stmt = conn.prepareStatement(sqlHotels);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    stats.setTotalHotels(rs.getInt("total"));
                    System.out.println("  Total Hotels: " + stats.getTotalHotels());
                }
            }

            // ĐÃ SỬA: bookings → booking
            String sqlBookings = "SELECT COUNT(*) as total FROM booking";
            try (PreparedStatement stmt = conn.prepareStatement(sqlBookings);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    stats.setTotalBookings(rs.getInt("total"));
                    System.out.println("  Total Bookings: " + stats.getTotalBookings());
                }
            }

            String sqlPending = "SELECT COUNT(*) as total FROM booking WHERE status = 'pending'";
            try (PreparedStatement stmt = conn.prepareStatement(sqlPending);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    stats.setPendingBookings(rs.getInt("total"));
                    System.out.println("  Pending Bookings: " + stats.getPendingBookings());
                }
            }

            String sqlConfirmed = "SELECT COUNT(*) as total FROM booking WHERE status = 'confirmed'";
            try (PreparedStatement stmt = conn.prepareStatement(sqlConfirmed);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    stats.setConfirmedBookings(rs.getInt("total"));
                    System.out.println("  Confirmed Bookings: " + stats.getConfirmedBookings());
                }
            }

            String sqlUsers = "SELECT COUNT(*) as total FROM user WHERE is_active = 1";
            try (PreparedStatement stmt = conn.prepareStatement(sqlUsers);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    stats.setTotalUsers(rs.getInt("total"));
                    System.out.println("  Total Users: " + stats.getTotalUsers());
                }
            }

            String sqlRevenue = "SELECT COALESCE(SUM(final_price), 0) as total FROM booking WHERE payment_status = 'success'";
            try (PreparedStatement stmt = conn.prepareStatement(sqlRevenue);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    stats.setTotalRevenue(rs.getBigDecimal("total"));
                    System.out.println("  Total Revenue: " + stats.getTotalRevenue());
                }
            }

            String sqlMonthRevenue = "SELECT COALESCE(SUM(final_price), 0) as total " +
                    "FROM booking " +
                    "WHERE payment_status = 'success' " +
                    "AND MONTH(created_at) = MONTH(CURRENT_DATE()) " +
                    "AND YEAR(created_at) = YEAR(CURRENT_DATE())";
            try (PreparedStatement stmt = conn.prepareStatement(sqlMonthRevenue);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    BigDecimal totalBookingMonth = rs.getBigDecimal("total");
                    // Tính doanh thu tháng = tổng booking * 10%
                    BigDecimal monthRevenue = totalBookingMonth.multiply(new BigDecimal("0.10"));
                    stats.setMonthRevenue(monthRevenue);
                    System.out.println("  Total Booking Month: " + totalBookingMonth);
                    System.out.println("  Month Revenue (10%): " + monthRevenue);
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

    public boolean toggleHotelStatus(int hotelId) {
        String sql = "UPDATE hotel SET is_featured = NOT is_featured WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, hotelId);
            int rowsAffected = stmt.executeUpdate();

            System.out.println("Toggle hotel featured status - ID: " + hotelId + ", Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error toggling hotel status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Hotel> getAllHotels(int page, int pageSize) {
        List<Hotel> hotels = new ArrayList<>();
        int offset = (page - 1) * pageSize;

        String sql = "SELECT h.*, c.city_name " +
                "FROM hotel h " +
                "LEFT JOIN city c ON h.city_id = c.id " +
                "ORDER BY h.id DESC " +
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
            System.err.println("Error getting hotels: " + e.getMessage());
            e.printStackTrace();
        }

        return hotels;
    }

    public int countHotels() {
        String sql = "SELECT COUNT(*) as total FROM hotel";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.err.println("Error counting hotels: " + e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }

    public boolean addHotel(Hotel hotel) {
        String sql = "INSERT INTO hotel (city_id, hotel_name, slug, address, star_rating, " +
                "min_price, max_price, description, main_image, is_featured) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, hotel.getCityId());
            stmt.setString(2, hotel.getName());
            String slug = hotel.getName().toLowerCase()
                    .replaceAll("[^a-z0-9\\s-]", "")
                    .replaceAll("\\s+", "-");
            stmt.setString(3, slug);
            stmt.setString(4, hotel.getAddress());
            stmt.setInt(5, hotel.getStarRating());
            stmt.setBigDecimal(6, hotel.getPricePerNight()); // min_price
            stmt.setBigDecimal(7, hotel.getDiscountPrice()); // max_price
            stmt.setString(8, hotel.getDescription());
            stmt.setString(9, null); // main_image
            stmt.setBoolean(10, hotel.isActive()); // is_featured

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        hotel.setId(generatedKeys.getInt(1));
                    }
                }
                System.out.println("Added hotel - ID: " + hotel.getId() + ", Name: " + hotel.getName());
                return true;
            }

            return false;

        } catch (SQLException e) {
            System.err.println("Error adding hotel: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateHotel(Hotel hotel) {
        String sql = "UPDATE hotel SET hotel_name = ?, city_id = ?, address = ?, star_rating = ?, " +
                "min_price = ?, max_price = ?, description = ?, is_featured = ? " +
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
            stmt.setBoolean(8, hotel.isActive());
            stmt.setInt(9, hotel.getId());

            int rowsAffected = stmt.executeUpdate();

            System.out.println("Update hotel - ID: " + hotel.getId() + ", Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating hotel: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteHotel(int hotelId) {
        // ĐÃ SỬA: hotels → hotel
        String sql = "DELETE FROM hotel WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, hotelId);
            int rowsAffected = stmt.executeUpdate();

            System.out.println("Delete hotel - ID: " + hotelId + ", Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting hotel: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Hotel getHotelById(int hotelId) {
        String sql = "SELECT h.*, c.city_name " +
                "FROM hotel h " +
                "LEFT JOIN city c ON h.city_id = c.id " +
                "WHERE h.id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, hotelId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractHotelFromResultSet(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getting hotel by ID: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    private Hotel extractHotelFromResultSet(ResultSet rs) throws SQLException {
        Hotel hotel = new Hotel();
        hotel.setId(rs.getInt("id"));
        hotel.setName(rs.getString("hotel_name"));
        hotel.setAddress(rs.getString("address"));
        hotel.setCityName(rs.getString("city_name"));
        hotel.setPricePerNight(rs.getBigDecimal("min_price"));
        hotel.setDiscountPrice(rs.getBigDecimal("max_price"));
        hotel.setStarRating(rs.getInt("star_rating"));
        hotel.setActive(rs.getBoolean("is_featured"));
        hotel.setImageCount(0);
        return hotel;
    }
}