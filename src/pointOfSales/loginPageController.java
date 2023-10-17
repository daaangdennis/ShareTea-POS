package pointOfSales;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;
import pointOfSales.services.SystemFunctions;
import java.util.ArrayList;

/**
 * The loginPageController class is used to control the logic of the loginPage
 * Specifically this class handles making all the buttons and password fields function and connects the UI to the backend and database.
 * 
 * @author Sam Trythall
 * @version v0.0.3
 * @since v0.0.1
 */

public class loginPageController {
    @FXML
    private PasswordField passwordField;

    private sceneController mySceneController;
    private static String employeeFirstName = "";
    private static String employeeLastName = "";
    private static String employeePosition = "";

    /**
     * The setController function sets the mySceneController private member of the loginPageController class.
     * @param sceneCtrl value of type sceneController that is set as the value of the private member mySceneController.
     */
    public void setController(sceneController sceneCtrl) {
        this.mySceneController = sceneCtrl;
    }

    /**
     * The setFirstName function sets the employeeFirstName private member of the loginPageController class.
     * @param name value of type String that is set as the value of the private member employeeFirstName.
     */

    public static void setFirstName(String name) {
        employeeFirstName = name;
    }

    /**
     * The setLastName function sets the employeeLastName private member of the loginPageController class.
     * @param lastname value of type String that is set as the value of the private member employeeLastName.
     */

    public static void setLastName(String lastname) {
        employeeLastName = lastname;
    }

    /**
     * The setPosition function sets the employeePosition private member of the loginPageController class.
     * @param pos value of type String that is set as the value of the private member employeePosition.
     */

    public static void setPosition(String pos) {
        employeePosition = pos;
    }

    /**
     * The getFirstName function returns the first name of the employee currently logged in.
     * @return String value of the private member employeeFirstName.
     */

    public static String getFirstName() {
        return employeeFirstName;
    }

    /**
     * The getLastName function returns the last name of the employee currently logged in.
     * @return String value of the private member employeeLastName.
     */

    public static String getLastName() {
        return employeeLastName;
    }

    /**
     * The getPosition function returns the position of the employee currently logged in.
     * @return String value of the private member employeePosition.
     */

    public static String getPosition() {
        return employeePosition;
    }

    /**
     * The handleNumberButtonClick function is the handler for numeric buttons on loginPage.fxml.
     * It adds numeric values to a password field when a numeric button is pressed.
     * 
     * @param event used to figure out which button was pressed.
     */

    @FXML
    private void handleNumberButtonClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonName = clickedButton.getId();
        char value = buttonName.charAt(buttonName.length() - 1);
        passwordField.appendText(String.valueOf(value));
    }

    /**
     * The handleDelButtonClick function is the handler for the delete button
     * This handler deletes the last element of the passwordField
     * 
     */

    @FXML
    private void handleDelButtonClick() {
        if(passwordField.getLength() != 0){
            passwordField.deleteText(passwordField.getLength() - 1, passwordField.getLength());
        }
    }

    /**
     * The handleClearButtonClick function is the handler for the clear button
     * This handler will clear the passwordField when the clear button is pressed
     * 
     */

    @FXML
    private void handleClearButtonClick() {
        passwordField.clear();
    }

    /**
     * The handleEnterButtonClick function is the handler for the enter button
     * This handler will check if the password matches a password for an employee and either clear the field or log the employee in.
     * @see {@link pointOfSales.services.SystemFunctions#verify()}
     */

    @FXML
    private void handleEnterButtonClick() {
        //Check if the password exists in the database
        ArrayList<String> values = SystemFunctions.verify(passwordField.getText());

        
        if (values.size() > 0 && values.get(2).equals("CASHIER")) {
            //if the password belongs to a Cashier switch to cashier page
            setFirstName(values.get(0));
            setLastName(values.get(1));
            setPosition(values.get(2));
            orderPage.setController(mySceneController);
            mySceneController.changeScene(orderPage.getScene());
        } 
        else if (values.size() > 0 && values.get(2).equals("MANAGER")) {
            // if the password belongs to a Manager switch to manager page
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
