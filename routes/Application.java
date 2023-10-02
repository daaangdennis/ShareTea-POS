package routes;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import com.sun.net.httpserver.HttpServer;

import entities.dbconfig;
import services.SystemFunctions;
import services.addOrderFull;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;



public class Application {
    Connection conn; 

    public static Map<String, List<String>> splitQuery(String query) {
        if (query == null || "".equals(query)) {
            return Collections.emptyMap();
        }

        return Pattern.compile("&").splitAsStream(query)
                .map(s -> Arrays.copyOf(s.split("=", 2), 2))
                .collect(Collectors.groupingBy(s -> decode(s[0]),
                        Collectors.mapping(s -> s.length > 1 ? decode(s[1]) : null, Collectors.toList())));
    }

    public static String decode(String value) {
        try {
            return URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpServer Start(int port) throws IOException {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315_904_03db",
                    dbconfig.user, dbconfig.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");

        return HttpServer.create(new InetSocketAddress(port), 0);

    }

    public static void main(String[] agrs) throws IOException {
        
        Application app = new Application();
        HttpServer server = app.Start(8000);
        
        server.createContext("/login", (exchange -> {

            Map<String, List<String>> params = splitQuery(exchange.getRequestURI().getRawQuery());
            String noNameText = "Invalid";
            String passcode = params.getOrDefault("passcode", List.of(noNameText)).stream().findFirst().orElse(noNameText);
            
            String verification = new SystemFunctions().verify(app.conn, passcode);
            
            String respText = "Logged in as: " + verification;
            if (verification.equals("Invalid")){
                respText = "Invalid passcode.";
            }

            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
            exchange.close();
        }));

        server.createContext("/addOrder", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                int order_id = new addOrderFull().addOrderToDB(app.conn);
                String respText = "Order " + Integer.toString(order_id) + " has been added.";

                exchange.sendResponseHeaders(200, respText.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(respText.getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));

        server.createContext("/addOrderProduct", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                Map<String, List<String>> params = splitQuery(exchange.getRequestURI().getRawQuery());
                String noNameText = "N/A";
                String order_id = params.getOrDefault("orderID", List.of(noNameText)).stream().findFirst().orElse(noNameText);
                String product_id = params.getOrDefault("productID", List.of(noNameText)).stream().findFirst().orElse(noNameText);
                String quantity = params.getOrDefault("quantity", List.of(noNameText)).stream().findFirst().orElse(noNameText);

                if(order_id.equals(noNameText) || product_id.equals(noNameText) || quantity.equals(noNameText)){
                    exchange.close();
                }

                new addOrderFull().addOrderProductsToDB(app.conn, Integer.valueOf(order_id) , Integer.valueOf(product_id), Integer.valueOf(quantity));
                String respText = quantity + " of " + "ProductID:" + product_id + " has been added to OrderID: " + order_id;

                exchange.sendResponseHeaders(200, respText.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(respText.getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));

        server.setExecutor(null); // creates a default executor
        server.start();
    }

}
