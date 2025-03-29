/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Cart;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author d
 */
public class DaoCart extends DBContext {

    public Cart getCartByUserId(int userId) {
        String sql = "SELECT CartID, UserID FROM Cart WHERE UserID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Cart(rs.getInt("CartID"), rs.getInt("UserID"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int createCart(Cart cart) {
        String sql = "INSERT INTO Cart (UserID) VALUES (?)";
        int generatedId = -1;
        try {
            // Add RETURN_GENERATED_KEYS flag
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, cart.getUserID());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return generatedId;
    }

    public void deleteCart(int cartID, int userId) {
        String sql = "DELETE FROM Cart WHERE CartID = ? AND UserID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cartID);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
