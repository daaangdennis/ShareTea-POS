package pointOfSales.services;
import pointOfSales.entities.*;
import java.sql.*;
import java.util.ArrayList;

public class addOrderFull {

    public String nextOrderID(){
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;
        order order_obj = new order(conn);
        return (order_obj.nextAvailableOrder()+"");

    }
    public void addOrder(String customerFirst, String customerLast, String employeeFirst, String employeeLast, ArrayList<String> orderProducts) {
        dbconnect dbconn = new dbconnect();
        Connection conn = dbconn.conn;

        customer customer_obj = new customer(conn);
        customer_obj.getCustomerByName(customerFirst, customerLast);

        employee employee_obj = new employee(conn);
        employee_obj.getEmployeeByName(employeeFirst, employeeLast);

        order new_order = new order(conn, customer_obj.CustomerID, employee_obj.EmployeeID, 0.00);
        int order_id = new_order.createOrder();

        orderProduct orderProductObj = new orderProduct(conn);
        product productObj = new product(conn);

        for(int i = 0; i < orderProducts.size(); i+=3){
            int productID = productObj.getProductByName(orderProducts.get(i));
            int quantity = Integer.parseInt(orderProducts.get(i+1));
            String note = orderProducts.get(i+2);
            orderProductObj.addOrderProduct(productID, order_id, quantity, note);

            double productPrice = productObj.getProductPriceByID(productID);
            new_order.updateTotal(order_id, productPrice*quantity);
        }

    }

}
