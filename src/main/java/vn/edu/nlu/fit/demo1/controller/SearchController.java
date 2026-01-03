package vn.edu.nlu.fit.demo1.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.nlu.fit.demo1.model.User;
import vn.edu.nlu.fit.demo1.service.HotelService;
import vn.edu.nlu.fit.demo1.service.HotelService.SearchFilter;
import vn.edu.nlu.fit.demo1.service.HotelService.SearchResult;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SearchController", urlPatterns = {"/search"})
public class SearchController extends HttpServlet {

    private final HotelService hotelService = new HotelService();

    private static final String[][] AMENITIES = {
            {"WiFi", "fa-wifi", "WiFi miễn phí"},
            {"Pool", "fa-person-swimming", "Hồ bơi"},
            {"Parking", "fa-square-parking", "Bãi đỗ xe"},
            {"Free breakfast", "fa-mug-hot", "Bữa sáng miễn phí"},
            {"Gym", "fa-dumbbell", "Gym"},
            {"Spa", "fa-spa", "Spa"}
    };

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String location = request.getParameter("location");
        String cityIdRe = request.getParameter("cityId");
        String checkin = request.getParameter("checkin");
        String checkout = request.getParameter("checkout");
        String guestsRe = request.getParameter("guests");
        String minPriceRe = request.getParameter("minPrice");
        String maxPriceRe = request.getParameter("maxPrice");
        String[] starRating = request.getParameterValues("starRating");
        String[] amenityRe = request.getParameterValues("amenity");

        Integer minPrice = parseInt(minPriceRe, 400000);
        Integer maxPrice = parseInt(maxPriceRe, 20000000);

        List<String> selectedStars = (starRating != null)
                ? Arrays.asList(starRating) : new ArrayList<>();
        List<String> selectedAmenities = (amenityRe != null)
                ? Arrays.asList(amenityRe) : new ArrayList<>();

        SearchFilter filter = new SearchFilter(
                location,
                cityIdRe,
                checkin,
                checkout,
                guestsRe,
                minPrice,
                maxPrice,
                starRating,
                selectedAmenities
        );

        SearchResult result = hotelService.searchHotels(filter);

        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        boolean isLoggedIn = (currentUser != null);
        String displayName = getUserDisplayName(currentUser);

        String guestsDisplay = (guestsRe != null && !guestsRe.trim().isEmpty())
                ? guestsRe.trim() : "";

        String minPriceFormat = hotelService.formatPrice(minPrice);
        String maxPriceFormat = (maxPrice >= 20000000)
                ? "20.000.000" : hotelService.formatPrice(maxPrice);
        String priceRange = (maxPrice >= 20000000) ? "+" : "";

        String resetUrl = buildResetUrl(
                request.getContextPath(),
                result.getCityInfo().getCityId(),
                result.getCityInfo().getCityName(),
                checkin,
                checkout,
                guestsDisplay
        );

        String pageTitle = buildPageTitle(result.getCityInfo().getCityName());

        request.setAttribute("isLoggedIn", isLoggedIn);
        request.setAttribute("displayName", displayName);
        request.setAttribute("hotels", result.getHotels());
        request.setAttribute("searchLocation", result.getCityInfo().getCityName());
        request.setAttribute("searchCheckin", checkin != null ? checkin.trim() : "");
        request.setAttribute("searchCheckout", checkout != null ? checkout.trim() : "");
        request.setAttribute("searchGuests", guestsDisplay);
        request.setAttribute("cityId", result.getCityInfo().getCityId());
        request.setAttribute("selectedStars", selectedStars);
        request.setAttribute("selectedAmenities", selectedAmenities);
        request.setAttribute("AMENITIES", AMENITIES);
        request.setAttribute("minPrice", minPrice);
        request.setAttribute("maxPrice", maxPrice);
        request.setAttribute("minPriceFormat", minPriceFormat);
        request.setAttribute("maxPriceFormat", maxPriceFormat);
        request.setAttribute("priceRange", priceRange);
        request.setAttribute("resetUrl", resetUrl);
        request.setAttribute("pageTitle", pageTitle);

        request.getRequestDispatcher("/WEB-INF/view/search.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private Integer parseInt(String value, Integer defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private String getUserDisplayName(User user) {
        if (user == null) {
            return "";
        }
        return (user.getFullName() != null && !user.getFullName().trim().isEmpty())
                ? user.getFullName() : user.getUsername();
    }

    private String buildResetUrl(String contextPath, Integer cityId, String cityName,
                                 String checkin, String checkout, String guests) {
        try {
            StringBuilder url = new StringBuilder(contextPath + "/search?");
            if (cityId != null) {
                url.append("cityId=").append(cityId).append("&");
            }
            url.append("location=").append(URLEncoder.encode(cityName, "UTF-8"));
            url.append("&checkin=").append(URLEncoder.encode(checkin != null ? checkin : "", "UTF-8"));
            url.append("&checkout=").append(URLEncoder.encode(checkout != null ? checkout : "", "UTF-8"));
            url.append("&guests=").append(URLEncoder.encode(guests, "UTF-8"));
            return url.toString();
        } catch (Exception e) {
            return contextPath + "/search";
        }
    }

    private String buildPageTitle(String cityName) {
        return (cityName != null && !cityName.isEmpty())
                ? cityName + " - Tìm kiếm khách sạn • Group15"
                : "Tìm kiếm khách sạn • Group15";
    }
}