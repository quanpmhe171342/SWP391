/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.DAOCartItem;
import DAO.DaoCart;
import DAO.DaoProduct;
import DAO.OrderDAO;
import DTO.OrderDetailDTO;
import DTO.ProductDTO;
import Model.CartItem;
import Model.Order;
import Model.OrderDetail;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author d
 */
public class PaymentController extends HttpServlet {

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
    private final OrderDAO daoOrder = new OrderDAO();
    private final DAOCartItem daoCartItem = new DAOCartItem();
    private final DaoCart daoCart = new DaoCart();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        var cartItems = daoCartItem.getCartItemsByUserId(user.getUserId());
        if (cartItems.isEmpty()) {
            request.setAttribute("error", "Your cart is empty. Please add items before checkout.");
            request.getRequestDispatcher("Views/Payment.jsp").forward(request, response);
            return;
        }
        List<ProductDTO> products = new ArrayList<>();
        for (var cart : cartItems) {
            ProductDTO productDTO = daoProduct.getListProductById(cart.getProductID());
            if (productDTO != null) {
                productDTO.setQuantity(cart.getQuantity());
                products.add(productDTO);
            }
        }
        Order order = new Order();
        order.setUserID(user.getUserId());
        order.setSaleID(user.getUserId());
        order.setStatus("Wait");
        order.setPaymentMethod("Bank");
        order.setOrderDate(Date.valueOf(LocalDate.now()));
        int orderId = daoOrder.addOrder(order);
        List<OrderDetail> listOrderDetails = new ArrayList<>();
        products.stream().forEach(p -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProductId(p.getProductId());
            orderDetail.setQuantity(p.getQuantity());
            orderDetail.setPrice(p.getQuantity() * (p.getSalePrice() == null ? p.getOriginalPrice() : p.getSalePrice()));
            orderDetail.setOrderId(orderId);
            daoOrder.addOrderDetail(orderDetail);
            listOrderDetails.add(orderDetail);
        });
        double totalAmount = listOrderDetails.stream()
                .mapToDouble(detail -> detail.getPrice())
                .sum();
        List<OrderDetailDTO> orderDetails = daoOrder.getOrderDetailsByOrderId(orderId);
        request.setAttribute("order", order);
        request.setAttribute("orderDetails", orderDetails);
        request.setAttribute("totalAmount", totalAmount);
        for (CartItem cartItem : cartItems) {
            daoCartItem.deleteCartItem(cartItem.getCartItemID());
            daoCart.deleteCart(cartItem.getCartID(), user.getUserId());
            var productVariant = daoProduct.getVariantsByProductId(cartItem.getProductID()).get(0);
            if (productVariant != null) {
                productVariant.setStock(productVariant.getStock() - cartItem.getQuantity());
                daoProduct.updateStock(productVariant.getVariantId(), productVariant.getStock());
            }
        }
        request.getRequestDispatcher("Views/Payment.jsp").forward(request, response);
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
