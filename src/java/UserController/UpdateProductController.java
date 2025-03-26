/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package UserController;

import DAO.DaoCategoryProduct;
import DAO.DaoColor;
import DAO.DaoProduct;
import DAO.DaoSize;
import Model.CategoryProduct;
import Model.Color;
import Model.Size;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author hieum
 */
public class UpdateProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        int productId = Integer.parseInt(request.getParameter("productId"));

        DaoProduct productDAO = new DaoProduct();
        var product = productDAO.getProductByProductId(productId);
        var variants = productDAO.getProductVariantsByProductId(productId);

        DaoCategoryProduct categoryDAO = new DaoCategoryProduct();
        List<CategoryProduct> categoryProducts = categoryDAO.getCateProduct();

        DaoSize sizeDAO = new DaoSize();
        List<Size> sizes = sizeDAO.getSize(1);

        DaoColor colorDAO = new DaoColor();
        List<Color> colors = colorDAO.getColor();

        request.setAttribute("product", product);
        request.setAttribute("variants", variants);
        request.setAttribute("categories", categoryProducts);
        request.setAttribute("sizes", sizes);
        request.setAttribute("colors", colors);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/auth/updateproduct.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        int productId = Integer.parseInt(request.getParameter("productId"));
        String productName = request.getParameter("product_name");
        double originalPrice = Double.parseDouble(request.getParameter("original_price"));
        String productDescription = request.getParameter("description");
        String briefInformation = request.getParameter("information");
        int categoryId = Integer.parseInt(request.getParameter("category_id"));
        double importPrice = Double.parseDouble(request.getParameter("import_price"));
        boolean status = Integer.parseInt(request.getParameter("status")) == 1;

        DaoProduct productDAO = new DaoProduct();

        // Update product info
        boolean updated = productDAO.updateProduct(productId, productName, originalPrice, productDescription, briefInformation, categoryId, importPrice, status);

        if (updated) {
            // Delete old variants
            productDAO.deleteProductVariantsByProductId(productId);

            // Add new variants
            String[] sizes = request.getParameterValues("size");
            String[] colors = request.getParameterValues("color");
            String[] stocks = request.getParameterValues("stock");
            String[] imageURLs = request.getParameterValues("imageURL");

            if (sizes != null && colors != null && stocks != null && imageURLs != null) {
                for (int i = 0; i < sizes.length; i++) {
                    int sizeId = Integer.parseInt(sizes[i]);
                    int colorId = Integer.parseInt(colors[i]);
                    int stock = Integer.parseInt(stocks[i]);
                    String imageURL = imageURLs[i].trim();
                    productDAO.addProductVariant(productId, sizeId, colorId, stock, imageURL);
                }
            }

            request.setAttribute("success", "Sản phẩm đã được cập nhật thành công!");
        } else {
            request.setAttribute("error", "Có lỗi xảy ra khi cập nhật sản phẩm.");
        }

        // Load lại dữ liệu để hiển thị
        doGet(request, response);
    }
}
