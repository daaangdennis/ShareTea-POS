package pointOfSales.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class orderProduct {
    public int ProductID;
    public int OrderID;
    public int Quantity;
    public ArrayList<String> Toppings;
    public String Note = "";
    public double SugarLevel = 100;

    public orderProduct(String PID, int ProductQuantity, ArrayList<String> ToppingList, double Sugar,
            String NoteInput) {
        this.ProductID = Integer.parseInt(PID);
        this.Quantity = ProductQuantity;
        this.Toppings = ToppingList;
        this.SugarLevel = Sugar;
        this.Note = NoteInput;
    }

    public orderProduct(String PID, int ProductQuantity, int Sugar, String NoteInput) {
        this.ProductID = Integer.parseInt(PID);
        this.Quantity = ProductQuantity;
        this.SugarLevel = Sugar;
        this.Note = NoteInput;
    }

    public void addOrderProduct(Connection conn, int productID, int orderID) {
        String toppingList = "";
        if (!Toppings.isEmpty()) {
            for (int i = 0; i < Toppings.size() - 1; ++i) {
                toppingList += Toppings.get(i) + ", ";
            }
            toppingList += Toppings.get(Toppings.size() - 1);
        }
        if (this.SugarLevel != 1) {
            if (Note.equals("")) {
                Note += (SugarLevel) + "%" + " sugar.";
            } else {
                Note += " " + (SugarLevel) + "%" + " sugar.";
            }
        }
        try {
            String sql = "INSERT INTO order_product (Product_ID, Order_ID, Quantity, Note, toppings) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productID);
            pstmt.setInt(2, orderID);
            pstmt.setInt(3, this.Quantity);
            pstmt.setString(4, this.Note);
            pstmt.setString(5, toppingList);
            pstmt.executeUpdate();
            subtractInventory(conn);
            subtractToppings(conn);
        }

        catch (Exception e) {
            System.out.println(
                    "Error createOrderProduct(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
    }

    public void subtractInventory(Connection conn) {
        try {
            String sql = "UPDATE inventory SET quantity = quantity - ? WHERE inventory_id IN (SELECT inventory_id FROM inventory_product WHERE product_id = ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, this.Quantity);
            pstmt.setInt(2, this.ProductID);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error in subtracting inventory from order_product.");
        }
    }

    public void subtractToppings(Connection conn) {
        try {
            String sql = "UPDATE inventory SET quantity = quantity - 1 WHERE name = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < this.Toppings.size(); ++i) {
                pstmt.setString(1, this.Toppings.get(i));
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (Exception e) {
            System.out.println("Error in subtracting inventory from order_product.");
        }
    }

}
