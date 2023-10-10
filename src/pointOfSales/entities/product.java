package pointOfSales.entities;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class product {
    String Name = null;
    BigDecimal Price = new BigDecimal(0);
    String Category = null;
    int ProductID = -1;
    Connection conn = null;

    public product(Connection conn) {
        this.conn = conn;
    }

    public product(Connection conn, String Name, String Category) {
        this.Name = Name;
        this.Category = Category;
    }

    public product(Connection conn, String Name, String Category, Double Price) {
        this.Name = Name;
        this.Category = Category;
        this.Price = new BigDecimal(Price).setScale(2, RoundingMode.HALF_UP);
        this.conn = conn;
    }

    public void createProduct() {
        try {
            String sql = "INSERT INTO product (name, category, price) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Name);
            pstmt.setString(2, Category);
            pstmt.setBigDecimal(3, Price);
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

    public static double getProductPriceByID(Connection conn, int ProductID) {
        try {
            double product_price = 0;
            String price_query = "SELECT price FROM product WHERE product_id = ?";
            PreparedStatement pstmtPrice = conn.prepareStatement(price_query);
            pstmtPrice.setInt(1, ProductID);
            ResultSet resultSet = pstmtPrice.executeQuery();
            if (resultSet.next()) {
                product_price = resultSet.getDouble("price");
            }
            return product_price;
        } catch (Exception e) {
            System.out.println(
                    "Error getProductPriceByID(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
        return -1;
    }

    public static int getProductByName(Connection conn, String productName) {
        int product_id = -1;
        try {
            String query = "SELECT product_id FROM product WHERE name = ?";
            PreparedStatement pstmtPrice = conn.prepareStatement(query);
            pstmtPrice.setString(1, productName);
            ResultSet resultSet = pstmtPrice.executeQuery();
            if (resultSet.next()) {
                product_id = resultSet.getInt("product_id");
            }
            return product_id;
        } catch (Exception e) {
            System.out.println(
                    "Error getProductPriceByID(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
        return -1;
    }

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


    public static ArrayList<ArrayList<String>> getProductsPriceByCategory(Connection conn, String Category) {
        ArrayList<ArrayList<String>> productPrice_array = new ArrayList<>();
        ArrayList<String> product_array = new ArrayList<>();
        ArrayList<String> price_array = new ArrayList<>();
        productPrice_array.add(product_array);
        productPrice_array.add(price_array);
        try {
            String query = "SELECT name,price FROM product WHERE category = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, Category);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String Name = resultSet.getString("name");
                String Price = resultSet.getDouble("price") + "";
                productPrice_array.get(0).add(Name);
                productPrice_array.get(1).add(Price);
            }
            return productPrice_array;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productPrice_array;
    }

}
