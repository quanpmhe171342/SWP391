/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.CategoryProduct;
import Model.Color;
import Model.Product;
import Model.ProductVariant;
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
public class DaoCategoryProduct extends DBContext{
    public List<CategoryProduct> getCateProduct() {
        List<CategoryProduct> cateproduct = new ArrayList();
        try {
            String query = "Select  cp.CategoryID,cp.category_name,cp.category_description,cp.image   \n"
                    + "					from CategoryProduct cp \n";

            PreparedStatement stm = conn.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CategoryProduct cp = new CategoryProduct(rs.getInt("CategoryID"),
                        rs.getString("category_name"), rs.getString("category_description"), rs.getString("image"));
               
                cateproduct.add(cp);
            }
        } catch (SQLException e) {
            System.out.print(e);
        }
        return cateproduct;
    }
    public static void main(String[] args) {
        DaoCategoryProduct db = new DaoCategoryProduct();
        System.out.println(db.getCateProduct());
    }
}
