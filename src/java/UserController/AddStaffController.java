package UserController;

import DAO.UserDAO;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

@WebServlet(name = "AddStaffController", urlPatterns = {"/addStaff"})
public class AddStaffController extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("auth/addstaff.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        if (userDAO.checkExistUser(username)) {
            request.setAttribute("errorMessage", "Tên đăng nhập đã tồn tại. Vui lòng chọn tên khác.");
        } else {
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setPhone(phone);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setDob(autoDOB());
            user.setRoleId(2);
            user.setIsActive(true);
            user.setToken(null);
            user.setExpiredToken(null);

            userDAO.createUser(user);
            request.setAttribute("successMessage", "Thêm nhân viên thành công!");
        }

        request.getRequestDispatcher("auth/addstaff.jsp").forward(request, response);
    }

    private Date autoDOB() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JANUARY, 1, 0, 0, 0);
        return calendar.getTime();
    }

    @Override
    public String getServletInfo() {
        return "Servlet xử lý thêm nhân viên";
    }
}
