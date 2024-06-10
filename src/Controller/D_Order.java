package Controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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

    @FXML
    public void gotoLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
