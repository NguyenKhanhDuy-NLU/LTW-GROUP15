package vn.edu.nlu.fit.demo1.controller;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.nlu.fit.demo1.model.User;
import vn.edu.nlu.fit.demo1.service.ReviewService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "CreateReviewController", urlPatterns = {"/create-review"})
public class CreateReviewController extends HttpServlet {

    private final ReviewService reviewService = new ReviewService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> result = new HashMap<>();
        PrintWriter out = response.getWriter();

        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                result.put("success", false);
                result.put("message", "Vui lòng đăng nhập để đánh giá");
                out.print(gson.toJson(result));
                return;
            }

            User currentUser = (User) session.getAttribute("user");
            String hotelIdStr = request.getParameter("hotelId");
            String ratingStr = request.getParameter("rating");
            String comment = request.getParameter("comment");
            if (hotelIdStr == null || ratingStr == null || comment == null) {
                result.put("success", false);
                result.put("message", "Thiếu thông tin đánh giá");
                out.print(gson.toJson(result));
                return;
            }

            int hotelId = Integer.parseInt(hotelIdStr);
            double rating = Double.parseDouble(ratingStr);

            boolean created = reviewService.createReview(
                    currentUser.getId(),
                    hotelId,
                    0,
                    rating,
                    comment.trim()
            );

            if (created) {
                result.put("success", true);
                result.put("message", "Đánh giá của bạn đã được gửi thành công!");
            } else {
                result.put("success", false);
                result.put("message", "Không thể tạo đánh giá. Vui lòng thử lại!");
            }

        } catch (NumberFormatException e) {
            result.put("success", false);
            result.put("message", "Dữ liệu không hợp lệ");
        } catch (IllegalStateException e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        } catch (IllegalArgumentException e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "Có lỗi xảy ra: " + e.getMessage());
        }

        out.print(gson.toJson(result));
        out.flush();
    }
}