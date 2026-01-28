package vn.edu.nlu.fit.demo1.controller.cart;

import vn.edu.nlu.fit.demo1.cart.Cart;
import vn.edu.nlu.fit.demo1.cart.CartItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "RemoveAllCartController", urlPatterns = "/cart/remove-all")
public class RemoveAllCartController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart != null) {
            List<CartItem> deletedList = cart.removeAll();
        }
        resp.sendRedirect(req.getContextPath() + "/cart");
    }
}
