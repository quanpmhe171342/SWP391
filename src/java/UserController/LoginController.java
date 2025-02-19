package UserController;

import DAO.UserDAO;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginController extends HttpServlet {

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
        request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Kiểm tra xem username và password có hợp lệ không
        User user = userDAO.getUserByUsername(username);
        
        if (user != null) {
            // So sánh mật khẩu với dữ liệu trong cơ sở dữ liệu (nên mã hóa mật khẩu thực tế trong cơ sở dữ liệu)
            if (user.getPassword().equals(password)) {
                request.getSession().setAttribute("user", user);  // Lưu thông tin người dùng vào session
                
                // Chuyển hướng đến trang chính sau khi đăng nhập thành công
                response.sendRedirect(request.getContextPath() + "/auth/hometest.jsp");
            } else {
                // Nếu mật khẩu sai, hiển thị thông báo lỗi
                request.setAttribute("errorMessage", "Sai mật khẩu. Vui lòng thử lại.");
                request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
            }
        } else {
            // Nếu username không tồn tại trong cơ sở dữ liệu, hiển thị thông báo lỗi
            request.setAttribute("errorMessage", "Tài khoản không tồn tại. Vui lòng thử lại.");
            request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet that handles user login.";
    }

}
