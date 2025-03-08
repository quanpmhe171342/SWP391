/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.CartItem;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 *
 * @author d
 */
public class DAOCartItem extends DBContext {

    public CartItem getCartItemByProductAndCart(int productId, int cartId) {
        String sql = "SELECT CartItemID, CartID, ProductID, Quantity FROM CartItems WHERE ProductID = ? AND CartID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            ps.setInt(2, cartId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new CartItem(
                        rs.getInt("CartItemID"),
                        rs.getInt("CartID"),
                        rs.getInt("ProductID"),
                        rs.getInt("Quantity")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void updateCartItem(CartItem cartItem) {
        String sql = "UPDATE CartItems SET Quantity = ? WHERE CartItemID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cartItem.getQuantity());
            ps.setInt(2, cartItem.getCartItemID());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void createCartItem(CartItem cartItem) {
        String sql = """
                     INSERT INTO [dbo].[CartItems]
                                ([CartID]
                                ,[ProductID]
                                ,[Quantity])
                          VALUES
                                (?
                                ,?
                                ,?)
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cartItem.getCartID());
            ps.setInt(2, cartItem.getProductID());
            ps.setInt(3, cartItem.getQuantity());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Read by ID
    public CartItem getCartItemById(int cartItemID) {
        String sql = "SELECT CartItemID, CartID, ProductID, Quantity FROM CartItems WHERE CartItemID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cartItemID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new CartItem(
                        rs.getInt("CartItemID"),
                        rs.getInt("CartID"),
                        rs.getInt("ProductID"),
                        rs.getInt("Quantity")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Read by CartID
    public List<CartItem> getCartItemsByCartId(int cartID) {
        List<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT CartItemID, CartID, ProductID, Quantity FROM CartItems WHERE CartID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cartID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cartItems.add(new CartItem(
                        rs.getInt("CartItemID"),
                        rs.getInt("CartID"),
                        rs.getInt("ProductID"),
                        rs.getInt("Quantity")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return cartItems;
    }

    // Delete
    public void deleteCartItem(int cartItemID) {
        String sql = "DELETE FROM CartItems WHERE CartItemID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cartItemID);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // List all cart items
    public List<CartItem> getAllCartItems() {
        List<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT CartItemID, CartID, ProductID, Quantity FROM CartItems";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cartItems.add(new CartItem(
                        rs.getInt("CartItemID"),
                        rs.getInt("CartID"),
                        rs.getInt("ProductID"),
                        rs.getInt("Quantity")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return cartItems;
    }
     public List<CartItem> getCartItemsByUserId(int userId) {
        List<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT ci.CartItemID, ci.CartID, ci.ProductID, ci.Quantity " +
                     "FROM Cart c " +
                     "JOIN CartItems ci ON c.CartID = ci.CartID " +
                     "WHERE c.UserID = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CartItem cartItem = new CartItem();
                cartItem.setCartItemID(rs.getInt("CartItemID"));
                cartItem.setCartID(rs.getInt("CartID"));
                cartItem.setProductID(rs.getInt("ProductID"));
                cartItem.setQuantity(rs.getInt("Quantity"));

                cartItems.add(cartItem);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return cartItems;
    }
     public static void main(String[] args) {
         new DAOCartItem().getCartItemsByUserId(1)
                 .forEach(u -> {
                     System.out.println(u);
                 });
    }
}
