package UserController;

import DAO.UserDAO;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteUserController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String username = request.getParameter("username");

            if (username == null || username.isEmpty()) {
                request.getSession().setAttribute("message", "Tên đăng nhập không hợp lệ!");
                response.sendRedirect("viewuser");
                return;
            }

            // Lấy thông tin user để kiểm tra role
            User user = userDAO.getUserByUsername(username);
            if (user == null) {
                request.getSession().setAttribute("message", "Người dùng không tồn tại!");
                response.sendRedirect("viewuser");
                return;
            }

            boolean success = userDAO.deleteUserByUsername(username);

            if (success) {
                request.getSession().setAttribute("message", "Xóa người dùng thành công!");
            } else {
                request.getSession().setAttribute("message", "Xóa thất bại, vui lòng thử lại.");
            }

            // Điều hướng theo role
            if (user.getRoleId() == 2) {
                response.sendRedirect("viewstaff");
            } else if (user.getRoleId() == 3) {
                response.sendRedirect("viewuser");
            } else {
                response.sendRedirect("viewuser");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("message", "Lỗi hệ thống, vui lòng thử lại.");
            response.sendRedirect("viewuser");
        }
    }

    @Override
    public String getServletInfo() {
        return "Xóa user theo username";
    }
}
