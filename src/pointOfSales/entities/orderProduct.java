package pointOfSales.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class orderProduct {
    int ProductID;
    public String ProductName;
    int OrderID;
    public int Quantity;
    public ArrayList<String> Toppings;
    public String Note = "";
    public double SugarLevel = 1;

    public orderProduct(String Product, int ProductQuantity, ArrayList<String> ToppingList, double Sugar, String NoteInput) {
        this.ProductName = Product;
        this.Quantity = ProductQuantity;
        this.Toppings = ToppingList;
        this.SugarLevel = Sugar;
        this.Note = NoteInput;
    }
    public orderProduct(String Product, int ProductQuantity, int Sugar, String NoteInput) {
        this.ProductName = Product;
        this.Quantity = ProductQuantity;
        this.SugarLevel = Sugar;
        this.Note = NoteInput;
    }

    public void addOrderProduct(Connection conn, int productID, int orderID) {
        String toppingList = "";
        if(!Toppings.isEmpty()){
            for(int i = 0; i < Toppings.size() - 1; ++i){
                toppingList += Toppings.get(i) + ", ";
            }
            toppingList += Toppings.get(Toppings.size() - 1);
        }
        if(this.SugarLevel != 1){
            if(Note.equals("")){
                Note += (SugarLevel*100) + "%" + " sugar.";
            } 
            else{
                Note += " " + (SugarLevel*100) + "%" + " sugar.";
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

        } 
        catch (Exception e) {
            System.out.println(
                    "Error createOrderProduct(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
    }

}
