package pointOfSales;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;


public class orderPageController implements Initializable {
    private sceneController sceneCtrl;

    @FXML 
    private GridPane menuItemsGridPane;
    @FXML
    private TableView<Object[]> checkoutTable;
    @FXML
    private TableColumn<Object[], String> productTableColumn;
    @FXML
    private TableColumn<Object[], String> quantityTableColumn;
    @FXML
    private TableColumn<Object[], String> priceTableColumn;
    
    public void initialize(URL location, ResourceBundle resources) {
        productTableColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 0) {
                return new SimpleStringProperty(cellData.getValue()[0].toString());
            } else {
                return new SimpleStringProperty("product");
            }
        });
        
        quantityTableColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 1) { // Use index 1 for quantity
                return new SimpleStringProperty(cellData.getValue()[1].toString());
            } else {
                return new SimpleStringProperty("quantity");
            }
        });
        
        priceTableColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 2) { // Use index 2 for price
                return new SimpleStringProperty(cellData.getValue()[2].toString());
            } else {
                return new SimpleStringProperty("price");
            }
        });
    }

    public TableView<Object[]> getTable(){
        return this.checkoutTable;
    } 

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

    @FXML
    private void addButtons(ActionEvent event){
        int num_buttons = 1;
        //if milktea

        //if brewedtea

        //if fruittea

        //if iceblended 

        //if tea mojito

        //if creama

        for(int i = 0; i < num_buttons; i++)
        {
            try {

                //Initialize Buttons
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("designFiles/menuItemButton.fxml"));
                Node buttonNode = loader2.load();
                // Button button = (Button) buttonNode.lookup("#");
                // button.setOnAction(pressed ->{
                //     handleSubButton();
                // });
                menuItemsGridPane.add(buttonNode, i % 2, i % 2);
                menuItemButtonController buttonController = loader2.getController();
                buttonController.setOrderControl(this);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleSubButton(ActionEvent event){
        ObservableList<Object[]> data = FXCollections.observableArrayList(
            new Object[]{"MilkTea", "1", "$5.00"}, new Object[]{"BrewedTea", "1", "$4.00"}, new Object[]{"FruitTea", "1", "$3.00"}
        );



        

        checkoutTable.setItems(data);
    }
}
