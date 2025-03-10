/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package UserController;

import DAO.UserDAO;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hieum
 */
public class ViewUserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Kiểm tra nếu chưa đăng nhập hoặc không phải admin
        if (user == null || user.getRoleId() != 1) {
            response.sendRedirect(request.getContextPath() + "/auth/adminlogin.jsp");
            return;
        }

        String roleParam = request.getParameter("role");
        int roleID = 0; // Mặc định lấy tất cả

        if (roleParam != null) {
            try {
                roleID = Integer.parseInt(roleParam);
            } catch (NumberFormatException e) {
                roleID = 0;
            }
        }

        // FIX: Chỉ chấp nhận roleID là 2 hoặc 3, nếu không thì lấy tất cả
        if (roleID != 2 && roleID != 3) {
            roleID = 0;
        }

        UserDAO userDAO = new UserDAO();
        List<User> userList = userDAO.getUsersByRole(roleID);

        request.setAttribute("userList", userList);
        request.setAttribute("selectedRole", roleID);

        request.getRequestDispatcher("/auth/viewuser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
