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

    public static ArrayList<ArrayList<String>> getProducts(){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return product.getProductInfo(conn);
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

    public static void updateInventory(String inventoryName, Integer inventoryNumber){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        inventory.addSubInventory(conn, inventoryName, inventoryNumber);
    }

    public static void deleteInventory(String inventoryName){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        inventory.deleteInv(conn, inventoryName);
    }

    public static void updateAddProduct(String productName, String productCategory , Double productPrice){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        product.updateAddProduct(conn, productName, productCategory, productPrice);
    }

    public static void deleteProduct(String productName){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        product.deletePro(conn, productName);
    }

}
