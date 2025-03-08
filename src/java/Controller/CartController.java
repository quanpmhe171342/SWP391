/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.DAOCartItem;
import DAO.DaoCart;
import Model.Cart;
import Model.CartItem;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
    private final DaoCart daoCart = new DaoCart();
    private final DAOCartItem daoCartItem = new DAOCartItem();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("pid"));
        int quantity = 1;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Cart existingCart = daoCart.getCartByUserId(user.getUserId());
        int cartId;
        if (existingCart == null) {
            Cart newCart = new Cart();
            newCart.setUserID(user.getUserId());
            cartId = daoCart.createCart(newCart);
        } else {
            cartId = existingCart.getCartID();
        }
        CartItem existingItem = daoCartItem.getCartItemByProductAndCart(productId, cartId);
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            daoCartItem.updateCartItem(existingItem);
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setProductID(productId);
            newCartItem.setCartID(cartId);
            newCartItem.setQuantity(quantity);
            daoCartItem.createCartItem(newCartItem);
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
        String action = request.getParameter("action");
        if (action == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Action parameter is required");
            return;
        }
        try {
            switch (action) {
                case "update" ->
                    updateCart(request, response);
                case "delete" ->
                    deleteFromCart(request, response);
                default -> {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Invalid action");
                }
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid input parameters");
        }
    }

    private void updateCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int cartId = Integer.parseInt(request.getParameter("pid"));
        int newQuantity = Integer.parseInt(request.getParameter("quantity"));
        if (newQuantity < 1) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Quantity must be greater than 0");
            return;
        }
        CartItem cartItemC = daoCartItem.getCartItemById(cartId);
        if (newQuantity > cartItemC.getQuantity() && cartItemC != null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Quantity must be smaller than in product");
            return;
        }
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(newQuantity);
        cartItem.setCartItemID(cartId);
        daoCartItem.updateCartItem(cartItem);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("Quantity updated successfully");
    }

    private void deleteFromCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int cartId = Integer.parseInt(request.getParameter("pid"));
        CartItem cartItem = new CartItem();
        daoCartItem.deleteCartItem(cartId);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("Product removed successfully");
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
