package entities;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public static int getInventoryByName(Connection conn, String inventoryName) {
        int inventory_id = -1;
        try {
            String query = "SELECT inventory_id FROM inventory WHERE name = ?";
            PreparedStatement pstmtPrice = conn.prepareStatement(query);
            pstmtPrice.setString(1, inventoryName);
            ResultSet resultSet = pstmtPrice.executeQuery();
            if (resultSet.next()) {
                inventory_id = resultSet.getInt("inventory_id");
            }
            return inventory_id;
        } catch (Exception e) {
            System.out.println("Error");
        }
        return -1;
    }

    public static ArrayList<ArrayList<String>> getInventory(Connection conn) {
        ArrayList<ArrayList<String>> inventory_array = new ArrayList<>();
        try {
            String query = "SELECT * FROM inventory";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<String> id_array = new ArrayList<>();
            ArrayList<String> name_array = new ArrayList<>();
            ArrayList<String> quantity_array = new ArrayList<>();
            ArrayList<String> updated_array = new ArrayList<>();
            inventory_array.add(id_array);
            inventory_array.add(name_array);
            inventory_array.add(quantity_array);
            inventory_array.add(updated_array);
            while (resultSet.next()) {
                String id = resultSet.getInt("inventory_id") + "";
                String name = resultSet.getString("name");
                String quantity = resultSet.getInt("quantity") + "";
                String update = resultSet.getDate("last_updated") + "";
                
                inventory_array.get(0).add(id);
                inventory_array.get(1).add(name);
                inventory_array.get(2).add(quantity);
                inventory_array.get(3).add(update);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventory_array;
    }

    public static void addSubInventory(Connection conn, int inventoryID, int inventoryNumber) {

        java.util.Date currentDateUtil = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDateStr = sdf.format(currentDateUtil);
        Date currentDateSql = Date.valueOf(formattedDateStr);

        String updateQuery = "UPDATE inventory SET quantity = quantity + ? WHERE inventory_id = ?";
        String updateDateQuery = "UPDATE inventory SET last_updated = ? WHERE inventory_id = ?";
        try {
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            PreparedStatement updateDateStatement = conn.prepareStatement(updateDateQuery);
            updateStatement.setInt(1, inventoryNumber);
            updateStatement.setInt(2, inventoryID);
            updateDateStatement.setDate(1, currentDateSql);
            updateDateStatement.setInt(2, inventoryID);
            updateStatement.executeUpdate();
            updateDateStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(
                    "Error createOrder(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
    }
}
