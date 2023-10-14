import java.sql.*;

// To run this example write: 
// javac dbconfig.java
// java -cp ".:postgresql-42.2.8.jar" jdbcpostgreSQL.java

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
      DatabaseMetaData metaData = conn.getMetaData();
      String[] types = { "TABLE" };
      ResultSet tables = metaData.getTables(null, null, "%", types);
      while (tables.next()) {
        System.out.println(tables.getString("TABLE_NAME"));
      }
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
