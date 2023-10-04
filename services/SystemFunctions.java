package services;
import java.sql.*;

import entities.employee;

public class SystemFunctions {
    
    public String verify(Connection conn, String PW){
        employee verify_employee = new employee(conn);
        return verify_employee.verifyEmployee(conn, PW);
    }

}
