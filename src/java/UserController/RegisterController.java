package UserController;

import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import DAO.UserDAO;


public class RegisterController extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
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

        // Kiểm tra username đã tồn tại chưa
        if (userDAO.checkExistUser(username)) {
            request.setAttribute("errorMessage", "Tên đăng nhập đã tồn tại. Vui lòng chọn tên khác!");
            request.getRequestDispatcher("auth/register.jsp").forward(request, response);
            return;
        }

        // Tạo user mới
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDob(autoDOB()); // Set ngày sinh mặc định
        user.setRoleId(3); 
        user.setIsActive(false);
        user.setToken(null);
        user.setExpiredToken(null);

        // Thêm vào database
        boolean success = userDAO.createUser(user);
        if (success) {
            request.setAttribute("successMessage", "Đăng ký tài khoản thành công!");
        } else {
            request.setAttribute("errorMessage", "Đã xảy ra lỗi. Vui lòng thử lại!");
        }

        // Quay về trang đăng ký mà không chuyển hướng
        request.getRequestDispatcher("auth/register.jsp").forward(request, response);
    }

    // Hàm tạo ngày sinh mặc định là 1/1/2025
    private Date autoDOB() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JANUARY, 1);
        return calendar.getTime();
    }

    @Override
    public String getServletInfo() {
        return "Servlet xử lý đăng ký tài khoản";
    }
}
