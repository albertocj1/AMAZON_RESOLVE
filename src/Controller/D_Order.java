package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class D_Order {

    @FXML
    private Text resolutionNum, ticketNum, ticketCategory, ticketDate, ticketDateDetails;

    @FXML
    private Label ticketSubject;

    @FXML
    private TableView<resolution> orderDepartmentTable;

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

    @FXML
    private Pane resolution_details, resolution_update, resolution_approval, inProgressPane, WaitingPane, ResolvedPane, flagUnresolvable, returnTicketPane;

    @FXML
    private Button AUpdate, BUpdate, CUpdate, DUpdate, EUpdate, FUpdate, confirmUpdate, cancelUpdate, confirmUpdate1, cancelUpdate1, unresolvableButton;

    @FXML
    private Circle ACircle, BCircle, CCircle, DCircle, ECircle, FCircle;

    @FXML
    private Text ATime, BTime, CTime, DTime, ETime, FTime;

    private ObservableList<resolution> resolutionList;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private resolution selectedResolution;

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
        orderDepartmentTable.setItems(resolutionList);

        loadDataFromDatabase();
    }

    private void refreshTableView() {
        resolutionList.clear();
        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
        connection = DbConnect.getConnect();
        String query = "SELECT r.*, c.complainant_FName, c.complainant_LName, comp.compt_Subject, comp.compt_Category, comp.compt_CreatedDate, comp.compt_Desc, comp.compt_OrderID " +
                "FROM resolution r " +
                "JOIN complainant c ON r.complainant_ID = c.complainant_ID " +
                "JOIN complaint_ticket comp ON r.compt_ID = comp.compt_ID " +
                "WHERE r.dept_ID = 1";

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
                String complainantID = resultSet.getString("complainant_ID");
                String description = resultSet.getString("resolution_Details");
                String status = resultSet.getString("resolution_Status");
                Date date = resultSet.getDate("resolution_Date");
                String subject = resultSet.getString("compt_Subject");
                String category = resultSet.getString("compt_Category");
                Date createdDate = resultSet.getDate("compt_CreatedDate");
                String comptDesc = resultSet.getString("compt_Desc");
                String comptOrderID = resultSet.getString("compt_OrderID");

                resolution res = new resolution(resolutionID, complaintID, deptID, complainantID, description, status, date);
                res.setComplainant_Name(complainantName);
                res.setCompt_Subject(subject);
                res.setCompt_Category(category);
                res.setComp_CreatedDate(createdDate);
                res.setCompt_Desc(comptDesc);
                res.setCompt_OrderID(comptOrderID);

                // Add to list only if status is not "Resolved"
                if (!status.equals("Resolved")) {
                    resolutionList.add(res);
                }
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
    public void handleUpdateButton(ActionEvent event) {
        selectedResolution = orderDepartmentTable.getSelectionModel().getSelectedItem();
        if (selectedResolution != null) {
            resolutionNum.setText("Resolution #" + selectedResolution.getResolution_ID());
            ticketNum.setText("Ticket #" + selectedResolution.getCompt_ID());
            ticketSubject.setText(selectedResolution.getCompt_Subject());
            ticketCategory.setText(selectedResolution.getCompt_Category());
            ticketDate.setText(String.valueOf(selectedResolution.getComp_CreatedDate()));
            ticketDateDetails.setText(String.valueOf(selectedResolution.getComp_CreatedDate()));
    
            updateCircleVisibility(selectedResolution.getResolution_Details());
            updateButtonVisibility(selectedResolution.getResolution_Details());
    
            resolution_details.setVisible(true);
        }
    }

    private void updateCircleVisibility(String resolutionDetail) {
        // Reset all circles to invisible
        ACircle.setVisible(false);
        BCircle.setVisible(false);
        CCircle.setVisible(false);
        DCircle.setVisible(false);
        ECircle.setVisible(false);
        FCircle.setVisible(false);
        inProgressPane.setVisible(false);
        WaitingPane.setVisible(false);
        ResolvedPane.setVisible(false);
    
        // Set circle visibility based on resolution detail
        switch (resolutionDetail) {
            case "Complaint received and initial assessment started":
                inProgressPane.setVisible(true);
                break;
            case "Investigation into the complaint has begun":
                ACircle.setVisible(true);
                // BCircle.setVisible(true);
                inProgressPane.setVisible(true);
                break;
            case "Action plan to resolve the complaint has been created":
                ACircle.setVisible(true);
                BCircle.setVisible(true);
                inProgressPane.setVisible(true);
                // CCircle.setVisible(true);
                break;
            case "The resolution plan is being implemented":
                ACircle.setVisible(true);
                BCircle.setVisible(true);
                CCircle.setVisible(true);
                inProgressPane.setVisible(true);
                // DCircle.setVisible(true);
                break;
            case "Customer has been informed about the resolution progress":
                ACircle.setVisible(true);
                BCircle.setVisible(true);
                CCircle.setVisible(true);
                DCircle.setVisible(true);
                inProgressPane.setVisible(true);
                // ECircle.setVisible(true);
                break;
            case "Waiting for customer confirmation on resolution":
                ACircle.setVisible(true);
                BCircle.setVisible(true);
                CCircle.setVisible(true);
                DCircle.setVisible(true);
                ECircle.setVisible(true);
                inProgressPane.setVisible(false);
                WaitingPane.setVisible(true);
                // FCircle.setVisible(true);
                break;
            case "Complaint has been successfully resolved":
                ACircle.setVisible(true);
                BCircle.setVisible(true);
                CCircle.setVisible(true);
                DCircle.setVisible(true);
                ECircle.setVisible(true);
                FCircle.setVisible(true);
                inProgressPane.setVisible(false);
                WaitingPane.setVisible(false);
                ResolvedPane.setVisible(true);
                break;
        }
    }

    private void updateButtonVisibility(String resolutionDetail) {
        // Reset all buttons to enabled
        AUpdate.setDisable(false);
        BUpdate.setDisable(false);
        CUpdate.setDisable(false);
        DUpdate.setDisable(false);
        EUpdate.setDisable(false);
        FUpdate.setDisable(false);

        // Set button visibility based on resolution detail
        switch (resolutionDetail) {
            case "Complaint received and initial assessment started":
                // No buttons to disable
                break;
            case "Investigation into the complaint has begun":
                AUpdate.setDisable(true);
                break;
            case "Action plan to resolve the complaint has been created":
                AUpdate.setDisable(true);
                BUpdate.setDisable(true);
                break;
            case "The resolution plan is being implemented":
                AUpdate.setDisable(true);
                BUpdate.setDisable(true);
                CUpdate.setDisable(true);
                break;
            case "Customer has been informed about the resolution progress":
                AUpdate.setDisable(true);
                BUpdate.setDisable(true);
                CUpdate.setDisable(true);
                DUpdate.setDisable(true);
                break;
            case "Waiting for customer confirmation on resolution":
                AUpdate.setDisable(true);
                BUpdate.setDisable(true);
                CUpdate.setDisable(true);
                DUpdate.setDisable(true);
                EUpdate.setDisable(true);
                break;
            case "Complaint has been successfully resolved":
                AUpdate.setDisable(true);
                BUpdate.setDisable(true);
                CUpdate.setDisable(true);
                DUpdate.setDisable(true);
                EUpdate.setDisable(true);
                FUpdate.setDisable(true);
                break;
        }
    }

    private void updateResolutionDetail(String detail, LocalDateTime dateTime, String status) {
        String updateQuery = "UPDATE resolution SET resolution_Details = ?, resolution_Date = ?, resolution_Status = ? WHERE resolution_ID = ?";
        String insertHistoryQuery = "INSERT INTO complaint_history (resolution_ID, history_Date, history_Status) VALUES (?, ?, ?)";
    
        try (Connection connection = DbConnect.getConnect();
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
             PreparedStatement insertStatement = connection.prepareStatement(insertHistoryQuery)) {
    
            // Update resolution details
            updateStatement.setString(1, detail);
            updateStatement.setTimestamp(2, java.sql.Timestamp.valueOf(dateTime));
            updateStatement.setString(3, status);
            updateStatement.setInt(4, selectedResolution.getResolution_ID());
    
            int affectedRows = updateStatement.executeUpdate();
    
            if (affectedRows > 0) {
                selectedResolution.setResolution_Details(detail);
                selectedResolution.setResolution_Status(status);
                updateCircleVisibility(detail);
                updateButtonVisibility(detail);
    
                // If the status is "Resolved," insert into complaint_history
                if (status.equals("Resolved")) {
                    insertStatement.setInt(1, selectedResolution.getResolution_ID());
                    insertStatement.setTimestamp(2, java.sql.Timestamp.valueOf(dateTime));
                    insertStatement.setString(3, "Resolved");
    
                    insertStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    @FXML
    public void handleAUpdate(ActionEvent event) {
        resolution_update.setVisible(true);
        confirmUpdate.setOnAction(confirmEvent -> {
            LocalDateTime now = LocalDateTime.now();
            updateResolutionDetail("Investigation into the complaint has begun", now, "In Progress");
            resolution_update.setVisible(false);
            BUpdate.setDisable(false);
        });
        cancelUpdate.setOnAction(cancelEvent -> resolution_update.setVisible(false));
    }
    
    @FXML
    public void handleBUpdate(ActionEvent event) {
        if (ACircle.isVisible() && selectedResolution.getResolution_Details().equals("Investigation into the complaint has begun")) {
            resolution_update.setVisible(true);
            confirmUpdate.setOnAction(confirmEvent -> {
                LocalDateTime now = LocalDateTime.now();
                updateResolutionDetail("Action plan to resolve the complaint has been created", now, "In Progress");
                resolution_update.setVisible(false);
                CUpdate.setDisable(false);
            });
            cancelUpdate.setOnAction(cancelEvent -> resolution_update.setVisible(false));
        }
    }
    
    @FXML
    public void handleCUpdate(ActionEvent event) {
        if (BCircle.isVisible() && selectedResolution.getResolution_Details().equals("Action plan to resolve the complaint has been created")) {
            resolution_update.setVisible(true);
            confirmUpdate.setOnAction(confirmEvent -> {
                LocalDateTime now = LocalDateTime.now();
                updateResolutionDetail("The resolution plan is being implemented", now, "In Progress");
                resolution_update.setVisible(false);
                DUpdate.setDisable(false);
            });
            cancelUpdate.setOnAction(cancelEvent -> resolution_update.setVisible(false));
        }
    }
    
    @FXML
    public void handleDUpdate(ActionEvent event) {
        if (CCircle.isVisible() && selectedResolution.getResolution_Details().equals("The resolution plan is being implemented")) {
            resolution_update.setVisible(true);
            confirmUpdate.setOnAction(confirmEvent -> {
                LocalDateTime now = LocalDateTime.now();
                updateResolutionDetail("Customer has been informed about the resolution progress", now, "In Progress");
                resolution_update.setVisible(false);
                EUpdate.setDisable(false);
            });
            cancelUpdate.setOnAction(cancelEvent -> resolution_update.setVisible(false));
        }
    }
    
    @FXML
    public void handleEUpdate(ActionEvent event) {
        if (DCircle.isVisible() && selectedResolution.getResolution_Details().equals("Customer has been informed about the resolution progress")) {
            resolution_update.setVisible(true);
            confirmUpdate.setOnAction(confirmEvent -> {
                LocalDateTime now = LocalDateTime.now();
                updateResolutionDetail("Waiting for customer confirmation on resolution", now, "Waiting");
                resolution_update.setVisible(false);
                inProgressPane.setVisible(false);
                WaitingPane.setVisible(true);
                FUpdate.setDisable(false);
            });
            cancelUpdate.setOnAction(cancelEvent -> resolution_update.setVisible(false));
        }
    }
    
    @FXML
    public void handleFUpdate(ActionEvent event) {
        if (ECircle.isVisible() && selectedResolution.getResolution_Details().equals("Waiting for customer confirmation on resolution")) {
            resolution_approval.setVisible(true);
            confirmUpdate1.setOnAction(confirmEvent -> {
                LocalDateTime now = LocalDateTime.now();
                updateResolutionDetail("Complaint has been successfully resolved", now, "Resolved");
                inProgressPane.setVisible(false);
                WaitingPane.setVisible(false);
                ResolvedPane.setVisible(true);
                resolution_approval.setVisible(false);
                resolution_details.setVisible(false);
                refreshTableView();
            });
            cancelUpdate1.setOnAction(cancelEvent -> resolution_approval.setVisible(false));
        }
    }
    
    @FXML
    public void handleCancelUpdate(ActionEvent event) {
        resolution_update.setVisible(false);
    }

    @FXML
    public void handleExitDetails(ActionEvent event) {
        refreshTableView();
        resolution_details.setVisible(false);
    }

    //UNRESOLVABLE
    
    @FXML
    public void handleUnresolvableButton(ActionEvent event) {
        selectedResolution = orderDepartmentTable.getSelectionModel().getSelectedItem();
        if (selectedResolution != null) {
            flagUnresolvable.setVisible(true);
        }
    }

    @FXML
    public void confirmUnresolvable(ActionEvent event) {
        if (selectedResolution != null) {
            int resolutionID = selectedResolution.getResolution_ID();
            int complaintID = selectedResolution.getCompt_ID();
            String complainantID = selectedResolution.getComplainant_ID();
            String subject = selectedResolution.getCompt_Subject();
            String description = selectedResolution.getCompt_Desc();
            String orderID = selectedResolution.getCompt_OrderID();
            String category = selectedResolution.getCompt_Category();
            Date createdDate = selectedResolution.getComp_CreatedDate();
            int deptID = selectedResolution.getDept_ID();
            String status = "Unresolvable";
            
            deleteResolution(resolutionID);
            
            insertUnresolvableComplaint(complaintID, complainantID, subject, description, orderID, category, createdDate, deptID, status);
            
            flagUnresolvable.setVisible(false);
            resolution_details.setVisible(false);
            refreshTableView(); 
        }
    }

    private void deleteResolution(int resolutionID) {
        String deleteQuery = "DELETE FROM resolution WHERE resolution_ID = ?";
    
        try (Connection connection = DbConnect.getConnect();
             PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
    
            deleteStatement.setInt(1, resolutionID);
            int affectedRows = deleteStatement.executeUpdate();
    
            if (affectedRows > 0) {
                System.out.println("Resolution deleted successfully.");
            } else {
                System.out.println("Failed to delete resolution.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    @FXML
    public void cancelUnresolvable(ActionEvent event) {
        flagUnresolvable.setVisible(false);
    }

    private void insertUnresolvableComplaint(int complaintID, String complainantID, String subject, String description, String orderID, String category, Date createdDate, int deptID, String status) {
        String checkQuery = "SELECT compt_ID, compt_Status FROM complaint_ticket WHERE compt_ID = ?";
        String deleteQuery = "DELETE FROM complaint_ticket WHERE compt_ID = ? AND compt_Status = 'Approved'";
        String insertQuery = "INSERT INTO complaint_ticket (compt_ID, complainant_ID, admin_ID, compt_Subject, compt_Desc, compt_OrderID, compt_Category, compt_ProdInfo, compt_CustServRate, compt_Status, compt_CreatedDate, compt_Dept) " +
                     "VALUES (?, ?, 1, ?, ?, ?, ?, 'Product Info', 0, ?, ?, ?)";

    
        try (Connection connection = DbConnect.getConnect();
             PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
             PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
             PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
    
            // Check if an entry with the same primary key exists
            checkStatement.setInt(1, complaintID);
            ResultSet resultSet = checkStatement.executeQuery();
    
            if (resultSet.next()) {
                String currentStatus = resultSet.getString("compt_Status");
    
                // If the entry exists and has an "Approved" status, delete it
                if ("Approved".equals(currentStatus)) {
                    deleteStatement.setInt(1, complaintID);
                    deleteStatement.executeUpdate();
                }
            }
    
            // Insert the new entry with the "Unresolvable" status
            insertStatement.setInt(1, complaintID);
            insertStatement.setString(2, complainantID);
            insertStatement.setString(3, subject);
            insertStatement.setString(4, description);
            insertStatement.setString(5, orderID);
            insertStatement.setString(6, category);
            insertStatement.setString(7, status);
            insertStatement.setDate(8, createdDate);
            insertStatement.setInt(9, deptID);
    
            int affectedRows = insertStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Complaint marked as Unresolvable successfully.");
            } else {
                System.out.println("Failed to mark complaint as Unresolvable.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    //RETURN TICKET
    @FXML
    public void handleReturnTicketButton(ActionEvent event) {
        selectedResolution = orderDepartmentTable.getSelectionModel().getSelectedItem();
        if (selectedResolution != null) {
            String resolutionDetail = selectedResolution.getResolution_Details();

            if (isResolutionDetailRestricted(resolutionDetail)) {
                showAlert("Cannot Return Ticket", "The ticket cannot be returned because it is in the following state: " + resolutionDetail);
            } else {
                returnTicketPane.setVisible(true);
            }
        }
    }

    private boolean isResolutionDetailRestricted(String resolutionDetail) {
        return "Action plan to resolve the complaint has been created".equals(resolutionDetail) ||
            "The resolution plan is being implemented".equals(resolutionDetail) ||
            "Customer has been informed about the resolution progress".equals(resolutionDetail) ||
            "Waiting for customer confirmation on resolution".equals(resolutionDetail);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    public void confirmReturnTicket(ActionEvent event) {
        if (selectedResolution != null) {
            int resolutionID = selectedResolution.getResolution_ID();
            int complaintID = selectedResolution.getCompt_ID();
            String complainantID = selectedResolution.getComplainant_ID();
            String subject = selectedResolution.getCompt_Subject();
            String description = selectedResolution.getCompt_Desc();
            String orderID = selectedResolution.getCompt_OrderID();
            String category = selectedResolution.getCompt_Category();
            Date createdDate = selectedResolution.getComp_CreatedDate();
            int deptID = selectedResolution.getDept_ID();
            String status = "Pending";
            
            deleteResolution(resolutionID);
            
            insertReturnTicketComplaint(complaintID, complainantID, subject, description, orderID, category, createdDate, deptID, status);
            
            returnTicketPane.setVisible(false);
            resolution_details.setVisible(false);
            refreshTableView(); 
        }
    }

    private void insertReturnTicketComplaint(int complaintID, String complainantID, String subject, String description, String orderID, String category, Date createdDate, int deptID, String status) {
        String checkQuery = "SELECT compt_ID, compt_Status FROM complaint_ticket WHERE compt_ID = ?";
        String deleteQuery = "DELETE FROM complaint_ticket WHERE compt_ID = ? AND compt_Status = 'Approved'";
        String insertQuery = "INSERT INTO complaint_ticket (compt_ID, complainant_ID, admin_ID, compt_Subject, compt_Desc, compt_OrderID, compt_Category, compt_ProdInfo, compt_CustServRate, compt_Status, compt_CreatedDate, compt_Dept) " +
                    "VALUES (?, ?, 1, ?, ?, ?, ?, 'Product Info', 0, ?, ?, ?)";

        try (Connection connection = DbConnect.getConnect();
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {

            // Check if an entry with the same primary key exists
            checkStatement.setInt(1, complaintID);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                String currentStatus = resultSet.getString("compt_Status");

                // If the entry exists and has an "Approved" status, delete it
                if ("Approved".equals(currentStatus)) {
                    deleteStatement.setInt(1, complaintID);
                    deleteStatement.executeUpdate();
                }
            }

            // Insert the new entry with the "Pending" status
            insertStatement.setInt(1, complaintID);
            insertStatement.setString(2, complainantID);
            insertStatement.setString(3, subject);
            insertStatement.setString(4, description);
            insertStatement.setString(5, orderID);
            insertStatement.setString(6, category);
            insertStatement.setString(7, status);
            insertStatement.setDate(8, createdDate);
            insertStatement.setInt(9, deptID);

            int affectedRows = insertStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Complaint marked as Pending successfully.");
            } else {
                System.out.println("Failed to mark complaint as Pending.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void cancelReturnTicket(ActionEvent event) {
        returnTicketPane.setVisible(false);
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
