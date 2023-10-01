import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import entities.*;
import java.sql.*;

public class addOrderFull {
    public int order_id;

    public void addOrderToDB(Connection conn) {
        order new_order = new order(conn, 10, 3, 0.00);
        this.order_id = new_order.createOrder();
        System.out.println(this.order_id);
    }

    public void addOrderProductsToDB(Connection conn, int product_id, int quantity) {
        orderProduct new_OrderProduct = new orderProduct(conn, 5, order_id, 1, "Hello");
        int orderProduct_ID = new_OrderProduct.createOrderProduct();
        double product_price = new product(conn).getProductPriceByID(product_id);
        new order(conn).updateTotal(order_id, product_price * quantity);
    }

}
