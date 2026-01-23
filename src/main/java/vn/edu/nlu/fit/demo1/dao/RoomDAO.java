package vn.edu.nlu.fit.demo1.dao;

import vn.edu.nlu.fit.demo1.model.Room;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO extends BaseDAO {

    public List<Room> getRoomsByHotelId(int hotelId) {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM room WHERE hotel_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, hotelId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public Room findById(int id) {
        String sql = "SELECT * FROM room WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    private Room mapRow(ResultSet rs) throws SQLException {
        Room r = new Room();
        r.setId(rs.getInt("id"));
        r.setHotelId(rs.getInt("hotel_id"));
        r.setRoomName(rs.getString("room_name"));
        r.setRoomType(rs.getString("room_type"));
        r.setMaxPeople(rs.getInt("max_people"));
        r.setBasePrice(rs.getDouble("base_price"));
        r.setQuantity(rs.getInt("quantity"));
        r.setImages(rs.getString("images"));
        r.setDescription(rs.getString("description"));
        r.setSize(rs.getString("size"));
        r.setView(rs.getString("view"));
        return r;
    }
}
