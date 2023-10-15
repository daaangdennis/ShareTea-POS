package pointOfSales;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
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

public class orderPageController implements Initializable {
    private sceneController sceneCtrl;

    @FXML
    public GridPane menuItemsGridPane;
    @FXML
    private GridPane foodCategoryGridPane;
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
    private AnchorPane orderInfoPane;
    @FXML
    private AnchorPane notesPane;
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
    private Double foodLabelCost = 0.0;
    public ArrayList<orderedProduct> items = new ArrayList<>();
    public ArrayList<String> categories = new ArrayList<>();
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

    public TableView<Object[]> getTable() {
        return this.checkoutTable;
    }

    public AnchorPane getMainMenu() {
        return this.menuItems;
    }

    public AnchorPane getSubMenu() {
        return this.orderCustomizationMenu;
    }

    public Label getFoodLabel() {
        return this.foodItemLabel;
    }

    public Double getFoodCost() {
        return this.foodLabelCost;
    }

    public void setFoodCost(Double cost) {
        this.foodLabelCost = cost;
    }

    public void setController(sceneController controller) {
        this.sceneCtrl = controller;
    }

    @FXML
    private void handleChangeScene(ActionEvent event) {
        Button pressedButton = (Button) event.getSource();
        if (pressedButton.getId().equals("logoutButton")) {
            employPosition = "";
            sceneCtrl.changeScene(loginPage.getScene());
        }
        // else if(pressedButton.getId().equals("logoutButton"))
    }

    /* #################UNDERCONSTRUCTION#################### */
    // This function needs to grab the gridpane @foodCategoryGridPane
    // to add categories gained from SystemFunctions.getCategories()
    // There should only be 2 rows but infinite columns
    // Possible implementation: add from columns until num of categories.
    // Do not forget about the sidebar button
    private void setUpTeaPane() {
        int numButtons = 1;
        categories.clear();
        categories = SystemFunctions.getCategories();
        numButtons = categories.size();
        foodCategoryGridPane.getChildren().clear();

        int index = 0;
        int swapper = 0;
        for (int i = 0; i < numButtons; i++) {
            try {
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("designFiles/foodCategoryButton.fxml"));
                Node buttonNode = loader2.load();
                ToggleButton button = (ToggleButton) buttonNode.lookup("#categoryButton");
                button.setText(categories.get(i));

                GridPane.setRowIndex(buttonNode, swapper);
                GridPane.setColumnIndex(buttonNode, index);

                // foodCategoryGridPane.add(buttonNode, index, swapper);
                foodCategoryGridPane.getChildren().add(buttonNode);
                GridPane.setHalignment(buttonNode, HPos.CENTER);
                // Set Toggle Group to Tea Group When they become togglebuttons
                button.setToggleGroup(teaGroup);
                if (swapper == 1) {
                    swapper = 0;
                    index++;
                } else if (swapper == 0) {
                    swapper = 1;
                }

                foodItemButtonController buttonController = loader2.getController();
                buttonController.setOrderControl(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    /* #################UNDERCONSTRUCTION#################### */

    @FXML
    private void addButtons(ActionEvent event) {
        int num_buttons = 1;
        int indexCount = 0;
        ToggleButton sourceButton = (ToggleButton) event.getSource();
        if (!sourceButton.isSelected()) {
            menuItemsGridPane.getChildren().clear();
            return;
        }
        // if milktea
        ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
        if (sourceButton.getText().equals("Milk Tea")) {
            results = SystemFunctions.productsAndPriceByCategory("Milk Tea");
            num_buttons = results.get(0).size();
        }
        // if brewedtea
        if (sourceButton.getText().equals("Brewed Tea")) {
            results = SystemFunctions.productsAndPriceByCategory("Brewed Tea");
            num_buttons = results.size();
        }
        // if fruittea
        if (sourceButton.getText().equals("Fruit Tea")) {
            results = SystemFunctions.productsAndPriceByCategory("Fruit Tea");
            num_buttons = results.size();
        }
        // if iceblended
        if (sourceButton.getText().equals("Ice Blended")) {
            results = SystemFunctions.productsAndPriceByCategory("Ice Blended");
            num_buttons = results.size();
        }
        // if tea mojito
        if (sourceButton.getText().equals("Tea Mojito")) {
            results = SystemFunctions.productsAndPriceByCategory("Tea Mojito");
            num_buttons = results.size();
        }
        // if creama
        if (sourceButton.getText().equals("Creama")) {
            results = SystemFunctions.productsAndPriceByCategory("Creama");
            num_buttons = results.size();
        }
        menuItemsGridPane.getChildren().clear();

        int val1 = 1;
        int val2 = 1;
        for (int i = 0; i < num_buttons; i++) {
            try {
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("designFiles/menuItemButton.fxml"));
                Node buttonNode = loader2.load();
                Button button = (Button) buttonNode.lookup("#subButton");
                Label label = (Label) loader2.getNamespace().get("foodItemLabel");
                label.setText(results.get(0).get(indexCount));
                Label priceLabel = (Label) loader2.getNamespace().get("priceLabel");
                priceLabel.setText("Price: $" + results.get(1).get(indexCount));

                GridPane.setRowIndex(buttonNode, val1);
                GridPane.setColumnIndex(buttonNode, val2);

                val2++;
                if (val2 == 4) {
                    val1++;
                    val2 = 1;
                }
                String pid = results.get(2).get(indexCount);
                buttonLabelMap.put(button, label);
                buttonCostMap.put(button, priceLabel);
                buttonIdMap.put(button, pid);
                menuItemsGridPane.getChildren().add(buttonNode);
                menuItemButtonController buttonController = loader2.getController();
                buttonController.setOrderControl(this);
                indexCount = indexCount + 1;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void handleCloseButton(ActionEvent event) {
        menuItems.setVisible(!menuItems.isVisible());
        orderCustomizationMenu.setVisible(!orderCustomizationMenu.isVisible());
        if (items.size() > 0) {
            items.remove(items.size() - 1);
        }
        Toggle iceToggle = iceGroup.getSelectedToggle();
        Toggle sugarToggle = sugarGroup.getSelectedToggle();
        iceToggle.setSelected(false);
        sugarToggle.setSelected(false);

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
    private void addItem(ActionEvent event) {
        sugarSelection = checkToggledButton(sugarGroup);
        iceSelection = checkToggledButton(iceGroup);
        if (sugarSelection == "" | iceSelection == "") {
            return;
        }

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

        double calculated_cost = Double.parseDouble(items.get(items.size() - 1).getPrice().substring(8));
        calculated_cost += toppingCost;

        data.add(new Object[] { items.get(items.size() - 1).getTeaType(), items.get(items.size() - 1).getQuantity(),
                String.format("%.2f", calculated_cost) });
        orderTotal += calculated_cost;
        checkoutTable.setItems(data);
        menuItems.setVisible(!menuItems.isVisible());
        orderCustomizationMenu.setVisible(!orderCustomizationMenu.isVisible());
        // String formattedNumber = String.format("%.2f", number);
        Label checkoutSubTotal = (Label) orderInfoPane.lookup("#checkoutSubTotal");
        checkoutSubTotal.setText("$" + String.format("%.2f", orderTotal));
        Label checkoutTax = (Label) orderInfoPane.lookup("#checkoutTax");
        checkoutTax.setText("$" + String.format("%.2f", (orderTotal * 0.0825)));
        Label checkoutTrueTotal = (Label) orderInfoPane.lookup("#checkoutTrueTotal");
        checkoutTrueTotal.setText("$" + String.format("%.2f", (orderTotal + (orderTotal * 0.0825))));

    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        data.clear();
        Label checkoutSubTotal = (Label) orderInfoPane.lookup("#checkoutSubTotal");
        checkoutSubTotal.setText("$0.00");
        Label checkoutTax = (Label) orderInfoPane.lookup("#checkoutTax");
        checkoutTax.setText("$0.00");
        Label checkoutTrueTotal = (Label) orderInfoPane.lookup("#checkoutTrueTotal");
        checkoutTrueTotal.setText("$0.00");
    }

    @FXML
    private void handleToppingButtons(ActionEvent event) {
        ToggleButton sourceButton = (ToggleButton) event.getSource();
        if (!sourceButton.isSelected()) {
            foodLabelCost -= 0.75;
        } else {
            foodLabelCost += 0.75;
        }
        DecimalFormat df = new DecimalFormat("Food Item $0.00 ");
        String formattedCost = df.format(foodLabelCost);
        foodItemLabel.setText(formattedCost);
    }

    @FXML
    private void handleProceedButton(ActionEvent event) {
        // Customer First and Last Name:
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

        // System.out.println("Customer Name: " + customerFirstName + " " +
        // customerLastName);
        // System.out.println("Employee Name: " + employeeFirstName + " " +
        // employeeLastName);
        // for(int i = 0; i < listOfItems.size(); i++){
        // System.out.println("Product: " + listOfItems.get(i).ProductID);
        // System.out.println("Quantity: " + listOfItems.get(i).Quantity);
        // System.out.println("Sugar: " + listOfItems.get(i).SugarLevel);
        // System.out.println("Note: " + listOfItems.get(i).Note);
        // for(int j = 0; j < (items.get(i).getToppings().size()); j++){
        // System.out.println("Topping: " + listOfItems.get(i).Toppings.get(j));
        // }

        // }
        // System.out.println("OrderCost: " + orderTotal);
        orderTotal += orderTotal * 0.0825;
        // Customer First Name, Customer Last Name, Employee First Name, Employee Last
        // Name, ArrayList of OrderProduct
        SystemFunctions.addOrder(customerFirstName, customerLastName, employeeFirstName, employeeLastName, listOfItems,
                orderTotal);
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

}
