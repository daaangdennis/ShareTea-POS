package pointOfSales.entities;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Represents an employee in the point of sales system.
 */
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

    /**
     * Initializes an employee with a database connection.
     *
     * @param conn The database connection to be used.
     */
    public employee(Connection conn) {
        this.conn = conn;
    }

    /**
     * Initializes an employee with essential details.
     *
     * @param conn      The database connection to be used.
     * @param FirstName First name of the employee.
     * @param LastName  Last name of the employee.
     * @param Position  Position of the employee (e.g., MANAGER, CASHIER).
     * @param Passcode  Unique passcode for the employee.
     */
    public employee(Connection conn, String FirstName, String LastName, String Position, String Passcode) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Position = Position.toUpperCase() == "MANAGER" ? "MANAGER" : "CASHIER";
        this.Passcode = Passcode;
        this.conn = conn;
    }

    /**
     * Initializes an employee with detailed attributes.
     *
     * @param conn        The database connection to be used.
     * @param FirstName   First name of the employee.
     * @param LastName    Last name of the employee.
     * @param Position    Position of the employee (e.g., MANAGER, CASHIER).
     * @param Email       Email of the employee.
     * @param PhoneNumber Phone number of the employee.
     * @param HireDate    The date the employee was hired.
     * @param Passcode    Unique passcode for the employee.
     */
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

    /**
     * Adds the employee to the database.
     */
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

    /**
     * Retrieves an employee from the database by their unique ID.
     *
     * @param EmployeeID The unique identifier for the employee.
     * @return An employee object if found; null otherwise.
     */
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

    /**
     * Retrieves an employee's unique ID by their first and last name.
     *
     * @param conn      The database connection to be used.
     * @param FirstName First name of the employee.
     * @param LastName  Last name of the employee.
     * @return The unique identifier for the employee if found; -1 otherwise.
     */
    public static int getEmployeeByName(Connection conn, String FirstName, String LastName) {
        int resultEmployee = -1;
        try {
            String sql = "SELECT * FROM employee WHERE first_name = ? AND last_name = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, FirstName);
            pstmt.setString(2, LastName);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                resultEmployee = rs.getInt("employee_id");
                return resultEmployee;
            } else {
                System.out.println("Employee not found.");
            }

        } catch (Exception e) {
            System.out.println(
                    "Error getEmployeeByName(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
        return resultEmployee;
    }

    /**
     * Verifies an employee's credentials based on their passcode.
     *
     * @param conn The database connection to be used.
     * @param pw   The passcode to verify.
     * @return A list containing the verified employee's first name, last name,
     *         position, and ID.
     */
    public static ArrayList<String> verifyEmployee(Connection conn, String pw) {
        ArrayList<String> employee_verify = new ArrayList<>();
        try {
            String query = "SELECT first_name, last_name, position, employee_id FROM employee WHERE passcode = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, pw);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String position = resultSet.getString("position");
                String employeeID = resultSet.getString("employee_id");

                employee_verify.add(firstName);
                employee_verify.add(lastName);
                employee_verify.add(position);
                employee_verify.add(employeeID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee_verify;
    }

}
