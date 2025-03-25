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
public class DaoCategoryProduct extends DBContext {
    
    private final DBContext dbContext;
    private final Connection connection;

    public DaoCategoryProduct() {

        dbContext = new DBContext();
        connection = dbContext.conn;
    }

    public List<CategoryProduct> getCateProduct() {
        List<CategoryProduct> categoryProducts = new ArrayList<>();
        String query = "SELECT cp.CategoryID, cp.category_name, cp.category_description, cp.image FROM CategoryProduct cp";
        try (PreparedStatement stm = conn.prepareStatement(query); ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                CategoryProduct cp = new CategoryProduct(
                        rs.getInt("CategoryID"),
                        rs.getString("category_name"),
                        rs.getString("category_description"),
                        rs.getString("image")
                );
                categoryProducts.add(cp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryProducts;
    }

    public static void main(String[] args) {
        DaoCategoryProduct dao = new DaoCategoryProduct();
        System.out.println(dao.getCateProduct());
    }
}
