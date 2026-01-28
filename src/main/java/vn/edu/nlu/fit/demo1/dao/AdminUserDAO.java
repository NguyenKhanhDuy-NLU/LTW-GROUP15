package vn.edu.nlu.fit.demo1.dao;

import vn.edu.nlu.fit.demo1.config.DatabaseConfig;
import vn.edu.nlu.fit.demo1.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminUserDAO {

    public List<User> getAllUsers(int page, int pageSize, String search) {
        List<User> users = new ArrayList<>();
        int offset = (page - 1) * pageSize;

        // ĐÃ SỬA: Đổi 'users' thành 'user', 'bookings' thành 'booking'
        StringBuilder sql = new StringBuilder(
                "SELECT u.*, " +
                        "(SELECT COUNT(*) FROM booking WHERE user_id = u.id) as booking_count " +
                        "FROM user u ");

        if (search != null && !search.trim().isEmpty()) {
            sql.append("WHERE (u.username LIKE ? OR u.full_name LIKE ? OR u.email LIKE ?) ");
        }

        sql.append("ORDER BY u.created_at DESC LIMIT ? OFFSET ?");

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (search != null && !search.trim().isEmpty()) {
                String searchPattern = "%" + search + "%";
                stmt.setString(paramIndex++, searchPattern);
                stmt.setString(paramIndex++, searchPattern);
                stmt.setString(paramIndex++, searchPattern);
            }
            stmt.setInt(paramIndex++, pageSize);
            stmt.setInt(paramIndex, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = extractUserFromResultSet(rs);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading users: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    public int countUsers(String search) {
        // ĐÃ SỬA: Đổi 'users' thành 'user'
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM user");

        if (search != null && !search.trim().isEmpty()) {
            sql.append(" WHERE username LIKE ? OR full_name LIKE ? OR email LIKE ?");
        }

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            if (search != null && !search.trim().isEmpty()) {
                String searchPattern = "%" + search + "%";
                stmt.setString(1, searchPattern);
                stmt.setString(2, searchPattern);
                stmt.setString(3, searchPattern);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public User getUserById(int id) {
        // ĐÃ SỬA: Đổi 'users' thành 'user', 'bookings' thành 'booking'
        String sql = "SELECT u.*, " +
                "(SELECT COUNT(*) FROM booking WHERE user_id = u.id) as booking_count " +
                "FROM user u WHERE u.id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateUserStatus(int id, boolean isActive) {
        // ĐÃ SỬA: Đổi 'users' thành 'user'
        String sql = "UPDATE user SET is_active = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, isActive);
            stmt.setInt(2, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating user status: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUserRole(int id, String role) {
        // ĐÃ SỬA: Đổi 'users' thành 'user', 'role_id' thành 'role' (ENUM)
        String sql = "UPDATE user SET role = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, role);
            stmt.setInt(2, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating user role: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean verifyUser(int id) {
        // ĐÃ SỬA: Đổi 'users' thành 'user'
        String sql = "UPDATE user SET is_verified = TRUE, updated_at = CURRENT_TIMESTAMP WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error verifying user: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(int id) {
        // ĐÃ SỬA: Đổi 'users' thành 'user'
        String sql = "DELETE FROM user WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setAddress(rs.getString("address"));
        user.setGender(rs.getString("gender"));
        user.setAvatarUrl(rs.getString("avatar_url"));
        user.setVerified(rs.getBoolean("is_verified"));

        String role = rs.getString("role");
        user.setRole(role);

        user.setActive(rs.getBoolean("is_active"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        user.setUpdatedAt(rs.getTimestamp("updated_at"));

        try {
            user.setBookingCount(rs.getInt("booking_count"));
        } catch (SQLException e) {
        }

        return user;
    }
}