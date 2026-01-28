package vn.edu.nlu.fit.demo1.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.demo1.dao.AdminHotelDAO;

import java.io.IOException;

@WebServlet(name = "HotelStatusController", urlPatterns = {"/admin/hotels/toggle-status"})
public class HotelStatusController extends HttpServlet {

    private AdminHotelDAO adminHotelDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.adminHotelDAO = new AdminHotelDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/hotels?error=missing");
            return;
        }

        try {
            int hotelId = Integer.parseInt(idParam);
            boolean success = adminHotelDAO.toggleHotelStatus(hotelId);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/admin/hotels?success=toggle");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/hotels?error=toggle");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/hotels?error=invalid");
        }
    }
}