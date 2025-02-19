package UserController;

import DAO.UserDAO;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangePasswordController extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO(); // Initialize the DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Render trang thay đổi mật khẩu
        request.getRequestDispatcher("/auth/changepassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy user từ session
        User user = (User) request.getSession().getAttribute("user");

        // Kiểm tra xem user có tồn tại trong session không
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        String username = user.getUsername();

        // Lấy thông tin mật khẩu cũ và mật khẩu mới từ form
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String repeatNewPassword = request.getParameter("repeatNewPassword");

        // Kiểm tra mật khẩu mới và mật khẩu nhập lại có khớp không
        if (!newPassword.equals(repeatNewPassword)) {
            request.setAttribute("error", "Mật khẩu nhập lại không khớp!");
            request.getRequestDispatcher("/auth/changepassword.jsp").forward(request, response);
            return;
        }

        // Lấy thông tin người dùng từ cơ sở dữ liệu
        User dbUser = userDAO.getUserByUsername(username);

        // Kiểm tra mật khẩu cũ có đúng không
        if (dbUser == null || !dbUser.getPassword().equals(oldPassword)) {
            request.setAttribute("error", "Mật khẩu cũ không đúng!");
            request.getRequestDispatcher("/auth/changepassword.jsp").forward(request, response);
            return;
        }

        // Gọi phương thức đổi mật khẩu từ UserDAO
        boolean isSuccess = userDAO.changePassword(username, oldPassword, newPassword);

        // Xử lý kết quả
        if (isSuccess) {
            request.setAttribute("success", "Đổi mật khẩu thành công!");
        } else {
            request.setAttribute("error", "Đổi mật khẩu thất bại. Vui lòng kiểm tra lại thông tin.");
        }

        request.getRequestDispatcher("/auth/changepassword.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet xử lý thay đổi mật khẩu người dùng.";
    }
}
