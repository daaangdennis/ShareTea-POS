package pointOfSales;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Node;

public class menuItemButtonController {
    
    orderPageController orderPage;
    managerPageController managerPage;

    public void setOrderControl(orderPageController order){
        this.orderPage = order;
    }

    public void setManagerControl(managerPageController manage){
        this.managerPage = manage;
    }

    @FXML
    private void handleSubButton(ActionEvent event){
        Button sourceButton = (Button) event.getSource();
        Label foodType = null;
        Label cost = null;
        String id = null;
        if(orderPage != null && orderPage.employPosition.equals("CASHIER")){
            foodType = orderPage.buttonLabelMap.get(sourceButton);
            cost = orderPage.buttonCostMap.get(sourceButton);
            id = orderPage.buttonIdMap.get(sourceButton);
        }
        else if(managerPage != null && managerPage.employPosition.equals("MANAGER")){
            foodType = managerPage.buttonLabelMap.get(sourceButton);
            cost = managerPage.buttonCostMap.get(sourceButton);
            id = managerPage.buttonIdMap.get(sourceButton);
        }
        
        orderedProduct newItem = new orderedProduct();
        newItem.setId(id);
        newItem.setTeaType(foodType.getText());
        newItem.setQuantity(1);
        newItem.setPrice(cost.getText());
        if(orderPage != null){
            orderPage.items.add(newItem);
            AnchorPane subPane = orderPage.getSubMenu();
            subPane.setVisible(!subPane.isVisible());
            AnchorPane mainPane = orderPage.getMainMenu();
            mainPane.setVisible(!mainPane.isVisible());
            orderPage.setFoodCost(5.00);
            orderPage.getFoodLabel().setText("Food Item $5.00 ");
        }
        else if(managerPage != null){
            managerPage.items.add(newItem);
            AnchorPane subPane = managerPage.getSubMenu();
            subPane.setVisible(!subPane.isVisible());
            AnchorPane mainPane = managerPage.getMainMenu();
            mainPane.setVisible(!mainPane.isVisible());
            managerPage.setFoodCost(5.00);
            managerPage.getFoodLabel().setText("Food Item $5.00 ");
        }
        

        
    }

    
}
