package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class inventoryProduct {
    int ProductID;
    int InventoryID;
    BigDecimal Quantity = BigDecimal.valueOf(0);

    public inventoryProduct(int ProductID, int InventoryID) {
        this.ProductID = ProductID;
        this.InventoryID = InventoryID;
    }

    public inventoryProduct(int ProductID, int InventoryID, double Quantity) {
        this.ProductID = ProductID;
        this.InventoryID = InventoryID;
        this.Quantity = new BigDecimal(Quantity).setScale(2, RoundingMode.HALF_UP);
    }

    public void createInventoryProduct(Connection conn) {
        try {
            String sql = "INSERT INTO inventory_product (Product_ID, inventory_ID, Quantity) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, ProductID);
            pstmt.setInt(2, InventoryID);
            pstmt.setBigDecimal(3, Quantity);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("InventoryProduct added successfully!");
            } else {
                System.out.println("Failed to add the InventoryProduct.");
            }

        } catch (Exception e) {
            System.out.println(
                    "Error createInventoryProduct(): Name: " + e.getClass().getName() + " , Message: "
                            + e.getMessage());
        }
    }

}
