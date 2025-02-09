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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author phuan
 */
public class DaoProduct extends DBContext {

    public List<ProductVariant> getProduct() {
        List<ProductVariant> product = new ArrayList();
        try {
            String query = "Select pv.VariantID,pv.Stock,pv.ColorID,pv.ImageURL,pv.SizeID,s.SizeName,c.ColorName, p.ProductID, p.ProductName,p.original_price, p.sale_price, p.product_description,p.brief_information, cp.CategoryID,cp.category_name,cp.category_description,cp.image         \n"
                    + "                    from ProductVariant pv inner join Product p \n"
                    + "					on pv.ProductID = p.ProductID\n"
                    + "					inner join CategoryProduct cp \n"
                    + "                    on p.CategoryID =cp.CategoryID\n"
                    + "					inner join Size s \n"
                    + "					on pv.SizeID = s.SizeID\n"
                    + "					inner join Color c\n"
                    + "					on pv.ColorID = c.ColorID ";
            PreparedStatement stm = conn.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CategoryProduct cp = new CategoryProduct(rs.getInt("CategoryID"),
                        rs.getString("category_name"), rs.getString("category_description"), rs.getString("image"));
                Product p = new Product(rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("original_price"),
                        rs.getDouble("sale_price"),
                        rs.getString("product_description"),
                        rs.getString("brief_information"), cp);
                Size s = new Size(rs.getInt("SizeID"), rs.getString("SizeName"));
                Color c = new Color(rs.getInt("ColorID"), rs.getString("ColorName"));
                ProductVariant pv = new ProductVariant(
                        rs.getInt("VariantID"),
                        p,
                        s,
                        c,
                        rs.getInt("Stock"),
                        rs.getString("ImageURL")
                );
                product.add(pv);
            }
        } catch (SQLException e) {
            System.out.print(e);
        }
        return product;
    }

    public List<ProductVariant> getProduct1(HashMap<String, String> filter) {
        List<ProductVariant> product = new ArrayList();
        try {
                String query = "WITH ProductVariantRanked AS (\n"
                        + "    SELECT ProductID, ImageURL, \n"
                        + "           ROW_NUMBER() OVER (PARTITION BY ProductID ORDER BY Stock DESC) AS rn\n"
                        + "    FROM ProductVariant\n"
                        + "    WHERE Stock > 0\n"
                        + ")\n"
                        + "SELECT p.ProductID, p.ProductName, p.original_price, \n"
                        + "       p.sale_price, p.product_description, p.brief_information, \n"
                        + "       cp.CategoryID, cp.category_name, cp.category_description, cp.image, \n"
                        + "       pv.ImageURL      \n"
                        + "FROM Product p \n"
                        + "INNER JOIN ProductVariantRanked pv ON pv.ProductID = p.ProductID AND pv.rn = 1\n"
                        + "INNER JOIN CategoryProduct cp ON p.CategoryID = cp.CategoryID\n";
            //                    + "ORDER BY p.original_price " +"  " + FILTER;
               if(!filter.isEmpty()){
                     for (Map.Entry<String, String> item : filter.entrySet()) {
                       query += item.getValue();
                       }             
               }
                     System.out.println(query);
            PreparedStatement stm = conn.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CategoryProduct cp = new CategoryProduct(rs.getInt("CategoryID"),
                        rs.getString("category_name"), rs.getString("category_description"), rs.getString("image"));
                Product p = new Product(rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("original_price"),
                        rs.getDouble("sale_price"),
                        rs.getString("product_description"),
                        rs.getString("brief_information"), cp);
                Size s = new Size();
                Color c = new Color();
                ProductVariant pv = new ProductVariant(
                        1,
                        p,
                        s,
                        c,
                        0,
                        rs.getString("ImageURL")
                );
                product.add(pv);
            }
        } catch (SQLException e) {
            System.out.print(e);
        }
        return product;
    }
    
        public List<ProductVariant> getListByPage(List<ProductVariant> list, int start, int end) {
        List<ProductVariant> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }
// public List<ProductVariant> getProduct2(String FILTER) {
//        List<ProductVariant> product = new ArrayList();
//        try {
//            String query = "WITH ProductVariantRanked AS (\n"
//                    + "    SELECT ProductID, ImageURL, \n"
//                    + "           ROW_NUMBER() OVER (PARTITION BY ProductID ORDER BY Stock DESC) AS rn\n"
//                    + "    FROM ProductVariant\n"
//                    + "    WHERE Stock > 0\n"
//                    + ")\n"
//                    + "SELECT p.ProductID, p.ProductName, p.original_price, \n"
//                    + "       p.sale_price, p.product_description, p.brief_information, \n"
//                    + "       cp.CategoryID, cp.category_name, cp.category_description, cp.image, \n"
//                    + "       pv.ImageURL      \n"
//                    + "FROM Product p \n"
//                    + "INNER JOIN ProductVariantRanked pv ON pv.ProductID = p.ProductID AND pv.rn = 1\n"
//                    + "INNER JOIN CategoryProduct cp ON p.CategoryID = cp.CategoryID\n"
//                    + "ORDER BY p.original_price " +"  " + FILTER;
//
//            PreparedStatement stm = conn.prepareStatement(query);
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//                CategoryProduct cp = new CategoryProduct(rs.getInt("CategoryID"),
//                        rs.getString("category_name"), rs.getString("category_description"), rs.getString("image"));
//                Product p = new Product(rs.getInt("ProductID"),
//                        rs.getString("ProductName"),
//                        rs.getDouble("original_price"),
//                        rs.getDouble("sale_price"),
//                        rs.getString("product_description"),
//                        rs.getString("brief_information"), cp);
//                Size s = new Size();
//                Color c = new Color();
//                ProductVariant pv = new ProductVariant(
//                        1,
//                        p,
//                        s,
//                        c,
//                        0,
//                        rs.getString("ImageURL")
//                );
//                product.add(pv);
//            }
//        } catch (SQLException e) {
//            System.out.print(e);
//        }
//        return product;
//    }
    public static void main(String[] args) {
        DaoProduct pv = new DaoProduct();
        System.out.println("aa");
    }
}
