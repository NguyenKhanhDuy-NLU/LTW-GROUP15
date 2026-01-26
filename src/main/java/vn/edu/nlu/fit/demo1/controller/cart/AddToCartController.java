package vn.edu.nlu.fit.demo1.controller.cart;

import vn.edu.nlu.fit.demo1.cart.Cart;
import vn.edu.nlu.fit.demo1.model.Room;
import vn.edu.nlu.fit.demo1.service.RoomService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AddToCartController", urlPatterns = "/cart/add")
public class AddToCartController extends HttpServlet {
    private RoomService roomService = new RoomService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            int quantity = 1;
            if(req.getParameter("quantity") != null) {
                quantity = Integer.parseInt(req.getParameter("quantity"));
            }

            Room room = roomService.getRoomDetail(id);
            if (room != null) {
                HttpSession session = req.getSession();
                Cart cart = (Cart) session.getAttribute("cart");
                if (cart == null) {
                    cart = new Cart();
                    session.setAttribute("cart", cart);
                }
                cart.add(room, quantity);
            }
            resp.sendRedirect(req.getContextPath() + "/cart");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }
}
