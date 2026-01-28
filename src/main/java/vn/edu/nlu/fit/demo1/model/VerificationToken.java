package vn.edu.nlu.fit.demo1.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class VerificationToken implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int userId;
    private String token;
    private String tokenType;
    private LocalDateTime expiresAt;
    private Timestamp createdAt;
    private boolean isUsed;

    public VerificationToken() {
        this.isUsed = false;
    }

    public VerificationToken(int userId, String token, String tokenType, LocalDateTime expiresAt) {
        this.userId = userId;
        this.token = token;
        this.tokenType = tokenType;
        this.expiresAt = expiresAt;
        this.isUsed = false;
    }
    public VerificationToken(int id, int userId, String token, String tokenType,
                             LocalDateTime expiresAt, Timestamp createdAt, boolean isUsed) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.tokenType = tokenType;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
        this.isUsed = isUsed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getExpiryDate() {
        return expiresAt;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiresAt = expiryDate;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(boolean used) {
        isUsed = used;
    }

    public boolean isExpired() {
        return expiresAt != null && LocalDateTime.now().isAfter(expiresAt);
    }

    public boolean isValid() {
        return !isExpired() && !isUsed;
    }

    public String getTypeText() {
        if (tokenType == null) return "Unknown";
        switch (tokenType.toLowerCase()) {
            case "email_verification":
                return "Xác thực Email";
            case "password_reset":
                return "Đặt lại Mật khẩu";
            default:
                return tokenType;
        }
    }

    @Override
    public String toString() {
        return "VerificationToken{" +
                "id=" + id +
                ", userId=" + userId +
                ", token='" + token + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", expiresAt=" + expiresAt +
                ", isUsed=" + isUsed +
                ", isExpired=" + isExpired() +
                '}';
    }
}