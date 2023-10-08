package pointOfSales;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class loginPage extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("designFiles/loginPage.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Sharetea Point of Sales System");
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();

        //Setting Window Icon will get better if changed to platform specific icons
        Image icon = new Image(getClass().getResourceAsStream("images/sharetea_icon.png"));
        primaryStage.getIcons().add(icon);
        
        //primaryStage.setScene(new Scene(root, 1440, 720));

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


        // Set position for keypad and password field
        GridPane keypad = (GridPane) root.lookup("#keypad");
        keypad.setScaleX((screenWidth * 1.8 / 3.0) / (keypad.getPrefWidth() * 1.6));
        keypad.setScaleY(keypad.getScaleX());
        keypad.setLayoutX((screenWidth * 1.8 / 3.0));
        keypad.setLayoutY(screenHeight / 2);

        PasswordField passwordField = (PasswordField) root.lookup("#passwordField");
        passwordField.setScaleX(keypad.getScaleX());
        passwordField.setScaleY(keypad.getScaleX());
        passwordField.setLayoutX(keypad.getLayoutX() - (14 * keypad.getScaleX()));
        passwordField.setLayoutY(keypad.getLayoutY() - (85 * keypad.getScaleY()));

        sceneController sceneCtrl = new sceneController(primaryStage);
        loginPageController loginCtrl = loader.getController();
        loginCtrl.setController(sceneCtrl);

        primaryStage.show();

        //New Code for changing Scenes
        

    }

  
    public static void main(String[] args) {
        launch(args);
    }
}
