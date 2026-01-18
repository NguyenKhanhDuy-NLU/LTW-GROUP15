package vn.edu.nlu.fit.demo1.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class DashboardStats implements Serializable {
    private static final long serialVersionUID = 1L;

    private int totalHotels;
    private int totalBookings;
    private int pendingBookings;
    private int confirmedBookings;
    private int totalUsers;
    private BigDecimal totalRevenue;
    private BigDecimal monthRevenue;

    public DashboardStats() {}

    public int getTotalHotels() { return totalHotels; }
    public void setTotalHotels(int totalHotels) { this.totalHotels = totalHotels; }

    public int getTotalBookings() { return totalBookings; }
    public void setTotalBookings(int totalBookings) { this.totalBookings = totalBookings; }

    public int getPendingBookings() { return pendingBookings; }
    public void setPendingBookings(int pendingBookings) { this.pendingBookings = pendingBookings; }

    public int getConfirmedBookings() { return confirmedBookings; }
    public void setConfirmedBookings(int confirmedBookings) { this.confirmedBookings = confirmedBookings; }

    public int getTotalUsers() { return totalUsers; }
    public void setTotalUsers(int totalUsers) { this.totalUsers = totalUsers; }

    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }

    public BigDecimal getMonthRevenue() { return monthRevenue; }
    public void setMonthRevenue(BigDecimal monthRevenue) { this.monthRevenue = monthRevenue; }

    public String getFormattedTotalRevenue() {
        return formatCurrency(totalRevenue);
    }

    public String getFormattedMonthRevenue() {
        return formatCurrency(monthRevenue);
    }

    private String formatCurrency(BigDecimal amount) {
        if (amount == null) return "0 đ";
        return String.format("%,.0f đ", amount);
    }
}