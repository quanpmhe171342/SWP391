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
            request.setAttribute("errorMessage", "Username already exists. Please choose a different one.");
            request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
            return;
        }

        // Create a new User object
//        User user = new User(0, firstName, lastName, phone, email, username, password, generateRandomDob(), true, phone, email, 0, true, phone, firstName);
//        User user = new User();
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDob(generateRandomDob());
        user.setRoleId(3);  // Default role
        user.setIsActive(false);
        user.setToken(null);
        user.setExpiredToken(null);
        
        // Generate a random date of birth (between 1970 and 2000)
        Date randomDob = generateRandomDob();
        user.setDob(randomDob);

        // Insert user into the database
        userDAO.createUser(user);

        // Redirect or forward to a success page
        request.setAttribute("successMessage", "User created successfully!");
        request.getRequestDispatcher("/auth/login.jsp").forward(request, response);  // Redirect to login page or any other page
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
    }

    @Override
    public String getServletInfo() {
        return "Servlet that handles user registration.";
    }
}
