package Controller;

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

    // Handles the HTTP POST method for login
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        // Get parameters from the login form
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//
//        // Check if the user exists and the password matches
//        User user = userDAO.getUserByUsername(username);
//
//        if (user != null && user.getPassword().equals(password)) {
//            // Successful login
//            request.getSession().setAttribute("user", user); // Store the user in session
//            response.sendRedirect(request.getContextPath() + "/auth/changepassword.jsp"); // Redirect to the change password page
//        } else {
//            // Invalid username or password
//            request.setAttribute("errorMessage", "Invalid username or password. Please try again.");
//            request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
//        }
//    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userDAO.getUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            request.getSession().setAttribute("user", user);

            // Kiểm tra nếu người dùng cần thay đổi mật khẩu
             // Ví dụ: nếu roleId là 3 thì yêu cầu thay đổi mật khẩu
                response.sendRedirect(request.getContextPath() + "/auth/changepassword.jsp");
           
        } else {
            request.setAttribute("errorMessage", "Invalid username or password. Please try again.");
            request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet that handles user login.";
    }

}
