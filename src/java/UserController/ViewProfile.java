package UserController;

import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class ViewProfile extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Lấy session hiện tại
        HttpSession session = request.getSession();

        // Lấy user từ session
        User user = (User) session.getAttribute("user");

        // Nếu user chưa đăng nhập, chuyển hướng về login.jsp
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        // Chuyển tiếp đến viewprofile.jsp
        request.getRequestDispatcher("/auth/viewprofile.jsp").forward(request, response);
    }
}
