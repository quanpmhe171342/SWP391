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

public class ViewUserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Kiểm tra nếu chưa đăng nhập hoặc không phải admin và staff
        if (user == null || (user.getRoleId() != 1 && user.getRoleId() != 2)) {
            response.sendRedirect(request.getContextPath() + "/auth/adminlogin.jsp");
            return;
        }

        int roleID = 3; // Chỉ lấy user có roleID = 3
        
        UserDAO userDAO = new UserDAO();
        List<User> userList = userDAO.getUsersByRole(roleID);

        request.setAttribute("userList", userList);
        request.setAttribute("selectedRole", roleID);

        request.getRequestDispatcher("/auth/viewuser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hiện tại không xử lý POST
    }

    @Override
    public String getServletInfo() {
        return "Servlet hiển thị danh sách người dùng có roleID = 3";
    }
}