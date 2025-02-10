/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.CategoryProduct;
import Model.CategoryVariant;
import Model.Color;
import Model.Product;
import Model.ProductVariant;
import Model.Size;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author phuan
 */
public class DaoProduct extends DBContext {

    
      public void DeleteProduct(int id) {
        try {
            String query = " DELETE FROM Product\n"
                    + "      WHERE ProductId = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
      public void DeleteProductV(int id) {
        try {
            String query = " DELETE FROM [ProductVariant]\n"
                    + "      WHERE [ProductID] = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public ProductVariant getProductById(int id) {
        ProductVariant pv = new ProductVariant();
        try {
            String query = "WITH ProductVariantRanked AS \n"
                    + "   ( SELECT ProductID, ImageURL, \n"
                    + "           ROW_NUMBER() OVER (PARTITION BY ProductID ORDER BY Stock DESC) AS rn\n"
                    + "    FROM ProductVariant\n"
                    + "    WHERE Stock > 0)\n"
                    + "\n"
                    + "SELECT p.ProductID, p.ProductName, p.original_price, \n"
                    + "       p.sale_price, p.product_description, p.brief_information, \n"
                    + "       cp.CategoryID, cp.category_name, cp.category_description, cp.image, \n"
                    + "       pv.ImageURL      \n"
                    + "FROM Product p \n"
                    + "INNER JOIN ProductVariantRanked pv ON pv.ProductID = p.ProductID AND pv.rn = 1\n"
                    + "INNER JOIN CategoryVariant ca on ca.CategoryVariantID = p.CategoryVariantID\n"
                    + "INNER JOIN CategoryProduct cp ON cp.CategoryID = ca.CategoryVariantID\n"
                    + "WHERE p.ProductID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                    CategoryProduct cp = new CategoryProduct(rs.getInt("CategoryID"),
                        rs.getString("category_name"), rs.getString("category_description"), rs.getString("image"));
                CategoryVariant cv = new CategoryVariant(1,cp,null);
               
                Product p = new Product(rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("original_price"),
                        rs.getDouble("sale_price"),
                        rs.getString("product_description"),
                        rs.getString("brief_information"), cv);
                Size s = new Size();
                Color c = new Color();
                pv = new ProductVariant(
                        0,
                        p,
                        s,
                        c,
                        0,
                        rs.getString("ImageURL")
                );
                return pv;

            }
        } catch (SQLException e) {
            System.out.print(e);
        }
        return null;
    }
    
    public void updateProduct(int product_id,String product_name, double original_price, double sale_price, String product_description, String brief_information ) {
        try {
            String query = "UPDATE Product SET ProductName = ?, original_price = ?, sale_price = ?, product_description = ?, "
                    + "brief_information = ? WHERE ProductID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, product_name);
           stm.setFloat(2, (float) original_price);
            stm.setFloat(3, (float) sale_price);
            stm.setString(4, product_description);
            stm.setString(5, brief_information);
             stm.setInt(6, product_id);
            stm.executeUpdate();
        } catch (Exception e) {
        }
    }
    
    
     public void addProduct( String product_name, double original_price, double sale_price, String product_description, String brief_information, int CategoryVariantID) {
        try {
            String query = "INSERT INTO Product(\n"
                    + "    ProductName,\n"
                    + "    original_price,\n"
                    + "    sale_price,\n"
                    + "    product_description,\n"
                    + "    brief_information,\n"
                    + "    CategoryVariantID\n"
                    + ") VALUES (?, ?, ?, ?, ?, ?);\n";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, product_name);
            stm.setFloat(2, (float) original_price);
            stm.setFloat(3, (float) sale_price);
            stm.setString(4, product_description);
            stm.setString(5, brief_information);
            stm.setInt(6, CategoryVariantID);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
      
     
      public void addProductVariant( int ProductID, int SizeID, int ColorID, int  Stock, String ImageURL) {
        try {
            String query = "INSERT INTO ProductVariant(\n"
                    + "    ProductID,\n"
                    + "    SizeID,\n"
                    + "    ColorID,\n"
                    + "    Stock,\n"
                    + "    ImageURL\n"

                    + ") VALUES (?, ?, ?, ?, ?);\n";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, ProductID);
            stm.setInt(2,  SizeID);
            stm.setInt(3, ColorID);
            stm.setInt(4, Stock);
            stm.setString(5, ImageURL);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<ProductVariant> getSizeProduct(int id) {
        List<ProductVariant> pv = new ArrayList<>();
        try {
            String query = "select pv.ProductID, pv.SizeID, s.SizeName, pv.Stock from ProductVariant pv "
                    + "inner join Size s on pv.SizeID = s.SizeID where pv.ProductID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CategoryProduct cp = new CategoryProduct();
                Product p = new Product();
                Size s = new Size(0, rs.getString("SizeName"), cp);
                Color c = new Color();
                ProductVariant pv1 = new ProductVariant(
                        0,
                        p,
                        s,
                        c,
                        rs.getInt("Stock"),
                        null
                );
                pv.add(pv1);
            }
        } catch (SQLException e) {
            System.out.print(e);
        }
        return pv;

    }

    public List<ProductVariant> getColorProduct(int id) {
        List<ProductVariant> pv = new ArrayList<>();
        try {
            String query = "select pv.ProductID, pv.ColorID, c.ColorName,pv.SizeID, pv.Stock from ProductVariant pv "
                    + "inner join Color c on pv.ColorID = c.ColorID where pv.ProductID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CategoryProduct cp = new CategoryProduct();
                Product p = new Product();
                Size s = new Size(rs.getInt("SizeID"), null, cp);
                Color c = new Color(rs.getInt("ColorID"), rs.getString("ColorName"));
                ProductVariant pv1 = new ProductVariant(
                        0,
                        p,
                        s,
                        c,
                        rs.getInt("Stock"),
                        null
                );
                pv.add(pv1);
            }
        } catch (SQLException e) {
            System.out.print(e);
        }
        return pv;

    }

    public List<String> getImageProduct(int id) {
        List<String> pv = new ArrayList<>();
        try {
            String query = "select pv.ImageURL from ProductVariant pv "
                    + " where pv.ProductID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                pv.add(rs.getString("ImageURL"));
            }
        } catch (SQLException e) {
            System.out.print(e);
        }
        return pv;

    }

    public List<ProductVariant> getProduct1(HashMap<String, String> filter) {
        List<ProductVariant> product = new ArrayList();
        try {
            String query = "WITH ProductVariantRanked AS \n"
                    + "   ( SELECT ProductID, ImageURL, \n"
                    + "           ROW_NUMBER() OVER (PARTITION BY ProductID ORDER BY Stock DESC) AS rn\n"
                    + "    FROM ProductVariant\n"
                    + "    WHERE Stock > 0)\n"
                    + "\n"
                    + "SELECT p.ProductID, p.ProductName, p.original_price, \n"
                    + "       p.sale_price, p.product_description, p.brief_information, \n"
                    + "       cp.CategoryID, cp.category_name, cp.category_description, cp.image, \n"
                    + "       pv.ImageURL      \n"
                    + "FROM Product p \n"
                    + "INNER JOIN ProductVariantRanked pv ON pv.ProductID = p.ProductID AND pv.rn = 1\n"
                    + "INNER JOIN CategoryVariant ca on ca.CategoryVariantID = p.CategoryVariantID\n"
                    + "INNER JOIN CategoryProduct cp ON cp.CategoryID = ca.CategoryVariantID\n";
            //                    + "ORDER BY p.original_price " +"  " + FILTER;
            if (!filter.isEmpty()) {
                for (Map.Entry<String, String> item : filter.entrySet()) {
                    query += item.getValue();
                }
            }
            System.out.println(query);
            PreparedStatement stm = conn.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CategoryVariant cv = new CategoryVariant();
                CategoryProduct cp = new CategoryProduct(rs.getInt("CategoryID"),
                        rs.getString("category_name"), rs.getString("category_description"), rs.getString("image"));
                Product p = new Product(rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("original_price"),
                        rs.getDouble("sale_price"),
                        rs.getString("product_description"),
                        rs.getString("brief_information"), cv);
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

    public List<Product> getProductReleted(int pid) {
        List<Product> product = new ArrayList();
        try {
            String query = "Select p.ProductID,p.ProductName,p.original_price,p.sale_price,p.product_description,p.brief_information \n"
                    + "                    from Product p\n"
                    + "                    inner join \n"
                    + "					CategoryVariant cv on cv.CategoryVariantID =p.CategoryVariantID\n"
                    + "					inner join \n"
                    + "                    CategoryProduct cp on cv.CategoryID = cp.CategoryID \n"
                    + "                    where p.CategoryVariantID = (SELECT CategoryVariantID From Product where ProductID = ?)\n"
                    + "                    AND p.ProductID <> " + pid;
            PreparedStatement stm = conn.prepareStatement(query);
            System.out.println(query);
            stm.setInt(1, pid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CategoryVariant cv = new CategoryVariant();
                CategoryProduct cp = new CategoryProduct();
                Product p = new Product(rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("original_price"),
                        rs.getDouble("sale_price"),
                        rs.getString("product_description"),
                        rs.getString("brief_information"), cv);

                product.add(p);
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

    public List<ProductVariant> getProduct() {
        List<ProductVariant> product = new ArrayList();
        try {
            String query = "WITH ProductVariantRanked AS \n"
                    + "   ( SELECT ProductID, ImageURL, \n"
                    + "           ROW_NUMBER() OVER (PARTITION BY ProductID ORDER BY Stock DESC) AS rn\n"
                    + "    FROM ProductVariant\n"
                    + "    WHERE Stock > 0)\n"
                    + "\n"
                    + "SELECT p.ProductID, p.ProductName, p.original_price, \n"
                    + "       p.sale_price, p.product_description, p.brief_information, \n"
                    + "       cp.CategoryID, cp.category_name, cp.category_description, cp.image, \n"
                    + "       pv.ImageURL      \n"
                    + "FROM Product p \n"
                    + "INNER JOIN ProductVariantRanked pv ON pv.ProductID = p.ProductID AND pv.rn = 1\n"
                    + "INNER JOIN CategoryVariant ca on ca.CategoryVariantID = p.CategoryVariantID\n"
                    + "INNER JOIN CategoryProduct cp ON cp.CategoryID = ca.CategoryVariantID\n";

            PreparedStatement stm = conn.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CategoryProduct cp = new CategoryProduct(rs.getInt("CategoryID"),
                        rs.getString("category_name"), rs.getString("category_description"), rs.getString("image"));
                CategoryVariant cv = new CategoryVariant(1,cp,null);
                
                Product p = new Product(rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("original_price"),
                        rs.getDouble("sale_price"),
                        rs.getString("product_description"),
                        rs.getString("brief_information"), cv);
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

    public static void main(String[] args) {
        DaoProduct pv = new DaoProduct();
        System.out.println(pv.getColorProduct(3));
    }
}
