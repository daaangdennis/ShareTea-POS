package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class orderProduct {
    int ProductID;
    int OrderID;
    int Quantity;
    String Note;
    Connection conn = null;

    public orderProduct(Connection conn) {
        this.conn = conn;
    }

    public orderProduct(Connection conn, int ProductID, int OrderID, int Quantity) {
        this.ProductID = ProductID;
        this.OrderID = OrderID;
        this.Quantity = Quantity;
        this.conn = conn;

    }

    public orderProduct(Connection conn, int ProductID, int OrderID, int Quantity, String Note) {
        this.ProductID = ProductID;
        this.OrderID = OrderID;
        this.Quantity = Quantity;
        this.Note = Note;
        this.conn = conn;
    }

    public int createOrderProduct() {
        int returnOrderProductID = -1;
        try {
            String sql = "INSERT INTO order_product (Product_ID, Order_ID, Quantity, Note) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, ProductID);
            pstmt.setInt(2, OrderID);
            pstmt.setInt(3, Quantity);
            pstmt.setString(4, Note);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("orderProduct added successfully!");
                ResultSet Keys = pstmt.getGeneratedKeys();
                if (Keys.next()) {
                    returnOrderProductID = Keys.getInt(1);
                }
            } else {
                System.out.println("Failed to add the orderProduct.");
            }

        } catch (Exception e) {
            System.out.println(
                    "Error createOrderProduct(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
        return returnOrderProductID;
    }

}
