package pointOfSales;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class orderPageController {
    @FXML
    private sceneController sceneCtrl;

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
}
