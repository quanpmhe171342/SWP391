package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ProductStatisticsDAO {

    private final DBContext dbContext;
    private final Connection connection;

    public ProductStatisticsDAO() {

        dbContext = new DBContext();
        connection = dbContext.conn;
    }

    public int getTotalProducts() {
        String sql = "SELECT COUNT(*) FROM Product";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int getOutOfStockProducts() {
        String sql = "SELECT COUNT(*) FROM Product WHERE status =0";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int getProductInStock() {
        String sql = "SELECT COUNT(*) FROM Product WHERE status =1";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public Map<String, Integer> getProductsByCategory() {
        Map<String, Integer> categoryStats = new HashMap<>();
        String sql = "SELECT c.category_name, COUNT(p.ProductID) FROM Product p "
                + "JOIN CategoryProduct c ON p.CategoryProductID = c.CategoryID "
                + "GROUP BY c.category_name";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                categoryStats.put(rs.getString(1), rs.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryStats;
    }

    public int getLowStockProducts() {
        String sql = "SELECT COUNT(*) FROM ProductVariant WHERE stock < 20";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int getNewProductsInMonth() {
        String sql = "SELECT COUNT(*) FROM Product WHERE DATEDIFF(DAY, CreateDate, GETDATE()) <= 30";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int getNewProductsInWeek() {
        String sql = "SELECT COUNT(*) FROM Product WHERE DATEDIFF(DAY, CreateDate, GETDATE()) <= 7";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int getAllStock(){
        String sql = "SELECT SUM(stock) AS total_stock FROM ProductVariant;";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
