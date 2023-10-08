package entities;
import java.sql.Connection;
import java.sql.DriverManager;

public class dbconnect {
    public Connection conn;

    public dbconnect() {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315_904_03db",
                    dbconfig.user, dbconfig.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR: startConnection(): " + e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Opened database successfully");
    }

}
