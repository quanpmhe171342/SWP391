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
        System.out.println("Fetching ProductVariants for ProductID: " + productId);  // Debug

        String query = "SELECT * FROM ProductVariant WHERE ProductID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, productId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ProductVariant variant = new ProductVariant();
                    variant.setVariantID(rs.getInt("VariantID"));  // Đảm bảo cột VariantID đúng tên

                    // Debug từng giá trị lấy được
                    System.out.println("VariantID: " + rs.getInt("VariantID"));
                    System.out.println("ProductID: " + rs.getInt("ProductID"));
                    System.out.println("SizeID: " + rs.getInt("SizeID"));
                    System.out.println("ColorID: " + rs.getInt("ColorID"));
                    System.out.println("Stock: " + rs.getInt("Stock"));
                    System.out.println("ImageURL: " + rs.getString("ImageURL"));

                    // Lấy Product
                    DaoProduct productDAO = new DaoProduct();
                    Product product = productDAO.getProductByProductId(rs.getInt("ProductID"));
                    variant.setProduct(product);

                    // Lấy Size
                    DaoSize sizeDAO = new DaoSize();
                    Size size = sizeDAO.getSizeById(rs.getInt("SizeID"));
                    variant.setSize(size);

                    // Lấy Color
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

        System.out.println("Total variants found: " + variants.size());  // Debug
        return variants;
    }

    public Size getSizeById(int sizeId) {
        String query = "SELECT SizeID, SizeName, Category_Id FROM Size WHERE SizeID = ?";
        try (PreparedStatement stm = conn.prepareStatement(query)) {
            stm.setInt(1, sizeId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return new Size(
                            rs.getInt("SizeID"),
                            rs.getString("SizeName"),
                            rs.getInt("Category_Id")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Color getColorById(int colorId) {
        String query = "SELECT ColorID, ColorName FROM Color WHERE ColorID = ?";
        try (PreparedStatement stm = conn.prepareStatement(query)) {
            stm.setInt(1, colorId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return new Color(
                            rs.getInt("ColorID"),
                            rs.getString("ColorName")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT "
                + "p.ProductID, p.ProductName, p.original_price, p.import_price, p.CreateDate, p.Status, "
                + "p.product_description, p.brief_information, "
                + "c.CategoryID, c.category_name, "
                + "pv.Stock, "
                + "s.SizeID, COALESCE(s.SizeName, 'Không có') AS SizeName, "
                + "cl.ColorID, COALESCE(cl.ColorName, 'Không có') AS ColorName, "
                + "(SELECT TOP 1 ImageURL FROM ProductVariant WHERE ProductID = p.ProductID ORDER BY VariantID ASC) AS ImageURL "
                + "FROM Product p "
                + "JOIN CategoryProduct c ON p.CategoryProductID = c.CategoryID "
                + "LEFT JOIN ProductVariant pv ON p.ProductID = pv.ProductID "
                + "LEFT JOIN Size s ON pv.SizeID = s.SizeID "
                + "LEFT JOIN Color cl ON pv.ColorID = cl.ColorID";

        try (PreparedStatement pstmt = connection.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {

            Map<Integer, Product> productMap = new HashMap<>(); // Dùng Map để tránh trùng sản phẩm

            while (rs.next()) {
                int productId = rs.getInt("ProductID");

                // Kiểm tra nếu sản phẩm đã tồn tại trong danh sách hay chưa
                Product product = productMap.get(productId);
                if (product == null) {
                    CategoryProduct category = new CategoryProduct(
                            rs.getInt("CategoryID"),
                            rs.getString("category_name"),
                            "", "" // Các trường khác nếu cần
                    );

                    product = new Product();
                    product.setProduct_ID(productId);
                    product.setProduct_name(rs.getString("ProductName"));
                    product.setOriginal_price(rs.getDouble("original_price"));
                    product.setImport_price(rs.getDouble("import_price"));
                    product.setCreateDate(rs.getDate("CreateDate"));
                    product.setCategory(category);
                    product.setImageURL(rs.getString("ImageURL"));
                    product.setVariants(new ArrayList<>());
                    product.setStatus(rs.getInt("Status") == 1);
                    product.setProduct_description(rs.getString("product_description"));
                    product.setBrief_information(rs.getString("brief_information"));

                    productMap.put(productId, product);
                }

                // Kiểm tra nếu có SizeID và ColorID hợp lệ trước khi tạo đối tượng
                Size size = null;
                if (rs.getObject("SizeID") != null) { // Kiểm tra nếu không phải NULL
                    size = new Size(rs.getInt("SizeID"), rs.getString("SizeName"));
                }

                Color color = null;
                if (rs.getObject("ColorID") != null) { // Kiểm tra nếu không phải NULL
                    color = new Color(rs.getInt("ColorID"), rs.getString("ColorName"));
                }

                // Lưu thông tin ProductVariant
                ProductVariant variant = new ProductVariant();
                variant.setSize(size);
                variant.setColor(color);
                variant.setStock(rs.getInt("Stock"));

                product.getVariants().add(variant); // Thêm vào danh sách biến thể
            }

            products.addAll(productMap.values()); // Thêm tất cả sản phẩm vào danh sách

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Map<Integer, String> getProductImages() {
        Map<Integer, String> productImages = new HashMap<>();
        String query = "SELECT ProductID, ImageURL "
                + "FROM ProductVariant "
                + "WHERE VariantID IN (SELECT MIN(VariantID) FROM ProductVariant GROUP BY ProductID)";
        try (PreparedStatement pstmt = connection.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                productImages.put(rs.getInt("ProductID"), rs.getString("ImageURL"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productImages;
    }

    public boolean updateOrInsertProductVariant(int productId, int sizeId, int colorId, int stock, String imageURL) {

        String checkQuery = "SELECT COUNT(*) FROM ProductVariant WHERE ProductID = ? AND SizeID = ? AND ColorID = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, productId);
            checkStmt.setInt(2, sizeId);
            checkStmt.setInt(3, colorId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) == 0) {
                // Nếu không tồn tại -> INSERT
                String insertQuery = "INSERT INTO ProductVariant (ProductID, SizeID, ColorID, Stock, ImageURL) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                    insertStmt.setInt(1, productId);
                    insertStmt.setInt(2, sizeId);
                    insertStmt.setInt(3, colorId);
                    insertStmt.setInt(4, stock);
                    insertStmt.setString(5, imageURL);
                    int rowsInserted = insertStmt.executeUpdate();
                    return rowsInserted > 0;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateProductVariant(productId, sizeId, colorId, stock, imageURL);
    }

    private boolean updateProductVariant(int productId, int sizeId, int colorId, int stock, String imageURL) {
        String updateQuery = "UPDATE ProductVariant SET Stock = ?, ImageURL = ? WHERE ProductID = ? AND SizeID = ? AND ColorID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            pstmt.setInt(1, stock);
            pstmt.setString(2, imageURL);
            pstmt.setInt(3, productId);
            pstmt.setInt(4, sizeId);
            pstmt.setInt(5, colorId);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteProductVariant(int productId, int sizeId, int colorId) {
        String query = "DELETE FROM ProductVariant WHERE ProductID = ? AND SizeID = ? AND ColorID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, productId);
            pstmt.setInt(2, sizeId);
            pstmt.setInt(3, colorId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Product> getFilteredProducts(String category, String status, String sortPrice, String sortStock) {
        List<Product> products = new ArrayList<>();
        Map<Integer, Product> productMap = new HashMap<>();

        // Câu lệnh truy vấn SQL, đảm bảo lấy đầy đủ thông tin về category
        StringBuilder query = new StringBuilder("SELECT "
                + "p.ProductID, p.ProductName, p.original_price, p.import_price, p.CreateDate, p.Status, "
                + "p.product_description, p.brief_information, "
                + "c.categoryID, c.category_name, "
                + "pv.Stock, "
                + "s.SizeID, COALESCE(s.SizeName, 'Không có') AS SizeName, "
                + "cl.ColorID, COALESCE(cl.ColorName, 'Không có') AS ColorName, "
                + "(SELECT TOP 1 ImageURL FROM ProductVariant WHERE ProductID = p.ProductID ORDER BY VariantID ASC) AS ImageURL "
                + "FROM Product p "
                + "INNER JOIN CategoryProduct c ON p.CategoryProductID = c.categoryID "
                + "LEFT JOIN ProductVariant pv ON p.ProductID = pv.ProductID "
                + "LEFT JOIN Size s ON pv.SizeID = s.SizeID "
                + "LEFT JOIN Color cl ON pv.ColorID = cl.ColorID "
                + "WHERE 1=1 "); // Lọc tất cả sản phẩm

        // Lọc theo Danh mục
        if (category != null && !category.isEmpty()) {
            query.append(" AND c.CategoryID = ? ");
        }

        // Lọc theo Trạng thái
        if (status != null && !status.isEmpty()) {
            query.append(" AND p.Status = ? ");
        }

        // Điều kiện sắp xếp theo giá
        if (sortPrice != null && !sortPrice.isEmpty()) {
            if ("sellAsc".equals(sortPrice)) {
                query.append(" ORDER BY p.original_price ASC ");
                System.out.println("Sắp xếp theo giá bán tăng dần");
            } else if ("sellDesc".equals(sortPrice)) {
                query.append(" ORDER BY p.original_price DESC ");
                System.out.println("Sắp xếp theo giá bán giảm dần");
            } else if ("importAsc".equals(sortPrice)) {
                query.append(" ORDER BY p.import_price ASC ");
                System.out.println("Sắp xếp theo giá nhập tăng dần");
            } else if ("importDesc".equals(sortPrice)) {
                query.append(" ORDER BY p.import_price DESC ");
                System.out.println("Sắp xếp theo giá nhập giảm dần");
            }
        } else {
            System.out.println("Không có sắp xếp theo giá");
        }

        // Điều kiện sắp xếp theo số lượng
        if (sortStock != null && !sortStock.isEmpty()) {
            if (query.toString().contains("ORDER BY")) {
                // Nếu đã có ORDER BY, chỉ thêm điều kiện sắp xếp theo số lượng
                query.append(", pv.Stock " + ("asc".equals(sortStock) ? "ASC" : "DESC"));
            } else {
                // Nếu chưa có ORDER BY, thêm điều kiện sắp xếp theo số lượng
                query.append(" ORDER BY pv.Stock " + ("asc".equals(sortStock) ? "ASC" : "DESC"));
            }
        }

        // Thực thi truy vấn
        try (PreparedStatement pstmt = connection.prepareStatement(query.toString())) {
            int paramIndex = 1;

            // Set tham số lọc Danh mục
            if (category != null && !category.isEmpty()) {
                pstmt.setInt(paramIndex++, Integer.parseInt(category));
            }

            // Set tham số lọc Trạng thái
            if (status != null && !status.isEmpty()) {
                pstmt.setInt(paramIndex++, Integer.parseInt(status));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int productId = rs.getInt("ProductID");

                    // Kiểm tra nếu sản phẩm đã tồn tại trong map
                    Product product = productMap.get(productId);
                    if (product == null) {
                        // Lấy thông tin CategoryProduct
                        CategoryProduct categoryObj = new CategoryProduct(
                                rs.getInt("categoryID"),
                                rs.getString("category_name"),
                                "", "" // Nếu cần, bạn có thể lấy thêm thông tin từ category
                        );

                        // Tạo đối tượng Product mới
                        product = new Product();
                        product.setProduct_ID(productId);
                        product.setProduct_name(rs.getString("ProductName"));
                        product.setOriginal_price(rs.getDouble("original_price"));
                        product.setImport_price(rs.getDouble("import_price"));
                        product.setCreateDate(rs.getDate("CreateDate"));
                        product.setCategory(categoryObj);
                        product.setImageURL(rs.getString("ImageURL"));
                        product.setVariants(new ArrayList<>());
                        product.setStatus(rs.getInt("Status") == 1);
                        product.setProduct_description(rs.getString("product_description"));
                        product.setBrief_information(rs.getString("brief_information"));

                        productMap.put(productId, product);
                    }

                    // Lấy Size và Color nếu có
                    Size size = null;
                    if (rs.getObject("SizeID") != null) {
                        size = new Size(rs.getInt("SizeID"), rs.getString("SizeName"));
                    }

                    Color color = null;
                    if (rs.getObject("ColorID") != null) {
                        color = new Color(rs.getInt("ColorID"), rs.getString("ColorName"));
                    }

                    // Lưu thông tin ProductVariant
                    ProductVariant variant = new ProductVariant();
                    variant.setSize(size);
                    variant.setColor(color);
                    variant.setStock(rs.getInt("Stock"));

                    // Thêm variant vào sản phẩm
                    product.getVariants().add(variant);
                }

                // Thêm tất cả sản phẩm vào danh sách trả về
                products.addAll(productMap.values());

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
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
    public boolean addProductVariant(int productId, int sizeId, int colorId, int stock, String imageURL) {
        String query = "INSERT INTO ProductVariant (ProductID, SizeID, ColorID, Stock, ImageURL) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stm = connection.prepareStatement(query)) {
            stm.setInt(1, productId);
            stm.setInt(2, sizeId);
            stm.setInt(3, colorId);
            stm.setInt(4, stock);
            stm.setString(5, imageURL);

            int rowsInserted = stm.executeUpdate();
            return rowsInserted > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi
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

    public boolean updateProduct(int productId, String productName, double originalPrice,
            String productDescription, String briefInformation,
            int categoryId, double importPrice, boolean status) {
        String query = "UPDATE product SET ProductName = ?, original_price = ?, product_description = ?, "
                + "brief_information = ?, CategoryProductID = ?, import_price = ?, status = ? WHERE ProductID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, productName);
            pstmt.setDouble(2, originalPrice);
            pstmt.setString(3, productDescription);
            pstmt.setString(4, briefInformation);
            pstmt.setInt(5, categoryId);
            pstmt.setDouble(6, importPrice);
            pstmt.setInt(7, status ? 1 : 0);
            pstmt.setInt(8, productId);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Rows affected in Product update: " + rowsAffected);

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteProductAndVariants(int productId) {
        String deleteVariantsQuery = "DELETE FROM ProductVariant WHERE ProductID = ?";
        String deleteProductQuery = "DELETE FROM Product WHERE ProductID = ?";

        try {
            // Bắt đầu giao dịch để đảm bảo tính toàn vẹn dữ liệu
            connection.setAutoCommit(false);

            // Xoá các bản ghi liên quan trong ProductVariant trước
            try (PreparedStatement pstmtVariants = connection.prepareStatement(deleteVariantsQuery)) {
                pstmtVariants.setInt(1, productId);
                int rowsAffectedVariants = pstmtVariants.executeUpdate();
                if (rowsAffectedVariants == 0) {
                    throw new SQLException("Không tìm thấy ProductVariant để xoá.");
                }
            }

            // Sau đó xoá bản ghi trong Product
            try (PreparedStatement pstmtProduct = connection.prepareStatement(deleteProductQuery)) {
                pstmtProduct.setInt(1, productId);
                int rowsAffectedProduct = pstmtProduct.executeUpdate();
                if (rowsAffectedProduct == 0) {
                    throw new SQLException("Không tìm thấy sản phẩm để xoá.");
                }
            }

            // Cam kết giao dịch sau khi xoá thành công
            connection.commit();
            return true;

        } catch (SQLException e) {
            try {
                // Nếu có lỗi, rollback lại giao dịch
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true); // Đặt lại chế độ auto commit mặc định
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
