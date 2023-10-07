package pointOfSales;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class orderPageController {
    @FXML
    private sceneController sceneCtrl;
    @FXML 
    private GridPane gridPane;

    public void setController(sceneController controller)
    {
        this.sceneCtrl = controller;
    }

    @FXML
    private void handleChangeScene(ActionEvent event){
        Button pressedButton = (Button) event.getSource();
        if(pressedButton.getId().equals("logoutButton")){
            sceneCtrl.changeScene(loginPage.getScene());
        }
        // else if(pressedButton.getId().equals("logoutButton"))
    }

    @FXML
    private void addButtons(ActionEvent event){
        int num_buttons = 0;
        //if milktea

        //if brewedtea

        //if fruittea

        //if iceblended 

        //if tea mojito

        //if creama

        for(int i = 0; i < num_buttons; i++)
        {
            try {

                //Initialize Buttons
                FXMLLoader loader = new FXMLLoader(getClass().getResource("designFiles/menuItemButton"));
                Node buttonNode = loader.load();
                //Button button = (Button) buttonNode.lookup("#");
                // button.setOnAction(pressed ->{
                //     handleButtonClick();
                // });
                gridPane.add(buttonNode, i % 5, i % 5);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
