package vn.edu.nlu.fit.demo1.controller.cart;

import vn.edu.nlu.fit.demo1.cart.Cart;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "UpdateCartController", urlPatterns = "/cart/update")
public class UpdateCartController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));

            HttpSession session = req.getSession();
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart != null) {
                cart.update(id, quantity);
            }
            resp.sendRedirect(req.getContextPath() + "/cart");
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }
}
