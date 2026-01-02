package vn.edu.nlu.fit.demo1.service;
import vn.edu.nlu.fit.demo1.dao.BookingDAO;
import vn.edu.nlu.fit.demo1.model.Booking;
import java.util.List;

public class BookingService {
    private BookingDAO bookingDAO;

    public BookingService() {
        this.bookingDAO = new BookingDAO();
    }

    public List<Booking> getBookingsByUserId(int userId) {
        return bookingDAO.getBookingsByUserId(userId);
    }

    public Booking getBookingById(int id) {
        return bookingDAO.getBookingById(id);
    }

    public boolean createBooking(Booking booking) {
        if (booking.getBookingCode() == null || booking.getBookingCode().isEmpty()) {
            booking.setBookingCode(bookingDAO.generateBookingCode());
        }
        return bookingDAO.createBooking(booking);
    }

    public boolean updateBookingStatus(int bookingId, String status) {
        return bookingDAO.updateBookingStatus(bookingId, status);
    }

    public String generateBookingCode() {
        return bookingDAO.generateBookingCode();
    }
}