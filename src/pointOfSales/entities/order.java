package pointOfSales.entities;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class order {
    /** The ID of the customer associated with the order. */
    int CustomerID;
    /** The ID of the employee processing the order. */
    int EmployeeID;
    /** The date the order was placed. */
    Date OrderDate = null;
    /** The total value of the order. */
    BigDecimal Total = null;
    /** Flag to determine if the order is pending. */
    boolean IsPending = true;
    /** Flag to determine if the order is refunded. */
    boolean IsRefunded = false;
    /** Database connection. */
    Connection conn = null;

    /**
     * Constructs an order object with a database connection.
     * 
     * @param conn The database connection.
     */
    public order(Connection conn) {
        this.conn = conn;
    }

    /**
     * Constructs an order with essential details.
     * 
     * @param conn       The database connection.
     * @param CustomerID The customer's ID.
     * @param EmployeeID The employee's ID.
     * @param Total      The order's total amount.
     */
    public order(Connection conn, int CustomerID, int EmployeeID, double Total) {
        this.CustomerID = CustomerID;
        this.EmployeeID = EmployeeID;
        this.Total = new BigDecimal(Total).setScale(2, RoundingMode.HALF_UP);
        this.conn = conn;
    }

    /**
     * Constructs an order with essential details.
     * 
     * @param conn       The database connection.
     * @param CustomerID The customer's ID.
     * @param EmployeeID The employee's ID.
     * @param Total      The order's total amount.
     * @param OrderDate  The date of the order.
     * @param IsPending  The flag for order being pending.
     */
    public order(Connection conn, int CustomerID, int EmployeeID, double Total, String OrderDate, boolean IsPending,
            boolean IsRefunded) {
        this.CustomerID = CustomerID;
        this.EmployeeID = EmployeeID;
        this.conn = conn;
        this.Total = new BigDecimal(Total).setScale(2, RoundingMode.HALF_UP);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(OrderDate);
            this.OrderDate = new java.sql.Date(utilDate.getTime());
        } catch (Exception e) {
            System.out.println(
                    "Error order(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }

    }

    /**
     * Inserts a new order record into the database and returns its generated ID.
     * 
     * @param conn       The database connection.
     * @param CustomerID The customer's ID.
     * @param EmployeeID The employee's ID.
     * @param Total      The order's total amount.
     * @return The generated ID of the newly created order.
     */
    public static int createOrder(Connection conn, int CustomerID, int EmployeeID, double Total) {
        int returnOrderID = -1;
        try {
            PreparedStatement pstmt;
            if (CustomerID == -2) {
                String sql = "INSERT INTO orders (Employee_ID, Total, Is_Pending, Is_Refunded ) VALUES (?, ?, ? , ?)";
                pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, EmployeeID);
                pstmt.setBigDecimal(2, BigDecimal.valueOf(Total));
                pstmt.setBoolean(3, true);
                pstmt.setBoolean(4, false);
            } else {
                String sql = "INSERT INTO orders (Customer_ID, Employee_ID, Total, Is_Pending, Is_Refunded ) VALUES (?, ?, ?, ? , ?)";
                pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, CustomerID);
                pstmt.setInt(2, EmployeeID);
                pstmt.setBigDecimal(3, BigDecimal.valueOf(Total));
                pstmt.setBoolean(4, true);
                pstmt.setBoolean(5, false);
            }

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Order added successfully!");
                ResultSet Keys = pstmt.getGeneratedKeys();
                if (Keys.next()) {
                    returnOrderID = Keys.getInt(1);
                }
            } else {
                System.out.println("Failed to add the order.");
            }

        } catch (Exception e) {
            System.out.println(
                    "Error createOrder(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
        return returnOrderID;

    }

    /**
     * Updates the total value of a specified order.
     * 
     * @param conn               The database connection.
     * @param order_id           The ID of the order to update.
     * @param orderProduct_price The price to add to the order's total.
     */
    public static void updateTotal(Connection conn, int order_id, double orderProduct_price) {
        String updateQuery = "UPDATE orders SET total = total + ? WHERE order_id = ?";
        try {
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setDouble(1, orderProduct_price);
            updateStatement.setInt(2, order_id);
            updateStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(
                    "Error createOrder(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
    }

    /**
     * Retrieves the next available order number.
     * 
     * @param conn The database connection.
     * @return The next available order number.
     */
    public static int nextAvailableOrder(Connection conn) {
        int order_num = -1;
        String query = "SELECT MAX(order_id) FROM orders";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                order_num = resultSet.getInt("max") + 1;
            }
            return order_num;
        } catch (Exception e) {
            System.out.println(
                    "Error createOrder(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
        return order_num;
    }

    /**
     * Fetches a list of orders placed within a specified date range.
     * The results include the order ID, customer's name, and order date.
     * 
     * @param conn      The database connection.
     * @param startDate The starting date of the range.
     * @param endDate   The ending date of the range.
     * @return A list of orders' details placed within the specified range.
     */
    public static ArrayList<ArrayList<String>> OrdersByDates(Connection conn, String startDate, String endDate) {
        ArrayList<ArrayList<String>> ordersByDatesList = new ArrayList<>();
        ArrayList<String> ids = new ArrayList<>();
        ArrayList<String> customerName = new ArrayList<>();
        ArrayList<String> orderDate = new ArrayList<>();
        ordersByDatesList.add(ids);
        ordersByDatesList.add(customerName);
        ordersByDatesList.add(orderDate);

        String query = "select order_id, concat_ws(' ',first_name, last_name), order_date from orders join customer on orders.customer_id = customer.customer_id where order_date >= ? and order_date < date (?) + 1 order by order_date desc";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setDate(1, Date.valueOf(startDate));
            statement.setString(2, endDate);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getInt("order_id") + "";
                String name = resultSet.getString("concat_ws");
                String date = resultSet.getDate("order_date") + "";
                ordersByDatesList.get(0).add(id);
                ordersByDatesList.get(1).add(name);
                ordersByDatesList.get(2).add(date);
            }

            return ordersByDatesList;
        } catch (Exception e) {
            System.out.println("Error getting orders between date");
        }
        return ordersByDatesList;
    }

}
