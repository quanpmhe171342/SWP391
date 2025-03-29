/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.OrderDAO;
import DTO.ReportStateDTO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author d
 */
public class ReportState extends HttpServlet {

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
        
        // Lấy ngày mặc định nếu không có tham số
        Date startDate = (startDateParam != null) ? Date.valueOf(startDateParam) : Date.valueOf(LocalDate.now());
        Date endDate = (endDateParam != null) ? Date.valueOf(endDateParam) : Date.valueOf(LocalDate.now());
        
        // Lấy dữ liệu thống kê
        List<ReportStateDTO> states = daoOrder.getListState(startDate, endDate);
        
        request.setAttribute("states", states);
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        
        request.getRequestDispatcher("../Views/ReportState.jsp").forward(request, response);
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
