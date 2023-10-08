package services;
import java.sql.*;
import entities.*;
import java.util.ArrayList;

public class SystemFunctions {
    public ArrayList<String> verify(String PW){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        employee verify_employee = new employee(conn);
        return verify_employee.verifyEmployee(conn, PW);
    }

    public ArrayList<String> productsAndPriceByCategory(String category){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        product products_obj = new product(conn);
        return products_obj.getProductsPriceByCategory(category);
    }

    public ArrayList<String> getCategories(){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        product products_obj = new product(conn);
        return products_obj.getCategories();
    }

}
