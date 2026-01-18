package vn.edu.nlu.fit.demo1.controller.admin;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.edu.nlu.fit.demo1.dao.AdminHotelDAO;
import vn.edu.nlu.fit.demo1.dao.HotelDAO;
import vn.edu.nlu.fit.demo1.dao.HotelImageDAO;
import vn.edu.nlu.fit.demo1.model.Hotel;
import vn.edu.nlu.fit.demo1.model.HotelImage;
import vn.edu.nlu.fit.demo1.util.FileUploadUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AdminHotelController", urlPatterns = {
        "/admin/hotels",
        "/admin/hotels/add",
        "/admin/hotels/edit",
        "/admin/hotels/delete",
        "/admin/hotels/upload-images",
        "/admin/hotels/set-thumbnail",
        "/admin/hotels/delete-image"
})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 50
)
public class AdminHotelController extends HttpServlet {

    private AdminHotelDAO adminHotelDAO;
    private HotelDAO hotelDAO;
    private HotelImageDAO hotelImageDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        this.adminHotelDAO = new AdminHotelDAO();
        this.hotelDAO = new HotelDAO();
        this.hotelImageDAO = new HotelImageDAO();
        this.gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        switch (path) {
            case "/admin/hotels":
                showHotelList(request, response);
                break;
            case "/admin/hotels/add":
                showAddForm(request, response);
                break;
            case "/admin/hotels/edit":
                showEditForm(request, response);
                break;
            case "/admin/hotels/delete":
                deleteHotel(request, response);
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

        switch (path) {
            case "/admin/hotels/add":
                addHotel(request, response);
                break;
            case "/admin/hotels/edit":
                updateHotel(request, response);
                break;
            case "/admin/hotels/upload-images":
                uploadImages(request, response);
                break;
            case "/admin/hotels/set-thumbnail":
                setThumbnail(request, response);
                break;
            case "/admin/hotels/delete-image":
                deleteImage(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showHotelList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int page = 1;
        int pageSize = 10;

        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        List<Hotel> hotels = adminHotelDAO.getAllHotels(page, pageSize);
        int totalHotels = adminHotelDAO.countHotels();
        int totalPages = (int) Math.ceil((double) totalHotels / pageSize);

        request.setAttribute("hotels", hotels);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalHotels", totalHotels);

        request.getRequestDispatcher("/WEB-INF/views/admin/hotel-list.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/admin/hotel-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/admin/hotels");
            return;
        }

        try {
            int hotelId = Integer.parseInt(idParam);
            Hotel hotel = hotelDAO.getHotelById(hotelId);

            if (hotel == null) {
                response.sendRedirect(request.getContextPath() + "/admin/hotels");
                return;
            }

            List<HotelImage> images = hotelImageDAO.getImagesByHotelId(hotelId);

            request.setAttribute("hotel", hotel);
            request.setAttribute("images", images);
            request.setAttribute("isEdit", true);

            request.getRequestDispatcher("/WEB-INF/views/admin/hotel-form.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/hotels");
        }
    }


    private void addHotel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Hotel hotel = extractHotelFromRequest(request);

            boolean success = adminHotelDAO.addHotel(hotel);

            if (success) {
                uploadImagesForHotel(request, hotel.getId());

                response.sendRedirect(request.getContextPath() + "/admin/hotels?success=add");
            } else {
                request.setAttribute("errorMessage", "Thêm khách sạn thất bại");
                request.getRequestDispatcher("/WEB-INF/views/admin/hotel-form.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/admin/hotel-form.jsp").forward(request, response);
        }
    }

    private void updateHotel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Hotel hotel = extractHotelFromRequest(request);

            String idParam = request.getParameter("id");
            if (idParam != null) {
                hotel.setId(Integer.parseInt(idParam));
            }

            boolean success = adminHotelDAO.updateHotel(hotel);

            if (success) {
                uploadImagesForHotel(request, hotel.getId());

                response.sendRedirect(request.getContextPath() + "/admin/hotels?success=update");
            } else {
                request.setAttribute("errorMessage", "Cập nhật khách sạn thất bại");
                showEditForm(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi: " + e.getMessage());
            showEditForm(request, response);
        }
    }

    private void deleteHotel(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/admin/hotels");
            return;
        }

        try {
            int hotelId = Integer.parseInt(idParam);

            List<HotelImage> images = hotelImageDAO.getImagesByHotelId(hotelId);
            String uploadPath = getServletContext().getRealPath("");

            for (HotelImage image : images) {
                FileUploadUtil.deleteFile(image.getImageUrl(), uploadPath);
            }

            hotelImageDAO.deleteAllImagesByHotelId(hotelId);

            boolean success = adminHotelDAO.deleteHotel(hotelId);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/admin/hotels?success=delete");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/hotels?error=delete");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/hotels");
        }
    }

    private void uploadImages(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        PrintWriter out = response.getWriter();
        Map<String, Object> result = new HashMap<>();

        try {
            String hotelIdParam = request.getParameter("hotelId");
            if (hotelIdParam == null) {
                result.put("success", false);
                result.put("message", "Thiếu hotel ID");
                out.print(gson.toJson(result));
                return;
            }

            int hotelId = Integer.parseInt(hotelIdParam);
            int uploaded = uploadImagesForHotel(request, hotelId);

            result.put("success", true);
            result.put("message", "Upload thành công " + uploaded + " ảnh");
            result.put("count", uploaded);

        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "Lỗi: " + e.getMessage());
        }

        out.print(gson.toJson(result));
        out.flush();
    }

    private void setThumbnail(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        PrintWriter out = response.getWriter();
        Map<String, Object> result = new HashMap<>();

        try {
            String hotelIdParam = request.getParameter("hotelId");
            String imageIdParam = request.getParameter("imageId");

            if (hotelIdParam == null || imageIdParam == null) {
                result.put("success", false);
                result.put("message", "Thiếu thông tin");
                out.print(gson.toJson(result));
                return;
            }

            int hotelId = Integer.parseInt(hotelIdParam);
            int imageId = Integer.parseInt(imageIdParam);

            boolean success = hotelImageDAO.setThumbnail(hotelId, imageId);

            result.put("success", success);
            result.put("message", success ? "Đã set ảnh đại diện" : "Lỗi set ảnh đại diện");

        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "Lỗi: " + e.getMessage());
        }

        out.print(gson.toJson(result));
        out.flush();
    }

    private void deleteImage(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        PrintWriter out = response.getWriter();
        Map<String, Object> result = new HashMap<>();

        try {
            String imageIdParam = request.getParameter("imageId");

            if (imageIdParam == null) {
                result.put("success", false);
                result.put("message", "Thiếu image ID");
                out.print(gson.toJson(result));
                return;
            }

            int imageId = Integer.parseInt(imageIdParam);

            boolean success = hotelImageDAO.deleteImage(imageId);

            result.put("success", success);
            result.put("message", success ? "Đã xóa ảnh" : "Lỗi xóa ảnh");

        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "Lỗi: " + e.getMessage());
        }

        out.print(gson.toJson(result));
        out.flush();
    }


    private Hotel extractHotelFromRequest(HttpServletRequest request) {
        Hotel hotel = new Hotel();

        hotel.setName(request.getParameter("name"));
        hotel.setCityId(Integer.parseInt(request.getParameter("cityId")));
        hotel.setAddress(request.getParameter("address"));
        hotel.setStarRating(Integer.parseInt(request.getParameter("starRating")));
        hotel.setPricePerNight(new BigDecimal(request.getParameter("pricePerNight")));

        String discountPrice = request.getParameter("discountPrice");
        if (discountPrice != null && !discountPrice.trim().isEmpty()) {
            hotel.setDiscountPrice(new BigDecimal(discountPrice));
        }

        hotel.setDescription(request.getParameter("description"));
        hotel.setAmenities(request.getParameter("amenities"));

        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");
        if (latitude != null && !latitude.trim().isEmpty()) {
            hotel.setLatitude(new BigDecimal(latitude));
        }
        if (longitude != null && !longitude.trim().isEmpty()) {
            hotel.setLongitude(new BigDecimal(longitude));
        }

        String isActive = request.getParameter("isActive");
        hotel.setActive(isActive != null && isActive.equals("1"));

        return hotel;
    }

    private int uploadImagesForHotel(HttpServletRequest request, int hotelId) throws IOException, ServletException {
        String uploadPath = getServletContext().getRealPath("");
        List<String> uploadedPaths = new ArrayList<>();
        int uploadCount = 0;

        for (Part part : request.getParts()) {
            if ("hotelImages".equals(part.getName()) && part.getSize() > 0) {
                try {
                    String imagePath = FileUploadUtil.uploadFile(part, uploadPath);
                    uploadedPaths.add(imagePath);
                    uploadCount++;
                } catch (IOException e) {
                    System.err.println("Lỗi upload ảnh: " + e.getMessage());
                }
            }
        }

        for (int i = 0; i < uploadedPaths.size(); i++) {
            HotelImage image = new HotelImage();
            image.setHotelId(hotelId);
            image.setImageUrl(uploadedPaths.get(i));
            image.setThumbnail(false); // Sẽ set thumbnail sau
            image.setDisplayOrder(i);

            hotelImageDAO.addImage(image);
        }

        return uploadCount;
    }
}