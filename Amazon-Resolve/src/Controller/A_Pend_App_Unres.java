package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class A_Pend_App_Unres {

    @FXML
    Pane pendingpane, approvedpane, unresolvedpane, pending_preview, unresolved_preview, confirmation_approval, confirmation_delete, newTicket1, newTicket2;

    @FXML
    public void gotoApproved(ActionEvent event) throws IOException {
        pendingpane.setVisible(false);
        approvedpane.setVisible(true);
        unresolvedpane.setVisible(false);
    }

    @FXML
    public void gotoUnresolved(ActionEvent event) throws IOException {
        pendingpane.setVisible(false);
        unresolvedpane.setVisible(true);
        approvedpane.setVisible(false);
        confirmation_delete.setVisible(false);
        unresolved_preview.setVisible(false);
    }

    @FXML
    public void gotoPending(ActionEvent event) throws IOException {
        pendingpane.setVisible(true);
        unresolvedpane.setVisible(false);
        approvedpane.setVisible(false);
        pending_preview.setVisible(false);
        confirmation_approval.setVisible(false);
        newTicket1.setVisible(false);
        newTicket2.setVisible(false);
    }

    @FXML
    public void gotoPendingPrev(ActionEvent event) throws IOException {
        pendingpane.setVisible(false);
        pending_preview.setVisible(true);
    }

    @FXML
    public void gotoApprovedConfirmation(ActionEvent event) throws IOException {
        pendingpane.setVisible(false);
        pending_preview.setVisible(false);
        unresolvedpane.setVisible(false);
        unresolved_preview.setVisible(false);
        confirmation_approval.setVisible(true);
    }

    @FXML
    public void gotoDeleteConfirmation(ActionEvent event) throws IOException {
        pendingpane.setVisible(false);
        pending_preview.setVisible(false);
        unresolvedpane.setVisible(false);
        confirmation_delete.setVisible(true);
    }
    
    @FXML
    public void gotoUnresolvedPrev(ActionEvent event) throws IOException {
        unresolvedpane.setVisible(false);
        unresolved_preview.setVisible(true);
    }

    @FXML
    public void gotoTicket1(ActionEvent event) throws IOException {
        pendingpane.setVisible(false);
        newTicket1.setVisible(true);
    }

    @FXML
    public void gotoTicket2(ActionEvent event) throws IOException {
        newTicket1.setVisible(false);
        newTicket2.setVisible(true);
    }

    @FXML
    public void gotoOverview(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Admin-Dashboard.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void gotoHistory(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Admin-History.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}