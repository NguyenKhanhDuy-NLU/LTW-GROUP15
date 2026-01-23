package vn.edu.nlu.fit.demo1.dao;

import vn.edu.nlu.fit.demo1.model.Amenity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AmenityDAO extends BaseDAO {

    public List<Amenity> getByHotelId(int hotelId) {
        String sql = "SELECT a.* FROM amenity a JOIN hotel_amenity ha ON a.id = ha.amenity_id WHERE ha.hotel_id = ?";
        return getList(sql, hotelId);
    }

    public List<Amenity> getByRoomId(int roomId) {
        String sql = "SELECT a.* FROM amenity a JOIN room_amenity ra ON a.id = ra.amenity_id WHERE ra.room_id = ?";
        return getList(sql, roomId);
    }

    private List<Amenity> getList(String sql, int id) {
        List<Amenity> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Amenity a = new Amenity();
                a.setId(rs.getInt("id"));
                // Map đúng tên cột DB vào field class Amenity
                a.setAmenityName(rs.getString("amenity_name"));
                a.setAmenityIcon(rs.getString("amenity_icon"));
                a.setDescription(rs.getString("description"));
                list.add(a);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
