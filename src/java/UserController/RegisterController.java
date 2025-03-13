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
import java.time.LocalDate;

public class RegisterController extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to the login JSP page
        request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
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

        // Tạo token xác minh
        String token = UserDAO.generateToken();

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
        user.setToken(token);
        user.setExpiredToken(null);

        // Thêm vào database
        boolean success = userDAO.createUser(user);
        if (success) {
//            request.setAttribute("successMessage", "Đăng ký tài khoản thành công!");

            userDAO.saveVerificationToken(email, token);

            // **Tạo đường link xác minh**
            String verifyLink = "http://localhost:8080/SWP391/verify?token=" + token;

            // **Hiển thị link trên trang web**
            request.setAttribute("successMessage", "Đăng ký thành công! Dùng link sau để kích hoạt tài khoản: <br><a href='" + verifyLink + "'>" + verifyLink + "</a>");

        } else {
            request.setAttribute("errorMessage", "Đã xảy ra lỗi. Vui lòng thử lại!");
        }

        // Quay về trang đăng ký mà không chuyển hướng
        request.getRequestDispatcher("auth/register.jsp").forward(request, response);
    }

    // lấy ngày tạo tài khoản
    private Date autoDOB() {
    return java.sql.Date.valueOf(LocalDate.now());
}

    @Override
    public String getServletInfo() {
        return "Servlet xử lý đăng ký tài khoản";
    }
}
