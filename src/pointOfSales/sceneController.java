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

    /**
     * Retrieves the main stage of the application.
     *
     * @return The main Stage.
     */
    public Stage getStage() {
        return mainStage;
    }

    /**
     * Initializes a new sceneController with the provided stage.
     * 
     * @param currentStage The primary stage where scenes will be displayed.
     */
    public sceneController(Stage currentStage) {
        this.mainStage = currentStage;
    }

    /**
     * Changes the active scene on the main stage.
     * <p>
     * This method attempts to replace the current scene on the main stage
     * with the provided scene. If there are any issues during the process,
     * an exception is caught and printed to the console.
     * </p>
     * 
     * @param newScene The new scene to display on the main stage.
     */
    public void changeScene(Scene newScene) {
        try {
            mainStage.setScene(newScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
