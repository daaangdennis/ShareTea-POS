package pointOfSales;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import pointOfSales.services.SystemFunctions;
import java.util.ArrayList;


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
            ArrayList<String> values = SystemFunctions.verify(passwordField.getText());
            
            if(values.get(2).equals("Cashier")){
                //Call Cashier Switch
                
                orderPage.setController(mySceneController);
                
                mySceneController.changeScene(orderPage.getScene());
            }
            else if(values.get(2).equals("Manager"))
            {
                //Call Manager Switch
                orderPage.setController(mySceneController);
                mySceneController.changeScene(orderPage.getScene());
            }
            else
            {
                passwordField.clear();
            }

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
