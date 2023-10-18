package pointOfSales;

import javafx.scene.Scene;
// import javafx.stage.Screen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

/**
 * The orderPage class is the UI class for the checkoutPage.
 * This class loads all the UI elements and modifies them a bit.
 * 
 * @author Sam Trythall
 * @version v0.0.3
 * @since v0.0.2
 */

public class orderPage {
    /** The controller that handles scene interactions for the order page. */
    private static sceneController controller;

    /**
     * Default constructor for the orderPage class.
     */
    public orderPage() {
    }

    /**
     * Sets the scene controller for the order page.
     * 
     * @param ctrl The controller to be set for this order page.
     */
    public static void setController(sceneController ctrl) {
        controller = ctrl;
    }

    /**
     * Retrieves the Scene for the order page.
     * <p>
     * This method attempts to load the "cashier.fxml" file which contains the
     * design
     * of the order page. Upon successful loading, the scene is initialized and
     * returned. In case of any IO failures during the loading process, an exception
     * is printed and a null value is returned.
     * </p>
     * 
     * @return The Scene containing the order page's UI, or null if there's an IO
     *         error.
     */
    public static Scene getScene() {

        try {
            FXMLLoader loader = new FXMLLoader(orderPage.class.getResource("designFiles/cashier.fxml"));
            Parent root2 = loader.load();
            // double screenWidth = Screen.getPrimary().getBounds().getWidth();
            // double screenHeight = Screen.getPrimary().getBounds().getHeight();
            Scene cashierOrderScene = new Scene(root2, 1280, 720);
            orderPageController orderCtrl = loader.getController();
            orderCtrl.setController(controller);
            return cashierOrderScene;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
