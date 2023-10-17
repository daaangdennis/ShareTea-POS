package pointOfSales.services;

import java.sql.*;
import pointOfSales.entities.*;
import java.util.ArrayList;

public class SystemFunctions {

    // ------------------ ORDER FUNCTIONS ----------------------//

    // ------------------ ORDER FUNCTIONS ----------------------// 

    /** 
     * Looks in the database and gets the lowest available order_id 
     * @return String of next order ID
     * @see {@link  entities.product#nextAvailableOrder()  nextAvailableOrder()}
     */
    public static String nextOrderID(){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return (order.nextAvailableOrder(conn) + "");
    }

    /**
     * Adds order details in database from given parameters
     * @param customerFirst
     * @param customerLast
     * @param employeeFirst
     * @param employeeLast
     * @param orderProducts
     * @param orderTotal
     */
    public static void addOrder(String customerFirst, String customerLast, String employeeFirst, String employeeLast, ArrayList<orderProduct> orderProducts, double orderTotal) {
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



    // ------------------ SYSTEM FUNCTIONS ----------------------// 

    /**
     * Checks for valid passcode in database from given passcode and if there's a match, returns that employee's information
     * @param PW
     * @return Employee position, name, and id
     */
    public static ArrayList<String> verify(String PW){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return employee.verifyEmployee(conn, PW);
    }

    /**
     * Fetches information from all products in database
     * @return ArrayList<ArrayList<String> with 4 indexes, for IDs, Names, Categories, and Prices
     */
    public static ArrayList<ArrayList<String>> getProducts(){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return product.getProductInfo(conn);
    }

    /**
     * Fetches information from all products in database given a category
     * @param Category
     * @return ArrayList<ArrayList<String> with 3 indexes, for Names, Prices, and IDs
     */
    public static ArrayList<ArrayList<String>> productsAndPriceByCategory(String Category){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return product.getProductsPriceByCategory(conn, Category);
    }

    /**
     * Returns all categories from database
     * @return List of strings for all categories in database
     */
    public static ArrayList<String> getCategories(){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return product.getCategories(conn);
    }

    /**
     * Fetches all inventory data from database
     * @return ArrayList<ArrayList<String>> with 4 indexes, for IDs, Names, Quantities, and Last Updated 
     */
    public static ArrayList<ArrayList<String>> getInventory(){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return inventory.getInventory(conn);
    }

    /**
     * Given a name for an inventory item and a number, modifies the stock if the inventory exists. Otherwise, creates an inventory item
     * @param inventoryName
     * @param inventoryNumber
     */
    public static void updateInventory(String inventoryName, Integer inventoryNumber){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        inventory.addSubInventory(conn, inventoryName, inventoryNumber);
    }

    /**
     * Deletes item from the database if it exists
     * @param inventoryName
     */
    public static void deleteInventory(String inventoryName){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        inventory.deleteInv(conn, inventoryName);
    }

    /**
     * Given a name for a product, modifies its price and/or category if the product exists. Otherwise, creates a new product
     * @param productName
     * @param productCategory
     * @param productPrice
     */
    public static void updateAddProduct(String productName, String productCategory , Double productPrice){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        product.updateAddProduct(conn, productName, productCategory, productPrice);
    }

    /**
     * Deletes product from the database if it exists
     * @param productName
     */
    public static void deleteProduct(String productName){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        product.deletePro(conn, productName);
    }

    /**
     * Given a timeframe, returns order info from existing orders in the database
     * @param startDate
     * @param endDate
     * @return ArrayList with 3 lists, for IDs, customer name, and order date 
     */
    public static ArrayList<ArrayList<String>> getOrdersByDates(String startDate, String endDate){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return order.OrdersByDates(conn, startDate, endDate);
    }

    /**
     * Given an order id, returns product info associated with that order
     * @param order_id
     * @return ArrayList with 4 lists, for product names, product quantity, price, and total
     */
    public static ArrayList<ArrayList<String>> getOrderProductByID(Integer order_id){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return orderProduct.OrderProductByID(conn, order_id);
    }



    
    // ------------------ SALE REPORTS FUNCTIONS ----------------------// 

    /**
     * Given a timeframe, returns the number of products sold for all products in the database
     * @param startDate
     * @param endDate
     * @return ArrayList<ArrayList<Object>> with 2 lists, one having the product name and the other the number of products sold
     */
    public static ArrayList<ArrayList<Object>> getProductSales(String startDate, String endDate){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return sales.ProductSales(conn, startDate, endDate);
    }

    /**
     * Fetches inventory information for items with less than or equal to 25 in stock
     * @return ArrayList<ArrayList<Object>> with 3 lists, IDs, names, and quantity remaining
     */
    public static ArrayList<ArrayList<Object>> getLowStock(){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return sales.lowStock(conn);
    }

    /**
     * Given a date, returns a list of inventory items that have sold less than 10% of their stock from the last inventory update
     * @param startDate
     * @return ArrayList<ArrayList<Object>> with 4 lists, IDs, names, quantity used, and quantity remaining
     */
    public static ArrayList<ArrayList<Object>> getExcessStock(String startDate){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return sales.excessStock(conn, startDate);
    }

    /**
     * Returns most common product pairings given a timeframe
     * @param startDate
     * @param endDate
     * @return ArrayList<ArrayList<Object>> with 3 lists, product 1, product 2, and the combination count
     */
    public static ArrayList<ArrayList<Object>> getPairs(String startDate, String endDate){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return sales.commonPairs(conn, startDate, endDate);
    }

}
