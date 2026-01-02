package vn.edu.nlu.fit.demo1.dao;

import vn.edu.nlu.fit.demo1.config.DatabaseConfig;
import vn.edu.nlu.fit.demo1.model.Hotel;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HotelDAO {

    public List<Hotel> getAllHotels() {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT h.*, c.name as city_name FROM hotels h JOIN cities c ON h.city_id = c.id";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) hotels.add(extractHotelFromResultSet(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return hotels;
    }

    public Hotel getHotelById(int id) {
        String sql = "SELECT h.*, c.name as city_name FROM hotels h JOIN cities c ON h.city_id = c.id WHERE h.id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return extractHotelFromResultSet(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Hotel> searchHotelsByLocation(String location) {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT h.*, c.name as city_name FROM hotels h JOIN cities c ON h.city_id = c.id " +
                "WHERE c.name LIKE ? OR h.name LIKE ? OR h.address LIKE ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String pattern = "%" + location + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);
            stmt.setString(3, pattern);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) hotels.add(extractHotelFromResultSet(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return hotels;
    }

    public List<Hotel> advancedSearch(
            Integer cityId,
            String checkin,
            String checkout,
            Integer guests,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            List<Integer> stars,
            List<String> amenities
    ) {
        List<Hotel> hotels = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT h.*, c.name as city_name FROM hotels h " +
                        "JOIN cities c ON h.city_id = c.id WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (cityId != null && cityId > 0) {
            sql.append(" AND h.city_id = ?");
            params.add(cityId);
        }

        if (minPrice != null) {
            sql.append(" AND h.price_per_night >= ?");
            params.add(minPrice);
        }
        if (maxPrice != null) {
            sql.append(" AND h.price_per_night <= ?");
            params.add(maxPrice);
        }

        if (stars != null && !stars.isEmpty()) {
            sql.append(" AND h.star_rating IN (");
            for (int i = 0; i < stars.size(); i++) {
                sql.append(i == 0 ? "?" : ", ?");
                params.add(stars.get(i));
            }
            sql.append(")");
        }
        if (amenities != null && !amenities.isEmpty()) {
            sql.append(" AND (");
            for (int i = 0; i < amenities.size(); i++) {
                if (i > 0) sql.append(" OR "); // Hoặc dùng AND tùy logic muốn tìm chính xác hay tương đối
                sql.append("h.amenities LIKE ?");
                params.add("%" + amenities.get(i) + "%");
            }
            sql.append(")");
        }


        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    hotels.add(extractHotelFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
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
        hotel.setDescription(rs.getString("description"));
        hotel.setMainImage(rs.getString("main_image"));
        hotel.setAmenities(rs.getString("amenities"));
        // Thêm latitude/longitude nếu cần
        return hotel;
    }
}