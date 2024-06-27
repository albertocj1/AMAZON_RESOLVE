package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import Model.DbConnect;
import Model.complaint_history;

public class A_History {

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    complaint_history complaint_history = null;

    ObservableList<complaint_history> complaint_historyList = FXCollections.observableArrayList();

    @FXML
    private TableView<complaint_history> historyTable;

    @FXML
    private TableColumn<complaint_history, Integer> historyIDColumn;

    @FXML
    private TableColumn<complaint_history, Integer> resolutionIDColumn;

    @FXML
    private TableColumn<complaint_history, LocalDate> historyDateColumn;

    @FXML
    private TableColumn<complaint_history, String> historyStatusColumn;



    public void initialize(){
    
        loadHData();
        refreshHistoryTable();
    }

    @FXML
    public void loadHData() {
        connection = DbConnect.getConnect();

        historyIDColumn.setCellValueFactory(new PropertyValueFactory<>("history_ID"));
        resolutionIDColumn.setCellValueFactory(new PropertyValueFactory<>("resolution_ID"));
        historyDateColumn.setCellValueFactory(new PropertyValueFactory<>("history_Date"));
        historyStatusColumn.setCellValueFactory(new PropertyValueFactory<>("history_Status"));

        refreshHistoryTable();
    }

    public void refreshHistoryTable() {
        connection = DbConnect.getConnect();
        ObservableList<complaint_history> historyTicketList = FXCollections.observableArrayList();

        try {
            String query = "SELECT history_ID, resolution_ID, history_Date, history_Status FROM complaint_history";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                historyTicketList.add(new complaint_history(
                    resultSet.getInt("history_ID"),
                    resultSet.getInt("resolution_ID"),
                    resultSet.getDate("history_Date").toLocalDate(),
                    resultSet.getString("history_Status")
                ));
            }

            // Set items to the historyTable
            historyTable.setItems(historyTicketList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void gotoTicket(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Admin-Pending-Approved-Unresolved.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
}
