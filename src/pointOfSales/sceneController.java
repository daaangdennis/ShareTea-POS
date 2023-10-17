package pointOfSales;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The sceneController class handles all the scene changing logic 
 * 
 * @author Sam Trythall
 * @version v0.0.3
 * @since v0.0.2
 */

public class sceneController {

    // The stage variable is for future use if needed

    private Stage mainStage;

    public Stage getStage() {
        return mainStage;
    }

    public sceneController(Stage currentStage) {
        this.mainStage = currentStage;
    }

    public void changeScene(Scene newScene) {
        try {
            mainStage.setScene(newScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
