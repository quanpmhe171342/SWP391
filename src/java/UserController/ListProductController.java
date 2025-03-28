package UserController;

import DAO.DaoCategoryProduct;
import DAO.DaoProduct;
import Model.CategoryProduct;
import Model.Product;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ListProductController", urlPatterns = {"/listproduct"})
public class ListProductController extends HttpServlet {

    private DaoProduct productDAO = new DaoProduct();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy tham số từ request
        String categoryId = request.getParameter("category_id");
        String status = request.getParameter("status");
        String sortPrice = request.getParameter("sortPrice");
        String sortStock = request.getParameter("sortStock");

        // Xử lý tham số rỗng để tránh lỗi SQL
        if (categoryId != null && categoryId.isEmpty()) {
            categoryId = null;
        }
        if (status != null && status.isEmpty()) {
            status = null;
        }
        if (sortPrice != null && sortPrice.isEmpty()) {
            sortPrice = null;
        }
        if (sortStock != null && sortStock.isEmpty()) {
            sortStock = null;
        }

        // Gọi phương thức getFilteredProducts với các tham số đã lấy từ request
        List<Product> productList = productDAO.getFilteredProducts(categoryId, status, sortPrice, sortStock);
        
        System.out.println("Số lượng sản phẩm trả về: " + productList.size());

        DaoCategoryProduct categoryDAO = new DaoCategoryProduct();
        // Lấy danh sách các danh mục từ database
        List<CategoryProduct> categories = categoryDAO.getCateProduct();

        // Truyền dữ liệu vào request
        request.setAttribute("categories", categories);
        request.setAttribute("category_id", categoryId); // Truyền lại category_id để tự động chọn trong dropdown
        request.setAttribute("productList", productList);

        // Forward đến JSP
        request.getRequestDispatcher("/auth/listproduct.jsp").forward(request, response);
        
        

    }

    @Override
    public String getServletInfo() {
        return "List all products";
    }
}
