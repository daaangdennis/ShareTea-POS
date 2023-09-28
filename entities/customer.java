package entities;
import java.sql.*;

public class customer {
    String FirstName = null;
    String LastName = null;
    String Email = null;
    String PhoneNumber = null;
    int OrderCount = 1;

    public customer() {
    }

    public customer(String FirstName, String LastName, String Email, String PhoneNumber) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.PhoneNumber = PhoneNumber;
    }

    public void createCustomer(Connection conn) {
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

}
