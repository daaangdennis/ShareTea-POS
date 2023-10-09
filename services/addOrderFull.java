package services;
import entities.*;
import java.sql.*;
import java.util.ArrayList;

public class addOrderFull {

    public static String nextOrderID(){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return (order.nextAvailableOrder(conn)+"");

    }
    public static void addOrder(String customerFirst, String customerLast, String employeeFirst, String employeeLast, ArrayList<String> orderProducts) {
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;

        int customerID = customer.getCustomerByName(conn, customerFirst, customerLast);
        int employeeID = employee.getEmployeeByName(conn, employeeFirst, employeeLast);
        int order_id = order.createOrder(conn, customerID, employeeID);

        for(int i = 0; i < orderProducts.size(); i+=3){
            int productID = product.getProductByName(conn, orderProducts.get(i));
            int quantity = Integer.parseInt(orderProducts.get(i+1));
            String note = orderProducts.get(i+2);
            orderProduct.addOrderProduct(conn, productID, order_id, quantity, note);

            double productPrice = product.getProductPriceByID(conn, productID);
            order.updateTotal(conn, order_id, productPrice*quantity);
        }

    }

}