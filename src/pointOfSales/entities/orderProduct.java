package pointOfSales.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Represents an item in an order with details about product, quantity, and
 * customizations.
 */
public class orderProduct {
    /** The Product ID associated with the order item. */
    public int ProductID;
    /** The Order ID associated with the order item. */
    public int OrderID;
    /** The Quantity of the product ordered. */
    public int Quantity;
    /** List of Toppings added to the product. */
    public ArrayList<String> Toppings;
    /** Special notes or instructions for the product. */
    public String Note = "";
    /** Percentage of sugar to be added. Default is 100%. */
    public double SugarLevel = 100;
    /** Level of ice to be added to the product. */
    public String IceLevel = "";

    /**
     * Constructs an orderProduct object with all customizations.
     *
     * @param PID             Product ID as a string.
     * @param ProductQuantity Quantity of product ordered.
     * @param ToppingList     List of toppings.
     * @param Sugar           Sugar percentage.
     * @param ice             Ice level.
     * @param NoteInput       Special notes or instructions.
     */
    public orderProduct(String PID, int ProductQuantity, ArrayList<String> ToppingList, double Sugar, String ice,
            String NoteInput) {
        this.ProductID = Integer.parseInt(PID);
        this.Quantity = ProductQuantity;
        this.Toppings = ToppingList;
        this.SugarLevel = Sugar;
        this.IceLevel = ice;
        this.Note = NoteInput;
    }

    /**
     * Constructs an orderProduct object without toppings.
     *
     * @param PID             Product ID as a string.
     * @param ProductQuantity Quantity of product ordered.
     * @param Sugar           Sugar percentage.
     * @param ice             Ice level.
     * @param NoteInput       Special notes or instructions.
     */
    public orderProduct(String PID, int ProductQuantity, double Sugar, String ice, String NoteInput) {
        this.ProductID = Integer.parseInt(PID);
        this.Quantity = ProductQuantity;
        this.SugarLevel = Sugar;
        this.IceLevel = ice;
        this.Note = NoteInput;
    }

    /**
     * Inserts a new order product into the database.
     *
     * @param conn      Database connection.
     * @param productID Product ID.
     * @param orderID   Order ID.
     */
    public void addOrderProduct(Connection conn, int productID, int orderID) {
        String toppingList = "";
        if (!Toppings.isEmpty()) {
            for (int i = 0; i < Toppings.size() - 1; ++i) {
                toppingList += Toppings.get(i) + ", ";
            }
            toppingList += Toppings.get(Toppings.size() - 1);
        }
        if (this.SugarLevel != 100) {
            if (Note.equals("")) {
                Note += (SugarLevel) + "%" + " sugar.";
            } else {
                Note += " " + (SugarLevel) + "%" + " sugar.";
            }
        }
        if (this.IceLevel != "") {
            if (Note.equals("")) {
                Note += (IceLevel);
            } else {
                Note += " " + (IceLevel);
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

    /**
     * Subtracts the inventory for the ordered product.
     *
     * @param conn Database connection.
     */
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

    /**
     * Subtracts the inventory for the toppings of the ordered product.
     *
     * @param conn Database connection.
     */
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

    /**
     * Retrieves a list of order products associated with a given order ID.
     *
     * @param conn     Database connection.
     * @param order_id Order ID.
     * @return List containing details of order products for the given order ID.
     */
    public static ArrayList<ArrayList<String>> OrderProductByID(Connection conn, Integer order_id) {
        ArrayList<ArrayList<String>> OrderProductList = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> quantities = new ArrayList<>();
        ArrayList<String> prices = new ArrayList<>();
        ArrayList<String> totalPrice = new ArrayList<>();
        OrderProductList.add(names);
        OrderProductList.add(quantities);
        OrderProductList.add(prices);
        OrderProductList.add(totalPrice);
        double total = 0;

        String query = "select name, quantity, price, toppings from order_product op join product pro on op.product_id = pro.product_id where order_id = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, order_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                Integer quantity = resultSet.getInt("quantity");
                String tops = resultSet.getString("toppings");
                Double topsPrice = 0.00;
                if(tops != null){
                    long count = tops.chars().filter(ch -> ch == ',').count();
                    topsPrice = (count+1)*0.75;
                }
                total += (resultSet.getDouble("price") * quantity) + topsPrice;
                String price = String.format("%.2f", resultSet.getDouble("price") * quantity + topsPrice);

                OrderProductList.get(0).add(name);
                OrderProductList.get(1).add(quantity + "");
                OrderProductList.get(2).add(price);
            }
            OrderProductList.get(3).add(String.format("%.2f", total));

            return OrderProductList;
        } catch (Exception e) {
            System.out.println("Error getting order_product given an order_id");
        }
        return OrderProductList;
    }
}
