package vn.edu.nlu.fit.demo1.controller.admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.demo1.dao.AdminRoomDAO;
import vn.edu.nlu.fit.demo1.dao.AdminHotelDAO;
import vn.edu.nlu.fit.demo1.model.Room;
import vn.edu.nlu.fit.demo1.model.Hotel;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(name = "AdminRoomController", urlPatterns = {"/admin/rooms", "/admin/rooms/add", "/admin/rooms/edit", "/admin/rooms/delete"})
@MultipartConfig(
        maxFileSize = 10485760,
        maxRequestSize = 52428800,
        fileSizeThreshold = 1048576
)
public class AdminRoomController extends HttpServlet {
    private AdminRoomDAO roomDAO;
    private AdminHotelDAO hotelDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        roomDAO = new AdminRoomDAO();
        hotelDAO = new AdminHotelDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        
        try {
            switch (path) {
                case "/admin/rooms":
                    listRooms(request, response);
                    break;
                case "/admin/rooms/add":
                    showAddForm(request, response);
                    break;
                case "/admin/rooms/edit":
                    showEditForm(request, response);
                    break;
                case "/admin/rooms/delete":
                    deleteRoom(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        
        try {
            if (path.equals("/admin/rooms/add")) {
                addRoom(request, response);
            } else if (path.equals("/admin/rooms/edit")) {
                updateRoom(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void listRooms(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String hotelIdParam = request.getParameter("hotelId");
        
        if (hotelIdParam != null && !hotelIdParam.isEmpty()) {
            int hotelId = Integer.parseInt(hotelIdParam);
            List<Room> rooms = roomDAO.getRoomsByHotelId(hotelId);
            Hotel hotel = hotelDAO.getHotelById(hotelId);
            
            request.setAttribute("rooms", rooms);
            request.setAttribute("hotel", hotel);
            request.setAttribute("hotelId", hotelId);
        } else {
            List<Room> rooms = roomDAO.getAllRooms();
            request.setAttribute("rooms", rooms);
        }
        
        request.getRequestDispatcher("/WEB-INF/views/admin/room-list.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String hotelIdParam = request.getParameter("hotelId");
        
        if (hotelIdParam != null && !hotelIdParam.isEmpty()) {
            int hotelId = Integer.parseInt(hotelIdParam);
            Hotel hotel = hotelDAO.getHotelById(hotelId);
            request.setAttribute("hotel", hotel);
        }
        
        List<Hotel> hotels = hotelDAO.getAllHotels(1, 1000);
        request.setAttribute("hotels", hotels);
        request.getRequestDispatcher("/WEB-INF/views/admin/room-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int roomId = Integer.parseInt(request.getParameter("id"));
        Room room = roomDAO.getRoomById(roomId);
        List<Hotel> hotels = hotelDAO.getAllHotels(1, 1000);
        
        request.setAttribute("room", room);
        request.setAttribute("hotels", hotels);
        request.getRequestDispatcher("/WEB-INF/views/admin/room-form.jsp").forward(request, response);
    }

    private void addRoom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        Room room = new Room();
        room.setHotelId(Integer.parseInt(request.getParameter("hotelId")));
        room.setRoomName(request.getParameter("roomName"));
        room.setRoomType(request.getParameter("roomType"));
        room.setMaxPeople(Integer.parseInt(request.getParameter("maxPeople")));
        room.setBasePrice(new BigDecimal(request.getParameter("basePrice")));
        room.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        room.setSize(request.getParameter("size"));
        room.setView(request.getParameter("view"));
        room.setDescription(request.getParameter("description"));
        room.setAvailable(true);
        
        // Upload ảnh
        StringBuilder imagePaths = new StringBuilder();
        String uploadPath = getServletContext().getRealPath("") + "/uploads/rooms/";
        java.io.File uploadDir = new java.io.File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        Part mainImage = request.getPart("mainImage");
        if (mainImage != null && mainImage.getSize() > 0) {
            String fileName = System.currentTimeMillis() + "_" + mainImage.getSubmittedFileName();
            mainImage.write(uploadPath + fileName);
            imagePaths.append("/uploads/rooms/").append(fileName);
        }

        for (Part part : request.getParts()) {
            if ("otherImages".equals(part.getName()) && part.getSize() > 0) {
                String fileName = System.currentTimeMillis() + "_" + part.getSubmittedFileName();
                part.write(uploadPath + fileName);
                if (imagePaths.length() > 0) imagePaths.append(",");
                imagePaths.append("/uploads/rooms/").append(fileName);
            }
        }
        
        room.setImages(imagePaths.toString());
        
        boolean success = roomDAO.addRoom(room);
        
        if (success) {
            response.sendRedirect(request.getContextPath() + "/admin/rooms?hotelId=" + room.getHotelId() + "&success=add");
        } else {
            request.setAttribute("error", "Thêm phòng thất bại");
            showAddForm(request, response);
        }
    }

    private void updateRoom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        Room room = new Room();
        room.setId(Integer.parseInt(request.getParameter("id")));
        room.setHotelId(Integer.parseInt(request.getParameter("hotelId")));
        room.setRoomName(request.getParameter("roomName"));
        room.setRoomType(request.getParameter("roomType"));
        room.setMaxPeople(Integer.parseInt(request.getParameter("maxPeople")));
        room.setBasePrice(new BigDecimal(request.getParameter("basePrice")));
        room.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        room.setSize(request.getParameter("size"));
        room.setView(request.getParameter("view"));
        room.setDescription(request.getParameter("description"));
        
        String availableParam = request.getParameter("isAvailable");
        room.setAvailable(availableParam != null && availableParam.equals("true"));

        StringBuilder imagePaths = new StringBuilder();
        String uploadPath = getServletContext().getRealPath("") + "/uploads/rooms/";
        java.io.File uploadDir = new java.io.File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();
        
        Part mainImage = request.getPart("mainImage");
        if (mainImage != null && mainImage.getSize() > 0) {
            String fileName = System.currentTimeMillis() + "_" + mainImage.getSubmittedFileName();
            mainImage.write(uploadPath + fileName);
            imagePaths.append("/uploads/rooms/").append(fileName);
        } else {
            Room oldRoom = roomDAO.getRoomById(room.getId());
            if (oldRoom != null && oldRoom.getImages() != null) {
                imagePaths.append(oldRoom.getImages().split(",")[0]);
            }
        }
        
        for (Part part : request.getParts()) {
            if ("otherImages".equals(part.getName()) && part.getSize() > 0) {
                String fileName = System.currentTimeMillis() + "_" + part.getSubmittedFileName();
                part.write(uploadPath + fileName);
                if (imagePaths.length() > 0) imagePaths.append(",");
                imagePaths.append("/uploads/rooms/").append(fileName);
            }
        }
        
        room.setImages(imagePaths.toString());
        
        boolean success = roomDAO.updateRoom(room);
        
        if (success) {
            response.sendRedirect(request.getContextPath() + "/admin/rooms?hotelId=" + room.getHotelId() + "&success=update");
        } else {
            request.setAttribute("error", "Cập nhật phòng thất bại");
            showEditForm(request, response);
        }
    }

    private void deleteRoom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int roomId = Integer.parseInt(request.getParameter("id"));
        String hotelIdParam = request.getParameter("hotelId");
        
        boolean success = roomDAO.deleteRoom(roomId);
        
        String redirectUrl = request.getContextPath() + "/admin/rooms";
        if (hotelIdParam != null) {
            redirectUrl += "?hotelId=" + hotelIdParam;
        }
        redirectUrl += "&success=" + (success ? "delete" : "error");
        
        response.sendRedirect(redirectUrl);
    }
}
