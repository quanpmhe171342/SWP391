/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.DAOCartItem;
import DAO.DaoCart;
import DAO.DaoProduct;
import DTO.Config.VNPayConfig;
import DTO.ProductDTO;
import Model.CartItem;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author d
 */
public class ProductCart extends HttpServlet {

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
    private final DAOCartItem daoCartItem = new DAOCartItem();
    private final DaoCart daoCart = new DaoCart();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<ProductDTO> products = new ArrayList<>();
        List<String> errorMessages = new ArrayList<>();
        boolean hasQuantityError = false;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        var cartItems = daoCartItem.getCartItemsByUserId(user.getUserId());
        for (CartItem c : cartItems) {
            var productVariant = daoProduct.getListProductById(c.getProductID());       
            if (productVariant != null) {
                if (c.getQuantity() > productVariant.getQuantity()) {
                    daoCartItem.deleteCartItem(c.getCartItemID());
                    daoCart.deleteCart(c.getCartID(), user.getUserId());
                }
                productVariant.setCartId(c.getCartItemID());
                productVariant.setQuantity(c.getQuantity());
                productVariant.setOriginalPrice(c.getQuantity() * productVariant.getOriginalPrice());
                products.add(productVariant);
            }
        }
        double sumPrice = !products.isEmpty() ? products.stream()
                .mapToDouble(ProductDTO::getOriginalPrice)
                .sum() : 0;
        request.setAttribute("sumPrice", sumPrice);
        request.setAttribute("cartList", products);
        request.setAttribute("hasQuantityError", hasQuantityError);
        request.setAttribute("errorMessages", errorMessages);
        request.getRequestDispatcher("../Views/CartProduct.jsp").forward(request, response);
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
        String orderType = "other";
        String amountStr = request.getParameter("amount");
        amountStr = amountStr.replaceAll("[.,₫\\s]", "");
        long amount = (long) Double.parseDouble(amountStr) * 100;
        String bankCode = request.getParameter("bankCode");
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = request.getRemoteAddr();
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VNPayConfig.VNP_VERSION);
        vnp_Params.put("vnp_Command", VNPayConfig.VNP_COMMAND);
        vnp_Params.put("vnp_TmnCode", VNPayConfig.VNP_TMNCODE);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", VNPayConfig.VNP_CURRCODE);
        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", VNPayConfig.VNP_LOCALE);
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.VNP_RETURN_URL);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        var fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        var itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = hmacSHA512(VNPayConfig.VNP_HASHSECRET, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.VNP_PAYURL + "?" + queryUrl;

        response.sendRedirect(paymentUrl);
    }

    private String hmacSHA512(final String key, final String data) {
        try {
            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (IllegalStateException | NullPointerException | InvalidKeyException | NoSuchAlgorithmException ex) {
            return "";
        }
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
