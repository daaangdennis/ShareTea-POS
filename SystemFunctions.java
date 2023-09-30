import entities.*;
import java.sql.*;

public class SystemFunctions {
    
    public int verify(Connection conn, String PW){
        try {
            String query = "SELECT passcode, position FROM employee";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
    
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String realPW = resultSet.getString("passcode");
                String position = resultSet.getString("position");
                
                if (PW.equals(realPW)) {
                    switch (position) {
                        case "CASHIER":
                            return 1;
                        case "MANAGER":
                            return 2;
                        default:
                            return 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;   
    }



}
