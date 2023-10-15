package pointOfSales.services;

import java.sql.*;
import pointOfSales.entities.*;
import java.util.ArrayList;

public class SystemFunctions {

    // ------------------ ORDER FUNCTIONS ----------------------//

    public static String nextOrderID() {
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return (order.nextAvailableOrder(conn) + "");
    }

    public static void addOrder(String customerFirst, String customerLast, String employeeFirst, String employeeLast,
            ArrayList<orderProduct> orderProducts, double orderTotal) {
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;

        int customerID = customer.getCustomerByName(conn, customerFirst, customerLast);
        int employeeID = employee.getEmployeeByName(conn, employeeFirst, employeeLast);
        int order_id = order.createOrder(conn, customerID, employeeID, orderTotal);

        for (int i = 0; i < orderProducts.size(); ++i) {
            int productID = orderProducts.get(i).ProductID;
            orderProducts.get(i).addOrderProduct(conn, productID, order_id);
        }
    }

    // ------------------ SYSTEM FUNCTIONS ----------------------//

    public static ArrayList<String> verify(String PW) {
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return employee.verifyEmployee(conn, PW);
    }

    public static ArrayList<ArrayList<String>> getProducts() {
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return product.getProductInfo(conn);
    }

    public static ArrayList<ArrayList<String>> productsAndPriceByCategory(String Category) {
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return product.getProductsPriceByCategory(conn, Category);
    }

    public static ArrayList<String> getCategories() {
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return product.getCategories(conn);
    }

    public static ArrayList<ArrayList<String>> getInventory() {
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return inventory.getInventory(conn);
    }

    public static void updateInventory(String inventoryName, Integer inventoryNumber) {
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        inventory.addSubInventory(conn, inventoryName, inventoryNumber);
    }

    public static void deleteInventory(String inventoryName) {
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        inventory.deleteInv(conn, inventoryName);
    }

    public static void updateAddProduct(String productName, String productCategory, Double productPrice) {
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        product.updateAddProduct(conn, productName, productCategory, productPrice);
    }

    public static void deleteProduct(String productName) {
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        product.deletePro(conn, productName);
    }

    // ------------------ SALE REPORTS FUNCTIONS ----------------------//

    public static ArrayList<ArrayList<Object>> getProductSales(String startDate, String endDate) {
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return sales.ProductSales(conn, startDate, endDate);
    }

    public static ArrayList<ArrayList<Object>> getLowStock() {
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return sales.lowStock(conn);
    }

    public static ArrayList<ArrayList<Object>> getExcessStock(String startDate) {
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return sales.excessStock(conn, startDate);
    }

    public static ArrayList<ArrayList<Object>> getPairs(String startDate, String endDate){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return sales.commonPairs(conn, startDate, endDate);
    }

}
