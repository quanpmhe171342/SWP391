/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.DashboarDAO;
import DTO.ReportCustomerDTO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author d
 */
public class UserReportt extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final DashboarDAO daDAO = new DashboarDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String startDateParam = request.getParameter("startDate");
        String endDateParam = request.getParameter("endDate");
        Date startDate = (startDateParam != null) ? Date.valueOf(startDateParam) : Date.valueOf(LocalDate.now());
        Date endDate = (endDateParam != null) ? Date.valueOf(endDateParam) : Date.valueOf(LocalDate.now());
        List<ReportCustomerDTO> userReport = daDAO.getNewCustomerStats(startDate, endDate);
        Map<String, Integer> dailyStats = new HashMap<>();
        userReport.forEach(stat -> {
            String dateStr = new SimpleDateFormat("dd/MM/yyyy").format(stat.getPeriod());
            dailyStats.put(dateStr, stat.getCustomers());
        });
        List<String> allDates = new ArrayList<>();
        List<Integer> allCustomers = new ArrayList<>();
        LocalDate start = startDate.toLocalDate();
        LocalDate end = endDate.toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            String dateStr = date.format(formatter);
            allDates.add(dateStr);
            allCustomers.add(dailyStats.getOrDefault(dateStr, 0));
        }
        request.setAttribute("dates", allDates);
        request.setAttribute("customers", allCustomers);
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.getRequestDispatcher("../Views/ReportUser.jsp").forward(request, response);
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
