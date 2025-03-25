/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Color;
import Model.Size;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author phuan
 */
public class DaoColor extends DBContext{
    
    private final DBContext dbContext;
    private final Connection connection;

    public DaoColor() {

        dbContext = new DBContext();
        connection = dbContext.conn;
    }
    public List<Color> getColor() {
        List<Color> colors = new ArrayList<>();
        String query = "SELECT c.ColorID, c.ColorName FROM Color c";
        try (PreparedStatement stm = conn.prepareStatement(query);
             ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                Color color = new Color(
                        rs.getInt("ColorID"),
                        rs.getString("ColorName")
                );
                colors.add(color);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colors;
    }

    public static void main(String[] args) {
        DaoColor dao = new DaoColor();
        System.out.println(dao.getColor());
    }
}
