package entities;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class employee {
    String FirstName = null;
    String LastName = null;
    String Position = null;
    String Email = null;
    String PhoneNumber = null;
    Date HireDate = null;
    String Passcode = null;
    Connection conn = null;
    public int EmployeeID = -1;

    public employee(Connection conn) {
        this.conn = conn;
    }

    public employee(Connection conn, String FirstName, String LastName, String Position, String Passcode) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Position = Position.toUpperCase() == "MANAGER" ? "MANAGER" : "CASHIER";
        this.Passcode = Passcode;
        this.conn = conn;
    }

    public employee(Connection conn, String FirstName, String LastName, String Position, String Email,
            String PhoneNumber,
            String HireDate, String Passcode) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.Position = Position.toUpperCase() == "MANAGER" ? "MANAGER" : "CASHIER";
        this.PhoneNumber = PhoneNumber;
        this.Passcode = Passcode;
        this.conn = conn;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(HireDate);
            this.HireDate = new java.sql.Date(utilDate.getTime());
        } catch (Exception e) {
            System.out.println(
                    "Error employee(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
    }

    public void createEmployee() {
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

    public employee getEmployeeByID(int EmployeeID) {
        employee resultEmployee = null;
        try {
            String sql = "SELECT * FROM employee WHERE employee_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, EmployeeID);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                resultEmployee = new employee(conn,
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("position"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getDate("hire_date").toString(),
                        rs.getString("passcode"));
                resultEmployee.EmployeeID = EmployeeID;
            } else {
                System.out.println("Employee not found.");
            }

        } catch (Exception e) {
            System.out.println(
                    "Error getEmployeeByID(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
        return resultEmployee;
    }

    public employee getEmployeeByName(String FirstName, String LastName) {
        employee resultEmployee = null;
        try {
            String sql = "SELECT * FROM employee WHERE first_name = ? AND last_name = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, FirstName);
            pstmt.setString(2, LastName);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                resultEmployee = new employee(conn,
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("position"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getDate("hire_date").toString(),
                        rs.getString("passcode"));
                resultEmployee.EmployeeID = rs.getInt("employee_id");
                this.EmployeeID = rs.getInt("employee_id");
            } else {
                System.out.println("Employee not found.");
            }

        } catch (Exception e) {
            System.out.println(
                    "Error getEmployeeByName(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
        return resultEmployee;
    }

    public ArrayList<String> verifyEmployee(Connection conn, String pw){
        ArrayList<String> employee_verify = new ArrayList<>();
        try {
            String query = "SELECT first_name, last_name, position FROM employee WHERE passcode = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, pw);
    
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String position = resultSet.getString("position");
                
                employee_verify.add(firstName);
                employee_verify.add(lastName);
                employee_verify.add(position);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee_verify;   
    }
}
