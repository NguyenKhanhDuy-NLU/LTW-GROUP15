package vn.edu.nlu.fit.demo1.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.nlu.fit.demo1.model.Booking;
import vn.edu.nlu.fit.demo1.model.Hotel;
import vn.edu.nlu.fit.demo1.model.Room;
import vn.edu.nlu.fit.demo1.model.User;
import vn.edu.nlu.fit.demo1.service.BookingService;
import vn.edu.nlu.fit.demo1.service.HotelService;
import vn.edu.nlu.fit.demo1.service.RoomService;

import java.io.IOException;
import java.text.SimpleDateFormat;

@WebServlet(name = "BookingSuccessController", urlPatterns = {"/booking-success"})
public class BookingSuccessController extends HttpServlet {

    private final BookingService bookingService = new BookingService();
    private final HotelService hotelService = new HotelService();
    private final RoomService roomService = new RoomService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");


        String bookingIdStr = request.getParameter("bookingId");

        if (bookingIdStr == null || bookingIdStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        try {
            int bookingId = Integer.parseInt(bookingIdStr);

            Booking booking = bookingService.getBookingById(bookingId);

            if (booking == null) {
                request.setAttribute("error", "Không tìm thấy đặt phòng");
                request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request, response);
                return;
            }

            Hotel hotel = hotelService.getHotelById(booking.getHotelId());
            Room room = roomService.getRoomById(booking.getRoomId());

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

            String checkInFormatted = dateFormat.format(booking.getCheckInDate());
            String checkOutFormatted = dateFormat.format(booking.getCheckOutDate());

            long diff = booking.getCheckOutDate().getTime() - booking.getCheckInDate().getTime();
            int nights = (int) (diff / (1000 * 60 * 60 * 24));

            request.setAttribute("booking", booking);
            request.setAttribute("hotel", hotel);
            request.setAttribute("room", room);
            request.setAttribute("user", user);
            request.setAttribute("checkInFormatted", checkInFormatted);
            request.setAttribute("checkOutFormatted", checkOutFormatted);
            request.setAttribute("nights", nights);

            boolean canCancel = "confirmed".equals(booking.getStatus()) ||
                    "pending".equals(booking.getStatus());
            request.setAttribute("canCancel", canCancel);

            request.getRequestDispatcher("/WEB-INF/view/booking-success.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request, response);
        }
    }
}