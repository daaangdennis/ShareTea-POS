package pointOfSales;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class menuItemButtonController {
    
    orderPageController orderPage;

    public void setOrderControl(orderPageController order){
        this.orderPage = order;
    }

    @FXML
    private void handleSubButton(ActionEvent event){
        Button sourceButton = (Button) event.getSource();
        Label foodType = (Label) sourceButton.getScene().lookup("#foodItemLabel");
        Label cost = (Label) sourceButton.getScene().lookup("#priceLabel");
        orderedProduct newItem = new orderedProduct();
        newItem.setTeaType(foodType.getText());
        newItem.setQuantity(1);
        newItem.setPrice(cost.getText());
        orderPage.items.add(newItem);

        AnchorPane subPane = orderPage.getSubMenu();
        subPane.setVisible(!subPane.isVisible());
        AnchorPane mainPane = orderPage.getMainMenu();
        mainPane.setVisible(!mainPane.isVisible());
        orderPage.setFoodCost(5.00);
        orderPage.getFoodLabel().setText("Food Item $5.00 ");
    }

    @FXML
    private void addToTable(ActionEvent event){
        ObservableList<Object[]> data = FXCollections.observableArrayList(
            new Object[]{"MilkTea", "1", "$5.00"}, new Object[]{"BrewedTea", "1", "$4.00"}, new Object[]{"FruitTea", "1", "$3.00"}
        );
        orderPage.getTable().setItems(data);
    }
}
