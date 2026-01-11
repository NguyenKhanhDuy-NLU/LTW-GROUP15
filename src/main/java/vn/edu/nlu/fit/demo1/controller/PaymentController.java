package vn.edu.nlu.fit.demo1.controller;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.nlu.fit.demo1.model.*;
import vn.edu.nlu.fit.demo1.service.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "PaymentController", urlPatterns = {"/payment"})
public class PaymentController extends HttpServlet {

    private final HotelService hotelService = new HotelService();
    private final RoomService roomService = new RoomService();
    private final PaymentService paymentService = new PaymentService();
    private final DecimalFormat priceFormatter = new DecimalFormat("#,###");
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String hotelIdStr = request.getParameter("hotelId");
        String roomIdStr = request.getParameter("roomId");
        String checkin = request.getParameter("checkin");
        String checkout = request.getParameter("checkout");
        String guestsStr = request.getParameter("guests");

        if (hotelIdStr == null || roomIdStr == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        try {
            int hotelId = Integer.parseInt(hotelIdStr);
            int roomId = Integer.parseInt(roomIdStr);
            int guests = guestsStr != null ? Integer.parseInt(guestsStr) : 1;

            Hotel hotel = hotelService.getHotelById(hotelId);
            Room room = roomService.getRoomById(roomId);

            if (hotel == null || room == null) {
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }

            int nights = paymentService.calculateNights(checkin, checkout);
            BigDecimal basePrice = BigDecimal.valueOf(room.getBasePrice());
            BigDecimal totalPrice = basePrice.multiply(BigDecimal.valueOf(nights));

            request.setAttribute("hotel", hotel);
            request.setAttribute("room", room);
            request.setAttribute("checkin", checkin);
            request.setAttribute("checkout", checkout);
            request.setAttribute("guests", guests);
            request.setAttribute("nights", nights);
            request.setAttribute("basePrice", basePrice);
            request.setAttribute("totalPrice", totalPrice);
            request.setAttribute("basePriceFormatted", priceFormatter.format(basePrice));
            request.setAttribute("totalPriceFormatted", priceFormatter.format(totalPrice));
            request.setAttribute("isLoggedIn", true);
            request.setAttribute("displayName", getUserDisplayName(user));

            request.getRequestDispatcher("/WEB-INF/view/payment.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> result = new HashMap<>();
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);

        if (user == null) {
            result.put("success", false);
            result.put("message", "Vui lòng đăng nhập");
            out.print(gson.toJson(result));
            return;
        }

        String hotelIdStr = request.getParameter("hotelId");
        String roomIdStr = request.getParameter("roomId");
        String checkin = request.getParameter("checkin");
        String checkout = request.getParameter("checkout");
        String guestsStr = request.getParameter("guests");
        String roomType = request.getParameter("roomType");
        String totalPriceStr = request.getParameter("totalPrice");
        String notes = request.getParameter("notes");

        if (!paymentService.validatePaymentData(
                hotelIdStr, roomIdStr, checkin, checkout, totalPriceStr)) {

            result.put("success", false);
            result.put("message", "Dữ liệu không hợp lệ");
            out.print(gson.toJson(result));
            return;
        }

        int hotelId = Integer.parseInt(hotelIdStr);
        int roomId = Integer.parseInt(roomIdStr);
        int guests = guestsStr != null ? Integer.parseInt(guestsStr) : 1;
        BigDecimal totalPrice = new BigDecimal(totalPriceStr);

        Booking booking = paymentService.processPayment(
                user.getId(),
                hotelId,
                roomId,
                checkin,
                checkout,
                guests,
                roomType,
                totalPrice,
                notes
        );

        if (booking != null) {
            result.put("success", true);
            result.put("bookingCode", booking.getBookingCode());
            result.put("bookingId", booking.getId());
        } else {
            result.put("success", false);
            result.put("message", "Không thể tạo booking");
        }

        out.print(gson.toJson(result));
    }

    private String getUserDisplayName(User user) {
        return (user.getFullName() != null && !user.getFullName().isEmpty())
                ? user.getFullName() : user.getUsername();
    }
}
