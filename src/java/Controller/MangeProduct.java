package Controller;

import DAO.DaoCategoryProduct;
import DAO.DaoColor;
import DAO.DaoProduct;
import DAO.DaoSize;
import Model.ProductVariant;
import Model.Size;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.*;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class MangeProduct extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(MangeProduct.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DaoProduct productDao = new DaoProduct();
        DaoCategoryProduct categoryDao = new DaoCategoryProduct();
        DaoSize sizeDao = new DaoSize();
        DaoColor colorDao = new DaoColor();

        String action = request.getParameter("action");

        if (action != null) {
            if (action.equalsIgnoreCase("addproduct")) {
                request.setAttribute("cate", categoryDao.getCateProduct());
                request.setAttribute("sizes", sizeDao.getSize(1));
                request.setAttribute("color", colorDao.getColor());
                request.getRequestDispatcher("Views/AddProduct.jsp").forward(request, response);
            }
            else if (action.equalsIgnoreCase("editproduct")) {
            request.setAttribute("product", productDao.getProductById(Integer.parseInt(request.getParameter("pid"))));
            request.setAttribute("cate", categoryDao.getCateProduct());
            request.setAttribute("sizes", sizeDao.getSize(1));
            request.setAttribute("colorProduct", productDao.getColorProduct(Integer.parseInt(request.getParameter("pid"))));
            request.setAttribute("color", colorDao.getColor());
            
            request.getRequestDispatcher("Views/EditProduct.jsp").forward(request, response);
        } 
             else if (action.equalsIgnoreCase("deleteproduct")) {
                  productDao.DeleteProductV(Integer.parseInt(request.getParameter("pid")));
               productDao.DeleteProduct(Integer.parseInt(request.getParameter("pid")));
           response.sendRedirect("MangeProduct");

             }
        }
        else {
            request.setAttribute("Products", productDao.getProduct());
            request.getRequestDispatcher("Views/ManageProduct.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        DaoProduct productDao = new DaoProduct();

        if (action != null && action.equalsIgnoreCase("addProduct")) {
            try {
                // Parse product details
                String productName = request.getParameter("productName");
                String description = request.getParameter("description");
                String brief = request.getParameter("brief");
                String categoryId = request.getParameter("category");
                double originalPrice = Double.parseDouble(request.getParameter("originalPrice"));
                double salePrice = Double.parseDouble(request.getParameter("salePrice"));

                // Add base product
                productDao.addProduct(productName, originalPrice, salePrice, description, brief, 1);

                // Process color and size variants
                String[] colors = request.getParameterValues("color");
                if (colors != null) {
                    for (String colorId : colors) {
                        // Sizes could be dynamically fetched from database
                        String[] sizes = {"1", "2", "3", "4", "5"};

                        for (String sizeId : sizes) {
                            String quantityParam = "quantity-" + colorId + "-" + sizeId;
                            String quantityValue = request.getParameter(quantityParam);

                            if (quantityValue != null && !quantityValue.isEmpty()) {
                                try {
                                    int quantity = Integer.parseInt(quantityValue);
                                    if (quantity > 0) {
                                        // Upload image for this color-size combination
                                        String imageUrl = uploadProductImage(
                                                request.getPart("image-" + colorId),
                                                productName,
                                                colorId
                                        );
                                        List<ProductVariant> products = productDao.getProduct();
                                        int maxProductId = 0;

                                        for (ProductVariant product : products) {
                                            if (product.getProduct().getProduct_ID() > maxProductId) {
                                                maxProductId = product.getProduct().getProduct_ID();
                                            }
                                        }                                        // Add product variant
                                        productDao.addProductVariant(
                                                maxProductId + 1,
                                                Integer.parseInt(sizeId),
                                                Integer.parseInt(colorId),
                                                quantity,
                                                imageUrl
                                        );
                                    }
                                } catch (NumberFormatException e) {
                                    LOGGER.log(Level.SEVERE, "Error parsing quantity", e);
                                }
                            }
                        }
                    }
                }

                // Redirect to product list or success page
                response.sendRedirect("MangeProduct?success=true");

            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error adding product", e);
                request.setAttribute("errorMessage", "Failed to add product: " + e.getMessage());
                request.getRequestDispatcher("Views/AddProduct.jsp").forward(request, response);
            }
        } 
        else if(action != null && action.equalsIgnoreCase("editProduct")){
             try {
                // Parse product details
                String productName = request.getParameter("productName");
                String description = request.getParameter("description");
                String brief = request.getParameter("brief");
                String categoryId = request.getParameter("category");
                double originalPrice = Double.parseDouble(request.getParameter("originalPrice"));
                double salePrice = Double.parseDouble(request.getParameter("salePrice"));

                // Add base product
                productDao.updateProduct(Integer.parseInt(request.getParameter("productID")),productName, originalPrice, salePrice, description, brief);

                // Process color and size variants
                String[] colors = request.getParameterValues("color");
                if (colors != null) {
                    for (String colorId : colors) {
                        // Sizes could be dynamically fetched from database
                        String[] sizes = {"1", "2", "3", "4", "5"};

                        for (String sizeId : sizes) {
                            String quantityParam = "quantity-" + colorId + "-" + sizeId;
                            String quantityValue = request.getParameter(quantityParam);

                            if (quantityValue != null && !quantityValue.isEmpty()) {
                                try {
                                    int quantity = Integer.parseInt(quantityValue);
                                    if (quantity > 0) {
                                        // Upload image for this color-size combination
                                        String imageUrl = uploadProductImage(
                                                request.getPart("image-" + colorId),
                                                productName,
                                                colorId
                                        );
                                                                            // Add product variant
                                        productDao.addProductVariant(
                                                Integer.parseInt(request.getParameter("productID")),
                                                Integer.parseInt(sizeId),
                                                Integer.parseInt(colorId),
                                                quantity,
                                                imageUrl
                                        );
                                    }
                                } catch (NumberFormatException e) {
                                    LOGGER.log(Level.SEVERE, "Error parsing quantity", e);
                                }
                            }
                        }
                    }
                }

                // Redirect to product list or success page
                response.sendRedirect("MangeProduct?success=true");

            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error adding product", e);
                request.setAttribute("errorMessage", "Failed to add product: " + e.getMessage());
               response.sendRedirect("MangeProduct?success=true");
            }
        }
        else {
            // Handle size retrieval for dynamic size loading
            handleSizeRetrieval(request, response);
        }
    }

    private void handleSizeRetrieval(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        DaoSize sizeDao = new DaoSize();
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String id = request.getParameter("categoryId");
            if (id == null || id.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\": \"Missing categoryId\"}");
                return;
            }

            int categoryId = Integer.parseInt(id);
            List<Size> sizes = sizeDao.getSize(categoryId);

            if (sizes.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\": \"No sizes found\"}");
            } else {
                String jsonResponse = gson.toJson(sizes);
                out.print(jsonResponse);
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"Invalid categoryId format\"}");
        } finally {
            out.flush();
        }
    }

    private String uploadProductImage(Part imagePart, String productName, String colorId) throws IOException {
        if (imagePart == null || imagePart.getSize() == 0) {
            return ""; // Return empty string if no file
        }

        // Get application path and create upload directory
        String applicationPath = getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + File.separator + "product-images";
        File uploadDir = new File(uploadFilePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Generate unique filename
        String originalFileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String newFileName = "product_" + productName + "_" + colorId + "_"
                + System.currentTimeMillis() + fileExtension;

        // Create file path
        File file = new File(uploadFilePath, newFileName);

        // Save file
        try (InputStream input = imagePart.getInputStream(); FileOutputStream output = new FileOutputStream(file)) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        }

        // Return relative path for database storage
        return "product-images" + File.separator + newFileName;
    }
}
