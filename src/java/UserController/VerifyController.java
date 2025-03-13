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

/**
 *
 * @author hieum
 */
public class VerifyController extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = request.getParameter("token"); // Lấy token từ URL

        if (token == null || token.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/auth/verifyfail.jsp?error=missing_token");
            return;
        }

        // Kiểm tra token có tồn tại trong database không
        User user = userDAO.getUserByToken(token);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/auth/verifyfail.jsp?error=invalid_token");
            return;
        }

        // Kích hoạt tài khoản
        boolean activated = userDAO.activateUser(token);
        if (activated) {
            response.sendRedirect(request.getContextPath() + "/auth/verifysuccess.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/auth/verifyfail.jsp?error=activation_failed");
        }
    }
}

