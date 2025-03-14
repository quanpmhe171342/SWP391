package UserController;

import DAO.UserDAO;
import Model.User;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ViewStaffController extends HttpServlet {

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

        UserDAO userDAO = new UserDAO();
        List<User> staffList = userDAO.getUsersByRole(2); // Chỉ lấy nhân viên (roleID = 2)

        request.setAttribute("userList", staffList);
        request.getRequestDispatcher("/auth/viewstaff.jsp").forward(request, response);
    }
}
