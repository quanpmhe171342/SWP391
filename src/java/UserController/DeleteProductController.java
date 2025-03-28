/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package UserController;

import DAO.DaoProduct;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author hieum
 */
public class DeleteProductController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productIdStr = request.getParameter("product_ID");

        if (productIdStr != null && !productIdStr.isEmpty()) {
            int productId = Integer.parseInt(productIdStr);

           
            DaoProduct daoProduct = new DaoProduct();

            
            boolean success = daoProduct.deleteProductAndVariants(productId);

            if (success) {
               
                request.setAttribute("successMessage", "Sản phẩm đã được xoá thành công.");
                request.getRequestDispatcher("/auth/listproduct.jsp").forward(request, response);
            } else {
               
                request.setAttribute("errorMessage", "Không thể xoá sản phẩm.");
                request.getRequestDispatcher("/auth/listproduct.jsp").forward(request, response);
            }
        } else {
            
            request.getRequestDispatcher("/auth/listproduct.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
