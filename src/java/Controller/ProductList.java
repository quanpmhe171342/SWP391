/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.DaoCategoryProduct;
import DAO.DaoProduct;
import DAO.DaoSize;
import Model.ProductVariant;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author phuan
 */
public class ProductList extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProductList</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductList at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        DaoProduct db = new DaoProduct();
        DaoCategoryProduct db1 = new DaoCategoryProduct();
        String priceFilter = request.getParameter("priceFilter");
        String priceRange = request.getParameter("priceRange");
        HashMap<String, String> Noicau = new HashMap<String, String>();
        try {
            int category_id = Integer.parseInt(request.getParameter("cate"));
            Noicau.put("category", "Where cp.CategoryID = " + category_id);
        } catch (Exception e) {
            System.out.println(e);
        }
        
        if (priceRange != null) {
            System.out.println(priceRange);
            request.setAttribute("priceRange", priceRange);
            if (priceRange.contains("-")) {
                String[] parts = priceRange.split("-");
                int minPrice = Integer.parseInt(parts[0]);
                int maxPrice = Integer.parseInt(parts[1]);
                if (Noicau.isEmpty()) {
                    Noicau.put("priceRange", "Where p.original_price BETWEEN " + minPrice + " AND " + maxPrice);
                } else {
                    Noicau.put("priceRange", " and p.original_price BETWEEN " + minPrice + " AND " + maxPrice);
                }
            } else if (priceRange.contains("Isc")) {
                int minPrice = Integer.parseInt(priceRange.replace("Isc", ""));
                if (Noicau.isEmpty()) {
                    Noicau.put("priceRange", "Where p.original_price >= " + minPrice);
                } else {
                    Noicau.put("priceRange", " and p.original_price >=" + minPrice);
                }
            }
        }
        System.out.println(Noicau);
        if (priceFilter != null) {
            request.setAttribute("filteractive", priceFilter);
            String priceFilter1 = "ORDER BY p.original_price " + priceFilter;
            Noicau.put("pricesort", priceFilter1);
        }
        if (request.getParameter("cate") != null) {
            request.setAttribute("cate", request.getParameter("cate"));
        }
        
            List<ProductVariant> list1 = db.getProduct1(Noicau);

         int page = 0;
        int numberOfPage = 6;
        String xpage = request.getParameter("page");
        int size = list1.size();
        int num = (size % numberOfPage == 0 ? (size / numberOfPage) : ((size / numberOfPage) + 1));
             if (xpage == null) {
            page = 1;
        } else {
            try {
                page = Integer.parseInt(xpage);
                
            } catch (NumberFormatException ex) {
               response.sendRedirect("ProductsListPublic");
                return;
            }
        }
        int start, end;
        start = (page - 1) * numberOfPage;
        end = Math.min(page * numberOfPage, list1.size());
        List<ProductVariant> list = db.getListByPage(list1, start, end);
        request.setAttribute("totalPages", num);
        request.setAttribute("product", list);
        request.setAttribute("size", size);
        request.setAttribute("currentPage", page);
        request.setAttribute("cateproduct", db1.getCateProduct());
        request.getRequestDispatcher("Views/ProductList.jsp").forward(request, response);
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
        processRequest(request, response);
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
