/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package UserController;

import DAO.DaoProduct;
import DAO.ProductStatisticsDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *
 * @author hieum
 */
public class ProductStatisticController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductStatisticsDAO dao = new ProductStatisticsDAO();

        int totalProducts = dao.getTotalProducts();
        Map<String, Integer> categoryStats  = dao.getProductsByCategory();
        int lowStockProducts = dao.getLowStockProducts();
        int newProductsInWeek = dao.getNewProductsInWeek();
        int newProductsInMonth = dao.getNewProductsInMonth();
        int allStock = dao.getAllStock();
        int productInStock = dao.getProductInStock();
        int outOfStock = dao.getOutOfStockProducts();

        request.setAttribute("totalProducts", totalProducts);
        request.setAttribute("productsByCategory", categoryStats );
        request.setAttribute("lowStockProducts", lowStockProducts);
        request.setAttribute("allStock", allStock);
        request.setAttribute("newProductsInMonth", newProductsInMonth);
        request.setAttribute("ProductInStock", productInStock);
        request.setAttribute("OutOfProduct", outOfStock);
        
        
        String filterType = request.getParameter("filterType");
        String selectedDate = request.getParameter("selectedDate"); // YYYY-MM-DD
        String selectedMonth = request.getParameter("selectedMonth"); // YYYY-MM

        DaoProduct productDAO = new DaoProduct();
        int totalSoldProducts = 0;
        double totalRevenue = 0;

        if ("day".equals(filterType) && selectedDate != null) {
            totalSoldProducts = productDAO.getTotalSoldByDay(selectedDate);
            totalRevenue = productDAO.getTotalRevenueByDay(selectedDate);
        } else if ("month".equals(filterType) && selectedMonth != null) {
            totalSoldProducts = productDAO.getTotalSoldByMonth(selectedMonth);
            totalRevenue = productDAO.getTotalRevenueByMonth(selectedMonth);
        }

        request.setAttribute("totalSoldProducts", totalSoldProducts);
        request.setAttribute("totalRevenue", totalRevenue);
        

        request.getRequestDispatcher("/auth/productstatistic.jsp").forward(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
