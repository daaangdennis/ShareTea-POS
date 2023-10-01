package loginPage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("loginPageDemo.fxml"));
        primaryStage.setTitle("Sharetea Point of Sales System");
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();

        
        primaryStage.setScene(new Scene(root, screenWidth, screenHeight));
        primaryStage.setFullScreen(true);

        // Set the brick wall image to always be 1/3 of the screen width
        Pane wallImageContainer = (Pane) root.lookup("#wallImageContainer");
        wallImageContainer.setPrefWidth(screenWidth / 3.0);
        wallImageContainer.setPrefHeight(screenHeight);

        // Set the position of the logo to be centered on the brickwall
        ImageView logo = (ImageView) root.lookup("#companyLogo");
        logo.setPreserveRatio(true);
        wallImageContainer.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            logo.setX((newWidth.doubleValue() - logo.getFitWidth()) / 2);
            logo.setScaleX(newWidth.doubleValue() / logo.getFitWidth());
            logo.setScaleY(logo.getScaleX());
        });
    
        wallImageContainer.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            logo.setY((newHeight.doubleValue() - logo.getFitHeight()) / 5);
        });

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
