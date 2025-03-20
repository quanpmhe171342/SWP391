/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package UserController;

import DAO.UserDAO;
import Model.User;
import static Utils.Email.sendEmail;
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
        
        String resetLink = request.getRequestURL().toString().replace("sendresetlink", "resetpassword") + "?token=" + token;
        
        String subject = "Yêu cầu đổi mật khẩu!";
            String content = "Chào bạn" +",<br><br>"
                    + "Chúng tôi nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn.<br>"
                    + "Hãy bấm vào liên kết sau để tạo mật khẩu mới:<br>"
                    + "<a href='" + resetLink + "'>" + resetLink + "</a><br><br>"
                    + "Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua email này.<br><br>"
                    + "Trân trọng!";

            sendEmail(user.getEmail(), subject, content);
        
        
        request.setAttribute("message", "Kiểm tra email để nhận link để đặt lại mật khẩu.");
//        request.setAttribute("resetLink", resetLink);
//        request.setAttribute("message", "Sao chép link để đặt lại mật khẩu.");
        request.getRequestDispatcher("/auth/forgotpassword.jsp").forward(request, response);
    }
}
