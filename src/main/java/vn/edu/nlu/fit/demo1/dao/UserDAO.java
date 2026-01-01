
package vn.edu.nlu.fit.demo1.dao;

import vn.edu.nlu.fit.demo1.config.DatabaseConfig;
import vn.edu.nlu.fit.demo1.model.User;

import java.sql.*;

public class UserDAO {

    public User authenticate(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

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
        return user;
    }
}