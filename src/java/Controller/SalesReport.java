/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.OrderDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author d
 */
public class SalesReport extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final OrderDAO daoOrder = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
<<<<<<< HEAD:src/java/Controller/SalesReport.java
        String startDateParam = request.getParameter("startDate");
        String endDateParam = request.getParameter("endDate");
        Date startDate = (startDateParam != null) ? Date.valueOf(startDateParam) : Date.valueOf(LocalDate.now());
        Date endDate = (endDateParam != null) ? Date.valueOf(endDateParam) : Date.valueOf(LocalDate.now());
        var dailyReports = daoOrder.getRevenueByDate(startDate, endDate);
        request.setAttribute("dailyReports", dailyReports);
        request.getRequestDispatcher("../Views/SaleReport.jsp").forward(request, response);
=======
        HttpSession session = request.getSession(false); 
        
        if (session != null) {
            session.invalidate(); 
        }

//        chuyển hướng
        response.sendRedirect(request.getContextPath() + "/login");
>>>>>>> 612670468b8e97480829caa20b45e30aafe3dc05:src/java/UserController/LogoutController.java
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
