package vn.edu.nlu.fit.demo1.controller.admin;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.demo1.dao.AdminUserDAO;
import vn.edu.nlu.fit.demo1.model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AdminUserController", urlPatterns = {
        "/admin/users",
        "/admin/users/view",
        "/admin/users/update-status",
        "/admin/users/update-role",
        "/admin/users/verify",
        "/admin/users/delete"
})
public class AdminUserController extends HttpServlet {

    private AdminUserDAO adminUserDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        this.adminUserDAO = new AdminUserDAO();
        this.gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        switch (path) {
            case "/admin/users":
                showUserList(request, response);
                break;
            case "/admin/users/view":
                viewUserDetail(request, response);
                break;
            case "/admin/users/delete":
                deleteUser(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String path = request.getServletPath();

        switch (path) {
            case "/admin/users/update-status":
                updateUserStatus(request, response);
                break;
            case "/admin/users/update-role":
                updateUserRole(request, response);
                break;
            case "/admin/users/verify":
                verifyUser(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showUserList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int page = 1;
        int pageSize = 15;

        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        String search = request.getParameter("search");

        List<User> users = adminUserDAO.getAllUsers(page, pageSize, search);
        int totalUsers = adminUserDAO.countUsers(search);
        int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

        request.setAttribute("users", users);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalUsers", totalUsers);
        request.setAttribute("searchQuery", search);

        request.getRequestDispatcher("/WEB-INF/views/admin/user-list.jsp").forward(request, response);
    }

    private void viewUserDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/admin/users");
            return;
        }

        try {
            int userId = Integer.parseInt(idParam);
            User user = adminUserDAO.getUserById(userId);

            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/admin/users");
                return;
            }

            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/views/admin/user-detail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/users");
        }
    }

    private void updateUserStatus(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        PrintWriter out = response.getWriter();
        Map<String, Object> result = new HashMap<>();

        try {
            String idParam = request.getParameter("id");
            String isActiveParam = request.getParameter("isActive");

            if (idParam == null || isActiveParam == null) {
                result.put("success", false);
                result.put("message", "Thiếu thông tin");
                out.print(gson.toJson(result));
                return;
            }

            int userId = Integer.parseInt(idParam);
            boolean isActive = Boolean.parseBoolean(isActiveParam);

            boolean success = adminUserDAO.updateUserStatus(userId, isActive);

            result.put("success", success);
            result.put("message", success ? "Cập nhật thành công" : "Cập nhật thất bại");

        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "Lỗi: " + e.getMessage());
        }

        out.print(gson.toJson(result));
        out.flush();
    }

    private void updateUserRole(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        PrintWriter out = response.getWriter();
        Map<String, Object> result = new HashMap<>();

        try {
            String idParam = request.getParameter("id");
            String roleIdParam = request.getParameter("roleId");

            if (idParam == null || roleIdParam == null) {
                result.put("success", false);
                result.put("message", "Thiếu thông tin");
                out.print(gson.toJson(result));
                return;
            }

            int userId = Integer.parseInt(idParam);
            int roleId = Integer.parseInt(roleIdParam);

            boolean success = adminUserDAO.updateUserRole(userId, roleId);

            result.put("success", success);
            result.put("message", success ? "Cập nhật role thành công" : "Cập nhật role thất bại");

        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "Lỗi: " + e.getMessage());
        }

        out.print(gson.toJson(result));
        out.flush();
    }

    private void verifyUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        PrintWriter out = response.getWriter();
        Map<String, Object> result = new HashMap<>();

        try {
            String idParam = request.getParameter("id");

            if (idParam == null) {
                result.put("success", false);
                result.put("message", "Thiếu user ID");
                out.print(gson.toJson(result));
                return;
            }

            int userId = Integer.parseInt(idParam);
            boolean success = adminUserDAO.verifyUser(userId);

            result.put("success", success);
            result.put("message", success ? "Xác thực user thành công" : "Xác thực user thất bại");

        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "Lỗi: " + e.getMessage());
        }

        out.print(gson.toJson(result));
        out.flush();
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/admin/users");
            return;
        }

        try {
            int userId = Integer.parseInt(idParam);
            boolean success = adminUserDAO.deleteUser(userId);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/admin/users?success=delete");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/users?error=delete");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/users");
        }
    }
}