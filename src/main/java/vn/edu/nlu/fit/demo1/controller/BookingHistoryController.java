package vn.edu.nlu.fit.demo1.controller;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.demo1.model.Booking;
import vn.edu.nlu.fit.demo1.model.User;
import vn.edu.nlu.fit.demo1.service.BookingService;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "BookingHistoryController", urlPatterns = {"/bookings", "/booking-history"})
public class BookingHistoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookingService bookingService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.bookingService = new BookingService();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");

        List<Booking> bookings = bookingService.getBookingsByUserId(user.getId());

        request.setAttribute("user", user);
        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("/WEB-INF/views/booking-history.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}