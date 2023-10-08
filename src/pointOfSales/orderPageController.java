package pointOfSales;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableColumn;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Toggle;
import java.util.List;
import java.util.ArrayList;
import java.text.DecimalFormat;

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

    @FXML
    private AnchorPane menuItems;
    @FXML
    private AnchorPane orderCustomizationMenu;
    @FXML
    private AnchorPane teaPane;
    @FXML
    private AnchorPane sugarPane;
    @FXML
    private AnchorPane icePane;
    @FXML
    private AnchorPane toppingSelection;
    @FXML
    private TextArea additionalNotes;
    @FXML
    private Label foodItemLabel;

    private ToggleGroup teaGroup = new ToggleGroup();
    private ToggleGroup sugarGroup = new ToggleGroup();
    private ToggleGroup iceGroup = new ToggleGroup();
    private String sugarSelection;
    private String iceSelection;
    private List<String> toppingArray = new ArrayList<>();
    private ObservableList<Object[]> data = FXCollections.observableArrayList();
    private Double foodLabelCost = 0.0;
    

    
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

        ToggleButton milkButton = (ToggleButton) teaPane.lookup("#milkTeaButton");
        ToggleButton brewedButton = (ToggleButton) teaPane.lookup("#brewedTeaButton");
        ToggleButton fruitButton = (ToggleButton) teaPane.lookup("#fruitTeaButton");
        ToggleButton iceBlendButton = (ToggleButton) teaPane.lookup("#iceBlendedButton");
        ToggleButton mojitoButton = (ToggleButton) teaPane.lookup("#teaMojitoButton");
        ToggleButton creamButton = (ToggleButton) teaPane.lookup("#creamaButton");
        teaGroup.getToggles().addAll(milkButton, brewedButton, fruitButton, iceBlendButton, mojitoButton, creamButton);

        ToggleButton toggleButton1 = (ToggleButton) sugarPane.lookup("#hundredSugar");
        ToggleButton toggleButton2 = (ToggleButton) sugarPane.lookup("#fiftySugar");
        ToggleButton toggleButton3 = (ToggleButton) sugarPane.lookup("#noSugar");
        ToggleButton toggleButton4 = (ToggleButton) sugarPane.lookup("#hundredTwentySugar");
        ToggleButton toggleButton5 = (ToggleButton) sugarPane.lookup("#eightySugar");
        ToggleButton toggleButton6 = (ToggleButton) sugarPane.lookup("#thirtySugar");
        sugarGroup.getToggles().addAll(toggleButton1, toggleButton2, toggleButton3, toggleButton4, toggleButton5, toggleButton6);

        ToggleButton regularIceButton = (ToggleButton) icePane.lookup("#regularIce");
        ToggleButton lightIceButton = (ToggleButton) icePane.lookup("#lightIce");
        ToggleButton noIceButton = (ToggleButton) icePane.lookup("#noIce");
        ToggleButton extraIceButton = (ToggleButton) icePane.lookup("#extraIce");
        ToggleButton makeItHotButton = (ToggleButton) icePane.lookup("#hot");
        iceGroup.getToggles().addAll(regularIceButton, lightIceButton, noIceButton, extraIceButton, makeItHotButton);
    }

    public TableView<Object[]> getTable(){
        return this.checkoutTable;
    } 

    public AnchorPane getMainMenu(){
        return this.menuItems;
    }

    public AnchorPane getSubMenu(){
        return this.orderCustomizationMenu;
    }

    public Label getFoodLabel(){
        return this.foodItemLabel;
    }

    public Double getFoodCost(){
        return this.foodLabelCost;
    }

    public void setFoodCost(Double cost){
        this.foodLabelCost = cost;
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
        ToggleButton sourceButton = (ToggleButton) event.getSource();
        if(!sourceButton.isSelected())
        {
            menuItemsGridPane.getChildren().clear();
            return;
        }
        //if milktea

        //if brewedtea

        //if fruittea

        //if iceblended 

        //if tea mojito

        //if creama
        menuItemsGridPane.getChildren().clear();
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
    private void handleCloseButton(ActionEvent event){
        menuItems.setVisible(!menuItems.isVisible());
        orderCustomizationMenu.setVisible(!orderCustomizationMenu.isVisible());
    }

    public String checkToggledButton(ToggleGroup toggleGroup) {
        String text = "";
        Toggle selectedToggle = toggleGroup.getSelectedToggle();
        if (selectedToggle != null && selectedToggle instanceof ToggleButton) {
            ToggleButton selectedButton = (ToggleButton) selectedToggle;
            text = selectedButton.getText();
        }
        return text;
    }
    @FXML
    private void addItem(ActionEvent event){
        sugarSelection = checkToggledButton(sugarGroup);
        iceSelection = checkToggledButton(iceGroup);
        if(sugarSelection == "" | iceSelection == ""){
            return;
        }

        ToggleButton pearlButton = (ToggleButton) toppingSelection.lookup("#pearl");
        ToggleButton miniPearlButton = (ToggleButton) toppingSelection.lookup("#miniPearl");
        ToggleButton iceCreamButton = (ToggleButton) toppingSelection.lookup("#iceCream");
        ToggleButton puddingButton = (ToggleButton) toppingSelection.lookup("#pudding");
        ToggleButton aloeVeraButton = (ToggleButton) toppingSelection.lookup("#aloeVera");
        ToggleButton redBeanButton = (ToggleButton) toppingSelection.lookup("#redBean");
        ToggleButton herbJellyButton = (ToggleButton) toppingSelection.lookup("#herbJelly");
        ToggleButton aiyuJellyButton = (ToggleButton) toppingSelection.lookup("#aiyuJelly");
        ToggleButton lycheeJellyButton = (ToggleButton) toppingSelection.lookup("#lycheeJelly");
        ToggleButton crystalBobaButton = (ToggleButton) toppingSelection.lookup("#crystalBoba");

        if(pearlButton.isSelected()){
            toppingArray.add(pearlButton.getText());
        }
        if(miniPearlButton.isSelected()){
            toppingArray.add(miniPearlButton.getText());
        }
        if(iceCreamButton.isSelected()){
            toppingArray.add(iceCreamButton.getText());
        }
        if(puddingButton.isSelected()){
            toppingArray.add(puddingButton.getText());
        }
        if(aloeVeraButton.isSelected()){
            toppingArray.add(aloeVeraButton.getText());
        }
        if(redBeanButton.isSelected()){
            toppingArray.add(redBeanButton.getText());
        }
        if(herbJellyButton.isSelected()){
            toppingArray.add(herbJellyButton.getText());
        }
        if(aiyuJellyButton.isSelected()){
            toppingArray.add(aiyuJellyButton.getText());
        }
        if(lycheeJellyButton.isSelected()){
            toppingArray.add(lycheeJellyButton.getText());
        }
        if(crystalBobaButton.isSelected()){
            toppingArray.add(crystalBobaButton.getText());
        }

        data.add(new Object[]{"MilkTea", "1", "$5.00"}); 
        data.add(new Object[]{"BrewedTea", "1", "$4.00"});
        data.add(new Object[]{"FruitTea", "1", "$3.00"});
        
        ;
        checkoutTable.setItems(data);
        menuItems.setVisible(!menuItems.isVisible());
        orderCustomizationMenu.setVisible(!orderCustomizationMenu.isVisible());
    }
    @FXML 
    private void handleCancelButton(ActionEvent event){
        data.clear();
    }

    @FXML
    private void handleToppingButtons(ActionEvent event){
        ToggleButton sourceButton= (ToggleButton) event.getSource();
        if(!sourceButton.isSelected()){
            foodLabelCost -= 0.75;
        }
        else{
            foodLabelCost += 0.75;
        }
        DecimalFormat df = new DecimalFormat("Food Item $0.00 ");
        String formattedCost = df.format(foodLabelCost);
        foodItemLabel.setText(formattedCost);
    }



}
