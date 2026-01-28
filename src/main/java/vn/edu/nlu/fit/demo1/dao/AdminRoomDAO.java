package vn.edu.nlu.fit.demo1.dao;

import vn.edu.nlu.fit.demo1.config.DatabaseConfig;
import vn.edu.nlu.fit.demo1.model.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminRoomDAO {

    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT r.*, h.name as hotel_name FROM room r " +
                     "LEFT JOIN hotel h ON r.hotel_id = h.id " +
                     "ORDER BY r.created_at DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                rooms.add(extractRoomFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public List<Room> getRoomsByHotelId(int hotelId) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT r.*, h.name as hotel_name FROM room r " +
                     "LEFT JOIN hotel h ON r.hotel_id = h.id " +
                     "WHERE r.hotel_id = ? " +
                     "ORDER BY r.created_at DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, hotelId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    rooms.add(extractRoomFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public Room getRoomById(int id) {
        String sql = "SELECT r.*, h.name as hotel_name FROM room r " +
                     "LEFT JOIN hotel h ON r.hotel_id = h.id " +
                     "WHERE r.id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractRoomFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addRoom(Room room) {
        String sql = "INSERT INTO room (hotel_id, room_name, room_type, max_people, base_price, " +
                     "quantity, size, view, description, images, is_available, created_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, room.getHotelId());
            stmt.setString(2, room.getRoomName());
            stmt.setString(3, room.getRoomType());
            stmt.setInt(4, room.getMaxPeople());
            stmt.setBigDecimal(5, room.getBasePrice());
            stmt.setInt(6, room.getQuantity());
            stmt.setString(7, room.getSize());
            stmt.setString(8, room.getView());
            stmt.setString(9, room.getDescription());
            stmt.setString(10, room.getImages());
            stmt.setBoolean(11, room.isAvailable());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateRoom(Room room) {
        String sql = "UPDATE room SET hotel_id = ?, room_name = ?, room_type = ?, max_people = ?, " +
                     "base_price = ?, quantity = ?, size = ?, view = ?, description = ?, " +
                     "images = ?, is_available = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, room.getHotelId());
            stmt.setString(2, room.getRoomName());
            stmt.setString(3, room.getRoomType());
            stmt.setInt(4, room.getMaxPeople());
            stmt.setBigDecimal(5, room.getBasePrice());
            stmt.setInt(6, room.getQuantity());
            stmt.setString(7, room.getSize());
            stmt.setString(8, room.getView());
            stmt.setString(9, room.getDescription());
            stmt.setString(10, room.getImages());
            stmt.setBoolean(11, room.isAvailable());
            stmt.setInt(12, room.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteRoom(int id) {
        String sql = "DELETE FROM room WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean toggleRoomStatus(int id) {
        String sql = "UPDATE room SET is_available = NOT is_available WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Room extractRoomFromResultSet(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setId(rs.getInt("id"));
        room.setHotelId(rs.getInt("hotel_id"));
        room.setHotelName(rs.getString("hotel_name"));
        room.setRoomName(rs.getString("room_name"));
        room.setRoomType(rs.getString("room_type"));
        room.setMaxPeople(rs.getInt("max_people"));
        room.setBasePrice(rs.getBigDecimal("base_price"));
        room.setQuantity(rs.getInt("quantity"));
        room.setAvailable(rs.getBoolean("is_available"));
        room.setImages(rs.getString("images"));
        room.setDescription(rs.getString("description"));
        room.setSize(rs.getString("size"));
        room.setView(rs.getString("view"));
        room.setCreatedAt(rs.getTimestamp("created_at"));
        return room;
    }
}
