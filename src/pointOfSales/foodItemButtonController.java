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

public class foodItemButtonController {
    orderPageController orderPage;
    managerPageController managerPage;

    public void setOrderControl(orderPageController order) {
        this.orderPage = order;
    }

    public void setManagerControl(managerPageController manage) {
        this.managerPage = manage;
    }

    @FXML
    private void handleProductButton(ActionEvent event) {
        int num_buttons = 1;
        int indexCount = 0;
        // ToggleButton sourceButton = (ToggleButton) event.getSource();
        // if (!sourceButton.isSelected()) {
        // menuItemsGridPane.getChildren().clear();
        // return;
        // }
        Button sourceButton = (Button) event.getSource();

        ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
        results = SystemFunctions.productsAndPriceByCategory(sourceButton.getText());
        num_buttons = results.get(0).size();
        if (orderPage != null) {
            orderPage.menuItemsGridPane.getChildren().clear();
        } else if (managerPage != null) {
            managerPage.menuItemsGridPane.getChildren().clear();
        }

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
