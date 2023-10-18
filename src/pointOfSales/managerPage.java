package pointOfSales;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * The managerPage class initializes and modifies the UI for the manager.fxml design file.
 * 
 * @author Sam Trythall
 * @version v0.0.3
 * @since v0.0.2
 */

public class managerPage {

    private static sceneController controller;

    public managerPage() {
    }

    public static void setController(sceneController ctrl) {
        controller = ctrl;
    }

    /**
     * The getScene function loads the fxml file and creates a scene with dimensions 1280 x 720.
     * 
     * @return The function returns a scene with elements defined in manager.fxml
     *
     */

    public static Scene getScene(){

        try {
            //Load the manager.fxml
            FXMLLoader loader = new FXMLLoader(orderPage.class.getResource("designFiles/manager.fxml"));
            Parent root2 = loader.load();

            //Create the scene and set logic controllers for the scene
            Scene managerOrderScene = new Scene(root2, 1280, 720);
            managerPageController managerCtrl = loader.getController();
            managerCtrl.setController(controller);
            return managerOrderScene;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
