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

    public void setController(sceneController sceneCtrl){
        this.mySceneController = sceneCtrl;
    }

    /**

     * This method sets the employee's first name

     * @param  name  the first name of the employee passed as a String

     * @see String
     
     */
    public static void setFirstName(String name){
        employeeFirstName = name;
    }
    
    /**

     * This method sets the employee's last name.

     * @param  name  the last name of the employee
    
     * @see String

     */
    public static void setLastName(String name){
        employeeLastName = name;
    }

    /**

     * This method sets the employee's position.

     * @param  pos  the position of the employee
     
     * @see String

     */
    public static void setPosition(String pos){
        employeePosition = pos;
    }
    /**

     * This method gets the first name member of the loginPageControllerClass

     * @return String of the employees first name

     * @see String
     
     */
    public static String getFirstName(){
        return employeeFirstName;
    }

    /**

     * This method gets the employee's last name.

     * @return String of employee's last name
     
     * @see String

     */
    public static String getLastName(){
        return employeeLastName;
    }

    /**

     * This method gets the employee's position.

     * @return the position of the employee as a String

     * @see String
    
     */
    public static String getPosition(){
        return employeePosition;
    }



    
    /**

     * This handler append a number to the password

     *

     * @param event the onClick event provide the number by using the getId() method

     * @see ActionEvent, Button, String, Char 
     
     */
    @FXML
    private void handleNumberButtonClick(ActionEvent event){
        Button clickedButton = (Button) event.getSource();
        String buttonName = clickedButton.getId();
        char value = buttonName.charAt(buttonName.length() - 1);
        passwordField.appendText(String.valueOf(value));
    }
    /**

     * This is a handler for the delete 
eg reRemoves a chagracter offrom the passwordfield buffer.apeventreference to thereference to the clicking of the button, can be used to grab information relating to the eventt no nottu

     *

     * @param  name  the first name of the employee passed as a String

     * @see String
     
     */
    @FXML
    private void handleDelButtonClick(ActionEvent event){
        passwordField.deleteText(passwordField.getLength()-1, passwordField.getLength());
    }

    /**

     * This handler clear the password

     * 

     * @param event onClick event

     * @see ActionEvent
     
     */
    @FXML
    private void handleClearButtonClick(ActionEvent event){
        passwordField.clear();
    }

    /**

     * This handler submit the user using the password to the system and change the scene to order page 
        
     * It also provide checker to the verification flag in the given from the beckend

     * @param event onClick event 

     * @see ActionEvent, ArrayList, String
     
     */
    @FXML
    private void handleEnterButtonClick(ActionEvent event){
            //Run function to check if the password is correct
            ArrayList<String> values = SystemFunctions.verify(passwordField.getText());
            
            if(values.get(2).equals("CASHIER")){
                //Call Cashier Switch
                setFirstName(values.get(0));
                setLastName(values.get(1));
                setPosition(values.get(2));
                orderPage.setController(mySceneController);
                mySceneController.changeScene(orderPage.getScene());
            }
            else if(values.get(2).equals("MANAGER"))
            {
                //Call Manager Switch
                setFirstName(values.get(0));
                setLastName(values.get(1));
                setPosition(values.get(2));
                managerPage.setController(mySceneController);
                mySceneController.changeScene(managerPage.getScene());
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
