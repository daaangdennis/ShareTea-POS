package pointOfSales;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
import javafx.scene.input.MouseEvent;
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
import javafx.scene.control.TextField;
import pointOfSales.entities.orderProduct;

/**
 * The orderPageController class is the logic class for the checkoutPage.
 * This class handles all the ordering in the checkoutPage.
 * 
 * @author Sam Trythall
 * @version v0.0.3
 * @since v0.0.2
 */

public class orderPageController implements Initializable {
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
    private TableView<Object[]> orderHistoryTable;
    @FXML 
    private TableColumn<Object[], String> historyOrderID;
    @FXML 
    private TableColumn<Object[], String> historyCustomerName;
    @FXML 
    private TableColumn<Object[], String> historyOrderDate;

    //Table for hodling all product items on the menu

    @FXML
    private TableView<Object[]> orderHistoryInfoTable;
    @FXML 
    private TableColumn<Object[], String> productTableColumn1;
    @FXML 
    private TableColumn<Object[], String> quantityTableColumn1;
    @FXML 
    private TableColumn<Object[], String> priceTableColumn1;

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
    private TextArea additionalNotes;
    @FXML
    private Label foodItemLabel;
    @FXML
    private AnchorPane orderHistoryInfo;
    @FXML
    private AnchorPane orderHistoryPage;

    //Calendars used to select dates for reports

    @FXML
    private DatePicker orderHistoryStartDate;
    @FXML
    private DatePicker orderHistoryEndDate;
    
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
    private ObservableList<Object[]> historyData = FXCollections.observableArrayList();
    private ObservableList<Object[]> selectedHistoryData = FXCollections.observableArrayList();

    private Double foodLabelCost = 0.0;
    public ArrayList<orderedProduct> items = new ArrayList<>();
    public ArrayList<String> categories = new ArrayList<>();

    //Maps for keeping references to buttons created dynamically

    public Map<Button, Label> buttonLabelMap = new HashMap<>();
    public Map<Button, Label> buttonCostMap = new HashMap<>();
    public Map<Button, String> buttonIdMap = new HashMap<>();

    public Double orderTotal = 0.0;
    public String employPosition = "";
    private int historyCounter = 0;
    private ArrayList<ArrayList<String>> historyList = new ArrayList<>();

    /**
     * The initialize function initializes tables and and variables necessary when the page is loaded
     * 
     * @see {@link pointOfSales.services.SystemFunctions#nextOrderID()}
     * @see {@link pointOfSales.loginPageController#getPosition()}
     * @see {@link pointOfSales.orderPageController#setUpTeaPane()}
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
     * The getTable function returns a reference to the checkoutTable private member of the orderPageController class.
     * @return an object of type TableView<Object[]> to grab items from the table or modify values in the table
     */

    public TableView<Object[]> getTable() {
        return this.checkoutTable;
    }

    /**
     * The getMainMenu function returns a reference to the menuItems private member of the orderPageController class.
     * @return an object of type AnchorPane to grab elements defined in the anchorpane.
     */

    public AnchorPane getMainMenu() {
        return this.menuItems;
    }

    /**
     * The getSubMenu function returns a reference to the orderCustomizationMenu private member of the orderPageController class.
     * @return an object of type AnchorPane to grab elements defined in the anchorpane.
     */

    public AnchorPane getSubMenu() {
        return this.orderCustomizationMenu;
    }

    /**
     * The getFoodLabel function returns a reference to the foodItemLabel private member of the orderPageController class.
     * @return an object of type Label to modify in other classes
     */

    public Label getFoodLabel() {
        return this.foodItemLabel;
    }

     /**
     * The getFoodCost function returns a reference to the foodLabelCost private member of the orderPageController class.
     * @return an object of type Double used to get the cost of the current food product being processed.
     */

    public Double getFoodCost() {
        return this.foodLabelCost;
    }

    /**
     * The setFoodCost function sets the private member foodLabelCost of the orderPageController class.
     * @param cost The value that the member foodLabelCost is being set to.
     */

    public void setFoodCost(Double cost) {
        this.foodLabelCost = cost;
    }

    /**
     * The setController function sets the private member sceneCtrl of the orderPageController class.
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
        // else if(pressedButton.getId().equals("logoutButton"))
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
                buttonController.setOrderControl(this);
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
    private void handleCloseButton(ActionEvent event) {
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
     * @see {@link pointOfSales.orderPageController#checkToggledButton(ToggleGroup)}
     * @see {@link pointOfSales.orderedProduct#setIce(String)}
     * @see {@link pointOfSales.orderedProduct#setSugar(Double)}
     * @see {@link pointOfSales.orderedProduct#setNote(String)}
     * @see {@link pointOfSales.orderedProduct#addToList(String)}
     */

    @FXML
    private void addItem(ActionEvent event) {

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
    private void handleCancelButton(ActionEvent event) {
        data.clear();
        Label checkoutSubTotal = (Label) orderInfoPane.lookup("#checkoutSubTotal");
        checkoutSubTotal.setText("$0.00");
        Label checkoutTax = (Label) orderInfoPane.lookup("#checkoutTax");
        checkoutTax.setText("$0.00");
        Label checkoutTrueTotal = (Label) orderInfoPane.lookup("#checkoutTrueTotal");
        checkoutTrueTotal.setText("$0.00");
        orderTotal = 0.0;
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
    private void handleProceedButton(ActionEvent event) {
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
     * The checkoutButton function is the handler for the checkout button on the sidebar.
     * This function turns the visibilty of all other UI elements off and all of the UI for the checkout menu on.
     * The function also clears any left over values in the table that may be there before navigating off the checkout menu.
     * 
     * @see {@link pointOfSales.orderPageController#setUpTeaPane()}
     */

    @FXML
    private void checkoutButton(ActionEvent event) {

        //Toggle visibility of AnchorPanes

        if (menuItems.isVisible()) {
            return;
        }
        menuItemsGridPane.getChildren().clear();
        foodCategoryGridPane.getChildren().clear();
        setUpTeaPane();
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
     * @see {@link pointOfSales.orderPageController#initializeOrderHistoryTable()}
     * @see {@link pointOfSales.orderPageController#initializeHistoryInfoTable()}
     */

    @FXML 
    private void handleOrdersButton(){
        if (orderCustomizationMenu.isVisible()) {
            return;
        } else{
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
        // System.out.println("Buffer is at: " + bufferArray);
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




}
