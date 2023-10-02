import java.sql.*;

public class SystemFunctions {
    
    public String verify(Connection conn, String PW){
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
                            return "Cashier";
                        case "MANAGER":
                            return "Manager";
                        default:
                            return "Invalid";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Invalid";   
    }



}
