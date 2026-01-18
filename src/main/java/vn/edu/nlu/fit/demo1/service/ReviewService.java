package vn.edu.nlu.fit.demo1.service;

import vn.edu.nlu.fit.demo1.dao.ReviewDAO;
import vn.edu.nlu.fit.demo1.dao.ReviewDAO.RatingStats;
import vn.edu.nlu.fit.demo1.model.Review;

import java.sql.SQLException;
import java.util.List;

public class ReviewService {

    private final ReviewDAO reviewDAO = new ReviewDAO();

    public List<Review> getHotelReviews(int hotelId, Integer page, Integer pageSize)
            throws SQLException {

        if (page != null && pageSize != null) {
            return reviewDAO.getReviewsByHotelIdPaginated(hotelId, page, pageSize);
        }

        return reviewDAO.getReviewsByHotelId(hotelId);
    }

    public RatingStats getHotelRatingStats(int hotelId) throws SQLException {
        return reviewDAO.getRatingStats(hotelId);
    }
    public int countHotelReviews(int hotelId) throws SQLException {
        return reviewDAO.countReviewsByHotelId(hotelId);
    }

    public boolean createReview(int userId, int hotelId, int bookingId,
                                double rating, String comment) throws SQLException {

        validateReview(rating, comment);

        if (reviewDAO.hasUserReviewedHotel(userId, hotelId)) {
            throw new IllegalStateException("Bạn đã đánh giá khách sạn này rồi");
        }

        Review review = new Review(userId, hotelId, bookingId, rating, comment);
        return reviewDAO.createReview(review);
    }

    public boolean updateReview(int reviewId, int userId, double rating, String comment)
            throws SQLException {

        validateReview(rating, comment);

        Review review = reviewDAO.getReviewById(reviewId);

        if (review == null) {
            throw new IllegalArgumentException("Không tìm thấy đánh giá");
        }

        if (review.getUserId() != userId) {
            throw new IllegalStateException("Bạn không có quyền chỉnh sửa đánh giá này");
        }

        review.setRating(rating);
        review.setComment(comment);

        return reviewDAO.updateReview(review);
    }
    public boolean deleteReview(int reviewId, int userId) throws SQLException {
        Review review = reviewDAO.getReviewById(reviewId);

        if (review == null) {
            throw new IllegalArgumentException("Không tìm thấy đánh giá");
        }

        if (review.getUserId() != userId) {
            throw new IllegalStateException("Bạn không có quyền xóa đánh giá này");
        }

        return reviewDAO.deleteReview(reviewId);
    }

    public boolean canUserReviewHotel(int userId, int hotelId) throws SQLException {
        return !reviewDAO.hasUserReviewedHotel(userId, hotelId);
    }

    private void validateReview(double rating, String comment) {
        if (rating < 1.0 || rating > 5.0) {
            throw new IllegalArgumentException("Đánh giá không hợp lệ");
        }

        if (comment == null || comment.trim().isEmpty()) {
            throw new IllegalArgumentException("Nội dung không được để trống");
        }

        if (comment.length() < 10) {
            throw new IllegalArgumentException("Nội dung phải có ít nhất 10 ký tự");
        }

        if (comment.length() > 1000) {
            throw new IllegalArgumentException("Nội dung không được quá 1000 ký tự");
        }
    }


    public int calculateTotalPages(int totalReviews, int pageSize) {
        return (int) Math.ceil((double) totalReviews / pageSize);
    }
}