package entities;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;

public class employee {
    String FirstName = null;
    String LastName = null;
    String Position = null;
    String Email = null;
    String PhoneNumber = null;
    Date HireDate = null;
    String Passcode = null;

    public employee(String FirstName, String LastName, String Position, String Passcode) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Position = Position.toUpperCase() == "MANAGER" ? "MANAGER" : "CASHIER";
        this.Passcode = Passcode;
    }

    public employee(String FirstName, String LastName, String Position, String Email, String PhoneNumber,
            String HireDate, String Passcode) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.Position = Position.toUpperCase() == "MANAGER" ? "MANAGER" : "CASHIER";
        this.PhoneNumber = PhoneNumber;
        this.Passcode = Passcode;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(HireDate);
            this.HireDate = new java.sql.Date(utilDate.getTime());
        } catch (Exception e) {
            System.out.println(
                    "Error employee(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
    }

    public void createEmployee(Connection conn) {
        PreparedStatement pstmt;
        try {
            if (HireDate != null) {
                String sql = "INSERT INTO employee (first_name, last_name, position, passcode, hire_date, email, phone_number) VALUES (?, ?, ?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, FirstName);
                pstmt.setString(2, LastName);
                pstmt.setString(3, Position);
                pstmt.setString(4, Passcode);
                pstmt.setDate(5, HireDate);
                pstmt.setString(6, Email);
                pstmt.setString(7, PhoneNumber);
            } else {
                String sql = "INSERT INTO employee (first_name, last_name, position, passcode, email, phone_number) VALUES (?, ?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, FirstName);
                pstmt.setString(2, LastName);
                pstmt.setString(3, Position);
                pstmt.setString(4, Passcode);
                pstmt.setString(5, Email);
                pstmt.setString(6, PhoneNumber);
            }

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("employee added successfully!");
            } else {
                System.out.println("Failed to add the employee.");
            }

        } catch (Exception e) {
            System.out.println(
                    "Error createEmployee(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
    }
}
