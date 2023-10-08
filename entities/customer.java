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

    public void createCustomer() {
        try {
            String sql = "INSERT INTO customer (first_name, last_name, email, phone_number, order_count) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, this.FirstName);
            pstmt.setString(2, this.LastName);
            pstmt.setString(3, this.Email);
            pstmt.setString(4, this.PhoneNumber);
            pstmt.setInt(5, this.OrderCount);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Customer added successfully!");
            } else {
                System.out.println("Failed to add the customer.");
            }

        } catch (Exception e) {
            System.out.println(
                    "Error createCustomer(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
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
                System.out.println("Customer not found.");
            }

        } catch (Exception e) {
            System.out.println(
                    "Error getCustomerByName(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
        return customerID;
    }

}
