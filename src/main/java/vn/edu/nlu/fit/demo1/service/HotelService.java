
package vn.edu.nlu.fit.demo1.service;

import vn.edu.nlu.fit.demo1.dao.HotelDAO;
import vn.edu.nlu.fit.demo1.model.Hotel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HotelService {
    private final HotelDAO hotelDAO;

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


    public static class SearchFilter {
        private String location;
        private String cityId;
        private String checkin;
        private String checkout;
        private String guests;
        private Integer minPrice;
        private Integer maxPrice;
        private String[] starRating;
        private List<String> selectedAmenities;

        public SearchFilter(String location, String cityId, String checkin, String checkout,
                            String guests, Integer minPrice, Integer maxPrice,
                            String[] starRating, List<String> selectedAmenities) {
            this.location = location;
            this.cityId = cityId;
            this.checkin = checkin;
            this.checkout = checkout;
            this.guests = guests;
            this.minPrice = minPrice;
            this.maxPrice = maxPrice;
            this.starRating = starRating;
            this.selectedAmenities = selectedAmenities;
        }

        public String getLocation() { return location; }
        public String getCityId() { return cityId; }
        public String getCheckin() { return checkin; }
        public String getCheckout() { return checkout; }
        public String getGuests() { return guests; }
        public Integer getMinPrice() { return minPrice; }
        public Integer getMaxPrice() { return maxPrice; }
        public String[] getStarRating() { return starRating; }
        public List<String> getSelectedAmenities() { return selectedAmenities; }
    }

    public static class SearchResult {
        private List<Hotel> hotels;
        private CityInfo cityInfo;

        public SearchResult(List<Hotel> hotels, CityInfo cityInfo) {
            this.hotels = hotels;
            this.cityInfo = cityInfo;
        }

        public List<Hotel> getHotels() { return hotels; }
        public CityInfo getCityInfo() { return cityInfo; }

        public static class CityInfo {
            private Integer cityId;
            private String cityName;

            public CityInfo(Integer cityId, String cityName) {
                this.cityId = cityId;
                this.cityName = cityName;
            }

            public Integer getCityId() { return cityId; }
            public String getCityName() { return cityName; }
        }
    }

    public SearchResult searchHotels(SearchFilter filter) {
        List<Hotel> hotels;
        SearchResult.CityInfo cityInfo;

        Integer cityId = null;
        if (filter.getCityId() != null && !filter.getCityId().trim().isEmpty()) {
            try {
                cityId = Integer.parseInt(filter.getCityId());
            } catch (NumberFormatException e) {

            }
        }

        List<Integer> starRatings = new ArrayList<>();
        if (filter.getStarRating() != null) {
            for (String star : filter.getStarRating()) {
                try {
                    starRatings.add(Integer.parseInt(star));
                } catch (NumberFormatException e) {

                }
            }
        }

        String[] amenitiesArray = null;
        if (filter.getSelectedAmenities() != null && !filter.getSelectedAmenities().isEmpty()) {
            amenitiesArray = filter.getSelectedAmenities().toArray(new String[0]);
        }

        hotels = hotelDAO.searchHotels(
                filter.getLocation(),
                filter.getMinPrice(),
                filter.getMaxPrice(),
                amenitiesArray
        );

        if (!starRatings.isEmpty()) {
            List<Hotel> filteredHotels = new ArrayList<>();
            for (Hotel hotel : hotels) {
                if (starRatings.contains(hotel.getStarRating())) {
                    filteredHotels.add(hotel);
                }
            }
            hotels = filteredHotels;
        }

        String cityName = filter.getLocation() != null ? filter.getLocation() : "Tất cả";
        cityInfo = new SearchResult.CityInfo(cityId, cityName);

        return new SearchResult(hotels, cityInfo);
    }

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
    public String formatPrice(Integer price) {
        if (price == null) return "0";
        return String.format("%,d", price).replace(",", ".");
    }

    public String formatPrice(BigDecimal price) {
        if (price == null) return "0";
        return String.format("%,.0f", price).replace(",", ".");
    }
}