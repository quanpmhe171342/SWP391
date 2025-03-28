/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

<<<<<<< HEAD
import DAO.OrderDAO;
=======
import DAO.DashboarDAO;
>>>>>>> 612670468b8e97480829caa20b45e30aafe3dc05
import DTO.ReportProductDTO;
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
public class ReportProduct extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String startDateParam = request.getParameter("startDate");
        String endDateParam = request.getParameter("endDate");
        Date startDate = (startDateParam != null) ? Date.valueOf(startDateParam) : Date.valueOf(LocalDate.now());
        Date endDate = (endDateParam != null) ? Date.valueOf(endDateParam) : Date.valueOf(LocalDate.now());
<<<<<<< HEAD
        List<ReportProductDTO> reports = daoOrder.getReportProduct(startDate, endDate);
=======
        List<ReportProductDTO> reports = daDAO.getReportProduct(startDate, endDate);
>>>>>>> 612670468b8e97480829caa20b45e30aafe3dc05
        Map<String, Map<String, Object>> dailyStats = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        reports.forEach(report -> {
            String dateStr = formatter.format(report.getPeriod());
            Map<String, Object> stats = dailyStats.computeIfAbsent(dateStr, k -> new HashMap<>());
            stats.put("totalSold", report.getTotalSold());
            stats.put("revenue", report.getRevenue());
            stats.put("productName", report.getProductName());
        });
        List<String> allDates = new ArrayList<>();
        List<Integer> allTotalSold = new ArrayList<>();
        List<Double> allRevenue = new ArrayList<>();
        List<String> allProductNames = new ArrayList<>();
        LocalDate start = startDate.toLocalDate();
        LocalDate end = endDate.toLocalDate();
        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            String dateStr = date.format(dtFormatter);
            allDates.add(dateStr);
<<<<<<< HEAD
=======

>>>>>>> 612670468b8e97480829caa20b45e30aafe3dc05
            Map<String, Object> dayStats = dailyStats.getOrDefault(dateStr, new HashMap<>());
            allTotalSold.add((Integer) dayStats.getOrDefault("totalSold", 0));
            allRevenue.add((Double) dayStats.getOrDefault("revenue", 0.0));
            allProductNames.add((String) dayStats.getOrDefault("productName", ""));
        }
        request.setAttribute("dates", allDates);
        request.setAttribute("totalSold", allTotalSold);
        request.setAttribute("revenue", allRevenue);
        request.setAttribute("productNames", allProductNames);
        request.setAttribute("reports", reports);
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.getRequestDispatcher("../Views/ReportProduct.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
<<<<<<< HEAD
    private final OrderDAO daoOrder = new OrderDAO();
=======
    private final DashboarDAO daDAO = new DashboarDAO();
>>>>>>> 612670468b8e97480829caa20b45e30aafe3dc05

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
