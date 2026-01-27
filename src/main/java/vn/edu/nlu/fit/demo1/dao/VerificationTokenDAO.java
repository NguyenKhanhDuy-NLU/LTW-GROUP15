package vn.edu.nlu.fit.demo1.dao;

import vn.edu.nlu.fit.demo1.config.DatabaseConfig;
import vn.edu.nlu.fit.demo1.model.VerificationToken;

import java.sql.*;
import java.time.LocalDateTime;

public class VerificationTokenDAO {

    public boolean createToken(VerificationToken token) {
        String sql = "INSERT INTO verification_tokens (user_id, token, token_type, expiry_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, token.getUserId());
            stmt.setString(2, token.getToken());
            stmt.setString(3, token.getTokenType());
            stmt.setTimestamp(4, Timestamp.valueOf(token.getExpiryDate()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public VerificationToken getTokenByString(String tokenString) {
        String sql = "SELECT * FROM verification_tokens WHERE token = ? AND is_used = FALSE";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tokenString);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractTokenFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean markTokenAsUsed(String tokenString) {
        String sql = "UPDATE verification_tokens SET is_used = TRUE WHERE token = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tokenString);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deleteExpiredTokens() {
        String sql = "DELETE FROM verification_tokens WHERE expiry_date < NOW()";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int deleted = stmt.executeUpdate();
            System.out.println("Deleted " + deleted + " expired tokens");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private VerificationToken extractTokenFromResultSet(ResultSet rs) throws SQLException {
        VerificationToken token = new VerificationToken();
        token.setId(rs.getInt("id"));
        token.setUserId(rs.getInt("user_id"));
        token.setToken(rs.getString("token"));
        token.setTokenType(rs.getString("token_type"));
        token.setExpiryDate(rs.getTimestamp("expiry_date").toLocalDateTime());
        token.setUsed(rs.getBoolean("is_used"));
        token.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return token;
    }
}