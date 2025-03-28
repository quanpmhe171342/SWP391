package UserController;

import DAO.DaoCategoryProduct;
import DAO.DaoColor;
import DAO.DaoProduct;
import DAO.DaoSize;
import Model.CategoryProduct;
import Model.Color;
import Model.Product;
import Model.ProductVariant;
import Model.Size;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet xử lý cập nhật sản phẩm
 */
public class UpdateProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String productIdStr = request.getParameter("product_ID");
        if (productIdStr == null || productIdStr.isEmpty()) {
            request.setAttribute("error", "Thiếu mã sản phẩm.");
            forwardToUpdatePage(request, response);
            return;
        }

        try {
            int productId = Integer.parseInt(productIdStr);
            DaoProduct productDAO = new DaoProduct();
            Product product = productDAO.getProductByProductId(productId);

            // Lấy biến thể sản phẩm (chỉ lấy 1 biến thể)
            List<ProductVariant> variants = productDAO.getProductVariantsByProductId(productId);
            ProductVariant variant = (variants != null && !variants.isEmpty()) ? variants.get(0) : null;

            if (product == null) {
                request.setAttribute("error", "Không tìm thấy sản phẩm.");
            }

            DaoCategoryProduct categoryDAO = new DaoCategoryProduct();
            DaoSize sizeDAO = new DaoSize();
            DaoColor colorDAO = new DaoColor();
            int categoryId = product.getCategory().getCategory_productID();

            request.setAttribute("product", product);
            request.setAttribute("productVariant", variant);  // Truyền variant vào JSP
            request.setAttribute("categories", categoryDAO.getCateProduct());
            request.setAttribute("sizes", sizeDAO.getSize(categoryId));
            request.setAttribute("colors", colorDAO.getColor());

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Mã sản phẩm không hợp lệ.");
        }

        forwardToUpdatePage(request, response);
        System.out.println("Product ID from request: " + productIdStr);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try {
            // Lấy thông tin từ form
            String productIdStr = request.getParameter("product_ID");
            if (productIdStr == null || productIdStr.isEmpty()) {
                request.setAttribute("error", "Mã sản phẩm không hợp lệ.");
                forwardToUpdatePage(request, response);
                return;
            }

            int productId = Integer.parseInt(productIdStr);
            String productName = request.getParameter("product_name");
            double originalPrice = Double.parseDouble(request.getParameter("original_price"));
            String productDescription = request.getParameter("product_description") != null ? request.getParameter("product_description").trim() : "";
            String briefInformation = request.getParameter("brief_information") != null ? request.getParameter("brief_information").trim() : "";
            int categoryId = Integer.parseInt(request.getParameter("category_id"));
            double importPrice = Double.parseDouble(request.getParameter("import_price"));
            boolean status = Integer.parseInt(request.getParameter("status")) == 1;

            int newSizeId = Integer.parseInt(request.getParameter("size"));
            int newColorId = Integer.parseInt(request.getParameter("color"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            String imageURL = request.getParameter("imageURL") != null ? request.getParameter("imageURL").trim() : "";

            DaoProduct productDAO = new DaoProduct();

            // Kiểm tra các trường không hợp lệ
            if (productName == null || productName.trim().isEmpty() || originalPrice <= 0 || importPrice <= 0 || stock < 0) {
                request.setAttribute("error", "Các trường không hợp lệ. Vui lòng kiểm tra lại.");
                forwardToUpdatePage(request, response);
                return;
            }

            // Cập nhật sản phẩm
            boolean updatedProduct = productDAO.updateProduct(
                    productId, productName.trim(), originalPrice, productDescription, briefInformation, categoryId, importPrice, status
            );

            // Xử lý biến thể sản phẩm
            List<ProductVariant> variants = productDAO.getProductVariantsByProductId(productId);
            if (!variants.isEmpty()) {
                ProductVariant oldVariant = variants.get(0);

                int oldSizeId = oldVariant.getSize().getSizeID();
                int oldColorId = oldVariant.getColor().getColorID();

                if (oldSizeId != newSizeId || oldColorId != newColorId) {
                    productDAO.deleteProductVariant(productId, oldSizeId, oldColorId);
                }
            }

            // Cập nhật hoặc thêm mới biến thể sản phẩm
            boolean updatedVariant = productDAO.updateOrInsertProductVariant(productId, newSizeId, newColorId, stock, imageURL);

            // Kiểm tra kết quả
            if (updatedProduct && updatedVariant) {
                request.setAttribute("success", "Sản phẩm và biến thể đã được cập nhật thành công!");
            } else {
                request.setAttribute("error", "Có lỗi xảy ra khi cập nhật sản phẩm hoặc biến thể.");
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Dữ liệu nhập không hợp lệ.");
        }

        // Quay lại trang cập nhật sản phẩm
        doGet(request, response);
    }

    private void forwardToUpdatePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/auth/updateproduct.jsp");
        dispatcher.forward(request, response);
    }
}
