package vn.edu.nlu.fit.demo1.controller;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.nlu.fit.demo1.model.Booking;
import vn.edu.nlu.fit.demo1.model.User;
import vn.edu.nlu.fit.demo1.service.BookingService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "CancelBookingController", urlPatterns = {"/cancel-booking"})
public class CancelBookingController extends HttpServlet {

    private final BookingService bookingService = new BookingService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> result = new HashMap<>();

        try {
            HttpSession session = request.getSession(false);
            User user = (User) (session != null ? session.getAttribute("user") : null);

            if (user == null) {
                result.put("success", false);
                result.put("message", "Vui lòng đăng nhập để hủy đặt phòng");
                response.getWriter().write(gson.toJson(result));
                return;
            }

            String bookingIdStr = request.getParameter("bookingId");
            String reason = request.getParameter("reason");
            String otherReason = request.getParameter("otherReason");

            if (bookingIdStr == null || reason == null) {
                result.put("success", false);
                result.put("message", "Thiếu thông tin");
                response.getWriter().write(gson.toJson(result));
                return;
            }

            int bookingId = Integer.parseInt(bookingIdStr);

            Booking booking = bookingService.getBookingById(bookingId);

            if (booking == null) {
                result.put("success", false);
                result.put("message", "Không tìm thấy đặt phòng");
                response.getWriter().write(gson.toJson(result));
                return;
            }

            if (booking.getUserId() != user.getId()) {
                result.put("success", false);
                result.put("message", "Bạn không có quyền hủy đặt phòng này");
                response.getWriter().write(gson.toJson(result));
                return;
            }

            if (!"confirmed".equals(booking.getStatus()) && !"pending".equals(booking.getStatus())) {
                result.put("success", false);
                result.put("message", "Không thể hủy đặt phòng này");
                response.getWriter().write(gson.toJson(result));
                return;
            }

            String cancelReason = "Lý do khác".equals(reason) ? otherReason : reason;

            boolean success = bookingService.updateBookingStatus(bookingId, "cancelled");

            if (success) {
                result.put("success", true);
                result.put("message", "Hủy đặt phòng thành công! Tiền sẽ được hoàn trả sớm cho bạn.");
            } else {
                result.put("success", false);
                result.put("message", "Không thể hủy đặt phòng. Vui lòng thử lại");
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "Có lỗi xảy ra: " + e.getMessage());
        }

        response.getWriter().write(gson.toJson(result));
    }
}