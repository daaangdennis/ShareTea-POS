package pointOfSales;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import pointOfSales.services.SystemFunctions;
import javafx.event.ActionEvent;

/**
 * The foodItemButtonController class controls the logic for the food categories.
 * Loads the products that belong to the specific category when a category button is clicked.
 * 
 * @author Sam Trythall
 * @version v0.0.3
 * @since v0.0.3
 */

public class foodItemButtonController {
    //controllers to grab info from 
    orderPageController orderPage;
    managerPageController managerPage;

    /**
     * The setOrderControl function sets the orderPage private member for the foodItemButtonController class.
     * @param order value of type orderPageController to set private member's value to.
     */

    public void setOrderControl(orderPageController order) {
        this.orderPage = order;
    }

    /**
     * The setManagerControl function sets the managerPage private member for the foodItemButtonController class.
     * @param manage value of type managerPageController to set private member's value to.
     */

    public void setManagerControl(managerPageController manage) {
        this.managerPage = manage;
    }

    /**
     * The handleProductButton function is the handler for when the category buttons are clicked.
     * This function checks the page the button was pressed on, and calls the database to create buttons for each product in the category.
     * 
     * @param event This parameter is used to figure out which category button was pressed
     * @see pointOfSales.services.SystemFunctions#productsAndPriceByCategory(String)
     * 
     */

    @FXML
    public void handleProductButton(ActionEvent event) {
        int num_buttons = 1;
        int indexCount = 0;
        ToggleButton sourceButton = (ToggleButton) event.getSource();

        //Makes a database call for a list of all products and their respective prices
        ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
        results = SystemFunctions.productsAndPriceByCategory(sourceButton.getText());
        num_buttons = results.get(0).size();

        //orderPage and managerPage are mutually exclusive pages
        if (orderPage != null) {
            orderPage.menuItemsGridPane.getChildren().clear();
        } else if (managerPage != null) {
            managerPage.menuItemsGridPane.getChildren().clear();
        }

        int val1 = 1;
        int val2 = 1;
        for (int i = 0; i < num_buttons; i++) {
            try {
                //Load the fxml for the buttons, change labels and sets gridpane bounds where the max columns is 3.
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("designFiles/menuItemButton.fxml"));
                Node buttonNode = loader2.load();
                Button button = (Button) buttonNode.lookup("#subButton");
                Label label = (Label) loader2.getNamespace().get("foodItemLabel");
                label.setText(results.get(0).get(indexCount));
                Label priceLabel = (Label) loader2.getNamespace().get("priceLabel");
                priceLabel.setText("Price: $" + results.get(1).get(indexCount));
                GridPane.setRowIndex(buttonNode, val1);
                GridPane.setColumnIndex(buttonNode, val2);

                //Makes sure there is only 3 columns
                val2++;
                if (val2 == 4) {
                    val1++;
                    val2 = 1;
                }

                //Adds the buttons to 3 hashmaps for later access of button elements and adds the buttons to the grid pane.
                String pid = results.get(2).get(indexCount);
                if (orderPage != null) {
                    orderPage.buttonLabelMap.put(button, label);
                    orderPage.buttonCostMap.put(button, priceLabel);
                    orderPage.buttonIdMap.put(button, pid);
                    orderPage.menuItemsGridPane.getChildren().add(buttonNode);
                    menuItemButtonController buttonController = loader2.getController();
                    buttonController.setOrderControl(orderPage);
                } else if (managerPage != null) {
                    managerPage.buttonLabelMap.put(button, label);
                    managerPage.buttonCostMap.put(button, priceLabel);
                    managerPage.buttonIdMap.put(button, pid);
                    managerPage.menuItemsGridPane.getChildren().add(buttonNode);
                    menuItemButtonController buttonController = loader2.getController();
                    buttonController.setManagerControl(managerPage);
                }

                indexCount = indexCount + 1;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
