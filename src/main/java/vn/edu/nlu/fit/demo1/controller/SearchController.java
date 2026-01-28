package vn.edu.nlu.fit.demo1.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import vn.edu.nlu.fit.demo1.model.SearchHotelCard;
import vn.edu.nlu.fit.demo1.service.SearchHotelService;
import vn.edu.nlu.fit.demo1.dao.CityDAO;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

@WebServlet("/search")
public class SearchController extends HttpServlet {

    private final SearchHotelService service = new SearchHotelService();
    private final CityDAO cityDAO = new CityDAO();
    
    private static final String[][] AMENITIES = {
            {"wifi", "fa-wifi", "WiFi miễn phí"},
            {"pool", "fa-swimming-pool", "Hồ bơi"},
            {"parking", "fa-parking", "Bãi đỗ xe"},
            {"restaurant", "fa-utensils", "Nhà hàng"},
            {"gym", "fa-dumbbell", "Phòng gym"},
            {"spa", "fa-spa", "Spa"}
    };

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");


        String location = getParam(request, "location");
        String checkin = getParam(request, "checkin");
        String checkout = getParam(request, "checkout");
        String guests = getParam(request, "guests");


        Integer cityId = null;
        String cityIdParam = request.getParameter("cityId");

        if (cityIdParam != null && !cityIdParam.isBlank()) {
            cityId = parseInt(cityIdParam, null);
        }

        // 🔥 Nếu không có cityId nhưng có location → tìm theo tên
        if (cityId == null && location != null && !location.isBlank()) {
            cityId = cityDAO.findCityIdByName(location.trim());
        }

        String minPriceParam = request.getParameter("minPrice");
        String maxPriceParam = request.getParameter("maxPrice");

        Integer minPrice = (minPriceParam == null || minPriceParam.isBlank())
                ? 400000
                : parseInt(minPriceParam, 400000);

        Integer maxPrice = (maxPriceParam == null || maxPriceParam.isBlank())
                ? 20000000
                : parseInt(maxPriceParam, 20000000);

        String[] starsRaw = request.getParameterValues("starRating");
        String[] amenitiesRaw = request.getParameterValues("amenity");
        List<Integer> stars = parseIntList(starsRaw);
        List<String> amenities = amenitiesRaw != null
                ? Arrays.asList(amenitiesRaw)
                : new ArrayList<>();


        List<SearchHotelCard> hotels = new ArrayList<>();

        if (cityId != null) {
            hotels = service.searchHotels(cityId, minPrice, maxPrice, stars, amenities);
        }


        DecimalFormat formatter = new DecimalFormat("#,###");
        String minPriceFormat = formatter.format(minPrice).replace(",", ".");
        String maxPriceFormat = formatter.format(maxPrice).replace(",", ".");

        request.setAttribute("searchLocation", location != null ? location : "");
        request.setAttribute("searchCheckin", checkin != null ? checkin : "");
        request.setAttribute("searchCheckout", checkout != null ? checkout : "");
        request.setAttribute("searchGuests", guests != null ? guests : "");
        request.setAttribute("cityId", cityId);

        request.setAttribute("minPrice", minPrice);
        request.setAttribute("maxPrice", maxPrice);
        request.setAttribute("minPriceFormat", minPriceFormat);
        request.setAttribute("maxPriceFormat", maxPriceFormat);


        request.setAttribute("selectedStars", stars);
        request.setAttribute("selectedAmenities", amenities);

        request.setAttribute("AMENITIES", AMENITIES);


        request.setAttribute("hotels", hotels);

        String pageTitle = location != null && !location.isBlank()
                ? "Khách sạn tại " + location
                : "Tìm kiếm khách sạn";
        request.setAttribute("pageTitle", pageTitle);

        HttpSession session = request.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
        request.setAttribute("isLoggedIn", isLoggedIn);

        if (isLoggedIn) {
            request.setAttribute("displayName", session.getAttribute("displayName"));
        }
        request.getRequestDispatcher("/WEB-INF/views/search.jsp")
                .forward(request, response);
    }


    private String getParam(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        return (value != null && !value.isBlank()) ? value.trim() : null;
    }

    private Integer parseInt(String s, Integer def) {
        try {
            return (s == null || s.isBlank()) ? def : Integer.parseInt(s);
        } catch (Exception e) {
            return def;
        }
    }

    private List<Integer> parseIntList(String[] arr) {
        List<Integer> list = new ArrayList<>();
        if (arr != null) {
            for (String s : arr) {
                try {
                    list.add(Integer.parseInt(s));
                } catch (Exception ignored) {}
            }
        }
        return list;
    }
}
