package vn.edu.nlu.fit.demo1.dao;

import vn.edu.nlu.fit.demo1.config.DatabaseConfig;
import vn.edu.nlu.fit.demo1.model.VerificationToken;

import java.sql.*;
import java.time.LocalDateTime;

public class VerificationTokenDAO {

    public boolean createToken(VerificationToken token) {
        String sql = "INSERT INTO verification_tokens (user_id, token, token_type, expires_at) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, token.getUserId());
            stmt.setString(2, token.getToken());
            stmt.setString(3, token.getTokenType());

            stmt.setTimestamp(4, Timestamp.valueOf(token.getExpiresAt()));
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        token.setId(rs.getInt(1));
                        return true;
                    }
                }
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
            return false;
        }
    }

    public int deleteExpiredTokens() {
        String sql = "DELETE FROM verification_tokens WHERE expires_at < NOW()";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            return stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean deleteUserTokens(int userId, String tokenType) {
        String sql = "DELETE FROM verification_tokens WHERE user_id = ? AND token_type = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, tokenType);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isTokenValid(String tokenString) {
        VerificationToken token = getTokenByString(tokenString);
        return token != null && token.isValid();
    }

    private VerificationToken extractTokenFromResultSet(ResultSet rs) throws SQLException {
        VerificationToken token = new VerificationToken();

        token.setId(rs.getInt("id"));
        token.setUserId(rs.getInt("user_id"));
        token.setToken(rs.getString("token"));
        token.setTokenType(rs.getString("token_type"));

        Timestamp expiresTimestamp = rs.getTimestamp("expires_at");
        if (expiresTimestamp != null) {
            token.setExpiresAt(expiresTimestamp.toLocalDateTime());
        }

        token.setCreatedAt(rs.getTimestamp("created_at"));
        token.setUsed(rs.getBoolean("is_used"));

        return token;
    }
}