package UserController;

import DAO.UserDAO;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EditUserController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        if (username == null || username.isEmpty()) {
            response.sendRedirect("viewuser"); // Nếu không có username, quay lại danh sách
            return;
        }

        User user = userDAO.getUserByUsername(username);
        if (user == null) {
            response.sendRedirect("viewuser?error=user_not_found");
            return;
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("/auth/edituser.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    try {
        String username = request.getParameter("username");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        boolean isActive = "1".equals(request.getParameter("isActive"));

        User user = new User(username, firstName, lastName, phone, email, isActive);
        boolean updated = userDAO.updateUser(user);

        if (updated) {
            response.sendRedirect("edituser?username=" + username + "&success=true");
        } else {
            response.sendRedirect("edituser?username=" + username + "&error=true");
        }
    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect("edituser?username=" + request.getParameter("username") + "&error=true");
    }
}

}
