package vn.edu.nlu.fit.demo1.dao;

import vn.edu.nlu.fit.demo1.model.HotelDetail;
import java.sql.*;

public class HotelDetailDAO extends BaseDAO {

    public HotelDetail findById(int id) {
        String sql = "SELECT * FROM hotel WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                HotelDetail h = new HotelDetail();
                h.setId(rs.getInt("id"));
                h.setHotelName(rs.getString("hotel_name"));
                h.setStarRating(rs.getInt("star_rating"));
                h.setAverageStar(rs.getDouble("average_star"));
                h.setReviewCount(rs.getInt("review_count"));
                h.setAddress(rs.getString("address"));
                h.setMapImage(rs.getString("map_image"));
                h.setDescription(rs.getString("description"));
                h.setMainImage(rs.getString("main_image"));
                h.setCoverImages(rs.getString("cover_images"));
                h.setCheckInTime(rs.getTime("check_in_time"));
                h.setCheckOutTime(rs.getTime("check_out_time"));
                h.setCancellationPolicy(rs.getString("cancellation_policy"));
                h.setChildPolicy(rs.getString("child_policy"));
                h.setSmokingPolicy(rs.getString("smoking_policy"));
                return h;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
}
