package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.DbConnect;
import Model.resolution;
import javafx.beans.property.SimpleStringProperty;
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

public class D_Parcel {

    @FXML
    private TableView<resolution> parcelDepartmentTable;

    @FXML
    private TableColumn<resolution, Integer> resolutionIDColumn;

    @FXML
    private TableColumn<resolution, Integer> complaintIDColumn;

    @FXML
    private TableColumn<resolution, Integer> deptIDColumn;

    @FXML
    private TableColumn<resolution, String> complainantNameColumn;

    @FXML
    private TableColumn<resolution, String> descriptionColumn;

    @FXML
    private TableColumn<resolution, String> statusColumn;

    @FXML
    private TableColumn<resolution, Date> dateColumn;

    private ObservableList<resolution> resolutionList;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @FXML
    public void initialize() {
        resolutionIDColumn.setCellValueFactory(new PropertyValueFactory<>("resolution_ID"));
        complaintIDColumn.setCellValueFactory(new PropertyValueFactory<>("compt_ID"));
        deptIDColumn.setCellValueFactory(new PropertyValueFactory<>("dept_ID"));
        complainantNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getComplainant_Name()));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getResolution_Details()));
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getResolution_Status()));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("resolution_Date"));

        resolutionList = FXCollections.observableArrayList();
        parcelDepartmentTable.setItems(resolutionList);

        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
        connection = DbConnect.getConnect();
        String query = "SELECT r.*, c.complainant_FName, c.complainant_LName " +
                       "FROM resolution r " +
                       "JOIN complainant c ON r.complainant_ID = c.complainant_ID " +
                       "WHERE r.dept_ID = 2";

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int resolutionID = resultSet.getInt("resolution_ID");
                int complaintID = resultSet.getInt("compt_ID");
                int deptID = resultSet.getInt("dept_ID");
                String complainantFName = resultSet.getString("complainant_FName");
                String complainantLName = resultSet.getString("complainant_LName");
                String complainantName = complainantFName + " " + complainantLName;
                String description = resultSet.getString("resolution_Details");
                String status = resultSet.getString("resolution_Status");
                Date date = resultSet.getDate("resolution_Date");

                resolution res = new resolution(resolutionID, complaintID, deptID, null, description, status, date);
                res.setComplainant_Name(complainantName);
                resolutionList.add(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
