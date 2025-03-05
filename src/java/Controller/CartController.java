/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.DaoProduct;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author d
 */
public class CartController extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final DaoProduct daoProduct = new DaoProduct();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("pid"));
        int quantity = 1;
        String productId = String.valueOf(id);
        String cartContext = productId + ":" + quantity;
        Cookie[] cookies = request.getCookies();
        String cartData = "";
        boolean cartFound = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cart")) {
                    cartData = cookie.getValue();
                    cartFound = true;
                    break;
                }
            }
        }

        if (cartFound) {
            String[] items = cartData.split("_");
            StringBuilder newCartData = new StringBuilder();
            boolean productExists = false;

            for (String item : items) {
                if (!item.isEmpty()) {
                    String[] parts = item.split(":");
                    if (parts.length == 2) {
                        String itemId = parts[0];
                        int itemQuantity = Integer.parseInt(parts[1]);
                        if (itemId.equals(productId)) {
                            itemQuantity += 1;
                            productExists = true;
                        }
                        newCartData.append(itemId).append(":")
                                .append(itemQuantity).append("_");
                    }
                }
            }
            if (!productExists) {
                newCartData.append(cartContext).append("_");
            }

            String finalCartData = newCartData.toString();
            if (finalCartData.endsWith("_")) {
                finalCartData = finalCartData.substring(0, finalCartData.length() - 1);
            }


            Cookie cartCookie = new Cookie("cart", finalCartData);
            cartCookie.setMaxAge(60 * 60 * 24 * 7);
            cartCookie.setPath("/");
            response.addCookie(cartCookie);
        } else {
            Cookie cartCookie = new Cookie("cart", cartContext);
            cartCookie.setMaxAge(60 * 60 * 24 * 7);
            cartCookie.setPath("/");
            response.addCookie(cartCookie);
        }
        String referer = request.getHeader("referer");
        if (referer != null) {
            response.sendRedirect(referer);
        } else {
            response.sendRedirect("home");
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
