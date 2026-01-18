package vn.edu.nlu.fit.demo1.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.demo1.dao.AdminHotelDAO;
import vn.edu.nlu.fit.demo1.model.DashboardStats;

import java.io.IOException;

@WebServlet(name = "AdminDashboardController", urlPatterns = {"/admin/dashboard", "/admin"})
public class AdminDashboardController extends HttpServlet {

    private AdminHotelDAO adminHotelDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.adminHotelDAO = new AdminHotelDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DashboardStats stats = adminHotelDAO.getDashboardStats();

        request.setAttribute("stats", stats);

        request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);
    }
}