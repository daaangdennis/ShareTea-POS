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

    public static void updateAddProduct(Connection conn, String name, String category , Double price) {
        String updateQuery = "UPDATE product SET price = ? WHERE name = ?";
        try {
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setDouble(1, price);
            updateStatement.setString(2, name);
            int count = updateStatement.executeUpdate();
            if(count < 1){
                product.createProduct(conn, name, category, price);
            }
        } catch (Exception e) {
            System.out.println(
                    "Error createOrder(): Name: " + e.getClass().getName() + " , Message: " + e.getMessage());
        }
    }

    public static void deletePro(Connection conn, String productName){
        String updateQuery = "DELETE FROM product WHERE name = ?";
        try {
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setString(1, productName);
            int count = updateStatement.executeUpdate();
            if(count > 0){
                System.out.println("Deleted product.");
            }
        } catch (Exception e) {
            System.out.println(
                    "Error");
        }
    }

}
