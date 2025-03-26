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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Statement;

/**
 *
 * @author phuan
 */
public class DaoProduct extends DBContext {

    private final DBContext dbContext;
    private final Connection connection;

    public DaoProduct() {

        dbContext = new DBContext();
        connection = dbContext.conn;
    }

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
                    + "       p.create_date, p.import_price, p.status, \n"
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
                CategoryProduct cp = new CategoryProduct(
                        rs.getInt("CategoryID"),
                        rs.getString("category_name"),
                        rs.getString("category_description"),
                        rs.getString("image")
                );

                Product p = new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("original_price"),
                        rs.getDouble("sale_price"),
                        rs.getString("product_description"),
                        rs.getString("brief_information"),
                        cp,
                        rs.getDate("create_date"),
                        rs.getDouble("import_price"),
                        rs.getBoolean("status")
                );

                Size s = new Size(); // Có thể lấy thêm thông tin nếu cần
                Color c = new Color(); // Có thể lấy thêm thông tin nếu cần
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

    public Product getProductByProductId(int productId) {
        String query = "SELECT * FROM Product WHERE ProductID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, productId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Product product = new Product();
                    product.setProduct_ID(rs.getInt("ProductID"));
                    product.setProduct_name(rs.getString("ProductName"));
                    product.setOriginal_price(rs.getDouble("original_price"));
                    product.setProduct_description(rs.getString("product_description"));
                    product.setBrief_information(rs.getString("brief_information"));

                    // Lấy category bằng CategoryProductDAO
                    int categoryId = rs.getInt("CategoryProductID");
                    DaoCategoryProduct categoryDAO = new DaoCategoryProduct();
                    CategoryProduct category = categoryDAO.getCategoryById(categoryId);
                    product.setCategory(category);

                    product.setCreateDate(rs.getDate("CreateDate"));
                    product.setImport_price(rs.getDouble("import_price"));
                    product.setStatus(rs.getBoolean("status"));
                    return product;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ProductVariant> getProductVariantsByProductId(int productId) {
        List<ProductVariant> variants = new ArrayList<>();
        String query = "SELECT * FROM ProductVariant WHERE ProductID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, productId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ProductVariant variant = new ProductVariant();
                    variant.setVariantID(rs.getInt("ProductVariantID"));

                    // Lấy Product đầy đủ
                    DaoProduct productDAO = new DaoProduct();
                    Product product = productDAO.getProductByProductId(rs.getInt("ProductID"));
                    variant.setProduct(product);

                    // Lấy Size đầy đủ
                    DaoSize sizeDAO = new DaoSize();
                    Size size = sizeDAO.getSizeById(rs.getInt("SizeID"));
                    variant.setSize(size);

                    // Lấy Color đầy đủ
                    DaoColor colorDAO = new DaoColor();
                    Color color = colorDAO.getColorById(rs.getInt("ColorID"));
                    variant.setColor(color);

                    variant.setStock(rs.getInt("Stock"));
                    variant.setImageURL(rs.getString("ImageURL"));
                    variants.add(variant);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return variants;
    }

    public int addProductReturnId(String productName, double originalPrice, String productDescription, String briefInformation, int categoryId, double importPrice, boolean status) {
        String query = "INSERT INTO product (ProductName, original_price, product_description, brief_information, CategoryProductID, CreateDate, import_price, status) "
                + "VALUES (?, ?, ?, ?, ?, GETDATE(), ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, productName);
            pstmt.setDouble(2, originalPrice);
            pstmt.setString(3, productDescription);
            pstmt.setString(4, briefInformation);
            pstmt.setInt(5, categoryId);
            pstmt.setDouble(6, importPrice);
            pstmt.setBoolean(7, status);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

// Thêm biến thể sản phẩm
    public void addProductVariant(int productId, int sizeId, int colorId, int stock, String imageURL) {
        String query = "INSERT INTO ProductVariant (ProductID, SizeID, ColorID, Stock, ImageURL) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stm = connection.prepareStatement(query)) {
            stm.setInt(1, productId);
            stm.setInt(2, sizeId);
            stm.setInt(3, colorId);
            stm.setInt(4, stock);
            stm.setString(5, imageURL);

            int rowsInserted = stm.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Product variant added successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int addProduct(String productName, double originalPrice, String productDescription, String briefInformation, int categoryId, double importPrice, boolean status) {
        String query = "INSERT INTO product (ProductName, original_price, product_description, brief_information, CategoryProductID, CreateDate, import_price, status) "
                + "VALUES (?, ?, ?, ?, ?, GETDATE(), ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, productName);
            pstmt.setDouble(2, originalPrice);
            pstmt.setString(3, productDescription);
            pstmt.setString(4, briefInformation);
            pstmt.setInt(5, categoryId);
            pstmt.setDouble(6, importPrice);
            pstmt.setBoolean(7, status);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean updateProduct(int productId, String productName, double originalPrice, String productDescription, String briefInformation, int categoryId, double importPrice, boolean status) {
        String query = "UPDATE product SET ProductName = ?, original_price = ?, product_description = ?, brief_information = ?, CategoryProductID = ?, import_price = ?, status = ? WHERE ProductID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, productName);
            pstmt.setDouble(2, originalPrice);
            pstmt.setString(3, productDescription);
            pstmt.setString(4, briefInformation);
            pstmt.setInt(5, categoryId);
            pstmt.setDouble(6, importPrice);
            pstmt.setBoolean(7, status);
            pstmt.setInt(8, productId);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deleteProductVariantsByProductId(int productId) {
        String query = "DELETE FROM ProductVariant WHERE ProductID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, productId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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

    public static void main(String[] args) {
        DaoProduct pv = new DaoProduct();
        System.out.println(pv.getProductReleted(66));
    }
}
