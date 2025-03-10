/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.ReportCustomerDTO;
import DTO.ReportProductDTO;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NV200
 */
public class DashboarDAO extends DBContext{
     public List<ReportCustomerDTO> getNewCustomerStats(Date startDate, Date endDate) {
        List<ReportCustomerDTO> stats = new ArrayList<>();
        String sql = """
        SELECT 
            CAST(OrderDate AS DATE) as Period, 
            COUNT(DISTINCT u.UserID) as Customers
        FROM Users u
        JOIN [Order] o ON u.UserID = o.UserID
        WHERE u.RoleID = 1
            AND o.OrderDate BETWEEN ? AND ?
        GROUP BY CAST(OrderDate AS DATE) 
        ORDER BY Period
    """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(startDate.getTime()));
            ps.setDate(2, new java.sql.Date(endDate.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReportCustomerDTO stat = new ReportCustomerDTO();
                stat.setPeriod(rs.getDate("Period")); 
                stat.setCustomers(rs.getInt("Customers"));
                stats.add(stat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats;
    }
     public List<ReportProductDTO> getReportProduct(Date startDate, Date endDate) {
        List<ReportProductDTO> products = new ArrayList<>();
        String sql = """
                     SELECT
                         p.ProductID,
                         p.ProductName,
                         SUM(od.Quantity) as TotalSold,
                         SUM(od.Price - p.real_price*od.Quantity) as Revenue,
                         CAST(o.OrderDate AS DATE) as Period
                     FROM Product p
                     JOIN OrderDetails od ON p.ProductID = od.ProductID
                     JOIN [Order] o ON od.OrderID = o.OrderID
                     WHERE o.Status = 'Wait'
                         AND o.OrderDate BETWEEN ? AND ?
                     GROUP BY p.ProductID, p.ProductName, CAST(o.OrderDate AS DATE)
                     ORDER BY TotalSold DESC;
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(startDate.getTime()));
            ps.setDate(2, new java.sql.Date(endDate.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReportProductDTO report = new ReportProductDTO();
                report.setProductId(rs.getInt("ProductID"));
                report.setProductName(rs.getString("ProductName"));
                report.setPeriod(rs.getDate("Period"));
                report.setRevenue(rs.getDouble("Revenue"));
                report.setTotalSold(rs.getInt("TotalSold"));
                products.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
     public List<ReportCustomerDTO> getOldCustomer(Date startDate, Date endDate) {
        List<ReportCustomerDTO> stats = new ArrayList<>();
        String sql = """
            SELECT 
            CAST(OrderDate AS DATE) as Period,
            COUNT(DISTINCT u.UserID) as Customers
        FROM Users u
        JOIN [Order] o ON u.UserID = o.UserID
        WHERE u.UserID IN (
            SELECT UserID
            FROM [Order]
            GROUP BY UserID
            HAVING COUNT(*) >= 2
        )
        AND o.OrderDate BETWEEN ? AND ?
        GROUP BY CAST(OrderDate AS DATE)
        ORDER BY Period;
    """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(startDate.getTime()));
            ps.setDate(2, new java.sql.Date(endDate.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReportCustomerDTO stat = new ReportCustomerDTO();
                stat.setPeriod(rs.getDate("Period")); // Lấy giá trị kiểu Date
                stat.setCustomers(rs.getInt("Customers"));
                stats.add(stat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats;
    }
}
