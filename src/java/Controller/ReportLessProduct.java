/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.OrderDAO;
import DTO.LessProductsDTO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author d
 */
public class ReportLessProduct extends HttpServlet {
   
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final OrderDAO daoOrder = new OrderDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String startDateParam = request.getParameter("startDate");
        String endDateParam = request.getParameter("endDate");
        
        Date startDate = (startDateParam != null) ? Date.valueOf(startDateParam) : Date.valueOf(LocalDate.now());
        Date endDate = (endDateParam != null) ? Date.valueOf(endDateParam) : Date.valueOf(LocalDate.now());
        
        List<LessProductsDTO> products = daoOrder.getTopProducts(startDate, endDate);     
        request.setAttribute("products", products);
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.getRequestDispatcher("../../Views/ReportLessProduct.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
