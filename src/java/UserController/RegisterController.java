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
import java.util.Random;
import Model.User;
import DAO.UserDAO;
import static Utils.Email.sendEmail;
import java.time.LocalDate;

<<<<<<< HEAD


=======
>>>>>>> 612670468b8e97480829caa20b45e30aafe3dc05
public class RegisterController extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO(); // Initialize the DAO
    }

    // Handles the HTTP GET method to show the registration form
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to the registration JSP page
        request.getRequestDispatcher("auth/register.jsp").forward(request, response);
    }

    
    
    
    // Handles the HTTP POST method for registering a new user
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to the login JSP page
        request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get parameters from the registration form
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        Date dob;

        // Check if the username already exists
        if (userDAO.checkExistUser(username)) {
<<<<<<< HEAD
            request.setAttribute("errorMessage", "Username already exists. Please choose a different one.");
            request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
            return;
        }

        // Create a new User object
//        User user = new User(0, firstName, lastName, phone, email, username, password, generateRandomDob(), true, phone, email, 0, true, phone, firstName);
//        User user = new User();
=======
            request.setAttribute("errorMessage", "Tên đăng nhập đã tồn tại. Vui lòng chọn tên khác!");
            request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
            return;
        }

        // Tạo token xác minh
        String token = UserDAO.generateToken();

        // Tạo user mới
>>>>>>> 612670468b8e97480829caa20b45e30aafe3dc05
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setFirstName(firstName);
        user.setLastName(lastName);
<<<<<<< HEAD
        user.setDob(generateRandomDob());
        user.setRoleId(3);  // Default role
=======
        user.setDob(autoDOB()); // Set ngày sinh mặc định
        user.setRoleId(3);
>>>>>>> 612670468b8e97480829caa20b45e30aafe3dc05
        user.setIsActive(false);
        user.setToken(token);
        user.setExpiredToken(null);
        
        // Generate a random date of birth (between 1970 and 2000)
        Date randomDob = generateRandomDob();
        user.setDob(randomDob);

<<<<<<< HEAD
        // Insert user into the database
        userDAO.createUser(user);

        // Redirect or forward to a success page
        request.setAttribute("successMessage", "User created successfully!");
        request.getRequestDispatcher("/auth/hometest.jsp").forward(request, response);  // Redirect to login page or any other page
    }

    // Method to generate a random Date of Birth between 1970 and 2000
    private Date generateRandomDob() {
        Random rand = new Random();

        // Set the range for the year
        int startYear = 1970;
        int endYear = 2000;

        // Create a Calendar instance
        Calendar calendar = Calendar.getInstance();

        // Generate a random year between startYear and endYear
        int year = rand.nextInt(endYear - startYear + 1) + startYear;
        calendar.set(Calendar.YEAR, year);

        // Generate a random month between 0 (January) and 11 (December)
        int month = rand.nextInt(12);
        calendar.set(Calendar.MONTH, month);

        // Generate a random day of the month based on the actual number of days in the selected month
        int day = rand.nextInt(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) + 1;
        calendar.set(Calendar.DAY_OF_MONTH, day);

        // Return the random date
        return calendar.getTime();
=======
        // Thêm vào database
        boolean success = userDAO.createUser(user);
        if (success) {
//            request.setAttribute("successMessage", "Đăng ký tài khoản thành công!");

            userDAO.saveVerificationToken(email, token);

            // **Tạo đường link xác minh**
            String verifyLink = "http://localhost:8080/SWP391/verify?token=" + token;

            // **Hiển thị link trên trang web**
//            request.setAttribute("successMessage", "Đăng ký thành công! Dùng link sau để kích hoạt tài khoản: <br><a href='" + verifyLink + "'>" + verifyLink + "</a>");
            request.setAttribute("successMessage", "Đăng ký thành công! Hãy kiểm tra email để kích hoạt tài khoản.");
            
            String subject = "Đăng ký thành công!";
            String content = "Chào bạn đến với cửa hàng Male Fashion." +",<br><br>"
                    + "Cảm ơn bạn đã đăng ký tài khoản trên hệ thống.<br>"
                    + "Vui lòng bấm vào link sau để xác minh tài khoản của bạn:<br>"
                    + "<a href='" + verifyLink + "'>" + verifyLink + "</a><br><br>"
                    + "Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua email này.<br><br>"
                    + "Trân trọng!";

            sendEmail(email, subject, content);

        } else {
            request.setAttribute("errorMessage", "Đã xảy ra lỗi. Vui lòng thử lại!");
        }

        // Quay về trang đăng ký mà không chuyển hướng
        request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
    }

    // lấy ngày tạo tài khoản
    private Date autoDOB() {
        return java.sql.Date.valueOf(LocalDate.now());
>>>>>>> 612670468b8e97480829caa20b45e30aafe3dc05
    }

    @Override
    public String getServletInfo() {
        return "Servlet that handles user registration.";
    }
}
