package vn.edu.nlu.fit.demo1.service;

import vn.edu.nlu.fit.demo1.dao.BookingDAO;
import vn.edu.nlu.fit.demo1.model.Booking;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class PaymentService {

    private final BookingDAO bookingDAO = new BookingDAO();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    public int calculateNights(String checkin, String checkout) {
        if (checkin == null || checkout == null) return 1;

        try {
            LocalDate in = LocalDate.parse(checkin);
            LocalDate out = LocalDate.parse(checkout);
            int nights = (int) ChronoUnit.DAYS.between(in, out);
            return nights > 0 ? nights : 1;
        } catch (Exception e) {
            return 1;
        }
    }

    public boolean validatePaymentData(
            String hotelId,
            String roomId,
            String checkin,
            String checkout,
            String totalPrice
    ) {
        try {
            if (hotelId == null || roomId == null || totalPrice == null) return false;
            if (checkin == null || checkout == null) return false;

            Integer.parseInt(hotelId);
            Integer.parseInt(roomId);
            new BigDecimal(totalPrice);
            dateFormat.parse(checkin);
            dateFormat.parse(checkout);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Booking processPayment(
            int userId,
            int hotelId,
            int roomId,
            String checkin,
            String checkout,
            int guests,
            String roomType,
            BigDecimal totalPrice,
            String notes
    ) {
        try {
            Date checkInDate = dateFormat.parse(checkin);
            Date checkOutDate = dateFormat.parse(checkout);

            Booking booking = new Booking();
            booking.setUserId(userId);
            booking.setHotelId(hotelId);
            booking.setRoomId(roomId);
            booking.setCheckInDate(checkInDate);
            booking.setCheckOutDate(checkOutDate);
            booking.setGuests(guests);
            booking.setRoomType(roomType != null ? roomType : "Standard");
            booking.setTotalPrice(totalPrice);
            booking.setStatus("confirmed");
            booking.setPaymentStatus("paid");
            booking.setNotes(notes);

            if (booking.getBookingCode() == null || booking.getBookingCode().isEmpty()) {
                booking.setBookingCode(bookingDAO.generateBookingCode());
            }

            boolean created = bookingDAO.createBooking(booking);
            return created ? booking : null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BigDecimal calculateTotalPrice(
            BigDecimal basePrice,
            int nights,
            BigDecimal discountPercent
    ) {
        BigDecimal total = basePrice.multiply(BigDecimal.valueOf(nights));

        if (discountPercent != null && discountPercent.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal discount = total.multiply(discountPercent)
                    .divide(BigDecimal.valueOf(100));
            total = total.subtract(discount);
        }

        return total;
    }
}
