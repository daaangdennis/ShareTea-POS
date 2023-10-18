package pointOfSales;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

/**
 * The loginPage class loads the UI elements from the loginPage.fxml file, and performs some modifications to it.
 * This class has no logic for the loginPage in it, for the logic @see {@link loginPageController}
 * 
 * @author Sam Trythall
 * @version v0.0.3
 * @since v0.0.1
 */

public class loginPage {

    private static sceneController controller;

    /**
     * The setController function sets the controller private member of the loginPage class.
     * @param ctrl value of type sceneController that is set as the value of the private member controller.
     */

    public static void setController(sceneController ctrl) {
        controller = ctrl;
    }
    
    /**
     * The getScene function loads the loginPage onto a scene object with all modifications set
     * 
     * @return a scene type with everything loaded from the loginPage.fxml file
     * @throws IOException in case the fxml file fails to load.
     */
    public static Scene getScene() {

        try {
            //loads the fxml onto a Parent node
            FXMLLoader loader = new FXMLLoader(orderPage.class.getResource("designFiles/loginPage.fxml"));
            Parent root = loader.load();
            double screenWidth = Screen.getPrimary().getBounds().getWidth();
            double screenHeight = Screen.getPrimary().getBounds().getHeight();

            //loads and modifies the wall image from the fxml
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
            
            //Create and return scene with modified fxml 
            Scene loginScene = new Scene(root, screenWidth, screenHeight);

            loginPageController loginCtrl = loader.getController();
            loginCtrl.setController(controller);

            return loginScene;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
