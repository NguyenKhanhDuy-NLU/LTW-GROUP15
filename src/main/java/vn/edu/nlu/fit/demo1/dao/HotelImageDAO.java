package vn.edu.nlu.fit.demo1.dao;

import vn.edu.nlu.fit.demo1.config.DatabaseConfig;
import vn.edu.nlu.fit.demo1.model.HotelImage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelImageDAO {

    public List<HotelImage> getImagesByHotelId(int hotelId) {
        List<HotelImage> images = new ArrayList<>();
        String sql = "SELECT * FROM hotel_images WHERE hotel_id = ? ORDER BY display_order, id";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, hotelId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    images.add(extractImageFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return images;
    }

    public HotelImage getThumbnailByHotelId(int hotelId) {
        String sql = "SELECT * FROM hotel_images WHERE hotel_id = ? AND is_thumbnail = 1";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, hotelId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractImageFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addImage(HotelImage image) {
        String sql = "INSERT INTO hotel_images (hotel_id, image_url, is_thumbnail, display_order, caption) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, image.getHotelId());
            stmt.setString(2, image.getImageUrl());
            stmt.setBoolean(3, image.isThumbnail());
            stmt.setInt(4, image.getDisplayOrder());
            stmt.setString(5, image.getCaption());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        image.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setThumbnail(int hotelId, int imageId) {
        String sql = "{CALL sp_SetHotelThumbnail(?, ?)}";

        try (Connection conn = DatabaseConfig.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, hotelId);
            stmt.setInt(2, imageId);

            stmt.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteImage(int imageId) {
        String sql = "DELETE FROM hotel_images WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, imageId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAllImagesByHotelId(int hotelId) {
        String sql = "DELETE FROM hotel_images WHERE hotel_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, hotelId);
            return stmt.executeUpdate() >= 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateDisplayOrder(int imageId, int displayOrder) {
        String sql = "UPDATE hotel_images SET display_order = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, displayOrder);
            stmt.setInt(2, imageId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private HotelImage extractImageFromResultSet(ResultSet rs) throws SQLException {
        HotelImage image = new HotelImage();
        image.setId(rs.getInt("id"));
        image.setHotelId(rs.getInt("hotel_id"));
        image.setImageUrl(rs.getString("image_url"));
        image.setThumbnail(rs.getBoolean("is_thumbnail"));
        image.setDisplayOrder(rs.getInt("display_order"));
        image.setCaption(rs.getString("caption"));
        image.setCreatedAt(rs.getTimestamp("created_at"));
        return image;
    }
}