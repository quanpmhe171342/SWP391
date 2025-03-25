package UserController;

import DAO.DaoCategoryProduct;
import DAO.DaoColor;
import DAO.DaoProduct;
import DAO.DaoSize;
import Model.CategoryProduct;
import Model.Color;
import Model.ProductVariant;
import Model.Size;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class AddProductController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        DaoCategoryProduct categoryDAO = new DaoCategoryProduct();
        List<CategoryProduct> categoryProducts = categoryDAO.getCateProduct();

        DaoSize sizeDAO = new DaoSize();
        List<Size> sizes = sizeDAO.getSize(1);

        DaoColor colorDAO = new DaoColor();
        List<Color> colors = colorDAO.getColor();

        request.setAttribute("categories", categoryProducts);
        request.setAttribute("sizes", sizes);
        request.setAttribute("colors", colors);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/auth/addproduct.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // Lấy các tham số từ request
        String productName = request.getParameter("product_name");
        String originalPriceParam = request.getParameter("original_price");
        String productDescription = request.getParameter("description");
        String briefInformation = request.getParameter("information");
        String categoryIdParam = request.getParameter("category_id");
        String importPriceParam = request.getParameter("import_price");
        String statusParam = request.getParameter("status");

        // Chuyển đổi giá trị originalPrice và importPrice
        double originalPrice = 0;
        if (originalPriceParam != null && !originalPriceParam.trim().isEmpty()) {
            try {
                originalPrice = Double.parseDouble(originalPriceParam.trim());
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Giá bán phải là số hợp lệ.");
                doGet(request, response);
                return;
            }
        } else {
            request.setAttribute("error", "Giá bán không thể để trống.");
            doGet(request, response);
            return;
        }

        double importPrice = 0;
        if (importPriceParam != null && !importPriceParam.trim().isEmpty()) {
            try {
                importPrice = Double.parseDouble(importPriceParam.trim());
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Giá nhập phải là số hợp lệ.");
                doGet(request, response);
                return;
            }
        } else {
            request.setAttribute("error", "Giá nhập không thể để trống.");
            doGet(request, response);
            return;
        }

        int categoryId = 0;
        if (categoryIdParam != null && !categoryIdParam.trim().isEmpty()) {
            try {
                categoryId = Integer.parseInt(categoryIdParam.trim());
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Danh mục không hợp lệ.");
                doGet(request, response);
                return;
            }
        } else {
            request.setAttribute("error", "Danh mục không thể để trống.");
            doGet(request, response);
            return;
        }

        boolean status = false;
        if (statusParam != null && !statusParam.trim().isEmpty()) {
            try {
                status = Integer.parseInt(statusParam.trim()) == 1;
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Trạng thái không hợp lệ.");
                doGet(request, response);
                return;
            }
        } else {
            request.setAttribute("error", "Trạng thái không thể để trống.");
            doGet(request, response);
            return;
        }

        // Gọi DAO để thêm sản phẩm và lấy productId
        DaoProduct productDAO = new DaoProduct();
        int newProductId = productDAO.addProductReturnId(productName, originalPrice, productDescription, briefInformation, categoryId, importPrice, status);

        if (newProductId != -1) {
            // Xử lý các variants sản phẩm
            String[] sizes = request.getParameterValues("size");
            String[] colors = request.getParameterValues("color");
            String[] stocks = request.getParameterValues("stock");
            String[] imageURLs = request.getParameterValues("imageURL");

            if (sizes != null && colors != null && stocks != null && imageURLs != null) {
                for (int i = 0; i < sizes.length; i++) {
                    try {
                        int sizeId = Integer.parseInt(sizes[i].trim());
                        int colorId = Integer.parseInt(colors[i].trim());
                        int stock = Integer.parseInt(stocks[i].trim());
                        String imageURL = imageURLs[i].trim();

                        if (imageURL.isEmpty()) {
                            request.setAttribute("error", "URL hình ảnh không thể để trống.");
                            doGet(request, response);
                            return;
                        }

                        // Tạo đối tượng ProductVariant
                        ProductVariant productVariant = new ProductVariant();

                        // Gọi DAO để thêm ProductVariant
                        productDAO  .addProductVariant(newProductId, sizeId, colorId, stock, imageURL);
                    } catch (NumberFormatException e) {
                        request.setAttribute("error", "Thông tin size, color hoặc stock không hợp lệ.");
                        doGet(request, response);
                        return;
                    }
                }
            }

            // Thêm thông báo thành công
            request.setAttribute("success", "Sản phẩm đã được thêm thành công!");
            // Tiến hành forward lại đến trang addproduct.jsp để hiển thị thông báo
            doGet(request, response);
        } else {
            request.setAttribute("error", "Có lỗi xảy ra khi thêm sản phẩm.");
            doGet(request, response);
        }
    }
}
