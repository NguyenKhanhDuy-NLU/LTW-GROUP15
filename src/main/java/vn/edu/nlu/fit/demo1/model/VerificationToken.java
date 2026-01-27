package vn.edu.nlu.fit.demo1.model;

import java.time.LocalDateTime;

public class VerificationToken {
    private int id;
    private int userId;
    private String token;
    private String tokenType;
    private LocalDateTime expiryDate;
    private boolean isUsed;
    private LocalDateTime createdAt;

    public VerificationToken() {}

    public VerificationToken(int userId, String token, String tokenType, LocalDateTime expiryDate) {
        this.userId = userId;
        this.token = token;
        this.tokenType = tokenType;
        this.expiryDate = expiryDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }

    public LocalDateTime getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; }

    public boolean isUsed() { return isUsed; }
    public void setUsed(boolean used) { isUsed = used; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }
}