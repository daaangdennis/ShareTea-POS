package entities;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.math.RoundingMode;

//OrderID SERIAL PRIMARY KEY,
//CustomerID INT NOT NULL,
//EmployeeID INT NOT NULL,
//OrderDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//Total DECIMAL(10, 2) NOT NULL,
//IsPending BOOL DEFAULT TRUE,
//IsRefunded BOOL DEFAULT FALSE,
//FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID), 2
//FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID)

public class order {
    int CustomerID;
    int EmployeeID;
    Date OrderDate = null;
    BigDecimal Total = null;
    boolean IsPending = true;
    boolean IsRefunded = false;

    public order(int CustomerID, int EmployeeID, double Total) {
        this.CustomerID = CustomerID;
        this.EmployeeID = EmployeeID;
        this.Total = new BigDecimal(Total).setScale(2, RoundingMode.HALF_UP);
    }

    public order(int CustomerID, int EmployeeID, double Total, String OrderDate, boolean IsPending,
            boolean IsRefunded) {
        this.CustomerID = CustomerID;
        this.EmployeeID = EmployeeID;
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

    public void createOrder(Connection conn) {
        try {
            PreparedStatement pstmt;
            if (OrderDate == null) {
                String sql = "INSERT INTO orders (Customer_ID, Employee_ID, Total) VALUES (?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, CustomerID);
                pstmt.setInt(2, EmployeeID);
                pstmt.setBigDecimal(3, Total);
            } else {
                String sql = "INSERT INTO orders (Customer_ID, Employee_ID, Total, Order_Date, Is_Pending, IsRefunded ) VALUES (?, ?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, CustomerID);
                pstmt.setInt(2, EmployeeID);
                pstmt.setBigDecimal(3, Total);
                pstmt.setDate(4, OrderDate);
                pstmt.setBoolean(5, IsPending);
                pstmt.setBoolean(6, IsRefunded);
            }

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("order added successfully!");
            } else {
                System.out.println("Failed to add the order.");
            }

        } catch (Exception e) {
            System.out.println(
                    "Error createOrder(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }

    }

}
