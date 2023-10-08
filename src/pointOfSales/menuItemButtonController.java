package pointOfSales;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class menuItemButtonController {
    
    orderPageController orderPage;

    public void setOrderControl(orderPageController order){
        this.orderPage = order;
    }

    @FXML
    private void handleSubButton(ActionEvent event){
        ObservableList<Object[]> data = FXCollections.observableArrayList(
            new Object[]{"MilkTea", "1", "$5.00"}, new Object[]{"BrewedTea", "1", "$4.00"}, new Object[]{"FruitTea", "1", "$3.00"}
        );



        

        orderPage.getTable().setItems(data);
    }
}
