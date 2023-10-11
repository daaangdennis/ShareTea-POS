package pointOfSales.services;
import java.sql.*;
import pointOfSales.entities.*;
import java.util.ArrayList;

public class SystemFunctions {
    public static ArrayList<String> verify(String PW){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return employee.verifyEmployee(conn, PW);
    }



    public static ArrayList<ArrayList<String>> productsAndPriceByCategory(String category){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return product.getProductsPriceByCategory(conn, category);
    }

    public static ArrayList<String> getCategories(){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return product.getCategories(conn);
    }

    public static ArrayList<ArrayList<String>> getInventory(){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return inventory.getInventory(conn);
    }

    public static void updateInventory(String inventoryList, Integer inventoryNumber){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        inventory.addSubInventory(conn, inventoryList, inventoryNumber);
    }

    public static void updateAddProduct(String productName, String productCategory , Double productPrice){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        product.updateAddProduct(conn, productName, productCategory, productPrice);
    }
    
}
