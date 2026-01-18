package vn.edu.nlu.fit.demo1.dao;
import vn.edu.nlu.fit.demo1.config.DatabaseConfig;
import vn.edu.nlu.fit.demo1.model.Booking;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public List<Booking> getBookingsByUserId(int userId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT b.*, h.name as hotel_name, h.main_image as hotel_image, " +
                "c.name as city_name " +
                "FROM bookings b " +
                "JOIN hotels h ON b.hotel_id = h.id " +
                "JOIN cities c ON h.city_id = c.id " +
                "WHERE b.user_id = ? " +
                "ORDER BY b.created_at DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bookings.add(extractBookingFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public Booking getBookingById(int id) {
        String sql = "SELECT b.*, h.name as hotel_name, h.main_image as hotel_image, " +
                "c.name as city_name " +
                "FROM bookings b " +
                "JOIN hotels h ON b.hotel_id = h.id " +
                "JOIN cities c ON h.city_id = c.id " +
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
            e.printStackTrace();
        }
        return null;
    }

    public boolean createBooking(Booking booking) {
        String sql = "INSERT INTO bookings (user_id, hotel_id, booking_code, check_in_date, " +
                "check_out_date, guests, room_type, total_price, status, payment_status, notes) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getHotelId());
            stmt.setString(3, booking.getBookingCode());
            stmt.setDate(4, new java.sql.Date(booking.getCheckInDate().getTime()));
            stmt.setDate(5, new java.sql.Date(booking.getCheckOutDate().getTime()));
            stmt.setInt(6, booking.getGuests());
            stmt.setString(7, booking.getRoomType());
            stmt.setBigDecimal(8, booking.getTotalPrice());
            stmt.setString(9, booking.getStatus());
            stmt.setString(10, booking.getPaymentStatus());
            stmt.setString(11, booking.getNotes());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        booking.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateBookingStatus(int bookingId, String status) {
        String sql = "UPDATE bookings SET status = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, bookingId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Booking extractBookingFromResultSet(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setId(rs.getInt("id"));
        booking.setUserId(rs.getInt("user_id"));
        booking.setHotelId(rs.getInt("hotel_id"));
        booking.setRoomId(rs.getInt("room_id"));
        booking.setBookingCode(rs.getString("booking_code"));
        booking.setCheckInDate(rs.getDate("check_in_date"));
        booking.setCheckOutDate(rs.getDate("check_out_date"));
        booking.setGuests(rs.getInt("guests"));
        booking.setRoomType(rs.getString("room_type"));
        booking.setTotalPrice(rs.getBigDecimal("total_price"));
        booking.setStatus(rs.getString("status"));
        booking.setPaymentStatus(rs.getString("payment_status"));
        booking.setNotes(rs.getString("notes"));

        booking.setHotelName(rs.getString("hotel_name"));
        booking.setHotelImage(rs.getString("hotel_image"));
        booking.setCityName(rs.getString("city_name"));

        return booking;
    }

    public String generateBookingCode() {
        String sql = "SELECT MAX(CAST(SUBSTRING(booking_code, 11) AS UNSIGNED)) as max_num " +
                "FROM bookings " +
                "WHERE booking_code LIKE ?";

        String today = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
        String prefix = "BK" + today;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, prefix + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int maxNum = rs.getInt("max_num");
                    return prefix + String.format("%03d", maxNum + 1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prefix + "001";
    }
}