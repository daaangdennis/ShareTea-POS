package services;
import entities.*;
import java.sql.*;

public class addOrderFull {

    public int nextOrderID(){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        order order_obj = new order(conn);
        return order_obj.nextAvailableOrder();

    }
    public int addOrderToDB() {
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        order new_order = new order(conn, 10, 3, 0.00);
        int order_id = new_order.createOrder();
        return order_id;
    }

    public void addOrderProductsToDB(int order_id ,int product_id, int quantity) {
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        orderProduct new_OrderProduct = new orderProduct(conn, product_id, order_id, quantity, "Hello");
        new_OrderProduct.createOrderProduct();
        double product_price = new product(conn).getProductPriceByID(product_id);
        new order(conn).updateTotal(order_id, product_price * quantity);
    }

}
