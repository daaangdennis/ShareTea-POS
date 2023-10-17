package pointOfSales.entities;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * Represents an inventory with details such as name, details, quantity, and the
 * last updated date.
 */
public class inventory {
    /** The name of the inventory item. */
    String Name = null;
    /** Detailed description of the inventory item. */
    String Details = null;
    /** Quantity of the inventory item. */
    int Quantity = 0;
    /** The last updated date of the inventory item. */
    Date LastUpdated = null;
    /** Connection to the database. */
    Connection conn = null;

    /**
     * Initializes a new instance of the inventory class with a database connection.
     *
     * @param conn Database connection.
     */
    public inventory(Connection conn) {
        this.conn = conn;
    }

    /**
     * Initializes a new instance of the inventory class with a database connection
     * and a name.
     *
     * @param conn Database connection.
     * @param Name Name of the inventory item.
     */
    public inventory(Connection conn, String Name) {
        this.Name = Name;
        this.conn = conn;

    }

    /**
     * Initializes a new instance of the inventory class with detailed parameters.
     *
     * @param conn        Database connection.
     * @param Name        Name of the inventory item.
     * @param Details     Detailed description of the inventory item.
     * @param Quantity    Quantity of the inventory item.
     * @param LastUpdated The last updated date of the inventory item in
     *                    "yyyy-MM-dd" format.
     */
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

    /**
     * Creates a new inventory item in the database.
     *
     * @param conn              Database connection.
     * @param name              Name of the inventory item.
     * @param inventoryQuantity Quantity of the inventory item.
     */
    public static void createInventory(Connection conn, String name, int inventoryQuantity) {
        try {
            String query = "SELECT MAX(inventory_id) FROM inventory";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            int id = -1;
            while (resultSet.next()) {
                id = resultSet.getInt("max") + 1;
            }

            String sql = "INSERT INTO inventory (inventory_id, name, details, quantity) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt;
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, "");
            pstmt.setInt(4, inventoryQuantity);
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

    /**
     * Retrieves the ID of the inventory item by its name.
     *
     * @param conn          Database connection.
     * @param inventoryName Name of the inventory item.
     * @return The ID of the inventory item, or -1 if not found.
     */
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

    /**
     * Retrieves all inventory items from the database.
     *
     * @param conn Database connection.
     * @return A list containing details of all inventory items.
     */
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

    /**
     * Deletes an inventory item by its name from the database.
     *
     * @param conn          Database connection.
     * @param inventoryName Name of the inventory item.
     */
    public static void deleteInv(Connection conn, String inventoryName) {
        String updateQuery = "DELETE FROM inventory WHERE name = ?";
        try {
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setString(1, inventoryName);
            int count = updateStatement.executeUpdate();
            if (count > 0) {
                System.out.println("Deleted item.");
            }
        } catch (Exception e) {
            System.out.println(
                    "Error");
        }
    }

    /**
     * Adds or subtracts the quantity of an inventory item.
     * If the item doesn't exist, it creates a new entry.
     *
     * @param conn            Database connection.
     * @param inventoryName   Name of the inventory item.
     * @param inventoryNumber Quantity to be added/subtracted.
     */
    public static void addSubInventory(Connection conn, String inventoryName, int inventoryNumber) {
        java.util.Date currentDateUtil = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDateStr = sdf.format(currentDateUtil);
        Date currentDateSql = Date.valueOf(formattedDateStr);

        String updateQuery = "UPDATE inventory SET quantity = quantity + ? WHERE name = ?";
        String updateDateQuery = "UPDATE inventory SET last_updated = ? WHERE name = ?";

        try {
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            PreparedStatement updateDateStatement = conn.prepareStatement(updateDateQuery);
            updateStatement.setInt(1, inventoryNumber);
            updateStatement.setString(2, inventoryName);
            updateDateStatement.setDate(1, currentDateSql);
            updateDateStatement.setString(2, inventoryName);
            int count = updateStatement.executeUpdate();
            if (count < 1) {
                createInventory(conn, inventoryName, inventoryNumber);
            }
            updateDateStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(
                    "Error createOrder(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
    }

}
