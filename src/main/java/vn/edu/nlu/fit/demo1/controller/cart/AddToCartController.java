package vn.edu.nlu.fit.demo1.controller.cart;

import vn.edu.nlu.fit.demo1.cart.Cart;
import vn.edu.nlu.fit.demo1.model.*;
import vn.edu.nlu.fit.demo1.service.RoomService;
import vn.edu.nlu.fit.demo1.util.DateUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/cart/add")
public class AddToCartController extends HttpServlet {
    private RoomService roomService = new RoomService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int roomId = Integer.parseInt(req.getParameter("id"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));

            String checkIn = req.getParameter("checkin");
            String checkOut = req.getParameter("checkout");

            int nights = DateUtils.countNights(checkIn, checkOut);

            Room room = roomService.getRoomDetail(roomId);

            if (room != null) {
                HttpSession session = req.getSession();
                Cart cart = (Cart) session.getAttribute("cart");
                if (cart == null) {
                    cart = new Cart();
                }

                cart.add(room, quantity, nights);

                session.setAttribute("checkInDate", checkIn);
                session.setAttribute("checkOutDate", checkOut);
                session.setAttribute("totalNights", nights);
                session.setAttribute("cart", cart);
            }
            resp.sendRedirect(req.getContextPath() + "/cart");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(400, "Lỗi dữ liệu đặt phòng");
        }
    }
}
