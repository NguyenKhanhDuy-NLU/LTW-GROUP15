package vn.edu.nlu.fit.demo1.controller.admin;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.demo1.dao.AdminReviewDAO;
import vn.edu.nlu.fit.demo1.model.Review;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AdminReviewController", urlPatterns = {
        "/admin/reviews",
        "/admin/reviews/view",
        "/admin/reviews/delete",
        "/admin/reviews/update"
})
public class AdminReviewController extends HttpServlet {

    private AdminReviewDAO adminReviewDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        this.adminReviewDAO = new AdminReviewDAO();
        this.gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        switch (path) {
            case "/admin/reviews":
                showReviewList(request, response);
                break;
            case "/admin/reviews/view":
                viewReviewDetail(request, response);
                break;
            case "/admin/reviews/delete":
                deleteReview(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String path = request.getServletPath();

        if ("/admin/reviews/update".equals(path)) {
            updateReview(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showReviewList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int page = 1;
        int pageSize = 15;

        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        Integer hotelId = null;
        String hotelIdParam = request.getParameter("hotelId");
        if (hotelIdParam != null && !hotelIdParam.isEmpty()) {
            try {
                hotelId = Integer.parseInt(hotelIdParam);
            } catch (NumberFormatException e) {
                hotelId = null;
            }
        }

        List<Review> reviews = adminReviewDAO.getAllReviews(page, pageSize, hotelId);
        int totalReviews = adminReviewDAO.countReviews(hotelId);
        int totalPages = (int) Math.ceil((double) totalReviews / pageSize);

        request.setAttribute("reviews", reviews);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalReviews", totalReviews);
        request.setAttribute("filterHotelId", hotelId);

        request.getRequestDispatcher("/WEB-INF/views/admin/review-list.jsp").forward(request, response);
    }

    private void viewReviewDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/admin/reviews");
            return;
        }

        try {
            int reviewId = Integer.parseInt(idParam);
            Review review = adminReviewDAO.getReviewById(reviewId);

            if (review == null) {
                response.sendRedirect(request.getContextPath() + "/admin/reviews");
                return;
            }

            request.setAttribute("review", review);
            request.getRequestDispatcher("/WEB-INF/views/admin/review-detail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/reviews");
        }
    }

    private void updateReview(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        PrintWriter out = response.getWriter();
        Map<String, Object> result = new HashMap<>();

        try {
            String idParam = request.getParameter("id");
            String ratingParam = request.getParameter("rating");
            String comment = request.getParameter("comment");

            if (idParam == null || ratingParam == null) {
                result.put("success", false);
                result.put("message", "Thiếu thông tin");
                out.print(gson.toJson(result));
                return;
            }

            int reviewId = Integer.parseInt(idParam);
            int rating = Integer.parseInt(ratingParam);

            boolean success = adminReviewDAO.updateReview(reviewId, rating, comment);

            result.put("success", success);
            result.put("message", success ? "Cập nhật review thành công" : "Cập nhật review thất bại");

        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "Lỗi: " + e.getMessage());
        }

        out.print(gson.toJson(result));
        out.flush();
    }

    private void deleteReview(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/admin/reviews");
            return;
        }

        try {
            int reviewId = Integer.parseInt(idParam);
            boolean success = adminReviewDAO.deleteReview(reviewId);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/admin/reviews?success=delete");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/reviews?error=delete");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/reviews");
        }
    }
}