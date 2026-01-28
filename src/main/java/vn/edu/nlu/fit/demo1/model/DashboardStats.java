package vn.edu.nlu.fit.demo1.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class DashboardStats implements Serializable {
    private static final long serialVersionUID = 1L;

    private int totalHotels;
    private int totalBookings;
    private int totalUsers;
    private int pendingBookings;
    private int confirmedBookings;
    private int completedBookings;
    private int cancelledBookings;
    private BigDecimal monthRevenue;
    private BigDecimal totalRevenue;

    public DashboardStats() {
        this.monthRevenue = BigDecimal.ZERO;
        this.totalRevenue = BigDecimal.ZERO;
    }

    public int getTotalHotels() {
        return totalHotels;
    }

    public void setTotalHotels(int totalHotels) {
        this.totalHotels = totalHotels;
    }

    public int getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(int totalBookings) {
        this.totalBookings = totalBookings;
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    public int getPendingBookings() {
        return pendingBookings;
    }

    public void setPendingBookings(int pendingBookings) {
        this.pendingBookings = pendingBookings;
    }

    public int getConfirmedBookings() {
        return confirmedBookings;
    }

    public void setConfirmedBookings(int confirmedBookings) {
        this.confirmedBookings = confirmedBookings;
    }

    public int getCompletedBookings() {
        return completedBookings;
    }

    public void setCompletedBookings(int completedBookings) {
        this.completedBookings = completedBookings;
    }

    public int getCancelledBookings() {
        return cancelledBookings;
    }

    public void setCancelledBookings(int cancelledBookings) {
        this.cancelledBookings = cancelledBookings;
    }

    public BigDecimal getMonthRevenue() {
        return monthRevenue;
    }

    public void setMonthRevenue(BigDecimal monthRevenue) {
        this.monthRevenue = monthRevenue != null ? monthRevenue : BigDecimal.ZERO;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue != null ? totalRevenue : BigDecimal.ZERO;
    }

    public String getFormattedMonthRevenue() {
        if (monthRevenue == null || monthRevenue.compareTo(BigDecimal.ZERO) == 0) {
            return "0 đ";
        }
        return String.format("%,.0f đ", monthRevenue).replace(",", ".");
    }

    public String getFormattedTotalRevenue() {
        if (totalRevenue == null || totalRevenue.compareTo(BigDecimal.ZERO) == 0) {
            return "0 đ";
        }
        return String.format("%,.0f đ", totalRevenue).replace(",", ".");
    }

    public int getActiveBookings() {
        return pendingBookings + confirmedBookings;
    }

    public double getCompletionRate() {
        if (totalBookings == 0) return 0.0;
        return (double) completedBookings / totalBookings * 100.0;
    }

    public double getCancellationRate() {
        if (totalBookings == 0) return 0.0;
        return (double) cancelledBookings / totalBookings * 100.0;
    }

    @Override
    public String toString() {
        return "DashboardStats{" +
                "totalHotels=" + totalHotels +
                ", totalBookings=" + totalBookings +
                ", totalUsers=" + totalUsers +
                ", pendingBookings=" + pendingBookings +
                ", confirmedBookings=" + confirmedBookings +
                ", completedBookings=" + completedBookings +
                ", cancelledBookings=" + cancelledBookings +
                ", monthRevenue=" + getFormattedMonthRevenue() +
                ", totalRevenue=" + getFormattedTotalRevenue() +
                '}';
    }
}