/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.CategoryProduct;
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
     public List<Size> getSize() {
        List<Size> sizes = new ArrayList();
        try {
            String query = "Select s.SizeID, s.SizeName   \n"
                    + "					from Size s \n";

            PreparedStatement stm = conn.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Size cp = new Size(rs.getInt("SizeID"),
                        rs.getString("SizeName"));
               
                sizes.add(cp);
            }
        } catch (SQLException e) {
            System.out.print(e);
        }
        return sizes;
    }
     public static void main(String[] args) {
        DaoSize s = new DaoSize();
         System.out.println(s.getSize());
    }
}
