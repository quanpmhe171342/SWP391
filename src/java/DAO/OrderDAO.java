/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.LessProductsDTO;
import DTO.OrderDTO;
import DTO.OrderDetailDTO;
import DTO.ReportCustomerDTO;
import DTO.ReportProductDTO;
import DTO.ReportStateDTO;
import DTO.SalesReportDTO;
import Model.Order;
import Model.OrderDetail;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author d
 */
public class OrderDAO extends DBContext {

    public int addOrder(Order order) {
        int generatedOrderId = 0;
        String sql = "INSERT INTO [Order] (UserID, OrderDate, Status, SaleID, Payment_method) "
                + "OUTPUT INSERTED.OrderID "
                + "VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, order.getUserID());
            st.setDate(2, order.getOrderDate());
            st.setString(3, order.getStatus());
            st.setInt(4, order.getSaleID());
            st.setString(5, order.getPaymentMethod());

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                generatedOrderId = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error adding order: " + e.getMessage());
        }

        return generatedOrderId;
    }

    public int addOrderDetail(OrderDetail detail) {
        int generatedDetailId = 0;
        String sql = "INSERT INTO OrderDetails (OrderID, ProductID, Quantity, Price) "
                + "OUTPUT INSERTED.OrderDetailID "
                + "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, detail.getOrderId());
            st.setInt(2, detail.getProductId());
            st.setInt(3, detail.getQuantity());
            st.setDouble(4, detail.getPrice());

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                generatedDetailId = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Error adding order detail: " + e.getMessage());
        }

        return generatedDetailId;
    }

    public List<OrderDetailDTO> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetailDTO> details = new ArrayList<>();
        String sql = """
                     SELECT od.OrderDetailID, od.OrderID, od.ProductID, 
                     od.Quantity, od.Price, p.ProductName, p.original_price, 
                     p.sale_price, p.product_description, p.brief_information,o.Status
                     FROM OrderDetails od 
                     INNER JOIN Product p ON od.ProductID = p.ProductID 
                     INNER JOIN [Order] o ON o.OrderID = od.OrderID 
                     WHERE od.OrderID = ?
                     """;

        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, orderId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                OrderDetailDTO detail = new OrderDetailDTO(
                        rs.getInt("OrderDetailID"),
                        rs.getInt("OrderID"),
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("original_price"),
                        rs.getDouble("sale_price"),
                        rs.getString("product_description"),
                        rs.getString("brief_information"),
                        rs.getInt("Quantity"),
                        rs.getDouble("Price"),
                        rs.getString("Status")
                );
                details.add(detail);
            }

        } catch (SQLException e) {
            System.out.println("Error getting order details: " + e.getMessage());
        }

        return details;
    }

    public List<SalesReportDTO> getRevenueByDate(Date startDate, Date endDate) {
        List<SalesReportDTO> reports = new ArrayList<>();
        String sql = """
            SELECT 
                CONVERT(date, o.OrderDate) as Date,
                COUNT(DISTINCT o.OrderID) as TotalOrders,
                SUM(od.Quantity) as TotalProducts,
                SUM(od.Quantity * od.Price) as Revenue
            FROM [Order] o
            JOIN OrderDetails od ON o.OrderID = od.OrderID
            WHERE o.Status = 'Wait'
                AND o.OrderDate BETWEEN ? AND ?
            GROUP BY CONVERT(date, o.OrderDate)
            ORDER BY Date
        """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(startDate.getTime()));
            ps.setDate(2, new java.sql.Date(endDate.getTime()));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SalesReportDTO report = new SalesReportDTO();
                report.setDate(rs.getDate("Date"));
                report.setTotalOrders(rs.getInt("TotalOrders"));
                report.setTotalProducts(rs.getInt("TotalProducts"));
                report.setRevenue(rs.getDouble("Revenue"));
                reports.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }

    public List<ReportCustomerDTO> getNewCustomerStats(Date startDate, Date endDate) {
        List<ReportCustomerDTO> stats = new ArrayList<>();
        String sql = """
        SELECT 
            CAST(FirstOrderDate AS DATE) as Period, 
            COUNT(DISTINCT UserID) as NewCustomers
        FROM (
            SELECT 
                u.UserID,
                MIN(o.OrderDate) as FirstOrderDate
            FROM Users u
            JOIN [Order] o ON u.UserID = o.UserID
            WHERE u.RoleID = 1
            GROUP BY u.UserID
            HAVING MIN(o.OrderDate) BETWEEN ? AND ?
        ) as FirstTimeOrders
        GROUP BY CAST(FirstOrderDate AS DATE) 
        ORDER BY Period
    """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(startDate.getTime()));
            ps.setDate(2, new java.sql.Date(endDate.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReportCustomerDTO stat = new ReportCustomerDTO();
                stat.setPeriod(rs.getDate("Period")); // Lấy giá trị kiểu Date
                stat.setCustomers(rs.getInt("NewCustomers"));
                stats.add(stat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats;
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

    public List<ReportStateDTO> getListState(Date startDate, Date endDate) {
        List<ReportStateDTO> states = new ArrayList<>();
        String sql = """
                     SELECT 
                         CAST(o.OrderDate AS DATE) as Period,
                         o.Status,
                         COUNT(*) as OrderCount,
                         SUM(od.TotalAmount) as TotalAmount
                     FROM [Order] o
                     JOIN (
                         SELECT 
                             OrderID,
                             SUM(Quantity * Price) as TotalAmount
                         FROM OrderDetails
                         GROUP BY OrderID
                     ) od ON o.OrderID = od.OrderID
                     WHERE o.OrderDate BETWEEN ? AND ?
                     GROUP BY CAST(o.OrderDate AS DATE), o.Status
                     ORDER BY Period;
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(startDate.getTime()));
            ps.setDate(2, new java.sql.Date(endDate.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReportStateDTO result = new ReportStateDTO();
                result.setPeriod(rs.getDate("Period"));
                result.setOrderCount(rs.getInt("OrderCount"));
                result.setTotalAmount(rs.getDouble("TotalAmount"));
                result.setStatus(rs.getString("Status"));
                states.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return states;
    }

    public List<ReportProductDTO> getReportProduct(Date startDate, Date endDate) {
        List<ReportProductDTO> products = new ArrayList<>();
        String sql = """
                     SELECT TOP 10
                         p.ProductID,
                         p.ProductName,
                         SUM(od.Quantity) as TotalSold,
                         SUM(od.Price - p.real_price) as Revenue,
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

    public List<LessProductsDTO> getTopProducts(Date startDate, Date endDate) {
        List<LessProductsDTO> products = new ArrayList<>();
        String sql = """
                    SELECT TOP 10
                        p.ProductID,
                        p.ProductName,
                        COALESCE(SUM(od.Quantity), 0) as TotalSold,
                        COALESCE(SUM(od.Quantity * od.Price), 0) as Revenue
                    FROM Product p
                    LEFT JOIN OrderDetails od ON p.ProductID = od.ProductID
                    LEFT JOIN [Order] o ON od.OrderID = o.OrderID
                        AND o.Status = 'Wait'
                        AND o.OrderDate BETWEEN ? AND ?
                    GROUP BY p.ProductID, p.ProductName
                    ORDER BY TotalSold ASC
                    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(startDate.getTime()));
            ps.setDate(2, new java.sql.Date(endDate.getTime()));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LessProductsDTO product = new LessProductsDTO();
                product.setProductId(rs.getInt("ProductID"));
                product.setProductName(rs.getString("ProductName"));
                product.setTotalSold(rs.getInt("TotalSold"));
                product.setRevenue(rs.getDouble("Revenue"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<OrderDTO> getListOrder(int userId) {
    List<OrderDTO> orders = new ArrayList<>();
    String sql = """
                SELECT 
                    o.[OrderID],
                    u.[first_name] + ' ' + u.[last_name] AS CustomerName, 
                    o.[OrderDate],
                    o.[Status],
                    p.[ProductName]
                FROM 
                    [SWP391].[dbo].[Order] o
                INNER JOIN 
                    [SWP391].[dbo].[OrderDetails] od ON o.[OrderID] = od.[OrderID]
                INNER JOIN 
                    [SWP391].[dbo].[Product] p ON od.[ProductID] = p.[ProductID]
                INNER JOIN
                    [SWP391].[dbo].[Users] u ON o.[UserID] = u.[UserID]
                WHERE 
                    u.UserID = ?
                ORDER BY 
                    o.[OrderDate] DESC
                 """;
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        // Set the userId parameter in the prepared statement
        ps.setInt(1, userId);
        
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderId(rs.getInt("OrderID"));
            orderDTO.setCustomerName(rs.getString("CustomerName"));
            orderDTO.setOrderDate(rs.getDate("OrderDate"));
            orderDTO.setStatus(rs.getString("Status"));
            orderDTO.setProductName(rs.getString("ProductName"));
            orders.add(orderDTO);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return orders;
}

    public static void main(String[] args) {
        OrderDAO daoOrder = new OrderDAO();
        daoOrder.getListOrder(1).forEach(o -> {
            System.out.println(o);
        });
    }
}
