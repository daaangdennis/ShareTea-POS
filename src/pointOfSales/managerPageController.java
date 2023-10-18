package pointOfSales;
import javafx.scene.input.MouseEvent;
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
import javafx.geometry.HPos;
import javafx.scene.control.DatePicker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Toggle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.text.DecimalFormat;
import pointOfSales.services.SystemFunctions;
import javafx.scene.control.TextField;
import javafx.scene.chart.XYChart;
import pointOfSales.entities.orderProduct;
import javafx.scene.chart.BarChart;

/**
 * The managerPageController function handles all the logic that for the manager page
 * The logic includes but is not limited to switching active scenes/AnchorPanes.
 * Initializing, inputting, and clearing table values.
 * Keeping track of dynamic buttons and order details
 * Calling backend functions to add data to the database and get data from the database.
 */

public class managerPageController implements Initializable {
    private sceneController sceneCtrl;

    //GridPanes for adding buttons
    @FXML
    public GridPane menuItemsGridPane;
    @FXML
    private GridPane foodCategoryGridPane;

    //Table for holding checkout information
    @FXML
    private TableView<Object[]> checkoutTable;
    @FXML
    private TableColumn<Object[], String> productTableColumn;
    @FXML
    private TableColumn<Object[], String> quantityTableColumn;
    @FXML
    private TableColumn<Object[], String> priceTableColumn;

    //Table for holding all inventory items
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

    //Table for hodling all product items on the menu
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

    //Table for holding all excess report data
    @FXML
    private TableView<Object[]> excessReportTable;
    @FXML
    private TableColumn<Object[], String> excessIDColumn;
    @FXML
    private TableColumn<Object[], String> excessNameColumn;
    @FXML
    private TableColumn<Object[], String> excessStockUsedColumn;
    @FXML
    private TableColumn<Object[], String> excessCurrentStockColumn;

    //Table for holding all restock report data
    @FXML
    private TableView<Object[]> restockReportTable;
    @FXML
    private TableColumn<Object[], String> restockIDColumn;
    @FXML
    private TableColumn<Object[], String> restockNameColumn;
    @FXML
    private TableColumn<Object[], String> restockQuantityColumn;

    //Table for holding all popular pairs report data
    @FXML
    private TableView<Object[]> popularPairsTable;
    @FXML
    private TableColumn<Object[], String> rankingColumn;
    @FXML
    private TableColumn<Object[], String> pairingColumn;

    //Table for holding previous orders 
    @FXML
    private TableView<Object[]> orderHistoryTable;
    @FXML 
    private TableColumn<Object[], String> historyOrderID;
    @FXML 
    private TableColumn<Object[], String> historyCustomerName;
    @FXML 
    private TableColumn<Object[], String> historyOrderDate;

    //Table for holding all details for an order
    @FXML
    private TableView<Object[]> orderHistoryInfoTable;
    @FXML 
    private TableColumn<Object[], String> productTableColumn1;
    @FXML 
    private TableColumn<Object[], String> quantityTableColumn1;
    @FXML 
    private TableColumn<Object[], String> priceTableColumn1;

    @FXML
    private TableView<Object[]> productUsageTable;
    @FXML 
    private TableColumn<Object[], String> usageInventoryID;
    @FXML 
    private TableColumn<Object[], String> usageItemName;
    @FXML 
    private TableColumn<Object[], String> usageStockUsed;

    //All AnchorPanes necessary for getting and displaying specific elements of fxml files
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
    private AnchorPane editProductPage;
    @FXML
    private AnchorPane statisticsPage;
    @FXML
    private AnchorPane salesPane;
    @FXML
    private AnchorPane orderHistoryInfo;
    @FXML
    private AnchorPane orderHistoryPage;
    @FXML
    private AnchorPane productUsagePane;

    @FXML
    private TextArea additionalNotes;
    @FXML
    private Label foodItemLabel;
    @FXML
    private BarChart<String, Integer> salesReportGraph;
    @FXML

    //Calendars used to select dates for reports
    private DatePicker saleStartDate;
    @FXML
    private DatePicker saleEndDate;
    @FXML
    private DatePicker excessTimestamp;
    @FXML
    private DatePicker pairingStartDate;
    @FXML
    private DatePicker pairingEndDate;
    @FXML
    private DatePicker orderHistoryStartDate;
    @FXML
    private DatePicker orderHistoryEndDate;
    @FXML
    private DatePicker productUsageStartDate;
    @FXML
    private DatePicker productUsageEndDate;

    //Lables for orders page
    @FXML
    public Label orderHistoryTotal;
    @FXML
    public Label orderHistoryNameLabel;
    @FXML
    public Label orderHistoryNumberLabel;

    //Toggle groups for order options
    private ToggleGroup teaGroup = new ToggleGroup();
    private ToggleGroup sugarGroup = new ToggleGroup();
    private ToggleGroup iceGroup = new ToggleGroup();

    //Values of sugar and ice selections for a product ordered by a customer
    private String sugarSelection;
    private String iceSelection;

    //Lists of data to add to tables and graphs
    private ObservableList<Object[]> data = FXCollections.observableArrayList();
    private ObservableList<Object[]> inventoryData = FXCollections.observableArrayList();
    private ObservableList<Object[]> productData = FXCollections.observableArrayList();
    private ObservableList<Object[]> excessData = FXCollections.observableArrayList();
    private ObservableList<Object[]> pairData = FXCollections.observableArrayList();
    private ObservableList<Object[]> restockData = FXCollections.observableArrayList();
    private ObservableList<Object[]> historyData = FXCollections.observableArrayList();
    private ObservableList<Object[]> selectedHistoryData = FXCollections.observableArrayList();
    private ObservableList<Object[]> usageData = FXCollections.observableArrayList();

    private Double foodLabelCost = 0.0;
    public ArrayList<orderedProduct> items = new ArrayList<>();
    public ArrayList<String> categories = new ArrayList<>();
    private ArrayList<ArrayList<String>> historyList = new ArrayList<>();

    //Maps for keeping references to buttons created dynamically
    public Map<Button, Label> buttonLabelMap = new HashMap<>();
    public Map<Button, Label> buttonCostMap = new HashMap<>();
    public Map<Button, String> buttonIdMap = new HashMap<>();

    public Double orderTotal = 0.0;
    public String employPosition = "";
    private int historyCounter = 0;
    
    
    /**
     * The initialize function initializes tables and and variables necessary when the page is loaded
     * 
     * @see {@link pointOfSales.services.SystemFunctions#nextOrderID()}
     * @see {@link pointOfSales.loginPageController#getPosition()}
     * @see {@link pointOfSales.managerPageController#setUpTeaPane()}
     */
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

        ToggleButton toggleButton1 = (ToggleButton) sugarPane.lookup("#hundredSugar");
        ToggleButton toggleButton2 = (ToggleButton) sugarPane.lookup("#fiftySugar");
        ToggleButton toggleButton3 = (ToggleButton) sugarPane.lookup("#noSugar");
        ToggleButton toggleButton4 = (ToggleButton) sugarPane.lookup("#hundredTwentySugar");
        ToggleButton toggleButton5 = (ToggleButton) sugarPane.lookup("#eightySugar");
        ToggleButton toggleButton6 = (ToggleButton) sugarPane.lookup("#thirtySugar");
        sugarGroup.getToggles().addAll(toggleButton1, toggleButton2, toggleButton3, toggleButton4, toggleButton5,
                toggleButton6);

        ToggleButton regularIceButton = (ToggleButton) icePane.lookup("#regularIce");
        ToggleButton lightIceButton = (ToggleButton) icePane.lookup("#lightIce");
        ToggleButton noIceButton = (ToggleButton) icePane.lookup("#noIce");
        ToggleButton extraIceButton = (ToggleButton) icePane.lookup("#extraIce");
        ToggleButton makeItHotButton = (ToggleButton) icePane.lookup("#hot");
        iceGroup.getToggles().addAll(regularIceButton, lightIceButton, noIceButton, extraIceButton, makeItHotButton);

        Label orderNumber = (Label) orderInfoPane.lookup("#orderNumberLabel");
        orderNumber.setText("Order #" + SystemFunctions.nextOrderID());
        employPosition = loginPageController.getPosition();
        setUpTeaPane();
    }

    /**
     * The getTable function returns a reference to the checkoutTable private member of the managerPageController class.
     * @return an object of type TableView<Object[]> to grab items from the table or modify values in the table
     */
    public TableView<Object[]> getTable() {
        return this.checkoutTable;
    }

    /**
     * The getMainMenu function returns a reference to the menuItems private member of the managerPageController class.
     * @return an object of type AnchorPane to grab elements defined in the anchorpane.
     */

    public AnchorPane getMainMenu() {
        return this.menuItems;
    }

    /**
     * The getSubMenu function returns a reference to the orderCustomizationMenu private member of the managerPageController class.
     * @return an object of type AnchorPane to grab elements defined in the anchorpane.
     */

    public AnchorPane getSubMenu() {
        return this.orderCustomizationMenu;
    }

    /**
     * The getFoodLabel function returns a reference to the foodItemLabel private member of the managerPageController class.
     * @return an object of type Label to modify in other classes
     */

    public Label getFoodLabel() {
        return this.foodItemLabel;
    }

    /**
     * The getFoodCost function returns a reference to the foodLabelCost private member of the managerPageController class.
     * @return an object of type Double used to get the cost of the current food product being processed.
     */
    public Double getFoodCost() {
        return this.foodLabelCost;
    }

    /**
     * The setFoodCost function sets the private member foodLabelCost of the managerPageController class.
     * @param cost The value that the member foodLabelCost is being set to.
     */
    public void setFoodCost(Double cost) {
        this.foodLabelCost = cost;
    }

    /**
     * The setController function sets the private member sceneCtrl of the managerPageController class.
     * @param controller value that the member sceneCtrl is set to.
     */
    public void setController(sceneController controller) {
        this.sceneCtrl = controller;
    }

    /**
     * The function handleChangeScene is the handler for the logout button.
     * This function resets the current employee and changes back to the loginPage scene.
     * @param event used to confirm the logoutButton was pressed.
     * @see {@link pointOfSales.sceneController#changeScene()}
     */
    @FXML
    private void handleChangeScene(ActionEvent event) {
        Button pressedButton = (Button) event.getSource();
        if (pressedButton.getId().equals("logoutButton")) {
            employPosition = "";
            sceneCtrl.changeScene(loginPage.getScene());
        }
    }

    /**
     * The setUpTeaPane function handles loading all the categories of all products on the menu and adding them to a gridPane
     * 
     * @see {@link pointOfSales.services.SystemFunctions#getCategories()}
     * @see {@link pointOfSales.menuItemButtonController#setManagerControl()}
     * @throws Exception throws when the fxml file could not be loaded.
     */

    private void setUpTeaPane() {
        int numButtons = 1;

        //clear and setup variables before loading data 

        categories.clear();
        categories = SystemFunctions.getCategories();
        numButtons = categories.size();
        foodCategoryGridPane.getChildren().clear();

        int index = 0;
        int swapper = 0;
        for (int i = 0; i < numButtons; i++) {
            try {
                //load and setup buttons
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("designFiles/foodCategoryButton.fxml"));
                Node buttonNode = loader2.load();
                ToggleButton button = (ToggleButton) buttonNode.lookup("#categoryButton");
                button.setText(categories.get(i));

                GridPane.setRowIndex(buttonNode, swapper);
                GridPane.setColumnIndex(buttonNode, index);

                // add buttons to gridpane
                foodCategoryGridPane.getChildren().add(buttonNode);
                GridPane.setHalignment(buttonNode, HPos.CENTER);
                button.setToggleGroup(teaGroup);

                //Make sure there are only 2 rows of buttons to maintain format, and columns are dynamically created when needed
                if (swapper == 1) {
                    swapper = 0;
                    index++;
                } else if (swapper == 0) {
                    swapper = 1;
                }

                //set controllers 
                foodItemButtonController buttonController = loader2.getController();
                buttonController.setManagerControl(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /*
     * The handleCloseButton function is the handler for the close button on the manager page.
     * This function is used to reset all selected toggle buttons on menuItems anchorpane
     * The function also removes the last element from the list of products that a customer is ordering 
     * because that item was cancelled during customization.
     * 
     */

    @FXML
    private void handleCloseButton() {
        //Make the current anchorpane invisible

        menuItems.setVisible(!menuItems.isVisible());
        orderCustomizationMenu.setVisible(!orderCustomizationMenu.isVisible());

        //Remove the last item from the running list of products for current customers order
        if (items.size() > 0) {
            items.remove(items.size() - 1);
        }
        
        //Resets sugar and ice toggles

        Toggle iceToggle = iceGroup.getSelectedToggle();
        Toggle sugarToggle = sugarGroup.getSelectedToggle();
        if(iceToggle != null){
            iceToggle.setSelected(false);
        }
        if(sugarToggle != null){
            sugarToggle.setSelected(false);
        }
        
        //Resets all topping toggles 

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

        pearlButton.setSelected(false);
        miniPearlButton.setSelected(false);
        iceCreamButton.setSelected(false);
        puddingButton.setSelected(false);
        aloeVeraButton.setSelected(false);
        redBeanButton.setSelected(false);
        herbJellyButton.setSelected(false);
        aiyuJellyButton.setSelected(false);
        lycheeJellyButton.setSelected(false);
        crystalBobaButton.setSelected(false);
    }

    /**
     * The checkToggledButton function checks whether or not a toggleGroup has a toggle that is selected.
     * @param toggleGroup The toggleGroup that is currently being checked to see what is selected.
     * @return a value of type String that is the text of the button that is currently toggled, or an empty string if nothing is toggled.
     */

    public String checkToggledButton(ToggleGroup toggleGroup) {
        String text = "";
        Toggle selectedToggle = toggleGroup.getSelectedToggle();

        //Makes sure not to do any type casting without checking if the type casting can be performed.

        if (selectedToggle != null && selectedToggle instanceof ToggleButton) {
            ToggleButton selectedButton = (ToggleButton) selectedToggle;
            text = selectedButton.getText();
        }
        return text;
    }

    /**
     * The addItem function adds the customized item to the checkoutTable.
     * It also creates a orderedProduct to add to an array keeping track of all customizations to the product.
     * @see {@link pointOfSales.managerPageController#checkToggledButton(ToggleGroup)}
     * @see {@link pointOfSales.orderedProduct#setIce(String)}
     * @see {@link pointOfSales.orderedProduct#setSugar(Double)}
     * @see {@link pointOfSales.orderedProduct#setNote(String)}
     * @see {@link pointOfSales.orderedProduct#addToList(String)}
     */

    @FXML
    private void addItem() {

        //Makes sure items can't be added without selecting both a sugar and an ice button

        sugarSelection = checkToggledButton(sugarGroup);
        iceSelection = checkToggledButton(iceGroup);
        if (sugarSelection == "" | iceSelection == "") {
            return;
        }

        //Checks the sugar selected and converts it to a double

        items.get(items.size() - 1).setIce(iceSelection);
        Double sugarLevel = 0.0;
        if (sugarSelection.equals("100% Sugar")) {
            sugarLevel = 100.0;
        } else if (sugarSelection.equals("50% Sugar")) {
            sugarLevel = 50.0;
        } else if (sugarSelection.equals("No Sugar")) {
            sugarLevel = 0.0;
        } else if (sugarSelection.equals("120% Sugar")) {
            sugarLevel = 120.0;
        } else if (sugarSelection.equals("80% Sugar")) {
            sugarLevel = 80.0;
        } else if (sugarSelection.equals("30% Sugar")) {
            sugarLevel = 30.0;
        }

        items.get(items.size() - 1).setSugar(sugarLevel);
        TextArea additionalNotes = (TextArea) notesPane.lookup("#additionalNotes");
        items.get(items.size() - 1).setNote(additionalNotes.getText());

        //Turns off all selected toggle buttons so the next product starts off with a clean set of buttons

        Toggle iceToggle = iceGroup.getSelectedToggle();
        Toggle sugarToggle = sugarGroup.getSelectedToggle();
        iceToggle.setSelected(false);
        sugarToggle.setSelected(false);

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

        if (pearlButton.isSelected()) {
            items.get(items.size() - 1).addToList(pearlButton.getText());
            toppingCost += 0.75;
            pearlButton.setSelected(false);
        }
        if (miniPearlButton.isSelected()) {
            items.get(items.size() - 1).addToList(miniPearlButton.getText());
            toppingCost += 0.75;
            miniPearlButton.setSelected(false);
        }
        if (iceCreamButton.isSelected()) {
            items.get(items.size() - 1).addToList(iceCreamButton.getText());
            toppingCost += 0.75;
            iceCreamButton.setSelected(false);
        }
        if (puddingButton.isSelected()) {
            items.get(items.size() - 1).addToList(puddingButton.getText());
            toppingCost += 0.75;
            puddingButton.setSelected(false);
        }
        if (aloeVeraButton.isSelected()) {
            items.get(items.size() - 1).addToList(aloeVeraButton.getText());
            toppingCost += 0.75;
            aloeVeraButton.setSelected(false);
        }
        if (redBeanButton.isSelected()) {
            items.get(items.size() - 1).addToList(redBeanButton.getText());
            toppingCost += 0.75;
            redBeanButton.setSelected(false);
        }
        if (herbJellyButton.isSelected()) {
            items.get(items.size() - 1).addToList(herbJellyButton.getText());
            toppingCost += 0.75;
            herbJellyButton.setSelected(false);
        }
        if (aiyuJellyButton.isSelected()) {
            items.get(items.size() - 1).addToList(aiyuJellyButton.getText());
            toppingCost += 0.75;
            aiyuJellyButton.setSelected(false);
        }
        if (lycheeJellyButton.isSelected()) {
            items.get(items.size() - 1).addToList(lycheeJellyButton.getText());
            toppingCost += 0.75;
            lycheeJellyButton.setSelected(false);
        }
        if (crystalBobaButton.isSelected()) {
            items.get(items.size() - 1).addToList(crystalBobaButton.getText());
            toppingCost += 0.75;
            crystalBobaButton.setSelected(false);
        }

        //gets the cost of the item with the toppings added

        double calculated_cost = Double.parseDouble(items.get(items.size() - 1).getPrice().substring(8));
        calculated_cost += toppingCost;

        //adds the data to the checkoutTable

        data.add(new Object[] { items.get(items.size() - 1).getTeaType(), items.get(items.size() - 1).getQuantity(),
                String.format("%.2f", calculated_cost) });
        orderTotal += calculated_cost;
        checkoutTable.setItems(data);
        menuItems.setVisible(!menuItems.isVisible());
        orderCustomizationMenu.setVisible(!orderCustomizationMenu.isVisible());
        
        //changes labels for cost to reflect the current cost of the order

        Label checkoutSubTotal = (Label) orderInfoPane.lookup("#checkoutSubTotal");
        checkoutSubTotal.setText("$" + String.format("%.2f", orderTotal));
        Label checkoutTax = (Label) orderInfoPane.lookup("#checkoutTax");
        checkoutTax.setText("$" + String.format("%.2f", (orderTotal * 0.0825)));
        Label checkoutTrueTotal = (Label) orderInfoPane.lookup("#checkoutTrueTotal");
        checkoutTrueTotal.setText("$" + String.format("%.2f", (orderTotal + (orderTotal * 0.0825))));

    }

    /**
     * The handleCancelButton function clears all relevant variables, labels, and tables.
     * So the next order starts off as if no order was made before it.
     * 
     */

    @FXML
    private void handleCancelButton() {
        data.clear();
        orderTotal = 0.0;
        Label checkoutSubTotal = (Label) orderInfoPane.lookup("#checkoutSubTotal");
        checkoutSubTotal.setText("$0.00");
        Label checkoutTax = (Label) orderInfoPane.lookup("#checkoutTax");
        checkoutTax.setText("$0.00");
        Label checkoutTrueTotal = (Label) orderInfoPane.lookup("#checkoutTrueTotal");
        checkoutTrueTotal.setText("$0.00");
    }

    /**
     * The handleToppingButtons function dynamically changes the price displayed in the UI 
     * when you click and unclick the buttons.
     * @param event used to check if the source button is selected
     */
    
    @FXML
    private void handleToppingButtons(ActionEvent event) {
        ToggleButton sourceButton = (ToggleButton) event.getSource();
        if (!sourceButton.isSelected()) {
            foodLabelCost -= 0.75;
        } else {
            foodLabelCost += 0.75;
        }

        //Enforces a format for how to display the price

        DecimalFormat df = new DecimalFormat("Food Item $0.00 ");
        String formattedCost = df.format(foodLabelCost);
        foodItemLabel.setText(formattedCost);
    }

    /**
     * The handleProceedButton function is the handler for the proceed button.
     * This function grabs all the information about the current order and passes it to the backend
     * to add the order to the database.
     * 
     * @see {@link pointOfSales.loginPageController#getFirstName()}
     * @see {@link pointOfSales.loginPageController#getLastName()}
     * @see {@link pointOfSales.entities.orderProduct#orderproduct(String, int, ArrayList<String>, double, String, String)}
     * @see {@link pointOfSales.orderedProduct#getId()}
     * @see {@link pointOfSales.orderedProduct#getQuantity()}
     * @see {@link pointOfSales.orderedProduct#getToppings()}
     * @see {@link pointOfSales.orderedProduct#getSugar()}
     * @see {@link pointOfSales.orderedProduct#getIce()}
     * @see {@link pointOfSales.orderedProduct#getNote()}
     * @see {@link pointOfSales.services.SystemFunctions#addOrder(String, String, String, String, ArrayList, double)}
     * @see {@link pointOfSales.services.SystemFunctions#nextOrderID()}
     */

    @FXML
    private void handleProceedButton() {
        // Getting Customer First and Last Name:
        TextField customerName = (TextField) orderInfoPane.lookup("#customerNameTextField");
        String[] names = customerName.getText().split(" ");
        String customerFirstName = "";
        String customerLastName = "";
        customerFirstName = names[0];
        if (names.length == 2) {
            customerLastName = names[1];
        }
        String employeeFirstName = loginPageController.getFirstName();
        String employeeLastName = loginPageController.getLastName();

        //Adding orderProduct objects to an Array which represents the products and customizations in the order

        ArrayList<orderProduct> listOfItems = new ArrayList<>();

        // OrderProduct Details String Product, int ProductQuantity, ArrayList<String>
        // ToppingList, double Sugar, String NoteInput
        for (int i = 0; i < items.size(); i++) {
            // String PID, int ProductQuantity, ArrayList<String> ToppingList, double Sugar,
            // String NoteInput
            orderProduct itemProduct = new orderProduct(
                    items.get(i).getId(), items.get(i).getQuantity(), items.get(i).getToppings(),
                    items.get(i).getSugar(), items.get(i).getIce(), items.get(i).getNote());
            listOfItems.add(itemProduct);
        }

        orderTotal += orderTotal * 0.0825;

        SystemFunctions.addOrder(customerFirstName, customerLastName, employeeFirstName, employeeLastName, listOfItems,
                orderTotal);

        //Cleaning up variables and data for next order
        data.clear();
        orderTotal = 0.0;
        Label orderNumber = (Label) orderInfoPane.lookup("#orderNumberLabel");
        orderNumber.setText("Order #" + SystemFunctions.nextOrderID());
        Label checkoutSubTotal = (Label) orderInfoPane.lookup("#checkoutSubTotal");
        checkoutSubTotal.setText("$0.00");
        Label checkoutTax = (Label) orderInfoPane.lookup("#checkoutTax");
        checkoutTax.setText("$0.00");
        Label checkoutTrueTotal = (Label) orderInfoPane.lookup("#checkoutTrueTotal");
        checkoutTrueTotal.setText("$0.00");
        
    }

    /**
     * The handleInventoryButton function changes the active UI to the inventory menu.
     * This function also loads the inventory table, so the table is filled when the page is loaded.
     * 
     * @see {@link pointOfSales.managerPageController#initializeInventoryEditTable()}
     * @see {@link pointOfSales.services.SystemFunctions#getInventory()}
     */

    @FXML
    private void handleInventoryButton() {
        // Toggle Visibility of pretty much everything on the page (OrderInfoPane,
        // menuItems Pane, ordercustomization Pane)
        if (orderCustomizationMenu.isVisible()) {
            return;
        } else if (editMenuPage.isVisible()) {
            return;
        } else {
            inventoryData.clear();
            orderInfoPane.setVisible(false);
            statisticsPage.setVisible(false);
            orderHistoryInfo.setVisible(false);
            orderHistoryPage.setVisible(false);
            menuItems.setVisible(false);
            editInventoryPage.setVisible(true);

            //Initialize table and populate array with inventory values

            initializeInventoryEditTable();
            ArrayList<ArrayList<String>> inventory = new ArrayList<>();
            inventory = SystemFunctions.getInventory(); // id, name, quantity, last updated

            //load array values into the inventoryTable

            for (int i = 0; i < inventory.get(0).size(); i++) {
                inventoryData.add(new Object[] { inventory.get(0).get(i), inventory.get(1).get(i),
                        inventory.get(2).get(i), inventory.get(3).get(i) });
            }
            inventoryTable.setItems(inventoryData);
        }

    }

    /**
     * The handleEditMenuButton function changes to the editMenu page where products on the menu can be added or deleted
     * 
     * @see {@link pointOfSales.managerPageController#initializeMenuEditTable()}
     * @see {@link pointOfSales.services.SystemFunctions#getProducts()}
     */

    @FXML
    private void handleEditMenuButton() {
        //clear data fields and change the visiblity of the active UI
        productData.clear();
        editInventoryPage.setVisible(false);
        editMenuPage.setVisible(true);
        initializeMenuEditTable();

        //populate the array

        ArrayList<ArrayList<String>> productInventory = new ArrayList<>();
        productInventory = SystemFunctions.getProducts();

        //load the array values to the table

        for (int i = 0; i < productInventory.get(0).size(); i++) {
            productData.add(new Object[] { productInventory.get(0).get(i), productInventory.get(1).get(i),
                    productInventory.get(2).get(i), productInventory.get(3).get(i) });
        }
        inventoryMenuTable.setItems(productData);
    }

    /**
     * The handleEditInventoryButton function just switches from the menu product management page to the inventory management page.
     * 
     */

    @FXML
    private void handleEditInventoryButton() {
        editMenuPage.setVisible(false);
        editInventoryPage.setVisible(true);
    }

    /**
     * The initializeInventoryEditTable function initializes the inventory table to add values to it later.
     */

    private void initializeInventoryEditTable() {

        //initializes the Inventory ID column

        InventoryIdColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 0) {
                return new SimpleStringProperty(cellData.getValue()[0].toString());
            } else {
                return new SimpleStringProperty("Inventory ID");
            }
        });

        //initializes the Item Name column

        InventoryItemColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 1) { // Use index 1 for quantity
                return new SimpleStringProperty(cellData.getValue()[1].toString());
            } else {
                return new SimpleStringProperty("Item Name");
            }
        });

        //initializes the Stock Quantity column

        InventoryQuantityColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 2) { // Use index 2 for price
                return new SimpleStringProperty(cellData.getValue()[2].toString());
            } else {
                return new SimpleStringProperty("Stock Quantity");
            }
        });

        //initializes the Last Updated column

        InventoryUpdatedColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 3) { // Use index 2 for price
                return new SimpleStringProperty(cellData.getValue()[3].toString());
            } else {
                return new SimpleStringProperty("Last Updated");
            }
        });
    }

    /**
     * The initializeMenuEditTable function initializes the product table to add values to it later
     */

    private void initializeMenuEditTable() {

        //initializes the Product ID column

        menuIdColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 0) {
                return new SimpleStringProperty(cellData.getValue()[0].toString());
            } else {
                return new SimpleStringProperty("Product ID");
            }
        });

        //initializes the Product Name column

        menuItemColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 1) { // Use index 1 for quantity
                return new SimpleStringProperty(cellData.getValue()[1].toString());
            } else {
                return new SimpleStringProperty("Product Name");
            }
        });

        //initializes the Category column

        menuCategoryColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 2) { // Use index 2 for price
                return new SimpleStringProperty(cellData.getValue()[2].toString());
            } else {
                return new SimpleStringProperty("Category");
            }
        });

        //initializes the Price column

        menuPriceColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 3) { // Use index 2 for price
                return new SimpleStringProperty(cellData.getValue()[3].toString());
            } else {
                return new SimpleStringProperty("Price");
            }
        });
    }

    /**
     * The checkoutButton function is the handler for the checkout button on the sidebar.
     * This function turns the visibilty of all other UI elements off and all of the UI for the checkout menu on.
     * The function also clears any left over values in the table that may be there before navigating off the checkout menu.
     * 
     * @see {@link pointOfSales.managerPageController#setUpTeaPane()}
     */

    @FXML
    private void checkoutButton() {

        //Toggle visibility of AnchorPanes

        if (menuItems.isVisible()) {
            return;
        }
        menuItemsGridPane.getChildren().clear();
        foodCategoryGridPane.getChildren().clear();
        setUpTeaPane();
        statisticsPage.setVisible(false);
        editInventoryPage.setVisible(false);
        editMenuPage.setVisible(false);
        orderCustomizationMenu.setVisible(false);
        orderHistoryInfo.setVisible(false);
        orderHistoryPage.setVisible(false);
        orderInfoPane.setVisible(true);
        menuItems.setVisible(true);

        //Clear tables and labels in case any data was left over before navigating off of the page

        if (!items.isEmpty()) {
            orderTotal = 0.0;
            items.clear();
            data.clear();
            Label checkoutSubTotal = (Label) orderInfoPane.lookup("#checkoutSubTotal");
            checkoutSubTotal.setText("$0.00");
            Label checkoutTax = (Label) orderInfoPane.lookup("#checkoutTax");
            checkoutTax.setText("$0.00");
            Label checkoutTrueTotal = (Label) orderInfoPane.lookup("#checkoutTrueTotal");
            checkoutTrueTotal.setText("$0.00");
        }
    }

    /**
     * The handleAddInventory function is the handler for the add inventory button on the inventory management page.
     * This function adds whatever values were entered into the textfields to the database and refreshes the table
     * 
     * @see {@link pointOfSales.services.SystemFunctions#getInventory()}
     * @see {@link pointOfSales.services.SystemFunctions#updateInventory()}
     * @throws NumberFormatException if the values that were pulled from the database cannot be converted to doubles.
     */

    @FXML
    private void handleAddInventory() {

        //grabs the textfields information

        TextField itemName = (TextField) inventoryPane.lookup("#itemNameTextField");
        TextField itemQuantity = (TextField) inventoryPane.lookup("#invQuantityTextField");
        try {

            //attempts to update the database

            int value = 0;
            value = Integer.parseInt(itemQuantity.getText());
            SystemFunctions.updateInventory(itemName.getText(), value);
            ArrayList<ArrayList<String>> inventory = new ArrayList<>();

            //clears the table and populates it with the new inventory which includes the item just added

            inventoryData.clear();
            inventory = SystemFunctions.getInventory(); // id, name, quantity, last updated
            for (int i = 0; i < inventory.get(0).size(); i++) {
                inventoryData.add(new Object[] { inventory.get(0).get(i), inventory.get(1).get(i),
                        inventory.get(2).get(i), inventory.get(3).get(i) });
            }

            //sets the table and clears the text fields

            inventoryTable.setItems(inventoryData);
            itemName.clear();
            itemQuantity.clear();
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Couldn't convert to a double.");
        }

    }

    /**
     * The handleDeleteInventory function deletes an item from the inventory based off of the values in the text fields
     * 
     * @see {@link pointOfSales.services.SystemFunctions#deleteInventory(String))}
     * @see {@link pointOfSales.services.SystemFunctions#getInventory()}
     */

    @FXML
    private void handleDeleteInventory() {

        //get the text from the text fields

        TextField itemName = (TextField) inventoryPane.lookup("#itemNameTextField");
        TextField itemQuantity = (TextField) inventoryPane.lookup("#invQuantityTextField");

        //delete an item from the inventory

        SystemFunctions.deleteInventory(itemName.getText());

        //clears the table and grabs the new inventory data from the database to populate the table

        inventoryData.clear();
        ArrayList<ArrayList<String>> inventory = new ArrayList<>();
        inventory = SystemFunctions.getInventory(); // id, name, quantity, last updated
        for (int i = 0; i < inventory.get(0).size(); i++) {
            inventoryData.add(new Object[] { inventory.get(0).get(i), inventory.get(1).get(i), inventory.get(2).get(i),
                    inventory.get(3).get(i) });
        }

        //sets data to table and clears text fields

        inventoryTable.setItems(inventoryData);
        itemName.clear();
        itemQuantity.clear();
        inventoryTable.refresh();
    }

    /**
     * The handleAddProduct function is the handler for the add product button.
     * This function adds a new product to the database and updates the table immediately to display the new item
     * 
     * @see {@link pointOfSales.services.SystemFunctions#updateAddProduct(String, String, Double)}
     * @see {@link pointOfSales.services.SystemFunctions#getProducts()}
     * @throws NumberFormatException if the data from the database cannot be converted to a value of double
     */

    @FXML
    private void handleAddProduct() {

        //grabs the information from the text fields

        TextField name = (TextField) editProductPage.lookup("#productNameTextField");
        TextField category = (TextField) editProductPage.lookup("#stockTextField");
        TextField price = (TextField) editProductPage.lookup("#priceTextField");
        try {

            //adds the product to the database

            SystemFunctions.updateAddProduct(name.getText(), category.getText(), Double.parseDouble(price.getText()));

            //repopulates the table with the new products added

            ArrayList<ArrayList<String>> productInventory = new ArrayList<>();
            productInventory = SystemFunctions.getProducts();
            productData.clear();
            for (int i = 0; i < productInventory.get(0).size(); i++) {
                productData.add(new Object[] { productInventory.get(0).get(i), productInventory.get(1).get(i),
                        productInventory.get(2).get(i), productInventory.get(3).get(i) });
            }

            //clears the text fields and sets the data to the table

            inventoryMenuTable.setItems(productData);
            name.clear();
            category.clear();
            price.clear();
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Couldn't convert to a double.");
        }
    }

    /**
     * The handleDeleteProduct function is the handler for the delete button on the product management page.
     * This function deletes a product from the database and updates the table to show the change in the UI.
     * 
     * @see {@link pointOfSales.services.SystemFunctions#deleteProduct()}
     * @see {@link pointOfSales.services.SystemFunctions#getProducts()}
     * @throws NumberFormatException in case a value cannot be converted to a double
     */

    @FXML
    private void handleDeleteProduct() {

        //grabs the information from the text fields

        TextField name = (TextField) editProductPage.lookup("#productNameTextField");
        TextField category = (TextField) editProductPage.lookup("#stockTextField");
        TextField price = (TextField) editProductPage.lookup("#priceTextField");

        try {

            //deletes the product from the database

            SystemFunctions.deleteProduct(name.getText());

            // clears the table and populates an array with the new list of products

            ArrayList<ArrayList<String>> productInventory = new ArrayList<>();
            productInventory = SystemFunctions.getProducts();
            productData.clear();
            for (int i = 0; i < productInventory.get(0).size(); i++) {
                productData.add(new Object[] { productInventory.get(0).get(i), productInventory.get(1).get(i),
                        productInventory.get(2).get(i), productInventory.get(3).get(i) });
            }

            //sets the data onto the table and clears the text fields

            inventoryMenuTable.setItems(productData);
            name.clear();
            category.clear();
            price.clear();
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Couldn't convert to a double.");
        }
    }

    /**
     * The handleClearInventory function is the handler for the clear button on the inventory management page.
     * This function clears the textfields on the inventory management page.
     */

    @FXML
    private void handleClearInventory() {
        TextField itemName = (TextField) inventoryPane.lookup("#itemNameTextField");
        TextField itemQuantity = (TextField) inventoryPane.lookup("#invQuantityTextField");

        itemName.clear();
        itemQuantity.clear();
    }

    /**
     * The handleClearProduct function is the handler for the clear button on the product management page.
     * This function clears the textfields on the product management page.
     */

    @FXML
    private void handleClearProduct() {
        TextField name = (TextField) editProductPage.lookup("#productNameTextField");
        TextField category = (TextField) editProductPage.lookup("#stockTextField");
        TextField price = (TextField) editProductPage.lookup("#priceTextField");

        name.clear();
        category.clear();
        price.clear();
    }

    /**
     * The handleStatPage function is the handler for the statistics button on the right nav bar.
     * This function turns off the visibility of all UI elements and turns on the UI elements that the stat page should only display
     * 
     * @see {@link pointOfSales.managerPageController#initializeExcessTable()}
     * @see {@link pointOfSales.managerPageController#initializePairTable()}
     * @see {@link pointOfSales.managerPageController#initializeRestockTable()}
     * @see {@link pointOfSales.managerPageController#handleRestockReport()}
     */

    @FXML
    private void handleStatPage() {
        if (orderCustomizationMenu.isVisible()) {
            return;
        } else if (editMenuPage.isVisible()) {
            return;
        } else{
            editInventoryPage.setVisible(false);
            editMenuPage.setVisible(false);
            orderCustomizationMenu.setVisible(false);
            orderInfoPane.setVisible(false);
            menuItems.setVisible(false);
            orderHistoryInfo.setVisible(false);
            orderHistoryPage.setVisible(false);
            salesPane.setVisible(true);
            productUsagePane.setVisible(false);
            statisticsPage.setVisible(true);
            initializeExcessTable();
            initializePairTable();
            initializeRestockTable();
            handleRestockReport();
        }
    }

    /**
     * The handleSalesReport function is the handler for the generate sales report button.
     * This function takes the entries from the 2 dates the user entered in the UI and creates
     * a bar graph displaying how each product performed in sales over that given time period
     * 
     * @see {@link pointOfSales.services.SystemFunctions#getProductSales(String, String)}
     * @throws NumberFormatException fails in formatting the dates correctly
     */

    @FXML
    private void handleSalesReport() {

        //grabs data from the date entries and clears the bar graph

        String startText = saleStartDate.getValue().toString();
        String endText = saleEndDate.getValue().toString();
        salesReportGraph.getData().clear();

        ArrayList<ArrayList<Object>> values = new ArrayList<>();
        values = SystemFunctions.getProductSales(startText, endText);
        try {

            //populates a series with datapoints of each products performance in sales

            XYChart.Series<String, Integer> series = new XYChart.Series<>();

            for (int i = 0; i < values.get(0).size(); i++) {
                String products = "";
                if((values.get(0).get(i).toString()).length() > 12){
                    products = (values.get(0).get(i).toString()).substring(0, 11) + "...";
                }
                else{
                    products = (values.get(0).get(i).toString());
                }
                
                int itemsSold = Integer.parseInt(values.get(1).get(i).toString());
                // XYChart.Series<String, Integer> series = new XYChart.Series<>();
                series.getData().add(new XYChart.Data<>(products, itemsSold));
                // series.setName(products);
                // salesReportGraph.getData().add(series);
            }

            //adds the series to the graph to display the stats

            salesReportGraph.getData().add(series);

            // Change the bar colors to match theme
            for (XYChart.Data<String, Integer> datapoint : series.getData()) {
                datapoint.getNode().setStyle("-fx-bar-fill: #cf152d;");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Error in formatting info in @handleSalesReport");
        }

    }

    /**
     * The handleExcessReport function is the handler for the date entry field.
     * This function when a date is entered attempts to display the excess stock for that day in the excess table
     * 
     * @see {@link pointOfSales.services.SystemFunctions#getExcessStock(String)}
     */

    @FXML
    private void handleExcessReport() {

        //clears the table and calls the function to grab the excess stock

        excessData.clear();
        String excessTime = excessTimestamp.getValue().toString();
        ArrayList<ArrayList<Object>> excessStock = new ArrayList<>();
        excessStock = SystemFunctions.getExcessStock(excessTime);

        //adds the data points for the excess stock to a data field

        for (int i = 0; i < excessStock.get(0).size(); i++) {
            excessData.add(new Object[] { excessStock.get(0).get(i), excessStock.get(1).get(i),
                    excessStock.get(2).get(i), excessStock.get(3).get(i) });
        }

        //adds the datafield to the table

        excessReportTable.setItems(excessData);
    }

    /**
     * The handlePopularPair function is a handler for the generate report button in the popular pair field.
     * This function takes 2 dates from datefields and ranks the 2 most often sold products together from greatest to least.
     * Then it displays the ranking in a table
     * 
     * @see {@link pointOfSales.services.SystemFunctions#getPairs(String, String)}
     */

    @FXML
    private void handlePopularPair() {
        pairData.clear();
        String pairStartText = pairingStartDate.getValue().toString();
        String pairEndText = pairingEndDate.getValue().toString();
        ArrayList<ArrayList<Object>> pairList = new ArrayList<>();

        //populating the datafield with the arraylist from the function

        pairList = SystemFunctions.getPairs(pairStartText, pairEndText);
        for (int i = 0; i < pairList.get(0).size(); i++) {
            pairData.add(new Object[] { (i + 1), pairList.get(0).get(i) + ", " + pairList.get(1).get(i)});
        }
        popularPairsTable.setItems(pairData);
    }
    
    /**
     * The handleRestockReport function determines whether or not items are
     * passed the threshold determined in the database to be restocked and if there are any items, then they are displayed in a table.
     * 
     * @see {@link pointOfSales.services.SystemFunctions#getLowStock()}
     */

    private void handleRestockReport(){
        restockData.clear();
        ArrayList<ArrayList<Object>> restockList = new ArrayList<>();

        //populates the data field and sets it to the table with the array from the getLowStock function

        restockList = SystemFunctions.getLowStock();
        for (int i = 0; i < restockList.get(0).size(); i++) {
            restockData.add(new Object[] { restockList.get(0).get(i), restockList.get(1).get(i), restockList.get(2).get(i)});
        }
        restockReportTable.setItems(restockData);
    }

    /**
     * The initializeExcessTable function initializes the excess table so data can be added to it.
     */

    private void initializeExcessTable() {

        //initializes the Inventory ID column

        excessIDColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 0) {
                return new SimpleStringProperty(cellData.getValue()[0].toString());
            } else {
                return new SimpleStringProperty("Inventory ID");
            }
        });

        //initializes the Item Name column

        excessNameColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 1) { // Use index 1 for quantity
                return new SimpleStringProperty(cellData.getValue()[1].toString());
            } else {
                return new SimpleStringProperty("Item Name");
            }
        });

        //initializes the Stock Used column

        excessStockUsedColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 2) { // Useindex 2 for price
                return new SimpleStringProperty(cellData.getValue()[2].toString());
            } else {
                return new SimpleStringProperty("Stock Used");
            }
        });

        //initializes the Current Stock column

        excessCurrentStockColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 3) { // Use index 3 for price
                return new SimpleStringProperty(cellData.getValue()[3].toString());
            } else {
                return new SimpleStringProperty("Current Stock");
            }
        });

    }

    /**
     * The initializePairTable function initializes the pair table so data can be added to it.
     */

    private void initializePairTable() {

        //initializes the ranking column

        rankingColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 0) {
                return new SimpleStringProperty(cellData.getValue()[0].toString());
            } else {
                return new SimpleStringProperty("Ranking");
            }
        });

        //initializes the pairing column

        pairingColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 1) { // Use index 1 for quantity
                return new SimpleStringProperty(cellData.getValue()[1].toString());
            } else {
                return new SimpleStringProperty("Pairing");
            }
        });

    }

    /**
     * The initializeRestockTable function initializes the restock table so data can be added to it.
     */
    
    private void initializeRestockTable(){

        //initializes the Inventory ID column

        restockIDColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 0) {
                return new SimpleStringProperty(cellData.getValue()[0].toString());
            } else {
                return new SimpleStringProperty("Inventory ID");
            }
        });

        //initializes the Item Name column

        restockNameColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 1) { // Use index 1 for quantity
                return new SimpleStringProperty(cellData.getValue()[1].toString());
            } else {
                return new SimpleStringProperty("Item Name");
            }
        });

        //initializes the Stock Quantity column

        restockQuantityColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 2) { // Useindex 2 for price
                return new SimpleStringProperty(cellData.getValue()[2].toString());
            } else {
                return new SimpleStringProperty("Stock Quantity");
            }
        });
    }

    /**
     * The initializeOrderHistoryTable function initializes the orderHistory table so data can be added to it.
     */

    private void initializeOrderHistoryTable(){

        //initializes the Order ID column

        historyOrderID.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 0) {
                return new SimpleStringProperty(cellData.getValue()[0].toString());
            } else {
                return new SimpleStringProperty("Order ID");
            }
        });

        //initializes the Customer Name column

        historyCustomerName.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 1) { // Use index 1 for quantity
                return new SimpleStringProperty(cellData.getValue()[1].toString());
            } else {
                return new SimpleStringProperty("Customer Name");
            }
        });

        //initializes the Order Date column

        historyOrderDate.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 2) { // Useindex 2 for price
                return new SimpleStringProperty(cellData.getValue()[2].toString());
            } else {
                return new SimpleStringProperty("Order Date");
            }
        });
    }

    /**
     * The handleOrdersButton is the handler for the orders button on the right nav bar.
     * This function toggles the visibility of all UI elements not on that page off 
     * and toggles on all the UI elements that are on the page.
     * It also initializes the tables on the page.
     * 
     * @see {@link pointOfSales.managerPageController#initializeOrderHistoryTable()}
     * @see {@link pointOfSales.managerPageController#initializeHistoryInfoTable()}
     */

    @FXML 
    private void handleOrdersButton(){
        if (orderCustomizationMenu.isVisible()) {
            return;
        } else if (editMenuPage.isVisible()) {
            return;
        } else{
            statisticsPage.setVisible(false);
            editInventoryPage.setVisible(false);
            editMenuPage.setVisible(false);
            orderCustomizationMenu.setVisible(false);
            orderInfoPane.setVisible(false);
            menuItems.setVisible(false);
            orderHistoryInfo.setVisible(true);
            orderHistoryPage.setVisible(true);
            initializeOrderHistoryTable();
            initializeHistoryInfoTable();
        }
    }

    /**
     * The handleGenerateOrderHistory function is the handler for the generate button on the orders page.
     * This function takes a date window and adds 25 orders to the orderHistory table.
     * 
     * @see {@link pointOfSales.services.SystemFunctions#getOrdersByDates(String, String)}
     * 
     */

    @FXML
    private void handleGenerateOrderHistory(){

        //clear the data and list private members and get the dates

        historyData.clear();
        historyList.clear();
        historyCounter = 0;
        String orderHistoryStart = orderHistoryStartDate.getValue().toString();
        String orderHistoryEnd = orderHistoryEndDate.getValue().toString();

        //populate the list with the array from the getOrdersByDates

        historyList = SystemFunctions.getOrdersByDates(orderHistoryStart, orderHistoryEnd);
        int buffer = 25;
        if(buffer > historyList.get(0).size()){
            buffer = historyList.get(0).size();
        }
        for (int i = 0; i < buffer; i++) {
            historyData.add(new Object[] { historyList.get(0).get(i), historyList.get(1).get(i), historyList.get(2).get(i)});
        }

        //set the data to the table

        orderHistoryTable.setItems(historyData);
    }

    /**
     * The handleForwardHistory function is the handler for the next button on the orders page.
     * This function grabs the next 1 <= x <= 25 values from the array that was generated by handleGenerateOrderHistory.
     * Then it clears the table and displays the next 25 values.
     */

    @FXML
    private void handleForwardHistory(){

        //make sure there are more values to get

        historyCounter++;
        int bufferArray = (historyCounter + 1) * 25;
        int indexMod = 25;
        if(historyList.get(0).size() / 25 < historyCounter){
            historyCounter--;
            return;
        }
        if(bufferArray > historyList.get(0).size()){
            bufferArray -= 25;
            indexMod = historyList.get(0).size() - bufferArray;
            bufferArray = historyList.get(0).size();
        }

        //clear the table and add the next values in the range 1 <= x <= 25 to the table

        historyData.clear();
        for (int i = bufferArray - indexMod; i < bufferArray; i++) {
            historyData.add(new Object[] { historyList.get(0).get(i), historyList.get(1).get(i), historyList.get(2).get(i)});
        }
        orderHistoryTable.setItems(historyData);
    }

    /**
     * The handleBackwardHistory function is the handler for the prev button on the orders page.
     * This function grabs the previous 25 values and loads them into the table.
     */

    @FXML
    private void handleBackwardHistory(){

        //make sure the index does not go below zero

        historyCounter--;
        if(historyCounter < 0){
            historyCounter++;
            return;
        }
        int bufferArray = (historyCounter + 1) * 25;
        
        //clear the table and add the previous 25 entries to the table

        historyData.clear();
        for (int i = bufferArray - 25; i < bufferArray; i++) {
            historyData.add(new Object[] { historyList.get(0).get(i), historyList.get(1).get(i), historyList.get(2).get(i)});
        }
        orderHistoryTable.setItems(historyData);
    }

    /**
     * The initializeHistoryInfoTable function initializes the historyInfoTable so values can be added to it.
     */

    private void initializeHistoryInfoTable(){

        //initializes the product name column

        productTableColumn1.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 0) {
                return new SimpleStringProperty(cellData.getValue()[0].toString());
            } else {
                return new SimpleStringProperty("Product Name");
            }
        });

        //initializes the quantity column

        quantityTableColumn1.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 1) { // Use index 1 for quantity
                return new SimpleStringProperty(cellData.getValue()[1].toString());
            } else {
                return new SimpleStringProperty("Quantity");
            }
        });

        //initializes the price column

        priceTableColumn1.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 2) { // Useindex 2 for price
                return new SimpleStringProperty(cellData.getValue()[2].toString());
            } else {
                return new SimpleStringProperty("Price ($)");
            }
        });
    }

    /**
     * The handleHistoryTableClick function is the handler the order history table.
     * This function whenever a row is selected on the table, it adds the details of the order to the
     * order history info table to show what was ordered and how much it cost.
     * @param event is used to make sure that a row was clicked
     * @see {@link pointOfSales.services.SystemFunctions#getOrderProductByID(Integer)}
     */

    @FXML
    private void handleHistoryTableClick(MouseEvent event){

        //clears table and grabs the info from the selected row

        selectedHistoryData.clear();
        Object[] selectedRow = null;
        if(event.getClickCount() == 1){
            selectedRow = orderHistoryTable.getSelectionModel().getSelectedItem();
            //Check if null
        }
        if(selectedRow != null)
        {
            //Changes the labels for the customer name, id, and total cost
            //Also fills the table with the products that the orderId ordered

            orderHistoryNameLabel.setText(selectedRow[1].toString());
            orderHistoryNumberLabel.setText("Order #" + selectedRow[0].toString());
            ArrayList <ArrayList<String>> values = new ArrayList<>();
            values = SystemFunctions.getOrderProductByID(Integer.parseInt(selectedRow[0].toString()));
            for (int i = 0; i < values.get(0).size(); i++) {
                selectedHistoryData.add(new Object[] { values.get(0).get(i), values.get(1).get(i), values.get(2).get(i)});
            }
            orderHistoryInfoTable.setItems(selectedHistoryData);
          
            orderHistoryTotal.setText("$"+ values.get(3).get(0));
        } 
    }

    /**
     * The handleProductUsage function switches the panes on the stat page
     * between the sales report and the product usage report.
     * @see {@link pointOfSales.managerPageController#initializeProductUsage()}
     */

    @FXML 
    private void handleProductUsage(){
        salesPane.setVisible(false);
        productUsagePane.setVisible(true);
        initializeProductUsage();   
    }

    /**
     * The handleSalesButton function switches the panes on the stat page
     * between the product usage report and the sales report
     */

    @FXML
    private void handleSalesButton(){
        salesPane.setVisible(true);
        productUsagePane.setVisible(false);
    }

    /**
     * The initializeProductUsage function initializes the product usage table and 
     * columns.
     */

    private void initializeProductUsage(){
        //initializes the product name column

        usageInventoryID.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 0) {
                return new SimpleStringProperty(cellData.getValue()[0].toString());
            } else {
                return new SimpleStringProperty("Inventory ID");
            }
        });

        //initializes the quantity column

        usageItemName.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 1) { // Use index 1 for quantity
                return new SimpleStringProperty(cellData.getValue()[1].toString());
            } else {
                return new SimpleStringProperty("Item Name");
            }
        });

        //initializes the price column

        usageStockUsed.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().length > 2) { // Useindex 2 for price
                return new SimpleStringProperty(cellData.getValue()[2].toString());
            } else {
                return new SimpleStringProperty("Stock Used");
            }
        });
    }

    /**
     * The handleUsage function adds values to the Usage table.
     * The table returns values of the change in the inventory over a time window
     * @see {@link pointOfSales.services.SystemFunctions#getInventoryUsage()}
     */

    @FXML 
    private void handleUsage(){
        String startDate = productUsageStartDate.getValue().toString();
        String endDate = productUsageEndDate.getValue().toString();
        ArrayList <ArrayList<String>> values = new ArrayList<>();
        values = SystemFunctions.getInventoryUsage(startDate, endDate);
        usageData.clear();
        for (int i = 0; i < values.get(0).size(); i++) {
            usageData.add(new Object[] { values.get(0).get(i), values.get(1).get(i), values.get(2).get(i)});
        }
        productUsageTable.setItems(usageData);
    }
}
