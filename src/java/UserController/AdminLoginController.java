package UserController;

import DAO.UserDAO;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminLoginController extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO(); // Initialize the DAO
    }

    // Handles the HTTP GET method to show the login form
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to the login JSP page
        request.getRequestDispatcher("/auth/adminlogin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Kiểm tra xem username có tồn tại không
        User user = userDAO.getUserByUsername(username);

        if (user != null) {
            // Kiểm tra nếu tài khoản chưa kích hoạt
            if (!user.isIsActive()) {
                request.setAttribute("errorMessage", "Tài khoản chưa được kích hoạt. Vui lòng liên hệ quản trị viên.");
                request.getRequestDispatcher("/auth/adminlogin.jsp").forward(request, response);
                return;
            }

            // Kiểm tra mật khẩu
            if (user.getPassword().equals(password)) {
                if (user.getRoleId() == 1) {
                    request.getSession().setAttribute("user", user);
                    response.sendRedirect(request.getContextPath() + "/auth/hometest.jsp");
                } else if (user.getRoleId() == 2) {
                    request.getSession().setAttribute("user", user);
                    response.sendRedirect(request.getContextPath() + "/Views/staffDashboard.jsp");
                } else {
                    request.setAttribute("errorMessage", "Vui lòng sử dụng tài khoản Admin hoặc Staff.");
                    request.getRequestDispatcher("/auth/adminlogin.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("errorMessage", "Sai mật khẩu. Vui lòng thử lại.");
                request.getRequestDispatcher("/auth/adminlogin.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Tài khoản không tồn tại. Vui lòng thử lại.");
            request.getRequestDispatcher("/auth/adminlogin.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet that handles user login.";
    }

}
