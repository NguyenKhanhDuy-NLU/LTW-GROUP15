package vn.edu.nlu.fit.demo1.dao;

import vn.edu.nlu.fit.demo1.config.DatabaseConfig;
import vn.edu.nlu.fit.demo1.model.Hotel;
import vn.edu.nlu.fit.demo1.model.DashboardStats;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminHotelDAO {

    public DashboardStats getDashboardStats() {
        DashboardStats stats = new DashboardStats();
        String sql = "SELECT * FROM vw_admin_dashboard_stats";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                stats.setTotalHotels(rs.getInt("total_hotels"));
                stats.setTotalBookings(rs.getInt("total_bookings"));
                stats.setPendingBookings(rs.getInt("pending_bookings"));
                stats.setConfirmedBookings(rs.getInt("confirmed_bookings"));
                stats.setTotalUsers(rs.getInt("total_users"));
                stats.setTotalRevenue(rs.getBigDecimal("total_revenue"));
                stats.setMonthRevenue(rs.getBigDecimal("month_revenue"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats;
    }

    public List<Hotel> getAllHotels(int page, int pageSize) {
        List<Hotel> hotels = new ArrayList<>();
        int offset = (page - 1) * pageSize;

        String sql = "SELECT h.*, c.name as city_name, " +
                "(SELECT COUNT(*) FROM hotel_images WHERE hotel_id = h.id) as image_count " +
                "FROM hotels h " +
                "LEFT JOIN cities c ON h.city_id = c.id " +
                "ORDER BY h.created_at DESC " +
                "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, offset);
            stmt.setInt(2, pageSize);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Hotel hotel = extractHotelFromResultSet(rs);
                    // Thêm thông tin số lượng ảnh
                    hotel.setImageCount(rs.getInt("image_count"));
                    hotels.add(hotel);
                }
            }
        } catch (SQLException e) {
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
                "price_per_night, discount_price, description, amenities, " +
                "latitude, longitude, is_active) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, hotel.getName());
            stmt.setInt(2, hotel.getCityId());
            stmt.setString(3, hotel.getAddress());
            stmt.setInt(4, hotel.getStarRating());
            stmt.setBigDecimal(5, hotel.getPricePerNight());
            stmt.setBigDecimal(6, hotel.getDiscountPrice());
            stmt.setString(7, hotel.getDescription());
            stmt.setString(8, hotel.getAmenities());
            stmt.setBigDecimal(9, hotel.getLatitude());
            stmt.setBigDecimal(10, hotel.getLongitude());
            stmt.setBoolean(11, hotel.isActive());

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
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateHotel(Hotel hotel) {
        String sql = "UPDATE hotels SET name = ?, city_id = ?, address = ?, " +
                "star_rating = ?, price_per_night = ?, discount_price = ?, " +
                "description = ?, amenities = ?, latitude = ?, longitude = ?, " +
                "is_active = ?, updated_at = GETDATE() " +
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
            stmt.setString(8, hotel.getAmenities());
            stmt.setBigDecimal(9, hotel.getLatitude());
            stmt.setBigDecimal(10, hotel.getLongitude());
            stmt.setBoolean(11, hotel.isActive());
            stmt.setInt(12, hotel.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteHotel(int id) {
        String sql = "UPDATE hotels SET is_active = 0, updated_at = GETDATE() WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
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
        hotel.setCityId(rs.getInt("city_id"));
        hotel.setCityName(rs.getString("city_name"));
        hotel.setAddress(rs.getString("address"));
        hotel.setStarRating(rs.getInt("star_rating"));
        hotel.setPricePerNight(rs.getBigDecimal("price_per_night"));
        hotel.setDiscountPrice(rs.getBigDecimal("discount_price"));
        hotel.setDescription(rs.getString("description"));
        hotel.setMainImage(rs.getString("main_image"));
        hotel.setAmenities(rs.getString("amenities"));
        hotel.setLatitude(rs.getBigDecimal("latitude"));
        hotel.setLongitude(rs.getBigDecimal("longitude"));
        hotel.setActive(rs.getBoolean("is_active"));
        return hotel;
    }
}