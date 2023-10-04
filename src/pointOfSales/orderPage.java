package pointOfSales;

import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class orderPage {
    public static Scene getScene(){
        
        try{
            FXMLLoader loader = new FXMLLoader(orderPage.class.getResource("designFiles/cashier.fxml"));
            Parent root2 = loader.load();
            double screenWidth = Screen.getPrimary().getBounds().getWidth();
            double screenHeight = Screen.getPrimary().getBounds().getHeight();
            Scene cashierOrderScene = new Scene(root2, screenWidth, screenHeight);
            return cashierOrderScene;
        }
        catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
