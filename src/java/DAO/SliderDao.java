/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Color;
import Model.NewProduct;
import Model.Product;
import Model.ProductSlider;
import Model.ProductVariant;
import Model.Size;
import Model.Slider;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Model.NewSlider;

/**
 *
 * @author NV200
 */
public class SliderDao extends DBContext {

    public List<ProductSlider> getAllProductVariants() {
        List<ProductSlider> productVariants = new ArrayList<>();
        String query = """
        SELECT VariantID
        FROM ProductVariant
    """;

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int variantID = rs.getInt("VariantID");

                ProductSlider productVariant = new ProductSlider(variantID);
                productVariants.add(productVariant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productVariants;
    }

    public List<Slider> loadAllSlider() {
        List<Slider> list = new ArrayList<>();
        String query = """
            SELECT  s.[SliderID], s.[title], s.[image], s.[status], s.[Description], 
                           pv.[VariantID], pv.[Stock], pv.[ImageURL],
                           p.[ProductID], p.[ProductName], p.[original_price], p.[sale_price], p.[product_description], p.[brief_information], p.[CreateDate]
                    FROM [Slider] s
                    JOIN ProductVariant pv ON s.produtVarianID = pv.VariantID
                    JOIN Product p ON pv.ProductID = p.ProductID
                    ORDER BY s.[status] DESC;
            """;

        try (PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Size size = new Size();
                Color color = new Color();

                // Lấy thông tin Product
                int productID = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                double originalPrice = rs.getDouble("original_price");
                double salePrice = rs.getDouble("sale_price");
                String productDescription = rs.getString("product_description");
                String briefInformation = rs.getString("brief_information");
                Date createDate = rs.getDate("CreateDate");

                Product product = new Product(productID, productName, originalPrice, salePrice, productDescription, briefInformation, null);
                NewProduct newProduct = new NewProduct(product, createDate);

                // Lấy thông tin ProductVariant
                int variantID = rs.getInt("VariantID");
                int stock = rs.getInt("Stock");
                String imageUrl = rs.getString("ImageURL");
                ProductVariant productVariant = new ProductVariant(variantID, product, size, color, stock, imageUrl);

                // Tạo Slider
                Model.Slider slider = new Model.Slider(rs.getInt("SliderID"), rs.getString("title"), rs.getString("image"), rs.getInt("status"), rs.getString("Description"), productVariant);
                list.add(slider);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addSlider(String title, String image, int status, String description, int productVariantId) {
        String query = """
        INSERT INTO Slider (title, image, status, Description, produtVarianID) 
        VALUES (?, ?, ?, ?, ?)
    """;

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, title);
            ps.setString(2, image);
            ps.setInt(3, status);
            ps.setString(4, description);
            ps.setInt(5, productVariantId);

            ps.executeUpdate(); // Trả về true nếu thêm thành công
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
           return false;
        }
        
    }

    public boolean updateSlider(int id, String title, String image, int status, String description, int productVariantId) {
        String query = """
        UPDATE Slider 
        SET title = ?, image = ?, status = ?, Description = ?, produtVarianID = ?
        WHERE SliderID = ?
    """;

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, title);
            ps.setString(2, image);
            ps.setInt(3, status);
            ps.setString(4, description);
            ps.setInt(5, productVariantId);
            ps.setInt(6, id);
            ps.executeUpdate(); // Kiểm tra số hàng bị ảnh hưởng
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
    }

    public Slider loadSliderById(int sliderId) {
        String query = """
            SELECT s.[SliderID], s.[title], s.[image], s.[status], s.[Description], 
                   pv.[VariantID], pv.[Stock], pv.[ImageURL],
                   p.[ProductID], p.[ProductName], p.[original_price], p.[sale_price], 
                   p.[product_description], p.[brief_information], p.[CreateDate]
            FROM [Slider] s
            JOIN ProductVariant pv ON s.produtVarianID = pv.VariantID
            JOIN Product p ON pv.ProductID = p.ProductID
            WHERE s.[SliderID] = ?;
            """;

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, sliderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Size size = new Size();
                    Color color = new Color();

                    // Lấy thông tin Product
                    int productID = rs.getInt("ProductID");
                    String productName = rs.getString("ProductName");
                    double originalPrice = rs.getDouble("original_price");
                    double salePrice = rs.getDouble("sale_price");
                    String productDescription = rs.getString("product_description");
                    String briefInformation = rs.getString("brief_information");
                    Date createDate = rs.getDate("CreateDate");

                    Product product = new Product(productID, productName, originalPrice, salePrice, productDescription, briefInformation, null);
                    NewProduct newProduct = new NewProduct(product, createDate);

                    // Lấy thông tin ProductVariant
                    int variantID = rs.getInt("VariantID");
                    int stock = rs.getInt("Stock");
                    String imageUrl = rs.getString("ImageURL");
                    ProductVariant productVariant = new ProductVariant(variantID, product, size, color, stock, imageUrl);

                    // Tạo và trả về Slider
                    return new Slider(rs.getInt("SliderID"), rs.getString("title"), rs.getString("image"), rs.getInt("status"), rs.getString("Description"), productVariant);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy slider với ID truyền vào
    }

    public boolean hideSlider(int sliderID) {
    String query = "UPDATE Slider SET status = 0 WHERE SliderID = ?";
    
    try {
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, sliderID);
         ps.executeUpdate();
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    public boolean deleteSlider(int sliderID) {
    String query = "DELETE FROM Slider WHERE SliderID = ?";
    
    try {
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, sliderID);
         ps.executeUpdate();
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


    public static void main(String[] args) {
        SliderDao sliderDao = new SliderDao();
   sliderDao.hideSlider(9);
        

    }
}
