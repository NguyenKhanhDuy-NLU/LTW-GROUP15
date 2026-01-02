package vn.edu.nlu.fit.demo1.controller;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.demo1.model.Hotel;
import vn.edu.nlu.fit.demo1.service.HotelService;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeController", urlPatterns = {"", "/", "/index"})
public class HomeController extends HttpServlet {
    private HotelService hotelService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.hotelService = new HotelService();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Hotel> featuredHotels = hotelService.getAllHotels();
        request.setAttribute("featuredHotels", featuredHotels);

        request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}