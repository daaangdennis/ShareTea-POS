package pointOfSales;

import javafx.scene.Scene;
import javafx.stage.Stage;



public class sceneController {

    //The stage variable is for future use if needed

    private Stage mainStage;
    
    public Stage getStage(){
        return mainStage;
    }
    
    public sceneController(Stage currentStage){
        this.mainStage = currentStage;
    }   

    public void changeScene(Scene newScene){
        mainStage.setScene(newScene);
        
    }
}
