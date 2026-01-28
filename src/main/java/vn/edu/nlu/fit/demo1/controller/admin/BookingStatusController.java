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
import java.util.Map;

@WebServlet(name = "BookingStatusController", urlPatterns = {"/admin/bookings/update-status"})
public class BookingStatusController extends HttpServlet {

    private AdminBookingDAO adminBookingDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        this.adminBookingDAO = new AdminBookingDAO();
        this.gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> result = new HashMap<>();
        PrintWriter out = response.getWriter();

        try {
            String idParam = request.getParameter("id");
            String status = request.getParameter("status");

            if (idParam == null || status == null) {
                result.put("success", false);
                result.put("message", "Thiếu thông tin bắt buộc");
                out.print(gson.toJson(result));
                return;
            }

            int bookingId = Integer.parseInt(idParam);
            Booking booking = adminBookingDAO.getBookingById(bookingId);

            if (booking == null) {
                result.put("success", false);
                result.put("message", "Không tìm thấy booking");
                out.print(gson.toJson(result));
                return;
            }

            booking.setStatus(status);
            boolean success = adminBookingDAO.updateBooking(booking);

            result.put("success", success);
            result.put("message", success ? "Cập nhật thành công" : "Cập nhật thất bại");
            out.print(gson.toJson(result));

        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Có lỗi xảy ra: " + e.getMessage());
            out.print(gson.toJson(result));
            e.printStackTrace();
        }
    }
}