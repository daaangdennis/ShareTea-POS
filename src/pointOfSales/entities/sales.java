package pointOfSales.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.text.DecimalFormat;

/**
 * Represents sales-related functionalities in the Point of Sale system.
 */
public class sales {

    /**
     * Fetches the product sales between the given start and end dates.
     *
     * @param conn      Database connection.
     * @param startDate Start date of the range.
     * @param endDate   End date of the range.
     * @return A list of product names and their respective sold quantities.
     */
    public static ArrayList<ArrayList<Object>> ProductSales(Connection conn, String startDate, String endDate) {
        ArrayList<ArrayList<Object>> productSaleList = new ArrayList<>();
        ArrayList<Object> nameList = new ArrayList<>();
        ArrayList<Object> soldList = new ArrayList<>();
        productSaleList.add(nameList);
        productSaleList.add(soldList);

        try {
            String query = "SELECT (SELECT name FROM product WHERE product_id = sub.product_id) AS product_name, SUM(quantity) AS total_quantity FROM order_product sub WHERE order_id IN (SELECT order_id FROM orders WHERE order_date >= ? AND order_date <= ?) GROUP BY product_id ORDER BY product_id";
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setDate(1, Date.valueOf(startDate));
            preparedStatement.setDate(2, Date.valueOf(endDate));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("product_name");
                Integer sold = resultSet.getInt("total_quantity");
                productSaleList.get(0).add(name);
                productSaleList.get(1).add(sold);
            }
            return productSaleList;

        } catch (Exception e) {
            System.out.println("Couldn't display product sales.");
        }
        return productSaleList;
    }

    /**
     * Retrieves products that have low stock in inventory.
     *
     * @param conn Database connection.
     * @return A list of product IDs, names, and the remaining quantities.
     */
    public static ArrayList<ArrayList<Object>> lowStock(Connection conn) {
        ArrayList<ArrayList<Object>> lowStockList = new ArrayList<>();
        ArrayList<Object> idList = new ArrayList<>();
        ArrayList<Object> nameList = new ArrayList<>();
        ArrayList<Object> lowList = new ArrayList<>();
        lowStockList.add(idList);
        lowStockList.add(nameList);
        lowStockList.add(lowList);

        try {
            String query = "SELECT inventory_id, name, quantity AS remaining FROM inventory inv WHERE quantity < 25";
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("inventory_id");
                String name = resultSet.getString("name");
                Integer num = resultSet.getInt("remaining");
                lowStockList.get(0).add(id);
                lowStockList.get(1).add(name);
                lowStockList.get(2).add(num);
            }
            return lowStockList;

        } catch (Exception e) {
            System.out.println("Couldn't display low stock.");
        }
        return lowStockList;
    }

    /**
     * Calculates inventory products that are excessively stocked based on
     * the usage since the provided start date.
     *
     * @param conn      Database connection.
     * @param startDate The start date for the inventory usage evaluation.
     * @return A list of inventory products that are under-utilized.
     */
    public static ArrayList<ArrayList<Object>> excessStock(Connection conn, String startDate) {
        ArrayList<ArrayList<Object>> inventoryUsage = new ArrayList<>();
        ArrayList<Object> idList = new ArrayList<>();
        ArrayList<Object> nameList = new ArrayList<>();
        ArrayList<Object> usedList = new ArrayList<>();
        ArrayList<Object> totalList = new ArrayList<>();
        inventoryUsage.add(idList);
        inventoryUsage.add(nameList);
        inventoryUsage.add(usedList);
        inventoryUsage.add(totalList);

        try {
            String query = "SELECT ip.inventory_id, (SELECT i.name FROM inventory i WHERE i.inventory_id = ip.inventory_id) AS inventory_name,  SUM((SELECT COALESCE(SUM(op.quantity),0) FROM order_product op WHERE op.product_id = ip.product_id AND op.order_id IN(SELECT o.order_id FROM orders o WHERE o.order_date >= ?))) AS quantity_used, (SELECT COALESCE(SUM(i.quantity),0) FROM inventory i WHERE i.inventory_id = ip.inventory_id) AS current_quantity FROM inventory_product ip GROUP BY ip.inventory_id, inventory_name";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(startDate));

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("inventory_id");
                String name = resultSet.getString("inventory_name");
                Integer used = resultSet.getInt("quantity_used");
                Integer current = resultSet.getInt("current_quantity");
                Double percentUsed = (used * 1.00) / ((current * 1.00) + (used * 1.00));
                percentUsed = Math.floor(percentUsed * 100) / 100;
                if (percentUsed <= 0.10) {
                    inventoryUsage.get(0).add(id);
                    inventoryUsage.get(1).add(name);
                    inventoryUsage.get(2).add(used);
                    inventoryUsage.get(3).add(current);
                }
            }
            return inventoryUsage;

        } catch (Exception e) {
            System.out.println("Couldn't display excess stock.");
        }
        return inventoryUsage;
    }

    /**
     * Calculates inventory products usage given a timeframe
     *
     * @param conn      Database connection.
     * @param startDate The start date for the inventory usage evaluation.
     * @param endDate The end date for the inventory usage evaluation.
     * @return A list of inventory products as their id, name, and usage.
     */
    public static ArrayList<ArrayList<String>> inventoryUsage(Connection conn, String startDate, String endDate) {
        ArrayList<ArrayList<String>> inventoryUsage = new ArrayList<>();
        ArrayList<String> idList = new ArrayList<>();
        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<String> usedList = new ArrayList<>();
        inventoryUsage.add(idList);
        inventoryUsage.add(nameList);
        inventoryUsage.add(usedList);

        try {
            String query = "SELECT ip.inventory_id, (SELECT i.name FROM inventory i WHERE i.inventory_id = ip.inventory_id) AS inventory_name,  SUM((SELECT COALESCE(SUM(op.quantity),0) FROM order_product op WHERE op.product_id = ip.product_id AND op.order_id IN(SELECT o.order_id FROM orders o WHERE o.order_date >= ? and o.order_date < date (?) + 1))) AS quantity_used FROM inventory_product ip GROUP BY ip.inventory_id, inventory_name";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(startDate));
            preparedStatement.setString(1, endDate);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getInt("inventory_id") + "";
                String name = resultSet.getString("inventory_name");
                String used = resultSet.getInt("quantity_used") + "";
                inventoryUsage.get(0).add(id + "");
                inventoryUsage.get(1).add(name);
                inventoryUsage.get(2).add(used + "");

            }
            return inventoryUsage;

        } catch (Exception e) {
            System.out.println("Couldn't display excess stock.");
        }
        return inventoryUsage;
    }


    /**
     * Identifies commonly bought product pairings between the given start
     * and end dates.
     *
     * @param conn      Database connection.
     * @param startDate Start date of the range.
     * @param endDate   End date of the range.
     * @return A list of product pairs and the number of times they were purchased
     *         together.
     */
    public static ArrayList<ArrayList<Object>> commonPairs(Connection conn, String startDate, String endDate) {
        ArrayList<ArrayList<Object>> pairList = new ArrayList<>();
        ArrayList<Object> product1 = new ArrayList<>();
        ArrayList<Object> product2 = new ArrayList<>();
        ArrayList<Object> count = new ArrayList<>();
        pairList.add(product1);
        pairList.add(product2);
        pairList.add(count);

        try {
            String query = "SELECT p1.name AS product1, p2.name AS product2, COUNT(DISTINCT op1.order_id) AS combination_count FROM order_product AS op1 JOIN order_product AS op2 ON op1.order_id = op2.order_id AND op1.product_id < op2.product_id JOIN orders AS o ON op1.order_id = o.order_id JOIN product AS p1 ON op1.product_id = p1.product_id JOIN product AS p2 ON op2.product_id = p2.product_id WHERE o.order_date >= ? AND o.order_date <= ? GROUP BY p1.name, p2.name ORDER BY combination_count desc";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(startDate));
            preparedStatement.setDate(2, Date.valueOf(endDate));

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name1 = resultSet.getString("product1");
                String name2 = resultSet.getString("product2");
                Integer combination_count = resultSet.getInt("combination_count");
                pairList.get(0).add(name1);
                pairList.get(1).add(name2);
                pairList.get(2).add(combination_count);

            }
            return pairList;

        } catch (Exception e) {
            System.out.println("Couldn't display common pairings.");
        }
        return pairList;
    }
}
