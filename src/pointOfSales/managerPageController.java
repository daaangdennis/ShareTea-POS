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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.text.DecimalFormat;
import pointOfSales.services.SystemFunctions;
import pointOfSales.services.addOrderFull;
import javafx.scene.control.TextField;
import pointOfSales.entities.orderProduct;


public class managerPageController implements Initializable {
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
    private TableView<Object[]> inventoryTable;
    @FXML
    private TableColumn<Object[], String> InventoryIdColumn;
    @FXML
    private TableColumn<Object[], String> InventoryItemColumn;
    @FXML
    private TableColumn<Object[], String> InventoryQuantityColumn;
    @FXML
    private TableColumn<Object[], String> InventoryUpdatedColumn;

    @FXML
    private TableView<Object[]> inventoryMenuTable;
    @FXML
    private TableColumn<Object[], String> menuIdColumn;
    @FXML
    private TableColumn<Object[], String> menuItemColumn;
    @FXML
    private TableColumn<Object[], String> menuCategoryColumn;
    @FXML
    private TableColumn<Object[], String> menuPriceColumn;


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
    private AnchorPane orderInfoPane;
    @FXML
    private AnchorPane notesPane;
    @FXML
    private AnchorPane editInventoryPage;
    @FXML
    private AnchorPane editMenuPage;
    @FXML
    private AnchorPane inventoryPane;

    @FXML
    private TextArea additionalNotes;
    @FXML
    private Label foodItemLabel;

    private ToggleGroup teaGroup = new ToggleGroup();
    private ToggleGroup sugarGroup = new ToggleGroup();
    private ToggleGroup iceGroup = new ToggleGroup();
    private String sugarSelection;
    private String iceSelection;
    private ObservableList<Object[]> data = FXCollections.observableArrayList();
    private ObservableList<Object[]> inventoryData = FXCollections.observableArrayList();
    private Double foodLabelCost = 0.0;
    public ArrayList<orderedProduct> items = new ArrayList<>();
    public Map<Button, Label> buttonLabelMap = new HashMap<>();
    public Map<Button, Label> buttonCostMap = new HashMap<>();
    public Map<Button, String> buttonIdMap = new HashMap<>();
    public Double orderTotal = 0.0;
    public String employPosition = "";

    
    

    
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

        Label orderNumber = (Label) orderInfoPane.lookup("#orderNumberLabel");
        orderNumber.setText("Order #" + addOrderFull.nextOrderID());
        employPosition = loginPageController.getPosition();
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
            employPosition = "";
            sceneCtrl.changeScene(loginPage.getScene());
        }
        // else if(pressedButton.getId().equals("logoutButton"))
    }

    @FXML
    private void addButtons(ActionEvent event){
        int num_buttons = 1;
        int indexCount = 0;
        ToggleButton sourceButton = (ToggleButton) event.getSource();
        if(!sourceButton.isSelected())
        {
            menuItemsGridPane.getChildren().clear();
            return;
        }
        //if milktea
        ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
        if(sourceButton.getText().equals("Milk Tea"))
        {
            results = SystemFunctions.productsAndPriceByCategory("Milk Tea");
            num_buttons = results.get(0).size(); 
        }
        //if brewedtea
        if(sourceButton.getText().equals("Brewed Tea"))
        {
            results = SystemFunctions.productsAndPriceByCategory("Brewed Tea");
            num_buttons = results.size(); 
        }
        //if fruittea
        if(sourceButton.getText().equals("Fruit Tea"))
        {
            results = SystemFunctions.productsAndPriceByCategory("Fruit Tea");
            num_buttons = results.size(); 
        }
        //if iceblended 
        if(sourceButton.getText().equals("Ice Blended"))
        {
            results = SystemFunctions.productsAndPriceByCategory("Ice Blended");
            num_buttons = results.size(); 
        }
        //if tea mojito
        if(sourceButton.getText().equals("Tea Mojito"))
        {
            results = SystemFunctions.productsAndPriceByCategory("Tea Mojito");
            num_buttons = results.size(); 
        }
        //if creama
        if(sourceButton.getText().equals("Creama"))
        {
            results = SystemFunctions.productsAndPriceByCategory("Creama");
            num_buttons = results.size(); 
        }
        menuItemsGridPane.getChildren().clear();
        
        int val1 = 1;
        int val2 = 1;
        for(int i = 0; i < num_buttons; i++){
            try {
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("designFiles/menuItemButton.fxml"));
                Node buttonNode = loader2.load();
                Button button = (Button) buttonNode.lookup("#subButton");
                Label label = (Label) loader2.getNamespace().get("foodItemLabel");
                label.setText(results.get(0).get(indexCount));
                Label priceLabel = (Label) loader2.getNamespace().get("priceLabel");
                priceLabel.setText(String.format("Price: $%.2f", Double.parseDouble(results.get(1).get(indexCount))));
                
                GridPane.setRowIndex(buttonNode, val1);
                GridPane.setColumnIndex(buttonNode, val2);
                
                val2++;
                if(val2 == 4)
                {
                    val1++;
                    val2 = 1;
                }
                String pid = results.get(2).get(indexCount);
                buttonLabelMap.put(button, label);
                buttonCostMap.put(button, priceLabel);
                buttonIdMap.put(button, pid);
                menuItemsGridPane.getChildren().add(buttonNode);
                menuItemButtonController buttonController = loader2.getController();
                buttonController.setManagerControl(this);
                indexCount = indexCount + 1;
                

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

        items.get(items.size()-1).setIce(iceSelection);
        Double sugarLevel = 0.0;
        if(sugarSelection.equals("100% Sugar")){
            sugarLevel = 100.0;
        }
        else if(sugarSelection.equals("50% Sugar")){
            sugarLevel = 50.0;
        }
        else if(sugarSelection.equals("No Sugar")){
            sugarLevel = 0.0;
        }
        else if(sugarSelection.equals("120% Sugar")){
            sugarLevel = 120.0;
        }
        else if(sugarSelection.equals("80% Sugar")){
            sugarLevel = 80.0;
        }
        else if(sugarSelection.equals("30% Sugar")){
            sugarLevel = 30.0;
        }


        items.get(items.size()-1).setSugar(sugarLevel);
        TextArea additionalNotes = (TextArea) notesPane.lookup("#additionalNotes");
        items.get(items.size()-1).setNote(additionalNotes.getText());

        Double toppingCost = 0.00;
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
            items.get(items.size() -1 ).addToList(pearlButton.getText());
            toppingCost += 0.75;
        }
        if(miniPearlButton.isSelected()){
            items.get(items.size() -1 ).addToList(miniPearlButton.getText());
            toppingCost += 0.75;
        }
        if(iceCreamButton.isSelected()){
            items.get(items.size() -1 ).addToList(iceCreamButton.getText());
            toppingCost += 0.75;
        }
        if(puddingButton.isSelected()){
            items.get(items.size() -1 ).addToList(puddingButton.getText());
            toppingCost += 0.75;
        }
        if(aloeVeraButton.isSelected()){
            items.get(items.size() -1 ).addToList(aloeVeraButton.getText());
            toppingCost += 0.75;
        }
        if(redBeanButton.isSelected()){
            items.get(items.size() -1 ).addToList(redBeanButton.getText());
            toppingCost += 0.75;
        }
        if(herbJellyButton.isSelected()){
            items.get(items.size() -1 ).addToList(herbJellyButton.getText());
            toppingCost += 0.75;
        }
        if(aiyuJellyButton.isSelected()){
            items.get(items.size() -1 ).addToList(aiyuJellyButton.getText());
            toppingCost += 0.75;
        }
        if(lycheeJellyButton.isSelected()){
            items.get(items.size() -1 ).addToList(lycheeJellyButton.getText());
            toppingCost += 0.75;
        }
        if(crystalBobaButton.isSelected()){
            items.get(items.size() -1 ).addToList(crystalBobaButton.getText());
            toppingCost += 0.75;
        }
        
        double calculated_cost = Double.parseDouble(items.get(items.size() -1 ).getPrice().substring(8));
        calculated_cost += toppingCost;
        data.add(new Object[]{items.get(items.size() -1 ).getTeaType(), items.get(items.size() -1 ).getQuantity() ,calculated_cost}); 
        orderTotal+=calculated_cost;
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

    @FXML
    private void handleProceedButton(ActionEvent event)
    {
        //Customer First and Last Name:
        TextField customerName = (TextField) orderInfoPane.lookup("#customerNameTextField");
        String[] names = customerName.getText().split(" ");
        String customerFirstName = "";
        String customerLastName = "";
        customerFirstName = names[0];
        if(names.length == 2){
            customerLastName = names[1];
        }
        String employeeFirstName = loginPageController.getFirstName();
        String employeeLastName = loginPageController.getLastName();

        ArrayList<orderProduct> listOfItems = new ArrayList<>();
        //OrderProduct Details String Product, int ProductQuantity, ArrayList<String> ToppingList, double Sugar, String NoteInput
        for(int i = 0; i < items.size(); i++){
            //String PID, int ProductQuantity, ArrayList<String> ToppingList, double Sugar, String NoteInput
            orderProduct itemProduct = new orderProduct(
            items.get(i).getId(), items.get(i).getQuantity(), items.get(i).getToppings(), 
            items.get(i).getSugar(), items.get(i).getNote()
            );
            listOfItems.add(itemProduct);
        }
        
        // System.out.println("Customer Name: " + customerFirstName + " " + customerLastName);
        // System.out.println("Employee Name: " + employeeFirstName + " " + employeeLastName);
        // for(int i = 0; i < listOfItems.size(); i++){
        //     System.out.println("Product: " + listOfItems.get(i).ProductID);
        //     System.out.println("Quantity: " + listOfItems.get(i).Quantity);
        //     System.out.println("Sugar: " + listOfItems.get(i).SugarLevel);
        //     System.out.println("Note: " + listOfItems.get(i).Note);
        //     for(int j = 0; j < (items.get(i).getToppings().size()); j++){
        //         System.out.println("Topping: " + listOfItems.get(i).Toppings.get(j));
        //     }
            
        // }
        // System.out.println("OrderCost: " + orderTotal);

        //Customer First Name, Customer Last Name, Employee First Name, Employee Last Name, ArrayList of OrderProduct
        addOrderFull.addOrder(customerFirstName, customerLastName, employeeFirstName, employeeLastName, listOfItems, orderTotal);
        data.clear();
        orderTotal = 0.0;
        Label orderNumber = (Label) orderInfoPane.lookup("#orderNumberLabel");
        orderNumber.setText("Order #" + addOrderFull.nextOrderID());

    }

    @FXML
    private void handleInventoryButton(ActionEvent event){
        //Toggle Visibility of pretty much everything on the page (OrderInfoPane, menuItems Pane, ordercustomization Pane)
        if(orderCustomizationMenu.isVisible()){
            return;
        }
        else if(editMenuPage.isVisible())
        {
            return;
        }
        else{
            inventoryData.clear();
            orderInfoPane.setVisible(false);
            menuItems.setVisible(false);
            //Toggle Visibility for editInventory Page
            editInventoryPage.setVisible(true);
            initializeInventoryEditTable();
            ArrayList<ArrayList<String>> inventory = new ArrayList<>();
            inventory = SystemFunctions.getInventory(); // id, name, quantity, last updated
            for(int i = 0; i < inventory.get(0).size(); i++){
                inventoryData.add(new Object[]{inventory.get(0).get(i),inventory.get(1).get(i), inventory.get(2).get(i), inventory.get(3).get(i)}); 
            }
            inventoryTable.setItems(inventoryData);
            //System.out.println(inventory.get(0));
        }

       
    }

    @FXML
    private void handleEditMenuButton(ActionEvent event){
        editInventoryPage.setVisible(false);
        editMenuPage.setVisible(true);
    }

    @FXML
    private void handleEditInventoryButton(ActionEvent event){
        editMenuPage.setVisible(false);
        editInventoryPage.setVisible(true);
    }

    private void initializeInventoryEditTable(){
        InventoryIdColumn.setCellValueFactory(cellData -> {
        if (cellData.getValue() != null && cellData.getValue().length > 0) {
                return new SimpleStringProperty(cellData.getValue()[0].toString());
        } else {
                    return new SimpleStringProperty("Inventory ID");
            }
        });
            
        InventoryItemColumn.setCellValueFactory(cellData -> {
        if (cellData.getValue() != null && cellData.getValue().length > 1) { // Use index 1 for quantity
                return new SimpleStringProperty(cellData.getValue()[1].toString());
        } else {
                return new SimpleStringProperty("Item Name");
            }
        });
            
        InventoryQuantityColumn.setCellValueFactory(cellData -> {
        if (cellData.getValue() != null && cellData.getValue().length > 2) { // Use index 2 for price
                return new SimpleStringProperty(cellData.getValue()[2].toString());
        } else {
                return new SimpleStringProperty("Stock Quantity");
            }
        });

        InventoryUpdatedColumn.setCellValueFactory(cellData -> {
        if (cellData.getValue() != null && cellData.getValue().length > 3) { // Use index 2 for price
                return new SimpleStringProperty(cellData.getValue()[3].toString());
        } else {
                return new SimpleStringProperty("Last Updated");
            }
        });
    }

    @FXML
    private void checkoutButton(ActionEvent event){
        if(menuItems.isVisible()){
            return;
        }
        editInventoryPage.setVisible(false);
        editMenuPage.setVisible(false);
        orderCustomizationMenu.setVisible(false);
        orderInfoPane.setVisible(true);
        menuItems.setVisible(true);
        if(!items.isEmpty()){
            orderTotal -= Double.parseDouble(items.get(items.size()-1).getPrice());
            items.remove(items.size() - 1);
        }
    }

    @FXML
    private void handleAddInventory(ActionEvent event){
        TextField itemName = (TextField) inventoryPane.lookup("#itemNameTextField");
        TextField itemQuantity = (TextField) inventoryPane.lookup("#invQuantityTextField");
        try{
            int value = 0;
            value = Integer.parseInt(itemQuantity.getText());
            SystemFunctions.updateInventory(itemName.getText(), value);
            ArrayList<ArrayList<String>> inventory = new ArrayList<>();
            inventory = SystemFunctions.getInventory(); // id, name, quantity, last updated
            for(int i = 0; i < inventory.get(0).size(); i++){
                inventoryData.add(new Object[]{inventory.get(0).get(i),inventory.get(1).get(i), inventory.get(2).get(i), inventory.get(3).get(i)}); 
            }
            inventoryTable.setItems(inventoryData);
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Couldn't convert to a double.");
        }
        
    }


}
