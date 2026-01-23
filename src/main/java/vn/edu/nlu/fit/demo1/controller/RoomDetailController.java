package vn.edu.nlu.fit.demo1.controller;

import vn.edu.nlu.fit.demo1.model.Room;
import vn.edu.nlu.fit.demo1.service.RoomService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RoomDetailController", urlPatterns = {"/room-detail"})
public class RoomDetailController extends HttpServlet {
    private RoomService roomService = new RoomService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.sendError(400, "Missing Room ID");
            return;
        }

        try {
            int roomId = Integer.parseInt(idParam);
            Room room = roomService.getRoomDetail(roomId);

            if (room != null) {
                req.setAttribute("room", room);
                req.getRequestDispatcher("/WEB-INF/views/room-detail.jsp").forward(req, resp);
            } else {
                resp.sendError(404, "Room not found");
            }
        } catch (NumberFormatException e) {
            resp.sendError(400, "Invalid Room ID");
        }
    }
}
