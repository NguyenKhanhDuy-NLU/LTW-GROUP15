package vn.edu.nlu.fit.demo1.controller;

import vn.edu.nlu.fit.demo1.model.*;
import vn.edu.nlu.fit.demo1.service.HotelDetailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HotelDetailController", urlPatterns = {"/hotel-detail"})
public class HotelDetailController extends HttpServlet {
    private HotelDetailService hotelDetailService = new HotelDetailService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam == null) idParam = "1";

        try {
            int hotelId = Integer.parseInt(idParam);
            HotelDetail hotel = hotelDetailService.getHotelFullDetail(hotelId);

            if (hotel == null) {
                resp.sendError(404, "Hotel not found");
                return;
            }

            List<Room> rooms = hotelDetailService.getRoomsByHotel(hotelId);

            req.setAttribute("hotel", hotel);
            req.setAttribute("rooms", rooms);
            req.getRequestDispatcher("/WEB-INF/views/hotel-detail.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(400, "Invalid Hotel ID");
        }
    }
}
