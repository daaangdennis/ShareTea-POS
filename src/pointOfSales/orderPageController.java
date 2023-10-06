package pointOfSales;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class orderPageController {
    @FXML
    private sceneController sceneCtrl;

    public orderPageController(sceneController controller)
    {
        this.sceneCtrl = controller;
    }

    public void handleChangeScene(ActionEvent event){
        Button pressedButton = (Button) event.getSource();
        if(pressedButton.getId().equals("logoutButton")){
            //change scene to login page
            
        }
        // else if(pressedButton.getId().equals("logoutButton"))
    }
}
