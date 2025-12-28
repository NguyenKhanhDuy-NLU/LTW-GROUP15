package vn.edu.nlu.fit.demo1.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.nlu.fit.demo1.dao.ReviewDAO.RatingStats;
import vn.edu.nlu.fit.demo1.model.Hotel;
import vn.edu.nlu.fit.demo1.model.Review;
import vn.edu.nlu.fit.demo1.model.User;
import vn.edu.nlu.fit.demo1.service.HotelService;
import vn.edu.nlu.fit.demo1.service.ReviewService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ReviewController", urlPatterns = {"/reviews"})
public class ReviewController extends HttpServlet {

    private final ReviewService reviewService = new ReviewService();
    private final HotelService hotelService = new HotelService();

    private static final int PAGE_SIZE = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String hotelIdStr = request.getParameter("hotelId");
        String pageStr = request.getParameter("page");

        if (hotelIdStr == null || hotelIdStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        try {
            int hotelId = Integer.parseInt(hotelIdStr);
            int currentPage = (pageStr != null) ? Integer.parseInt(pageStr) : 1;

            Hotel hotel = hotelService.getHotelById(hotelId);

            if (hotel == null) {
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }
            List<Review> reviews = reviewService.getHotelReviews(hotelId, currentPage, PAGE_SIZE);

            RatingStats stats = reviewService.getHotelRatingStats(hotelId);

            int totalReviews = reviewService.countHotelReviews(hotelId);
            int totalPages = reviewService.calculateTotalPages(totalReviews, PAGE_SIZE);

            HttpSession session = request.getSession();
            User currentUser = (User) session.getAttribute("user");
            boolean isLoggedIn = (currentUser != null);

            String displayName = getUserDisplayName(currentUser);

            boolean canReview = false;
            if (currentUser != null) {
                canReview = reviewService.canUserReviewHotel(currentUser.getId(), hotelId);
            }

            request.setAttribute("hotel", hotel);
            request.setAttribute("reviews", reviews);
            request.setAttribute("stats", stats);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalReviews", totalReviews);
            request.setAttribute("isLoggedIn", isLoggedIn);
            request.setAttribute("displayName", displayName);
            request.setAttribute("canReview", canReview);

            request.getRequestDispatcher("/WEB-INF/view/reviews.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request, response);
        }
    }

    private String getUserDisplayName(User user) {
        if (user == null) return "";
        return (user.getFullName() != null && !user.getFullName().isEmpty())
                ? user.getFullName() : user.getUsername();
    }
}