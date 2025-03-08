/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Color;
import Model.Size;
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
    public List<Color> getColor() {
        List<Color> color = new ArrayList();
        try {
            String query = "Select c.ColorID, c.ColorName   \n"
                    + "					from Color c \n";

            PreparedStatement stm = conn.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Color c = new Color(rs.getInt("ColorID"),
                        rs.getString("ColorName"));
               
                color.add(c);
            }
        } catch (SQLException e) {
            System.out.print(e);
        }
        return color;
    }
}
