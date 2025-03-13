/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.ProductDTO;
import DTO.ProductVariantDTO;
import Model.CategoryProduct;
import Model.Color;
import Model.Product;
import Model.ProductVariant;
import Model.Size;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
                    + "INNER JOIN CategoryProduct cp ON cp.CategoryID = p.CategoryProductID\n"
                    + "WHERE p.ProductID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, id);
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

    public void updateProduct(int product_id, String product_name, double original_price, double sale_price, String product_description, String brief_information, int CategoryProductID) {
        try {
            String query = "UPDATE Product SET ProductName = ?, original_price = ?, sale_price = ?, product_description = ?, "
                    + "brief_information = ? , CategoryProductID = ? WHERE ProductID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, product_name);
            stm.setFloat(2, (float) original_price);
            stm.setFloat(3, (float) sale_price);
            stm.setString(4, product_description);
            stm.setString(5, brief_information);
            stm.setInt(6, CategoryProductID);
            stm.setInt(7, product_id);
            stm.executeUpdate();
        } catch (Exception e) {
        }
    }

    public void updateProduct1(int product_id, int SizeId, int Color_id, int Stock, String ImageURL) {
        try {
            String query = "UPDATE ProductVariant SET  SizeID = ?, ColorID = ?, Stock = ?, "
                    + " ImageURL = ? WHERE ProductID = ? and SizeID = ? and  ColorID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, SizeId);
            stm.setInt(2, Color_id);
            stm.setInt(3, Stock);
            stm.setString(4, ImageURL);
            stm.setInt(5, product_id);
            stm.setInt(6, SizeId);
            stm.setInt(7, Color_id);

            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean Check1(int product_id, int SizeId, int Color_id) {
        String query = "SELECT * FROM ProductVariant WHERE ProductID = ? AND SizeID = ? AND ColorID = ? ";
        try (PreparedStatement stm = conn.prepareStatement(query)) {
            stm.setInt(1, product_id);
            stm.setInt(2, SizeId);
            stm.setInt(3, Color_id);
            try (ResultSet rs = stm.executeQuery()) {
                return rs.next(); // Nếu có dữ liệu, trả về true
            }
        } catch (Exception e) {
            e.printStackTrace(); // Hiển thị lỗi
            return false; // Xử lý lỗi bằng cách trả về false
        }
    }

    public List<Map<String, String>> getProductImages(int product_id) {
        String query = "SELECT ColorID, MIN(ImageURL) AS ImageURL FROM ProductVariant WHERE ProductID = ? GROUP BY ColorID ";
        List<Map<String, String>> images = new ArrayList<>();

        try (PreparedStatement stm = conn.prepareStatement(query)) {
            stm.setInt(1, product_id);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Map<String, String> imageData = new HashMap<>();
                    imageData.put("ImageURL", rs.getString("ImageURL"));
                    imageData.put("ColorID", String.valueOf(rs.getInt("ColorID")));
                    images.add(imageData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Ghi log lỗi
        }

        return images; // Trả về danh sách ảnh và màu
    }

    public void addProduct(String product_name, double original_price, double sale_price, String product_description, String brief_information, int CategoryVariantID) {
        try {
            String query = "INSERT INTO Product(\n"
                    + "    ProductName,\n"
                    + "    original_price,\n"
                    + "    sale_price,\n"
                    + "    product_description,\n"
                    + "    brief_information,\n"
                    + "    CategoryProductID\n"
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

    public void addProductVariant(int ProductID, int SizeID, int ColorID, int Stock, String ImageURL) {
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
            stm.setInt(2, SizeID);
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
            String query = "select distinct pv.ProductID, pv.SizeID, s.SizeName from ProductVariant pv "
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
                        0,
                        null
                );
                pv.add(pv1);
            }
        } catch (SQLException e) {
            System.out.print(e);
        }
        return pv;

    }

    public List<ProductVariant> getSizeColorStockProduct(int id) {
        List<ProductVariant> pv = new ArrayList<>();
        try {
            String query = "select ColorID, SizeID, Stock from ProductVariant where ProductID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CategoryProduct cp = new CategoryProduct();
                Product p = new Product();
                Size s = new Size(rs.getInt("SizeID"), null, cp);
                Color c = new Color(rs.getInt("ColorID"), null);
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
            String query = "SELECT DISTINCT pv.ColorID, c.ColorName, pv.SizeID, pv.Stock FROM ProductVariant pv INNER JOIN Color c ON pv.ColorID = c.ColorID WHERE pv.ProductID = ?";
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

    public List<ProductVariant> getColorProduct1(int id) {
        List<ProductVariant> pv = new ArrayList<>();
        try {
            String query = "SELECT DISTINCT pv.ColorID, c.ColorName FROM ProductVariant pv INNER JOIN Color c ON pv.ColorID = c.ColorID WHERE pv.ProductID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CategoryProduct cp = new CategoryProduct();
                Product p = new Product();
                Size s = new Size(1, null, cp);
                Color c = new Color(rs.getInt("ColorID"), rs.getString("ColorName"));
                ProductVariant pv1 = new ProductVariant(
                        0,
                        p,
                        s,
                        c,
                        0,
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
                    + "INNER JOIN CategoryProduct cp ON cp.CategoryID = p.CategoryProductID\n";
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

    public List<ProductVariant> getProductReleted(int pid) {
        List<ProductVariant> product = new ArrayList();
        try {
            String query = "SELECT top 4 p.ProductID, p.ProductName, p.original_price, p.sale_price, \n"
                    + "       p.product_description, p.brief_information, MIN(pv.ImageURL) AS ImageURL\n"
                    + "FROM Product p\n"
                    + "INNER JOIN CategoryProduct cp ON p.CategoryProductID = cp.CategoryID\n"
                    + "INNER JOIN ProductVariant pv ON pv.ProductID = p.ProductID\n"
                    + "WHERE p.CategoryProductID = (SELECT CategoryProductID FROM Product WHERE ProductID = ?)\n"
                    + "AND p.ProductID <> " + pid + "\n"
                    + "GROUP BY p.ProductID, p.ProductName, p.original_price, p.sale_price, \n"
                    + "         p.product_description, p.brief_information;  ";
            PreparedStatement stm = conn.prepareStatement(query);
            System.out.println(query);
            stm.setInt(1, pid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CategoryProduct cp = new CategoryProduct();
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
                    + "INNER JOIN CategoryProduct cp ON cp.CategoryID = p.[CategoryProductID]\n";

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

    public List<Product> getProduct1() {
        List<Product> product = new ArrayList();
        try {
            String query = "Select * from Product";

            PreparedStatement stm = conn.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CategoryProduct cp = new CategoryProduct();

                Product p = new Product(rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("original_price"),
                        rs.getDouble("sale_price"),
                        rs.getString("product_description"),
                        rs.getString("brief_information"), cp);

                product.add(p);
            }
        } catch (SQLException e) {
            System.out.print(e);
        }
        return product;
    }

    public List<Product> findProductsByIds(List<Integer> ids) {
        List<Product> products = new ArrayList<>();
        if (ids == null || ids.isEmpty()) {
            return products;
        }
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            String placeholders = String.join(",", Collections.nCopies(ids.size(), "?"));
            String sql = "SELECT p.ProductID,\n"
                    + "       p.ProductName,\n"
                    + "       p.original_price,\n"
                    + "       p.sale_price,\n"
                    + "       p.product_description,\n"
                    + "       p.brief_information,\n"
                    + "       c.CategoryID,\n"
                    + "       c.category_name,\n"
                    + "       c.category_description,\n"
                    + "       c.image\n"
                    + "FROM [SWP391].[dbo].[Product] p\n"
                    + "INNER JOIN [SWP391].[dbo].[CategoryProduct] c \n"
                    + "    ON p.CategoryProductID = c.CategoryID\n"
                    + "WHERE p.ProductID IN (" + placeholders + ")";

            preparedStatement = conn.prepareStatement(sql);
            for (int i = 0; i < ids.size(); i++) {
                preparedStatement.setInt(i + 1, ids.get(i));
            }

            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProduct_ID(rs.getInt("ProductID"));
                product.setProduct_name(rs.getString("ProductName"));
                product.setOriginal_Price(rs.getDouble("original_price"));
                product.setSale_price(rs.getDouble("sale_price"));
                product.setProduct_description(rs.getString("product_description"));
                product.setBrief_information(rs.getString("brief_information"));

                CategoryProduct category = new CategoryProduct();
                category.setCategory_productID(rs.getInt("CategoryID"));
                category.setCategory_name(rs.getString("category_name"));
                category.setCategory_description(rs.getString("category_description"));
                category.setImage(rs.getString("image"));

                product.setCt(category);

                products.add(product);
            }
        } catch (SQLException ex) {
            System.out.println("Error at findProductsByIds: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error closing resources: " + ex.getMessage());
            }
        }
        return products;
    }

    public ProductDTO getListProductById(int id) {
        String sql = """
             SELECT 
                 p.ProductID,
                 p.ProductName,
                 p.original_price,
                 p.sale_price,
                 p.product_description,
                 p.brief_information,
                 s.SizeID,
                 s.SizeName,
                 c.ColorID,
                 c.ColorName,
                 pv.ImageURL,
                 pv.Stock
             FROM 
                 ProductVariant pv
             INNER JOIN 
                 Product p ON pv.ProductID = p.ProductID
             INNER JOIN 
                 Size s ON pv.SizeID = s.SizeID
             INNER JOIN 
                 Color c ON pv.ColorID = c.ColorID
             WHERE 
                 p.ProductID = ?
             """;

        ProductDTO product = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (product == null) {
                    product = new ProductDTO();
                    product.setProductId(rs.getInt("ProductID"));
                    product.setProductName(rs.getString("ProductName"));
                    product.setOriginalPrice(rs.getDouble("original_price"));
                    product.setSalePrice(rs.getDouble("sale_price"));
                    product.setProductDescription(rs.getString("product_description"));
                    product.setBriefInformation(rs.getString("brief_information"));
                    product.setImageUrl(rs.getString("ImageURL"));
                    product.setQuantity(rs.getInt("Stock"));
                }
                Size size = new Size();
                size.setSize_id(rs.getInt("SizeID"));
                size.setSize_name(rs.getString("SizeName"));
                if (!containsSize(product.getSizes(), size.getSize_id())) {
                    product.getSizes().add(size);
                }
                Color color = new Color();
                color.setColor_id(rs.getInt("ColorID"));
                color.setColor_Name(rs.getString("ColorName"));
                if (!containsColor(product.getColors(), color.getColor_id())) {
                    product.getColors().add(color);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return product;
    }

    private boolean containsSize(List<Size> sizes, int sizeId) {
        return sizes.stream().anyMatch(s -> s.getSize_id() == sizeId);
    }

    private boolean containsColor(List<Color> colors, int colorId) {
        return colors.stream().anyMatch(c -> c.getColor_id() == colorId);
    }
   public List<ProductVariantDTO> getVariantsByProductId(int productId) {
        List<ProductVariantDTO> variants = new ArrayList<>();
        String sql = "SELECT VariantID, ProductID, SizeID, ColorID, Stock, ImageURL " +
                    "FROM ProductVariant WHERE ProductID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                variants.add(new ProductVariantDTO(
                    rs.getInt("VariantID"),
                    rs.getInt("ProductID"),
                    rs.getInt("SizeID"),
                    rs.getInt("ColorID"),
                    rs.getInt("Stock"),
                    rs.getString("ImageURL")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return variants;
    }
   public boolean updateStock(int variantId, int newStock) {
        String sql = "UPDATE ProductVariant SET Stock = ? WHERE VariantID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, newStock);
            ps.setInt(2, variantId);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        DaoProduct pv = new DaoProduct();
        List<Integer> names = Arrays.asList(2);       
        names.stream().forEach(name -> {
            System.out.println(pv.getListProductById(name).getQuantity());
        });
    }
}
