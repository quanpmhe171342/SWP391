package DAO;

import Model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {

    private final DBContext dbContext;
    private final Connection connection;

    public UserDAO() {

        dbContext = new DBContext();
        connection = dbContext.conn;
    }

    public boolean createUser(User user) {
        if (checkExistUser(user.getUsername())) {
            System.out.println("Username already exists!");
            return false; // Không tạo user nếu đã tồn tại
        }

        String sql = "INSERT INTO Users (first_name, last_name, phone, email, username, password, dob, gender, address, avatar, roleId, isActive, token, expired_token) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getPhone());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getUsername());
            pstmt.setString(6, user.getPassword());
            pstmt.setDate(7, new java.sql.Date(user.getDob().getTime()));
            pstmt.setBoolean(8, user.isGender());
            pstmt.setString(9, user.getAddress());
            pstmt.setString(10, user.getAvatar());
            pstmt.setInt(11, user.getRoleId());
            pstmt.setBoolean(12, user.isIsActive());
            pstmt.setString(13, user.getToken());
            pstmt.setString(14, user.getExpiredToken());
            pstmt.executeUpdate();
            System.out.println("User created successfully!");
            return true; // Trả về true nếu tạo thành công
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, "Error inserting user", ex);
        }
        return false;
    }

    public static void main(String[] args) {
        UserDAO u = new UserDAO();
        User us = new User();
        us.setUsername("hieu");
        us.setPassword("1");
        us.setRoleId(3);
        us.setDob(generateRandomDob());
        u.createUser(us);
    }

    private static java.util.Date generateRandomDob() {
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

    // Lấy thông tin người dùng theo username
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapUser(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, "Error fetching user", ex);
        }
        return null;
    }

    // Cập nhật thông tin người dùng
    public boolean updateUser(int userId, String firstName, String lastName, String email, String phone, String password, int roleId) {
        String sql;
        boolean updatePassword = (password != null && !password.trim().isEmpty());

        if (updatePassword) {
            sql = "UPDATE Users SET first_name = ?, last_name = ?, email = ?, phone = ?, password = ?, RoleID = ? WHERE UserID = ?";
        } else {
            sql = "UPDATE Users SET first_name = ?, last_name = ?, email = ?, phone = ?, RoleID = ? WHERE UserID = ?";
        }

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, phone);

            int index = 5;
            if (updatePassword) {
                pstmt.setString(index++, password); // Nếu có mật khẩu mới, cập nhật nó
            }
            pstmt.setInt(index++, roleId);
            pstmt.setInt(index, userId);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa người dùng theo username
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM Users WHERE UserID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu xóa thành công
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy danh sách tất cả người dùng
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapUser(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, "Error fetching users", ex);
        }
        return users;
    }

    // Kiểm tra người dùng có tồn tại với username không
    public boolean checkExistUser(String username) {
        String sql = "SELECT COUNT(*) FROM Users WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu số lượng kết quả > 0 thì người dùng đã tồn tại
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, "Error checking user existence", ex);
        }
        return false; // Trả về false nếu không có người dùng nào
    }

    public boolean checkOldPassword(String username, String oldPassword) {
        String query = "SELECT password FROM Users WHERE username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username); // Gán giá trị username vào câu lệnh
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Lấy mật khẩu từ cơ sở dữ liệu
                String storedPassword = rs.getString("password");

                // So sánh mật khẩu cũ nhập vào với mật khẩu trong cơ sở dữ liệu
                return storedPassword.equals(oldPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Trả về false nếu không tìm thấy username hoặc có lỗi
    }

    // Phương thức đổi mật khẩu
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        if (checkOldPassword(username, oldPassword)) {
            String query = "UPDATE Users SET password = ? WHERE username = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, newPassword);
                stmt.setString(2, username);
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0; // Trả về true nếu cập nhật thành công
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // Phương thức hỗ trợ ánh xạ dữ liệu từ ResultSet sang User
    private User mapUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("userId"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("phone"),
                rs.getString("email"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getDate("dob"),
                rs.getBoolean("gender"),
                rs.getString("address"),
                rs.getString("avatar"),
                rs.getInt("roleId"),
                rs.getBoolean("isActive"),
                rs.getString("token"),
                rs.getString("expired_token")
        );
    }

    public List<User> getUsersByRole(int roleID) {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT UserID, first_name, last_name, phone, email, username, RoleID FROM Users";

        // Lọc theo RoleID: Nếu 2 hoặc 3 thì lọc theo role cụ thể, nếu 0 thì chỉ lấy Nhân viên & Khách hàng
        if (roleID == 2 || roleID == 3) {
            sql += " WHERE RoleID = ?";
        } else if (roleID == 0) {
            sql += " WHERE RoleID IN (2, 3)"; // Lọc tất cả nhưng không có RoleID = 1 (Admin)
        }

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            // Nếu lọc theo 1 role cụ thể (2 hoặc 3), gán giá trị vào câu lệnh SQL
            if (roleID == 2 || roleID == 3) {
                pstmt.setInt(1, roleID);
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("UserID"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setRoleId(rs.getInt("RoleID"));
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

}
