package UserController;

import DAO.UserDAO;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UpdateProfileController extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        // Lấy thông tin từ request
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        // Xử lý giá trị NULL (nếu người dùng để trống)
        firstName = (firstName == null || firstName.trim().isEmpty()) ? null : firstName.trim();
        lastName = (lastName == null || lastName.trim().isEmpty()) ? null : lastName.trim();
        email = (email == null || email.trim().isEmpty()) ? null : email.trim();
        phone = (phone == null || phone.trim().isEmpty()) ? null : phone.trim();

        // Kiểm tra dữ liệu hợp lệ
        if (firstName != null && !isValidName(firstName)) {
            response.sendRedirect("viewprofile?error=invalid_name");
            return;
        }
        if (lastName != null && !isValidName(lastName)) {
            response.sendRedirect("viewprofile?error=invalid_name");
            return;
        }
        if (email != null && !isValidEmail(email)) {
            response.sendRedirect("viewprofile?error=invalid_email");
            return;
        }
        if (phone != null && !isValidPhone(phone)) {
            response.sendRedirect("viewprofile?error=invalid_phone");
            return;
        }

        // Cập nhật thông tin user
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhone(phone);

        // Gọi DAO để cập nhật
        boolean updated = userDAO.updateProfile(user);

        if (updated) {
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/viewprofile?success=true");
        } else {
            response.sendRedirect(request.getContextPath() + "/viewprofile?error=true");
        }
    }

    // Hàm kiểm tra họ tên hợp lệ (chỉ chứa chữ cái và khoảng trắng)
    private boolean isValidName(String name) {
        return name != null && name.matches("^[A-Za-zÀ-ỹ\\s]+$");
    }

    // Hàm kiểm tra số điện thoại (phải có đúng 10 số)
    private boolean isValidPhone(String phone) {
        return phone != null && phone.matches("^\\d{10}$");
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}
