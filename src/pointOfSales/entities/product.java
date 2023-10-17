package pointOfSales.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Represents a product with associated details such as name, price, and
 * category.
 */
public class product {
    /** Product name. */
    String Name = null;
    /** Product price. */
    BigDecimal Price = new BigDecimal(0);
    /** Product category. */
    String Category = null;
    /** Product ID. */
    int ProductID = -1;
    /** Database connection object. */
    Connection conn = null;

    /**
     * Constructs a product object initializing the database connection.
     *
     * @param conn The database connection.
     */
    public product(Connection conn) {
        this.conn = conn;
    }

    /**
     * Constructs a product object with name and category.
     *
     * @param conn     The database connection.
     * @param Name     Product name.
     * @param Category Product category.
     */
    public product(Connection conn, String Name, String Category) {
        this.Name = Name;
        this.Category = Category;
    }

    /**
     * Constructs a product object with name, category, and price.
     *
     * @param conn     The database connection.
     * @param Name     Product name.
     * @param Category Product category.
     * @param Price    Product price.
     */
    public product(Connection conn, String Name, String Category, Double Price) {
        this.Name = Name;
        this.Category = Category;
        this.Price = new BigDecimal(Price).setScale(2, RoundingMode.HALF_UP);
        this.conn = conn;
    }

    /**
     * Adds a new product to the database.
     *
     * @param conn     The database connection.
     * @param name     Product name.
     * @param category Product category.
     * @param price    Product price.
     */
    public static void createProduct(Connection conn, String name, String category, double price) {
        try {
            String sql = "INSERT INTO product (name, category, price) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, category);
            pstmt.setBigDecimal(3, BigDecimal.valueOf(price));
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("product added successfully!");
            } else {
                System.out.println("Failed to add the product.");
            }

        } catch (Exception e) {
            System.out.println(
                    "Error createProduct(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
    }

    /**
     * Fetches a list of distinct product categories.
     *
     * @param conn The database connection.
     * @return List of product categories.
     */
    public static ArrayList<String> getCategories(Connection conn) {
        ArrayList<String> category_array = new ArrayList<>();
        try {
            String query = "SELECT DISTINCT category FROM product";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String category_name = resultSet.getString("category");
                category_array.add(category_name);
            }
            return category_array;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category_array;
    }

    /**
     * Fetches information about all products.
     *
     * @param conn The database connection.
     * @return List containing product details.
     */
    public static ArrayList<ArrayList<String>> getProductInfo(Connection conn) {
        ArrayList<ArrayList<String>> product_info = new ArrayList<>();
        ArrayList<String> id_array = new ArrayList<>();
        ArrayList<String> product_array = new ArrayList<>();
        ArrayList<String> category_array = new ArrayList<>();
        ArrayList<String> price_array = new ArrayList<>();
        product_info.add(id_array);
        product_info.add(product_array);
        product_info.add(category_array);
        product_info.add(price_array);
        try {
            String query = "SELECT product_id, name, category, price FROM product";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String ID = resultSet.getInt("product_id") + "";
                String Name = resultSet.getString("name");
                String cat = resultSet.getString("category");
                String Price = String.format("%.2f", resultSet.getDouble("price"));

                product_info.get(0).add(ID);
                product_info.get(1).add(Name);
                product_info.get(2).add(cat);
                product_info.get(3).add(Price);
            }
            return product_info;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product_info;
    }

    /**
     * Fetches information about products based on a specified category.
     *
     * @param conn     The database connection.
     * @param Category The product category.
     * @return List containing product details by category.
     */
    public static ArrayList<ArrayList<String>> getProductsPriceByCategory(Connection conn, String Category) {
        ArrayList<ArrayList<String>> productPrice_array = new ArrayList<>();
        ArrayList<String> product_array = new ArrayList<>();
        ArrayList<String> price_array = new ArrayList<>();
        ArrayList<String> id_array = new ArrayList<>();
        productPrice_array.add(product_array);
        productPrice_array.add(price_array);
        productPrice_array.add(id_array);
        try {
            String query = "SELECT name, price, product_id FROM product WHERE category = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, Category);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String Name = resultSet.getString("name");
                String Price = String.format("%.2f", resultSet.getDouble("price"));
                String ID = resultSet.getInt("product_id") + "";
                productPrice_array.get(0).add(Name);
                productPrice_array.get(1).add(Price);
                productPrice_array.get(2).add(ID);
            }
            return productPrice_array;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productPrice_array;
    }

    /**
     * Updates an existing product or adds a new product if not exists.
     *
     * @param conn     The database connection.
     * @param name     Product name.
     * @param category Product category.
     * @param price    Product price.
     */
    public static void updateAddProduct(Connection conn, String name, String category, Double price) {
        String updateQuery = "UPDATE product SET price = ? WHERE name = ?";
        try {
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setDouble(1, price);
            updateStatement.setString(2, name);
            int count = updateStatement.executeUpdate();
            if (count < 1) {
                product.createProduct(conn, name, category, price);
            }
        } catch (Exception e) {
            System.out.println(
                    "Error createOrder(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
    }

    /**
     * Deletes a product based on the product name.
     *
     * @param conn        The database connection.
     * @param productName The product name.
     */
    public static void deletePro(Connection conn, String productName) {
        String updateQuery = "DELETE FROM product WHERE name = ?";
        try {
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setString(1, productName);
            int count = updateStatement.executeUpdate();
            if (count > 0) {
                System.out.println("Deleted product.");
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

}
