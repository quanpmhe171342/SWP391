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
public class SendResetLinkController extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/auth/forgotpassword.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");

        // Kiểm tra username có tồn tại không
        User user = userDAO.getUserByUsername(username);
        if (user == null) {
            request.setAttribute("message", "Tài khoản không tồn tại.");
            request.getRequestDispatcher("/auth/forgotpassword.jsp").forward(request, response);
            return;
        }

        // Sinh token mới
        String token = userDAO.generateToken();
        userDAO.saveVerificationToken(username, token);

        // Hiển thị link reset (thay vì gửi email)
        String resetLink = request.getRequestURL().toString().replace("sendresetlink", "resetpassword") + "?token=" + token;
        request.setAttribute("resetLink", resetLink);
        request.setAttribute("message", "Sao chép link để đặt lại mật khẩu.");
        request.getRequestDispatcher("/auth/forgotpassword.jsp").forward(request, response);
    }
}
