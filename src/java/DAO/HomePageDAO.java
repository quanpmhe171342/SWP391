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
import Model.NewProduct;
import Model.NewProductVariant;
import Model.ProductVariant;
import Model.Size;
import Model.Slider;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
public class HomePageDAO extends DBContext {

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
            WHERE p.[sale_price] > 0  and pv.[Stock] > 0
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
            SELECT  s.[SliderID], s.[title], s.[image], s.[status], s.[Description], 
                           pv.[VariantID], pv.[Stock], pv.[ImageURL],
                           p.[ProductID], p.[ProductName], p.[original_price], p.[sale_price], p.[product_description], p.[brief_information], p.[CreateDate]
                    FROM [Slider] s
                    JOIN ProductVariant pv ON s.produtVarianID = pv.VariantID
                    JOIN Product p ON pv.ProductID = p.ProductID
                    WHERE s.[status] = 1
                    ORDER BY s.[SliderID] DESC;
            """;

    try (PreparedStatement ps = conn.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {
        
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
            Slider slider = new Slider(rs.getInt("SliderID"), rs.getString("title"), rs.getString("image"), rs.getInt("status"), rs.getString("Description"), productVariant);
            list.add(slider);
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

    public List<NewProductVariant> NewProduct() {

        List<NewProductVariant> newProduct = new ArrayList<>();
        String query = """
            SELECT TOP 4 
                                                    p.[ProductID],
                                                    p.[ProductName],
                                                    p.[original_price],
                                                    p.[sale_price],
                                                    p.[product_description],
                                                    p.[brief_information],
                                                    p.CreateDate,
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
                                                WHERE cp.[CategoryID] = 1 and pv.[Stock] > 0
                                                ORDER BY p.CreateDate DESC ;
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
                NewProduct m = new NewProduct(product, rs.getDate("CreateDate"));
                int variantID = rs.getInt("VariantID");
                int stock = rs.getInt("Stock");
                String imageUrl = rs.getString("ImageURL");

                NewProductVariant productVariant = new NewProductVariant(variantID, m, size, color, stock, imageUrl);

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
