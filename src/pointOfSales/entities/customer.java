package pointOfSales.entities;

import java.sql.*;

/**
 * Represents a customer in the point of sales system.
 */
public class customer {
    /** The first name of the customer. */
    String FirstName = null;
    /** The last name of the customer. */
    String LastName = null;
    /** The email of the customer. */
    String Email = null;
    /** The phone number of the customer. */
    String PhoneNumber = null;
    /** The count of orders associated with the customer. */
    int OrderCount = 1;
    /** The database connection used for customer operations. */
    Connection conn = null;
    /** The ID of the customer. */
    public int CustomerID = -1;

    /**
     * Constructs a customer object with a given database connection.
     *
     * @param conn The database connection.
     */
    public customer(Connection conn) {
        this.conn = conn;
    }

    /**
     * Constructs a customer object with details and a database connection.
     *
     * @param conn        The database connection.
     * @param FirstName   The first name of the customer.
     * @param LastName    The last name of the customer.
     * @param Email       The email of the customer.
     * @param PhoneNumber The phone number of the customer.
     */
    public customer(Connection conn, String FirstName, String LastName, String Email, String PhoneNumber) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.PhoneNumber = PhoneNumber;
        this.conn = conn;
    }

    /**
     * Creates a new customer in the database with the given first and last name.
     *
     * @param conn  The database connection.
     * @param First The first name of the new customer.
     * @param Last  The last name of the new customer.
     * @return The ID of the created customer or -1 if the creation fails.
     */
    public static int createCustomer(Connection conn, String First, String Last) {
        int customer_id = -1;
        try {
            String sql = "INSERT INTO customer (first_name, last_name, order_count) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, First);
            pstmt.setString(2, Last);
            pstmt.setInt(3, 1);
            pstmt.executeUpdate();
            System.out.println("Customer added successfully!");

            String query = "SELECT MAX(customer_id) FROM customer";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customer_id = resultSet.getInt("max");
                return customer_id;
            }

        } catch (Exception e) {
            System.out.println(
                    "Error createCustomer(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
        return customer_id;
    }

    /**
     * Retrieves a customer from the database by their ID.
     *
     * @param CustomerID The ID of the customer.
     * @return The customer object or null if not found.
     */
    public customer getCustomerByID(int CustomerID) {
        customer resultCustomer = null;
        try {
            String sql = "SELECT * FROM customer WHERE customer_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, CustomerID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                resultCustomer = new customer(conn,
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone_number"));
                resultCustomer.OrderCount = rs.getInt("order_count");
                this.CustomerID = CustomerID;
            } else {
                System.out.println("Customer not found.");
            }
        } catch (Exception e) {
            System.out.println(
                    "Error getCustomerByID(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
        return resultCustomer;
    }

    /**
     * Retrieves a customer ID from the database by their first and last name.
     * If the customer does not exist and the provided names are not empty, a new
     * 
     * customer will be created.
     * 
     * @param conn     The database connection.
     * @param FirstNam The first name of the customer.
     * @param LastName The last name of the customer.
     * 
     * @return The ID of the found or created customer, -1 if not found or failed,
     *         or -2 if first name is empty.
     */
    public static int getCustomerByName(Connection conn, String FirstName, String LastName) {
        int customerID = -1;
        try {
            String sql = "SELECT * FROM customer WHERE first_name = ? AND last_name = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, FirstName);
            pstmt.setString(2, LastName);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                customerID = rs.getInt("customer_id");
                String addCount = "UPDATE customer SET order_count = order_count + 1 WHERE customer_id = ?";
                PreparedStatement addCountST = conn.prepareStatement(addCount);
                addCountST.setInt(1, customerID);
                addCountST.executeUpdate();
                return customerID;
            } else if (FirstName.isEmpty()) {
                return -2;
            } else {
                customerID = customer.createCustomer(conn, FirstName, LastName);
                return customerID;
            }

        } catch (Exception e) {
            System.out.println(
                    "Error getCustomerByName(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
        return customerID;
    }

}
