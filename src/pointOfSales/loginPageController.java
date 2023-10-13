package pointOfSales;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import pointOfSales.services.SystemFunctions;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class loginPageController {
    @FXML
    private PasswordField passwordField;
    private sceneController mySceneController;
    private static String employeeFirstName = "";
    private static String employeeLastName = "";
    private static String employeePosition = "";

    // Event Handler for keypad numerical buttons

    public void setController(sceneController sceneCtrl) {
        this.mySceneController = sceneCtrl;
    }

    public static void setFirstName(String name) {
        employeeFirstName = name;
    }

    public static void setLastName(String lastname) {
        employeeLastName = lastname;
    }

    public static void setPosition(String pos) {
        employeePosition = pos;
    }

    @FXML
    private void handleDelButtonClick(ActionEvent event) {
        passwordField.deleteText(passwordField.getLength() - 1, passwordField.getLength());
    }

    @FXML
    private void handleClearButtonClick(ActionEvent event) {
        passwordField.clear();
    }

    @FXML
    private void handleEnterButtonClick(ActionEvent event) {
        // Run function to check if the password is correct
        ArrayList<String> values = SystemFunctions.verify(passwordField.getText());

        if (values.get(2).equals("CASHIER")) {
            // Call Cashier Switch
            setFirstName(values.get(0));
            setLastName(values.get(1));
            setPosition(values.get(2));
            orderPage.setController(mySceneController);
            mySceneController.changeScene(orderPage.getScene());
        } else if (values.get(2).equals("MANAGER")) {
            // Call Manager Switch
            setFirstName(values.get(0));
            setLastName(values.get(1));
            setPosition(values.get(2));
            managerPage.setController(mySceneController);
            mySceneController.changeScene(managerPage.getScene());
        } else {
            passwordField.clear();
        }
    }

}
