package vn.edu.nlu.fit.demo1.service;

import vn.edu.nlu.fit.demo1.dao.HotelDAO;
import vn.edu.nlu.fit.demo1.model.Hotel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HotelService {
    private final HotelDAO hotelDAO = new HotelDAO();

    public List<Hotel> searchByFilter(
            Integer cityId,
            String checkin,
            String checkout,
            Integer guests,
            Integer minPrice,
            Integer maxPrice,
            List<String> starRatings,
            List<String> amenityFilters
    ) {
        List<Integer> stars = null;
        if (starRatings != null && !starRatings.isEmpty()) {
            stars = new ArrayList<>();
            for (String s : starRatings) {
                try {
                    stars.add(Integer.parseInt(s));
                } catch (NumberFormatException e) {
                    System.err.println("Invalid star rating: " + s);
                }
            }
        }

        BigDecimal minPriceBD = (minPrice != null) ? BigDecimal.valueOf(minPrice) : null;
        BigDecimal maxPriceBD = (maxPrice != null) ? BigDecimal.valueOf(maxPrice) : null;

        return hotelDAO.advancedSearch(
                cityId,
                checkin,
                checkout,
                guests,
                minPriceBD,
                maxPriceBD,
                stars,
                amenityFilters
        );
    }

    public List<Hotel> searchHotelsByCityId(int cityId, String checkin, String checkout) {
        return hotelDAO.advancedSearch(cityId, checkin, checkout, null, null, null, null, null);
    }
    public List<Hotel> searchHotels(String location, String checkin, String checkout) {
        String searchLocation = (location != null) ? location.trim() : "";
        return hotelDAO.searchHotels(searchLocation, checkin, checkout);
    }
    public Hotel getHotelById(int id) {
        return hotelDAO.getHotelById(id);
    }
}