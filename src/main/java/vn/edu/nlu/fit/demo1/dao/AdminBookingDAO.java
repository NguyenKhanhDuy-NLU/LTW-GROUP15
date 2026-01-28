package vn.edu.nlu.fit.demo1.dao;

import vn.edu.nlu.fit.demo1.config.DatabaseConfig;
import vn.edu.nlu.fit.demo1.model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminBookingDAO {

    public List<Booking> getAllBookings(int page, int pageSize) {
        List<Booking> bookings = new ArrayList<>();
        int offset = (page - 1) * pageSize;

        String sql = "SELECT b.*, " +
                "h.hotel_name, " +
                "c.city_name, " +
                "u.username, u.full_name, u.email " +
                "FROM booking b " +
                "JOIN hotel h ON b.hotel_id = h.id " +
                "LEFT JOIN city c ON h.city_id = c.id " +
                "JOIN user u ON b.user_id = u.id " +
                "ORDER BY b.created_at DESC " +
                "LIMIT ? OFFSET ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pageSize);
            stmt.setInt(2, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bookings.add(extractBookingFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading bookings: " + e.getMessage());
            e.printStackTrace();
        }

        return bookings;
    }

    public int countBookings() {
        String sql = "SELECT COUNT(*) as total FROM booking";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public Booking getBookingById(int id) {
        String sql = "SELECT b.*, " +
                "h.hotel_name, h.main_image as hotel_image, " +
                "c.city_name, " +
                "u.username, u.full_name, u.email " +
                "FROM booking b " +
                "JOIN hotel h ON b.hotel_id = h.id " +
                "LEFT JOIN city c ON h.city_id = c.id " +
                "JOIN user u ON b.user_id = u.id " +
                "WHERE b.id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractBookingFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting booking by ID: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateBooking(Booking booking) {
        // ĐÃ SỬA: bookings → booking, updated_at → CURRENT_TIMESTAMP
        String sql = "UPDATE booking SET " +
                "status = ?, " +
                "payment_status = ?, " +
                "notes = ? " +
                "WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, booking.getStatus());
            stmt.setString(2, booking.getPaymentStatus());
            stmt.setString(3, booking.getNotes());
            stmt.setInt(4, booking.getId());

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Booking updated. Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating booking: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteBooking(int id) {
        // ĐÃ SỬA: bookings → booking
        String sql = "UPDATE booking SET status = 'cancelled' WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting booking: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean permanentDeleteBooking(int id) {
        String sql = "DELETE FROM booking WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error permanently deleting booking: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public List<Booking> getBookingsByStatus(String status, int page, int pageSize) {
        List<Booking> bookings = new ArrayList<>();
        int offset = (page - 1) * pageSize;

        String sql = "SELECT b.*, " +
                "h.hotel_name, " +
                "c.city_name, " +
                "u.username, u.full_name, u.email " +
                "FROM booking b " +
                "JOIN hotel h ON b.hotel_id = h.id " +
                "LEFT JOIN city c ON h.city_id = c.id " +
                "JOIN user u ON b.user_id = u.id " +
                "WHERE b.status = ? " +
                "ORDER BY b.created_at DESC " +
                "LIMIT ? OFFSET ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, pageSize);
            stmt.setInt(3, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bookings.add(extractBookingFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading bookings by status: " + e.getMessage());
            e.printStackTrace();
        }

        return bookings;
    }

    public int countBookingsByStatus(String status) {
        // ĐÃ SỬA: bookings → booking
        String sql = "SELECT COUNT(*) as total FROM booking WHERE status = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Booking extractBookingFromResultSet(ResultSet rs) throws SQLException {
        Booking booking = new Booking();

        booking.setId(rs.getInt("id"));
        booking.setUserId(rs.getInt("user_id"));
        booking.setHotelId(rs.getInt("hotel_id"));

        try {
            booking.setRoomId(rs.getInt("room_id"));
        } catch (SQLException e) {
            booking.setRoomId(0);
        }

        booking.setBookingCode(rs.getString("booking_code"));

        booking.setCheckInDate(rs.getDate("check_in"));
        booking.setCheckOutDate(rs.getDate("check_out"));

        try {
            booking.setGuests(rs.getInt("guests"));
        } catch (SQLException e) {
            booking.setGuests(0);
        }

        try {
            booking.setRoomType(rs.getString("room_type"));
        } catch (SQLException e) {
            booking.setRoomType("");
        }

        booking.setTotalPrice(rs.getBigDecimal("final_price"));
        booking.setStatus(rs.getString("status"));
        booking.setPaymentStatus(rs.getString("payment_status"));
        booking.setNotes(rs.getString("notes"));

        try {
            booking.setHotelName(rs.getString("hotel_name"));
        } catch (SQLException e) {
        }

        try {
            booking.setHotelImage(rs.getString("hotel_image"));
        } catch (SQLException e) {
        }

        try {
            booking.setCityName(rs.getString("city_name"));
        } catch (SQLException e) {
        }

        try {
            booking.setUsername(rs.getString("username"));
        } catch (SQLException e) {
        }

        try {
            booking.setUserFullName(rs.getString("full_name"));
        } catch (SQLException e) {
        }

        try {
            booking.setUserEmail(rs.getString("email"));
        } catch (SQLException e) {
        }

        return booking;
    }
}