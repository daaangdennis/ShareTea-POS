package entities;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class order {
    int CustomerID;
    int EmployeeID;
    Date OrderDate = null;
    BigDecimal Total = null;
    boolean IsPending = true;
    boolean IsRefunded = false;
    Connection conn = null;

    public order(Connection conn) {
        this.conn = conn;
    }

    public order(Connection conn, int CustomerID, int EmployeeID, double Total) {
        this.CustomerID = CustomerID;
        this.EmployeeID = EmployeeID;
        this.Total = new BigDecimal(Total).setScale(2, RoundingMode.HALF_UP);
        this.conn = conn;
    }

    public order(Connection conn, int CustomerID, int EmployeeID, double Total, String OrderDate, boolean IsPending,
            boolean IsRefunded) {
        this.CustomerID = CustomerID;
        this.EmployeeID = EmployeeID;
        this.conn = conn;
        this.Total = new BigDecimal(Total).setScale(2, RoundingMode.HALF_UP);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(OrderDate);
            this.OrderDate = new java.sql.Date(utilDate.getTime());
        } catch (Exception e) {
            System.out.println(
                    "Error order(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }

    }

    public int createOrder() {
        int returnOrderID = -1;
        try {
            PreparedStatement pstmt;
            if (OrderDate == null) {
                String sql = "INSERT INTO orders (Customer_ID, Employee_ID, Total, Is_Pending, Is_Refunded ) VALUES (?, ?, ?, ? , ?)";
                pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, CustomerID);
                pstmt.setInt(2, EmployeeID);
                pstmt.setBigDecimal(3, Total);
                pstmt.setBoolean(4, IsPending);
                pstmt.setBoolean(5, IsRefunded);
            } else {
                String sql = "INSERT INTO orders (Customer_ID, Employee_ID, Total, Order_Date, Is_Pending, Is_Refunded ) VALUES (?, ?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, CustomerID);
                pstmt.setInt(2, EmployeeID);
                pstmt.setBigDecimal(3, Total);
                pstmt.setDate(4, OrderDate);
                pstmt.setBoolean(5, IsPending);
                pstmt.setBoolean(6, IsRefunded);
            }

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Order added successfully!");
                ResultSet Keys = pstmt.getGeneratedKeys();
                if (Keys.next()) {
                    returnOrderID = Keys.getInt(1);
                }
            } else {
                System.out.println("Failed to add the order.");
            }

        } catch (Exception e) {
            System.out.println(
                    "Error createOrder(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
        return returnOrderID;

    }

    public void updateTotal(int order_id, double orderProduct_price) {
        String updateQuery = "UPDATE orders SET total = total + ? WHERE order_id = ?";
        try {
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setDouble(1, orderProduct_price);
            updateStatement.setInt(2, order_id);
            updateStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(
                    "Error createOrder(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
    }

    public int nextAvailableOrder(){
        int order_num = -1;
        String query = "SELECT MAX(order_id) FROM orders";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                order_num = resultSet.getInt("max") + 1;
            }
            return order_num;
        } catch (Exception e) {
            System.out.println(
                    "Error createOrder(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
        return order_num;
    }

}