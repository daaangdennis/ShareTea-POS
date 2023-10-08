package pointOfSales;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;


public class menuItemButtonController {
    
    orderPageController orderPage;

    public void setOrderControl(orderPageController order){
        this.orderPage = order;
    }

    @FXML
    private void handleSubButton(ActionEvent event){
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
