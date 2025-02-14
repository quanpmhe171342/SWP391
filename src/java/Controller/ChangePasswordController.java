/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.UserDAO;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author hieum
 */


public class ChangePasswordController extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO(); // Initialize the DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("../changepassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy thông tin từ form
        String username = (String) request.getSession().getAttribute("username"); // Lấy username từ session
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String repeatNewPassword = request.getParameter("repeatNewPassword");

        // Kiểm tra mật khẩu mới và mật khẩu nhập lại có khớp không
        if (!newPassword.equals(repeatNewPassword)) {
            request.setAttribute("error", "Mật khẩu nhập lại không khớp!");
            request.getRequestDispatcher("changepassword.jsp").forward(request, response);
            return;
        }

        // Gọi phương thức đổi mật khẩu từ UserDAO
        boolean isSuccess = userDAO.changePassword(username, oldPassword, newPassword);

        // Xử lý kết quả
        if (isSuccess) {
            request.setAttribute("success", "Đổi mật khẩu thành công!");
        } else {
            request.setAttribute("error", "Đổi mật khẩu thất bại. Vui lòng kiểm tra lại thông tin.");
        }
        request.getRequestDispatcher("changepassword.jsp").forward(request, response);
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
