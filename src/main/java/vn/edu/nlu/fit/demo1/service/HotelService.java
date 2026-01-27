package vn.edu.nlu.fit.demo1.service;

import vn.edu.nlu.fit.demo1.dao.SearchHotelDAO;
import vn.edu.nlu.fit.demo1.model.SearchHotelCard;

import java.util.List;

public class SearchHotelService {

    private final SearchHotelDAO dao = new SearchHotelDAO();

    public List<SearchHotelCard> getAllHotels() {
        return dao.getAllHotels();
    }


    public List<SearchHotelCard> searchHotels(
            Integer cityId,
            Integer minPrice,
            Integer maxPrice,
            List<Integer> stars,
            List<String> amenities
    ) {
        return dao.searchHotels(cityId, minPrice, maxPrice, stars, amenities);
    }
    public SearchHotelCard getById(int id) {
        return dao.getById(id);
    }
}
