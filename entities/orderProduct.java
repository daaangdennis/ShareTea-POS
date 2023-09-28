package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class orderProduct {
    int ProductID;
    int OrderID;
    int Quantity;
    String Note;

    public orderProduct(int ProductID, int OrderID, int Quantity) {
        this.ProductID = ProductID;
        this.OrderID = OrderID;
        this.Quantity = Quantity;
    }

    public orderProduct(int ProductID, int OrderID, int Quantity, String Note) {
        this.ProductID = ProductID;
        this.OrderID = OrderID;
        this.Quantity = Quantity;
        this.Note = Note;
    }

    public void createOrderProduct(Connection conn) {
        try {
            String sql = "INSERT INTO order_product (Product_ID, Order_ID, Quantity, Note) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, ProductID);
            pstmt.setInt(2, OrderID);
            pstmt.setInt(3, Quantity);
            pstmt.setString(4, Note);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("orderProduct added successfully!");
            } else {
                System.out.println("Failed to add the orderProduct.");
            }

        } catch (Exception e) {
            System.out.println(
                    "Error createOrderProduct(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
    }

}
