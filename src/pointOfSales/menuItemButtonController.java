package pointOfSales;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


/**
 * The menuItemButtonController class controls the logic for the product buttons
 * 
 * @author Sam Trythall
 * @version v0.0.3
 * @since v0.0.2
 */

public class menuItemButtonController {

    orderPageController orderPage;
    managerPageController managerPage;

    /**
     * The setOrderControl function is a setter for the orderPageController
     * private member of the menuItemButtonController.
     * @param order this is the value the orderPageController will be set to.
     */

    public void setOrderControl(orderPageController order) {
        this.orderPage = order;
    }

    /**
     * The setManagerControl function is a setter for the managerPageController
     * private member of the menuItemButtonController
     * @param manage this is the value the managerPageController will be set to
     */

    public void setManagerControl(managerPageController manage) {
        this.managerPage = manage;
    }

    /**
     * The handleSubButton function creates an orderedProduct object that 
     * holds all the information about the order that the customer made.
     * @param event this uses the source button to search the maps to get the values of the labels on the button
     * 
     * @see setId()
     * @see setTeaType
     * @see setQuantity
     * @see setPrice
     * @see setFoodCost
     * @see getFoodLabel
     * @see setFoodCost
     * @see setFoodLabel
     */

    @FXML
    private void handleSubButton(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        Label foodType = null;
        Label cost = null;
        String id = null;

        //Grabs the foodtype, cost, and the id from the maps using the source button

        if (orderPage != null && orderPage.employPosition.equals("CASHIER")) {
            foodType = orderPage.buttonLabelMap.get(sourceButton);
            cost = orderPage.buttonCostMap.get(sourceButton);
            id = orderPage.buttonIdMap.get(sourceButton);
        } else if (managerPage != null && managerPage.employPosition.equals("MANAGER")) {
            foodType = managerPage.buttonLabelMap.get(sourceButton);
            cost = managerPage.buttonCostMap.get(sourceButton);
            id = managerPage.buttonIdMap.get(sourceButton);
        }

        //Creates a new orderedProduct and adds it to an array of products that the customer ordered

        orderedProduct newItem = new orderedProduct();
        newItem.setId(id);
        newItem.setTeaType(foodType.getText());
        newItem.setQuantity(1);
        newItem.setPrice(cost.getText());
        if (orderPage != null) {
            //change displayed page and set labels to be based on the current total 
            orderPage.items.add(newItem);
            AnchorPane subPane = orderPage.getSubMenu();
            subPane.setVisible(!subPane.isVisible());
            AnchorPane mainPane = orderPage.getMainMenu();
            mainPane.setVisible(!mainPane.isVisible());
            orderPage.setFoodCost(Double.parseDouble(cost.getText().substring(8)));
            orderPage.getFoodLabel().setText("Food Item $" + cost.getText().substring(8));
        } else if (managerPage != null) {
            //change displayed page and set labels to be based on the current total 
            managerPage.items.add(newItem);
            AnchorPane subPane = managerPage.getSubMenu();
            subPane.setVisible(!subPane.isVisible());
            AnchorPane mainPane = managerPage.getMainMenu();
            mainPane.setVisible(!mainPane.isVisible());
            managerPage.setFoodCost(Double.parseDouble(cost.getText().substring(8)));
            managerPage.getFoodLabel().setText("Food Item $" + cost.getText().substring(8));
        }

    }

}
