package pointOfSales.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a product within the inventory with associated details such as
 * ProductID, InventoryID, and Quantity.
 */
public class inventoryProduct {
    /** The ID of the product. */
    int ProductID;
    /** The ID of the inventory associated with the product. */
    int InventoryID;
    /**
     * The quantity of the product in the inventory, stored with 2 decimal places
     * precision.
     */
    BigDecimal Quantity = BigDecimal.valueOf(0);
    /** Connection to the database. */
    Connection conn = null;

    /**
     * Initializes a new instance of the inventoryProduct class with a database
     * connection.
     *
     * @param conn Database connection.
     */
    public inventoryProduct(Connection conn) {
        this.conn = conn;
    }

    /**
     * Initializes a new instance of the inventoryProduct class with a database
     * connection,
     * product ID, and inventory ID.
     *
     * @param conn        Database connection.
     * @param ProductID   ID of the product.
     * @param InventoryID ID of the associated inventory.
     */
    public inventoryProduct(Connection conn, int ProductID, int InventoryID) {
        this.ProductID = ProductID;
        this.InventoryID = InventoryID;
        this.conn = conn;
    }

    /**
     * Initializes a new instance of the inventoryProduct class with detailed
     * parameters.
     *
     * @param conn        Database connection.
     * @param ProductID   ID of the product.
     * @param InventoryID ID of the associated inventory.
     * @param Quantity    Quantity of the product in the inventory.
     */
    public inventoryProduct(Connection conn, int ProductID, int InventoryID, double Quantity) {
        this.ProductID = ProductID;
        this.InventoryID = InventoryID;
        this.Quantity = new BigDecimal(Quantity).setScale(2, RoundingMode.HALF_UP);
        this.conn = conn;
    }

    /**
     * Creates a new inventory product in the database.
     */
    public void createInventoryProduct() {
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
