package vn.edu.nlu.fit.demo1.service;

import vn.edu.nlu.fit.demo1.dao.*;
import vn.edu.nlu.fit.demo1.model.*;
import java.util.List;

public class HotelDetailService {
    private HotelDetailDAO hotelDetailDAO = new HotelDetailDAO();
    private RoomDAO roomDAO = new RoomDAO();
    private AmenityDAO amenityDAO = new AmenityDAO();

    public HotelDetail getHotelFullDetail(int id) {
        HotelDetail hotel = hotelDetailDAO.findById(id);
        if (hotel != null) {
            hotel.setAmenities(amenityDAO.getByHotelId(id));
        }
        return hotel;
    }

    public List<Room> getRoomsByHotel(int hotelId) {
        return roomDAO.getRoomsByHotelId(hotelId);
    }
}
