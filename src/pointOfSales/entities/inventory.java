package pointOfSales.entities;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;

public class inventory {
    String Name = null;
    String Details = null;
    int Quantity = 0;
    Date LastUpdated = null;
    Connection conn = null;

    public inventory(Connection conn) {
        this.conn = conn;
    }

    public inventory(Connection conn, String Name) {
        this.Name = Name;
        this.conn = conn;

    }

    public inventory(Connection conn, String Name, String Details, int Quantity, String LastUpdated) {
        this.Name = Name;
        this.Details = Details;
        this.Quantity = Quantity;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(LastUpdated);
            this.LastUpdated = new java.sql.Date(utilDate.getTime());
        } catch (Exception e) {
            System.out.println(
                    "Error inventory(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
    }

    public void createInventory() {
        try {
            PreparedStatement pstmt;

            if (LastUpdated == null) {
                String sql = "INSERT INTO inventory (name, details, quantity) VALUES (?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, Name);
                pstmt.setString(2, Details);
                pstmt.setInt(3, Quantity);

            } else {
                String sql = "INSERT INTO inventory (name, details, quantity, last_updated ) VALUES (?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, Name);
                pstmt.setString(2, Details);
                pstmt.setInt(3, Quantity);
                pstmt.setDate(4, LastUpdated);
            }
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("inventory added successfully!");
            } else {
                System.out.println("Failed to add the inventory.");
            }
        } catch (Exception e) {
            System.out.println(
                    "Error createInventory(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
    }
}
