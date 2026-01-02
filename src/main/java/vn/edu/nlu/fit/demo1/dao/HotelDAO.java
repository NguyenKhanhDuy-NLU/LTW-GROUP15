package vn.edu.nlu.fit.demo1.dao;
import vn.edu.nlu.fit.demo1.config.DatabaseConfig;
import vn.edu.nlu.fit.demo1.model.Hotel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelDAO {

    public List<Hotel> getAllHotels() {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT h.*, c.name as city_name FROM hotels h " +
                "JOIN cities c ON h.city_id = c.id " +
                "ORDER BY h.created_at DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                hotels.add(extractHotelFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }

    public Hotel getHotelById(int id) {
        String sql = "SELECT h.*, c.name as city_name FROM hotels h " +
                "JOIN cities c ON h.city_id = c.id " +
                "WHERE h.id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractHotelFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Hotel> searchHotels(String cityName, Integer minPrice, Integer maxPrice, String[] amenities) {
        List<Hotel> hotels = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT h.*, c.name as city_name FROM hotels h " +
                        "JOIN cities c ON h.city_id = c.id WHERE 1=1"
        );

        List<Object> params = new ArrayList<>();

        if (cityName != null && !cityName.trim().isEmpty()) {
            sql.append(" AND (c.name LIKE ? OR c.name_en LIKE ?)");
            String searchPattern = "%" + cityName + "%";
            params.add(searchPattern);
            params.add(searchPattern);
        }

        if (minPrice != null) {
            sql.append(" AND h.price_per_night >= ?");
            params.add(minPrice);
        }

        if (maxPrice != null && maxPrice < 20000000) {
            sql.append(" AND h.price_per_night <= ?");
            params.add(maxPrice);
        }

        if (amenities != null && amenities.length > 0) {
            for (String amenity : amenities) {
                sql.append(" AND h.amenities LIKE ?");
                params.add("%" + amenity + "%");
            }
        }

        sql.append(" ORDER BY h.created_at DESC");

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
        hotel.setLatitude(rs.getBigDecimal("latitude"));
        hotel.setLongitude(rs.getBigDecimal("longitude"));
        return hotel;
    }
}