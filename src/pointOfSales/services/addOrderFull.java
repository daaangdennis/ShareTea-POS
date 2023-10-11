package pointOfSales.services;
import pointOfSales.entities.*;
import java.sql.*;
import java.util.ArrayList;

public class addOrderFull {

    public static String nextOrderID(){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        return (order.nextAvailableOrder(conn)+"");
    }

    public static void addOrder(String customerFirst, String customerLast, String employeeFirst, String employeeLast, ArrayList<orderProduct> orderProducts, double orderTotal) {
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;

        int customerID = customer.getCustomerByName(conn, customerFirst, customerLast);
        int employeeID = employee.getEmployeeByName(conn, employeeFirst, employeeLast);
        int order_id = order.createOrder(conn, customerID, employeeID, orderTotal);

        for(int i = 0; i < orderProducts.size(); ++i){
            int productID = orderProducts.get(i).ProductID;
            orderProducts.get(i).addOrderProduct(conn, productID, order_id);
        }
    }

}
