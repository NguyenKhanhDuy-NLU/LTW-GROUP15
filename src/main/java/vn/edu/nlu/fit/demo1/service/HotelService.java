package vn.edu.nlu.fit.demo1.service;
import vn.edu.nlu.fit.demo1.dao.HotelDAO;
import vn.edu.nlu.fit.demo1.model.Hotel;
import java.util.List;

public class HotelService {
    private HotelDAO hotelDAO;

    public HotelService() {
        this.hotelDAO = new HotelDAO();
    }

    public List<Hotel> getAllHotels() {
        return hotelDAO.getAllHotels();
    }

    public Hotel getHotelById(int id) {
        return hotelDAO.getHotelById(id);
    }

    public List<Hotel> searchHotels(String cityName, Integer minPrice, Integer maxPrice, String[] amenities) {
        return hotelDAO.searchHotels(cityName, minPrice, maxPrice, amenities);
    }
}