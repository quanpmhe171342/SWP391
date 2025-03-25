/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.CategoryProduct;
import Model.Size;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author phuan
 */
public class DaoSize extends DBContext {

    private final DBContext dbContext;
    private final Connection connection;

    public DaoSize() {

        dbContext = new DBContext();
        connection = dbContext.conn;
    }

    public List<Size> getSize(int categoryId) {
        List<Size> sizes = new ArrayList<>();
        String query = "SELECT s.SizeID, s.SizeName FROM Size s WHERE s.Category_Id = ?";
        try (PreparedStatement stm = conn.prepareStatement(query)) {
            stm.setInt(1, categoryId);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    // Nếu model Size có thuộc tính CategoryProduct thì khởi tạo trống hoặc null
                    Size size = new Size(
                            rs.getInt("SizeID"),
                            rs.getString("SizeName"),
                            categoryId
                    );
                    sizes.add(size);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sizes;
    }

    public static void main(String[] args) {
        DaoSize dao = new DaoSize();
        System.out.println(dao.getSize(1));
    }
}
