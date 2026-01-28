package vn.edu.nlu.fit.demo1.controller.admin;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.demo1.dao.AdminBookingDAO;
import vn.edu.nlu.fit.demo1.model.Booking;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AdminBookingController", urlPatterns = {
        "/admin/bookings",
        "/admin/bookings/view",
        "/admin/bookings/edit",
        "/admin/bookings/update",
        "/admin/bookings/delete"
})
public class AdminBookingController extends HttpServlet {

    private AdminBookingDAO adminBookingDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        this.adminBookingDAO = new AdminBookingDAO();
        this.gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        switch (path) {
            case "/admin/bookings":
                showBookingList(request, response);
                break;
            case "/admin/bookings/view":
                viewBookingDetail(request, response);
                break;
            case "/admin/bookings/edit":
                editBookingForm(request, response);
                break;
            case "/admin/bookings/delete":
                deleteBooking(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if ("/admin/bookings/update".equals(path)) {
            updateBooking(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showBookingList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int page = 1;
        int pageSize = 1000; // Hiển thị tất cả

        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        String statusFilter = request.getParameter("status");
        List<Booking> bookings;
        int totalBookings;

        if (statusFilter != null && !statusFilter.isEmpty()) {
            bookings = adminBookingDAO.getBookingsByStatus(statusFilter, page, pageSize);
            totalBookings = adminBookingDAO.countBookingsByStatus(statusFilter);
        } else {
            bookings = adminBookingDAO.getAllBookings(page, pageSize);
            totalBookings = adminBookingDAO.countBookings();
        }

        int totalPages = (int) Math.ceil((double) totalBookings / pageSize);

        System.out.println("=== ADMIN BOOKING LIST ===");
        System.out.println("Page: " + page);
        System.out.println("Status Filter: " + statusFilter);
        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Bookings retrieved: " + bookings.size());

        request.setAttribute("bookings", bookings);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalBookings", totalBookings);
        request.setAttribute("statusFilter", statusFilter);

        String success = request.getParameter("success");
        String error = request.getParameter("error");
        if (success != null) {
            request.setAttribute("successMessage", getSuccessMessage(success));
        }
        if (error != null) {
            request.setAttribute("errorMessage", getErrorMessage(error));
        }

        request.getRequestDispatcher("/WEB-INF/views/admin/booking-list.jsp")
                .forward(request, response);
    }

    private void viewBookingDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/bookings");
            return;
        }

        try {
            int bookingId = Integer.parseInt(idParam);
            Booking booking = adminBookingDAO.getBookingById(bookingId);

            if (booking == null) {
                response.sendRedirect(request.getContextPath() + "/admin/bookings?error=notfound");
                return;
            }

            System.out.println("Viewing booking: " + booking.getBookingCode());

            request.setAttribute("booking", booking);
            request.setAttribute("mode", "view");
            request.getRequestDispatcher("/WEB-INF/views/admin/booking-detail.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            System.err.println("Invalid booking ID: " + idParam);
            response.sendRedirect(request.getContextPath() + "/admin/bookings?error=invalid");
        }
    }

    private void editBookingForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/bookings");
            return;
        }

        try {
            int bookingId = Integer.parseInt(idParam);
            Booking booking = adminBookingDAO.getBookingById(bookingId);

            if (booking == null) {
                response.sendRedirect(request.getContextPath() + "/admin/bookings?error=notfound");
                return;
            }

            System.out.println("Editing booking: " + booking.getBookingCode());

            request.setAttribute("booking", booking);
            request.setAttribute("mode", "edit");
            request.getRequestDispatcher("/WEB-INF/views/admin/booking-detail.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            System.err.println("Invalid booking ID: " + idParam);
            response.sendRedirect(request.getContextPath() + "/admin/bookings?error=invalid");
        }
    }

    private void updateBooking(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            String idParam = request.getParameter("id");
            String status = request.getParameter("status");
            String paymentStatus = request.getParameter("paymentStatus");
            String notes = request.getParameter("notes");

            System.out.println("=== UPDATE BOOKING ===");
            System.out.println("ID: " + idParam);
            System.out.println("Status: " + status);
            System.out.println("Payment Status: " + paymentStatus);
            System.out.println("Notes: " + notes);

            if (idParam == null || idParam.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/admin/bookings?error=missing");
                return;
            }

            int bookingId = Integer.parseInt(idParam);
            Booking booking = adminBookingDAO.getBookingById(bookingId);

            if (booking == null) {
                response.sendRedirect(request.getContextPath() + "/admin/bookings?error=notfound");
                return;
            }

            booking.setStatus(status);
            booking.setPaymentStatus(paymentStatus);
            booking.setNotes(notes);

            boolean success = adminBookingDAO.updateBooking(booking);

            if (success) {
                System.out.println("Booking updated successfully");
                response.sendRedirect(request.getContextPath() +
                        "/admin/bookings?success=update");
            } else {
                System.err.println("Failed to update booking");
                response.sendRedirect(request.getContextPath() +
                        "/admin/bookings?error=update");
            }

        } catch (NumberFormatException e) {
            System.err.println("Invalid booking ID format");
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/bookings?error=invalid");
        } catch (Exception e) {
            System.err.println("Error updating booking: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/bookings?error=update");
        }
    }

    private void deleteBooking(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/bookings");
            return;
        }

        try {
            int bookingId = Integer.parseInt(idParam);

            System.out.println("Deleting booking ID: " + bookingId);

            boolean success = adminBookingDAO.deleteBooking(bookingId);

            if (success) {
                System.out.println("Booking deleted successfully");
                response.sendRedirect(request.getContextPath() +
                        "/admin/bookings?success=delete");
            } else {
                System.err.println("Failed to delete booking");
                response.sendRedirect(request.getContextPath() +
                        "/admin/bookings?error=delete");
            }

        } catch (NumberFormatException e) {
            System.err.println("Invalid booking ID: " + idParam);
            response.sendRedirect(request.getContextPath() + "/admin/bookings?error=invalid");
        }
    }

    private String getSuccessMessage(String key) {
        switch (key) {
            case "update":
                return "Cập nhật đặt phòng thành công!";
            case "delete":
                return "Xóa đặt phòng thành công!";
            default:
                return "Thao tác thành công!";
        }
    }

    private String getErrorMessage(String key) {
        switch (key) {
            case "update":
                return "Cập nhật đặt phòng thất bại!";
            case "delete":
                return "Xóa đặt phòng thất bại!";
            case "notfound":
                return "Không tìm thấy đặt phòng!";
            case "invalid":
                return "ID không hợp lệ!";
            case "missing":
                return "Thiếu thông tin bắt buộc!";
            default:
                return "Có lỗi xảy ra!";
        }
    }
}