package vn.edu.nlu.fit.demo1.controller;


import vn.edu.nlu.fit.demo1.model.Room;
import vn.edu.nlu.fit.demo1.service.RoomService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "RoomDetailController", urlPatterns = {"/room-detail"}
)
public class RoomDetailController extends HttpServlet {

    private RoomService roomService = new RoomService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu room id");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "room id không hợp lệ");
            return;
        }

        Room room = roomService.getRoomById(id);

        if (room == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy Room");
            return;
        }

        req.setAttribute("room", room);
        req.getRequestDispatcher("/WEB-INF/views/room-detail.jsp")
                .forward(req, resp);
    }
}

