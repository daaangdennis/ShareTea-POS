package pointOfSales.entities;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Handles the connection to the database for the point of sales system.
 */
public class dbconnect {
    /** The database connection. */
    public Connection conn;

    /**
     * Constructs a dbconnect object and initializes a connection to the database.
     * It uses configurations from the dbconfig class for the username and password.
     * If the connection is successful, a message is printed to the console.
     * If there's an error, the error details are printed to the error console.
     */
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
