package vn.edu.nlu.fit.demo1.dao;

import vn.edu.nlu.fit.demo1.config.DatabaseConfig;
import vn.edu.nlu.fit.demo1.model.User;
import vn.edu.nlu.fit.demo1.util.PasswordUtil;

import java.sql.*;

public class UserDAO {

    public User authenticate(String username, String password) {
        System.out.println("UserDAO.authenticate() - Starting authentication for: " + username);

        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("UserDAO.authenticate() - User found in database");

                    String hashedPasswordInDB = rs.getString("password");
                    String hashedInputPassword = PasswordUtil.hashPassword(password);

                    System.out.println("UserDAO.authenticate() - Hash from DB: " + hashedPasswordInDB);
                    System.out.println("UserDAO.authenticate() - Hash from input: " + hashedInputPassword);

                    if (PasswordUtil.checkPassword(password, hashedPasswordInDB)) {
                        System.out.println("UserDAO.authenticate() - ✓ Password matched! Authentication successful");
                        return extractUserFromResultSet(rs);
                    } else {
                        System.out.println("UserDAO.authenticate() - ✗ Password mismatch");
                    }
                } else {
                    System.out.println("UserDAO.authenticate() - ✗ User not found in database");
                }
            }
        } catch (SQLException e) {
            System.err.println("UserDAO.authenticate() - SQL Error: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("UserDAO.authenticate() - Authentication failed");
        return null;
    }

    public boolean register(User user) {
        System.out.println("UserDAO.register() - Starting registration for: " + user.getUsername());

        String sql = "INSERT INTO users (username, password, full_name, email, phone, address, gender, role_id, is_active) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // md5
            String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
            System.out.println("UserDAO.register() - Password hashed: " + hashedPassword);

            stmt.setString(1, user.getUsername());
            stmt.setString(2, hashedPassword);
            stmt.setString(3, user.getFullName());
            stmt.setString(4, user.getEmail());

            if (user.getPhone() != null && !user.getPhone().trim().isEmpty()) {
                stmt.setString(5, user.getPhone());
            } else {
                stmt.setNull(5, Types.VARCHAR);
            }

            if (user.getAddress() != null && !user.getAddress().trim().isEmpty()) {
                stmt.setString(6, user.getAddress());
            } else {
                stmt.setNull(6, Types.VARCHAR);
            }

            if (user.getGender() != null && !user.getGender().trim().isEmpty()) {
                stmt.setString(7, user.getGender());
            } else {
                stmt.setNull(7, Types.VARCHAR);
            }

            int roleId = user.getRoleId() > 0 ? user.getRoleId() : 2;
            stmt.setInt(8, roleId);

            stmt.setBoolean(9, true);

            System.out.println("UserDAO.register() - Executing SQL insert...");
            int affectedRows = stmt.executeUpdate();
            System.out.println("UserDAO.register() - Affected rows: " + affectedRows);

            if (affectedRows > 0) {
                // Lấy ID của user vừa tạo
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        user.setId(generatedId);
                        System.out.println("UserDAO.register() - ✓ SUCCESS! User ID: " + generatedId);
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("UserDAO.register() - SQL Error: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
        }

        System.out.println("UserDAO.register() - ✗ FAILED!");
        return false;
    }

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        // Xác thực password cũ trước
        User user = authenticate(username, oldPassword);
        if (user == null) {
            System.out.println("UserDAO.changePassword() - Old password incorrect");
            return false;
        }

        String sql = "UPDATE users SET password = ? WHERE username = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String hashedNewPassword = PasswordUtil.hashPassword(newPassword);

            stmt.setString(1, hashedNewPassword);
            stmt.setString(2, username);

            int result = stmt.executeUpdate();
            System.out.println("UserDAO.changePassword() - Password changed successfully");
            return result > 0;
        } catch (SQLException e) {
            System.err.println("UserDAO.changePassword() - Error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE users SET full_name = ?, email = ?, phone = ?, address = ?, gender = ?, avatar = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getAddress());
            stmt.setString(5, user.getGender());
            stmt.setString(6, user.getAvatar());
            stmt.setInt(7, user.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("UserDAO.updateUser() - Error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

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

    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

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

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

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

    public boolean verifyUser(int userId) {
        String sql = "UPDATE users SET is_verified = TRUE WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isUserVerified(int userId) {
        String sql = "SELECT is_verified FROM users WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("is_verified");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePasswordById(int userId, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String hashedPassword = PasswordUtil.hashPassword(newPassword);

            stmt.setString(1, hashedPassword);
            stmt.setInt(2, userId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean canResendVerification(int userId) {
        String sql = "SELECT created_at FROM verification_tokens " +
                "WHERE user_id = ? AND token_type = 'email_verification' " +
                "ORDER BY created_at DESC LIMIT 1";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Timestamp lastSent = rs.getTimestamp("created_at");
                    long minutesAgo = (System.currentTimeMillis() - lastSent.getTime()) / (1000 * 60);

                    return minutesAgo >= 5;
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setAddress(rs.getString("address"));
        user.setGender(rs.getString("gender"));
        user.setAvatar(rs.getString("avatar"));

        try {
            user.setVerified(rs.getBoolean("is_verified"));
        } catch (SQLException e) {
            user.setVerified(false);
        }

        try {
            user.setRoleId(rs.getInt("role_id"));
        } catch (SQLException e) {
            user.setRoleId(2);
        }

        try {
            user.setActive(rs.getBoolean("is_active"));
        } catch (SQLException e) {
            user.setActive(true);
        }

        return user;
    }

    public boolean isPhoneExists(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }

        String sql = "SELECT COUNT(*) FROM users WHERE phone = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phone.trim());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isEmailOrUsernameExists(String email, String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ? OR username = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email.trim());
            stmt.setString(2, username.trim());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserByEmailOrUsername(String identifier) {
        String sql = "SELECT * FROM users WHERE email = ? OR username = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, identifier.trim());
            stmt.setString(2, identifier.trim());

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

    public boolean isEmailTakenByOtherUser(int userId, String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ? AND id != ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email.trim());
            stmt.setInt(2, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isPhoneTakenByOtherUser(int userId, String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }

        String sql = "SELECT COUNT(*) FROM users WHERE phone = ? AND id != ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phone.trim());
            stmt.setInt(2, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}