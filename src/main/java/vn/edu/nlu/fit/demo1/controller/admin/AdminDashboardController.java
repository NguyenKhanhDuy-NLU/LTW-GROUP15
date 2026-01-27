package vn.edu.nlu.fit.demo1.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.demo1.dao.AdminHotelDAO;
import vn.edu.nlu.fit.demo1.model.DashboardStats;

import java.io.IOException;

@WebServlet(name = "AdminDashboardController", urlPatterns = {"/admin/dashboard", "/admin", "/admin/"})
public class AdminDashboardController extends HttpServlet {

    private AdminHotelDAO adminHotelDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.adminHotelDAO = new AdminHotelDAO();
        System.out.println("AdminDashboardController initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("=== ADMIN DASHBOARD ===");
        System.out.println("Loading dashboard statistics...");

        try {
            DashboardStats stats = adminHotelDAO.getDashboardStats();

            if (stats == null) {
                System.err.println("✗ Failed to load dashboard stats - stats is null");
                stats = new DashboardStats();
            } else {
                System.out.println("✓ Dashboard stats loaded successfully");
                System.out.println("  - Total Hotels: " + stats.getTotalHotels());
                System.out.println("  - Total Bookings: " + stats.getTotalBookings());
                System.out.println("  - Total Users: " + stats.getTotalUsers());
            }

            request.setAttribute("stats", stats);

            System.out.println("✓ Forwarding to dashboard.jsp...");
            request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("✗ Error loading dashboard: " + e.getMessage());
            e.printStackTrace();

            request.setAttribute("errorMessage", "Không thể tải dashboard. Vui lòng thử lại.");
            request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);
        }
    }
}