package UserController;

import DAO.UserDAO;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
<<<<<<< HEAD
        // Kiểm tra xem username và password có hợp lệ không
        User user = userDAO.getUserByUsername(username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                if (user.getRoleId() == 1) {
                    HttpSession session = request.getSession(false);
                    session.setAttribute("user", user);
                    response.sendRedirect(request.getContextPath() + "/ProductList");
                } else {
                    request.setAttribute("errorMessage", "Tài khoản không thể truy cập.");
                    request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("errorMessage", "Sai mật khẩu. Vui lòng thử lại.");
                request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
            }
        } else {
=======

        // Kiểm tra username có tồn tại không
        User user = userDAO.getUserByUsername(username);

        if (user == null) {
>>>>>>> 612670468b8e97480829caa20b45e30aafe3dc05
            request.setAttribute("errorMessage", "Tài khoản không tồn tại. Vui lòng thử lại.");
            request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
            return;
        }

        // Kiểm tra nếu tài khoản chưa kích hoạt
        if (!user.isIsActive()) {
            request.setAttribute("errorMessage", "Tài khoản chưa được kích hoạt. Vui lòng kiểm tra email để kích hoạt.");
            request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
            return;
        }

        // Kiểm tra mật khẩu (tránh lỗi NullPointerException)
        if (user.getPassword() == null || !user.getPassword().equals(password)) {
            request.setAttribute("errorMessage", "Sai mật khẩu. Vui lòng thử lại.");
            request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
            return;
        }

        // Kiểm tra quyền truy cập
        if (user.getRoleId() == 3) {
            request.getSession().setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/HomePage");
        } else {
            request.setAttribute("errorMessage", "Tài khoản không có quyền truy cập.");
            request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet that handles user login.";
    }

}