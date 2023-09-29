import java.sql.*;
import entities.*;

// To run this example write: 
// javac */**.java *.java
// java -cp ".:postgresql-42.2.8.jar" dbconnectionexample.java

public class dbconnectionexample {
  public static void main(String args[]) {
    Connection conn = null;
    dbconfig my = new dbconfig();
    try {
      Class.forName("org.postgresql.Driver");
      conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315_904_03db",
          my.user, my.pswd);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    System.out.println("Opened database successfully");
    try {
      customer abdullah = new customer(conn);
      abdullah.createCustomer();
    } catch (Exception e) {
      System.out.println("Error accessing Database.");
    }
    try {
      conn.close();
      System.out.println("Connection Closed.");
    } catch (Exception e) {
      System.out.println("Connection NOT Closed.");
    }
  }
}