package entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class product {
    String Name = null;
    BigDecimal Price = new BigDecimal(0);
    String Category = null;

    public product(String Name, String Category) {
        this.Name = Name;
        this.Category = Category;
    }

    public product(String Name, String Category, Double Price) {
        this.Name = Name;
        this.Category = Category;
        this.Price = new BigDecimal(Price).setScale(2, RoundingMode.HALF_UP);
    }

    public void createProduct(Connection conn) {
        try {
            String sql = "INSERT INTO product (name, category, price) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Name);
            pstmt.setString(2, Category);
            pstmt.setBigDecimal(3, Price);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("product added successfully!");
            } else {
                System.out.println("Failed to add the product.");
            }

        } catch (Exception e) {
            System.out.println(
                    "Error createProduct(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
    }

}
