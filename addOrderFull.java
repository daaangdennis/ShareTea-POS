import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import entities.*;
import java.sql.*;

public class addOrderFull {
    public int order_id;



    public void addOrderToDB(Connection conn) {
        DateTimeFormatter date_format = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
        LocalDateTime now = LocalDateTime.now();  
        String actual_date = date_format.format(now);
        
        order new_order = new order(10, 3, 0.00, "2000-01-01", true, false); //CHANGE DATE, 2000 IS JUST FOR TESTING
        this.order_id = new_order.createOrder(conn);
        System.out.println(this.order_id);
    }



    public void addOrderProductsToDB(Connection conn){
        int product_id = 1; 
        int quantity = 1;
        orderProduct new_OrderProduct = new orderProduct(5, order_id, 1, "Hello"); //CHANGE TO MANUAL PRODUCT_ID, QUANTITY AND NOTE
        int orderProduct_ID = new_OrderProduct.createOrderProduct(conn);

        /*try { //IN CASE QUANTITY NEEDS TO BE FETCHED?
            String quantity_query = "SELECT quantity FROM order_product WHERE order_product_id = ?";
            PreparedStatement pstmtQuantity = conn.prepareStatement(quantity_query);
            pstmtQuantity.setInt(1, orderProduct_ID);
            ResultSet resultSet = pstmtQuantity.executeQuery();
            if (resultSet.next()) {
                quantity = resultSet.getInt("quantity");  
            }
        } 
        catch (Exception e) {
            System.out.println("Error when calculating quantity.");
        }*/

        double product_price = 0;
        try {
            String price_query = "SELECT price FROM product WHERE product_id = ?";
            PreparedStatement pstmtPrice = conn.prepareStatement(price_query);
            pstmtPrice.setInt(1, product_id);
            ResultSet resultSet = pstmtPrice.executeQuery();
            if (resultSet.next()) {
                product_price = resultSet.getDouble("price");  
            }
        } 
        catch (Exception e) {
            System.out.println("Error when calculating price.");
        }
        updateTotal(conn, product_price*quantity);
    }



    public void updateTotal(Connection conn, double orderProduct_price){
        String updateQuery = "UPDATE orders SET total = total + ? WHERE order_id = ?";
        try {
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setDouble(1, orderProduct_price);
            updateStatement.setInt(2, this.order_id);
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Couldn't update total.");
        }
    }
 
}
