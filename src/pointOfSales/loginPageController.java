package pointOfSales;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;


public class loginPageController {

    @FXML
    private PasswordField passwordField;
    private sceneController mySceneController;
 

    // Event Handler for keypad numerical buttons

    public void setController(sceneController sceneCtrl){
        this.mySceneController = sceneCtrl;
    }
    @FXML

    private void handleNumberButtonClick(ActionEvent event){
        Button clickedButton = (Button) event.getSource();
        String buttonName = clickedButton.getId();
        char value = buttonName.charAt(buttonName.length() - 1);
        passwordField.appendText(String.valueOf(value));
    }

    @FXML
    private void handleDelButtonClick(ActionEvent event){
        passwordField.deleteText(passwordField.getLength()-1, passwordField.getLength());
    }

    @FXML
    private void handleClearButtonClick(ActionEvent event){
        passwordField.clear();
    }
    @FXML
    private void handleEnterButtonClick(ActionEvent event){
            //Run function to check if the password is correct

            mySceneController.changeScene(orderPage.getScene());
            
            // if(passwordField.getText() == passwordField.getText()){
            //     //Call Cashier Switch
            //     mySceneController.changeScene(orderPage.getScene());
            // }
            // else if(passwordField.getText() == "9876")
            // {
            //     //Call Manager Switch

            // }
            // else
            // {
            //     passwordField.clear();
            // }

            //Based off of function output:
            //if(output == "Cashier"){
            //  load cashier order scene
            //}
            //else if(output == "Manager"){
            //  load manager order scene
            //}
            //else
            //
    }
    
    //Ideas for further implementation: 
    //Add some sort of error message or reaction to tell the user the password is wrong
    //Add a way to change scenes when the login is successful
    

}
