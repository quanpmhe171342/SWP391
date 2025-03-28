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
public class ResetPasswordController extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/auth/resetpassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = request.getParameter("token");
        String newPassword = request.getParameter("newPassword");

        // Lấy user theo token
        User user = userDAO.getUserByToken(token);

        if (user != null) {
            boolean updateSuccess = userDAO.updatePassword(user.getUsername(), newPassword);

            if (updateSuccess) {
                // Xóa token sau khi đặt lại mật khẩu thành công
                userDAO.clearToken(user.getUsername());
                request.setAttribute("successMessage", "Mật khẩu đã đổi thành công!");
            } else {
                request.setAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật mật khẩu. Vui lòng thử lại.");
            }
        } else {
            request.setAttribute("errorMessage", "Link không hợp lệ hoặc đã hết hạn.");
        }

        // Quay lại trang resetpassword.jsp mà không chuyển hướng
        request.getRequestDispatcher("/auth/resetpassword.jsp").forward(request, response);
    }
}
