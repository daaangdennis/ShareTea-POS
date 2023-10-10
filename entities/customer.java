package entities;
import java.sql.*;

public class customer {
    String FirstName = null;
    String LastName = null;
    String Email = null;
    String PhoneNumber = null;
    int OrderCount = 1;
    Connection conn = null;
    public int CustomerID = -1;

    public customer(Connection conn) {
        this.conn = conn;
    }

    public customer(Connection conn, String FirstName, String LastName, String Email, String PhoneNumber) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.PhoneNumber = PhoneNumber;
        this.conn = conn;
    }

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
                return customerID;
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
