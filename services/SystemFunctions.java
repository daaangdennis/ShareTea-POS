package services;
import java.sql.*;
import entities.*;
import java.util.ArrayList;

public class SystemFunctions {
    
    public String verify(Connection conn, String PW){
        employee verify_employee = new employee(conn);
        return verify_employee.verifyEmployee(conn, PW);
    }

    public ArrayList<String> getCategories(Connection conn, String category){
        ArrayList<String> productsByCategory = new ArrayList<String>();
        productsByCategory = new product(conn).getProductByCategory(category);
        return productsByCategory;
    }

}
