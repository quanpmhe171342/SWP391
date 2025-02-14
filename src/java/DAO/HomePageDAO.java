/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Blog;
import Model.CategoryBlog;
import Model.CategoryProduct;
import Model.Color;
import Model.Product;
import Model.ProductVariant;
import Model.Size;
import Model.Slider;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Admin
 */
public class HomePageDAO extends DBContext{
    public List<ProductVariant> BestSeller() {
    List<ProductVariant> bestSellers = new ArrayList<>();
    String query = """
            SELECT TOP 8
                p.[ProductID],
                p.[ProductName],
                p.[original_price],
                p.[sale_price],
                p.[product_description],
                p.[brief_information],
                pv.[VariantID],
                pv.[SizeID],
                pv.[ColorID],
                pv.[Stock],
                pv.[ImageURL],
                cp.CategoryID,
                cp.category_name,
                cp.category_description,
                cp.image
            FROM [Product] p
            JOIN [ProductVariant] pv ON p.[ProductID] = pv.[ProductID]
            JOIN [CategoryProduct] cp ON p.[CategoryProductID] = cp.[CategoryID]
            ORDER BY p.sale_price DESC;
    """;

    try {
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            
            int categoryID = rs.getInt("CategoryID");
            String categoryName = rs.getString("category_name");
            String categoryDescription = rs.getString("category_description");
            String categoryImage = rs.getString("image");
            
            CategoryProduct categoryProduct = new CategoryProduct(categoryID, categoryName, categoryDescription, categoryImage);
            
            
            Size size = new Size(); 
            Color color = new Color(); 
        
            int productID = rs.getInt("ProductID");
            String productName = rs.getString("ProductName");
            double originalPrice = rs.getDouble("original_price");
            double salePrice = rs.getDouble("sale_price");
            String productDescription = rs.getString("product_description");
            String briefInformation = rs.getString("brief_information");

            Product product = new Product(productID, productName, originalPrice, salePrice, productDescription, briefInformation, categoryProduct);

            int variantID = rs.getInt("VariantID");
            int stock = rs.getInt("Stock");
            String imageUrl = rs.getString("ImageURL");

            ProductVariant productVariant = new ProductVariant(variantID, product, size, color, stock, imageUrl);

            bestSellers.add(productVariant); 
        }
    } catch (SQLException e) {
        e.printStackTrace(); 
    }

    return bestSellers;
}

     
 

    public List<Slider> slideHome() {
   
    List<Slider> list = new ArrayList<>();
    String query = """
            SELECT TOP 3 [SliderID]
                    ,[title]
                    ,[image]
                    ,[status]
                    ,[Description]
                FROM [Slider]
                WHERE [status] = 1
                ORDER BY [SliderID] DESC;
    """;

    try {
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            
           Slider sl = new Slider(rs.getInt("SliderID"), rs.getString("title"), rs.getString("image"), rs.getInt("status"), rs.getString("Description"));

            list.add(sl); 
        }
    } catch (SQLException e) {
        e.printStackTrace(); 
    }

    return list;
    }
    
    
    public List<Blog> TopBlogNew() {
   
    List<Blog> list = new ArrayList<>();
    String query = """
           SELECT TOP 3 [BlogID]
                      ,[Title]
                      ,[image]
                      ,[Content]
                  
                      ,[CreatedAt]
                  FROM [Blog]
                  ORDER BY [CreatedAt] DESC;
    """;

    try {
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            
            CategoryBlog cb = new CategoryBlog();
            Blog blog = new Blog(rs.getInt("BlogID"), rs.getString("Title"), rs.getString("image"), rs.getString("Content"), cb, rs.getDate("CreatedAt"));
            list.add(blog); 
        }
    } catch (SQLException e) {
        e.printStackTrace(); 
    }

    return list;
    }
    
    
    
    
    public List<ProductVariant> NewProduct() {
   
    List<ProductVariant> newProduct = new ArrayList<>();
    String query = """
            SELECT TOP 4 
                            p.[ProductID],
                            p.[ProductName],
                            p.[original_price],
                            p.[sale_price],
                            p.[product_description],
                            p.[brief_information],
                            pv.[VariantID],
                            pv.[SizeID],
                            pv.[ColorID],
                            pv.[Stock],
                            pv.[ImageURL],
                            cp.CategoryID,
                            cp.category_name,
                            cp.category_description,
                            cp.image
                        FROM [Product] p
                        JOIN [ProductVariant] pv ON p.[ProductID] = pv.[ProductID]
                        JOIN [CategoryProduct] cp ON p.[CategoryProductID] = cp.[CategoryID]
                        ORDER BY p.ProductID DESC;
    """;

    try {
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            
            int categoryID = rs.getInt("CategoryID");
            String categoryName = rs.getString("category_name");
            String categoryDescription = rs.getString("category_description");
            String categoryImage = rs.getString("image");
            
            CategoryProduct categoryProduct = new CategoryProduct(categoryID, categoryName, categoryDescription, categoryImage);
                   
            Size size = new Size(); 
            Color color = new Color(); 
        
            int productID = rs.getInt("ProductID");
            String productName = rs.getString("ProductName");
            double originalPrice = rs.getDouble("original_price");
            double salePrice = rs.getDouble("sale_price");
            String productDescription = rs.getString("product_description");
            String briefInformation = rs.getString("brief_information");

            Product product = new Product(productID, productName, originalPrice, salePrice, productDescription, briefInformation, categoryProduct);

            int variantID = rs.getInt("VariantID");
            int stock = rs.getInt("Stock");
            String imageUrl = rs.getString("ImageURL");

            ProductVariant productVariant = new ProductVariant(variantID, product, size, color, stock, imageUrl);

            newProduct.add(productVariant); 
        }
    } catch (SQLException e) {
        e.printStackTrace(); 
    }

    return newProduct;
    }
    
         public static void main(String[] args) {
         HomePageDAO db = new HomePageDAO();
             System.out.println(db.slideHome());
        
    }
}
