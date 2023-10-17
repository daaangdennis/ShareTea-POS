package pointOfSales;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.Scene;

 /**
 * This class is the main application class that launches the application
 * 
 * @author Sam Trythall
 * @version v0.0.3
 * @since v0.0.1
 */

public class app extends Application {
    /**
     * The start function starts the main application and sets all controllers to the main stage.
     * 
     * @param primaryStage passes in the main Stage necessary for adding and changing scenes
     * @throws ExceptionType The start function throws a runtime exception if something goes wrong while the application is running.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Sharetea Point of Sales System");
        sceneController sceneCtrl = new sceneController(primaryStage);

        //Setting page controllers to this stage so the other classes can communicate with the main app
        orderPage.setController(sceneCtrl);
        loginPage.setController(sceneCtrl);
        managerPage.setController(sceneCtrl);

        //Sets the first page as the login page and loads it onto the stage to display the page when the app starts
        Scene loginpage = loginPage.getScene();
        sceneCtrl.changeScene(loginpage);

        // Setting Window Icon will get better if changed to platform specific icons
        Image icon = new Image(getClass().getResourceAsStream("images/sharetea_icon.png"));
        primaryStage.getIcons().add(icon);

        primaryStage.setFullScreen(true);
        primaryStage.show();

    }
    /**
     * The main function loads all the libraries so the app can start
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

}
