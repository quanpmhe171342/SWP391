/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.CategoryProduct;
import Model.CategoryVariant;
import Model.Size;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author phuan
 */
public class DaoSize extends DBContext{
     public List<Size> getSize(int cate) {
        List<Size> sizes = new ArrayList();
        try {
            String query = "Select s.SizeID, s.SizeName   \n"
                    + "from Size s  inner join CategoryProduct c on s.Category_Id = s.Category_Id where c.CategoryID = ?\n";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, cate);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CategoryProduct cv = new CategoryProduct();
                Size cp = new Size(rs.getInt("SizeID"),
                        rs.getString("SizeName"),cv);
                sizes.add(cp);
            }
        } catch (SQLException e) {
            System.out.print(e);
        }
        return sizes;
    }
     public static void main(String[] args) {
        DaoSize s = new DaoSize();
         System.out.println(s.getSize(1));
    }
}
