package Controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class D_Order {
    
    @FXML
    Pane resolution_approval, resolution_update, resolution_details, orderfulfillment;

   @FXML
    public void gotoResolutionDetails() {
        resolution_details.setVisible(true);
        resolution_approval.setVisible(false);
        resolution_update.setVisible(false);
        orderfulfillment.setVisible(false);
    }

    @FXML
    public void gotoOrderFulfillment() {
        orderfulfillment.setVisible(true);
        resolution_approval.setVisible(false);
        resolution_update.setVisible(false);
        resolution_details.setVisible(false);
    }

    @FXML
    public void gotoResolutionUpdate() {
        resolution_update.setVisible(true);
        resolution_approval.setVisible(false);
        resolution_details.setVisible(true);
        orderfulfillment.setVisible(false);
    }

    @FXML
    public void gotoResolutionApproval() {
        resolution_approval.setVisible(true);
        resolution_update.setVisible(false);
        resolution_details.setVisible(true);
        orderfulfillment.setVisible(false);
    }

}
