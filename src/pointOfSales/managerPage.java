package pointOfSales;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class managerPage {
    
    private static sceneController controller;
    public managerPage(){}

    public static void setController(sceneController ctrl){
        controller = ctrl;
    }

    public static Scene getScene(){
        
        try{
            FXMLLoader loader = new FXMLLoader(orderPage.class.getResource("designFiles/manager.fxml"));
            Parent root2 = loader.load();
            // double screenWidth = Screen.getPrimary().getBounds().getWidth();
            // double screenHeight = Screen.getPrimary().getBounds().getHeight();
            Scene managerOrderScene = new Scene(root2, 1280, 720);
            managerPageController managerCtrl = loader.getController();
            managerCtrl.setController(controller);
            return managerOrderScene;
        }
        catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
