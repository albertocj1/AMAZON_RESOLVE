package Controller;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

import Model.DbConnect;
import Model.resolution;

public class A_Dashboard {

    @FXML
    private Text pendingTickets, topMonth, totalResolvedTixDept, topDepartment, activeFurniture, activeElectronics, activeBooks, activeClothes, statusComplaintID, liveClock, currentMonth;

    @FXML
    private TextField searchComplaintID;

    @FXML
    private BarChart<String, Integer> resolvedTicketsChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private BarChart<String, Integer> resolvedTicketsByCategoryChart;

    @FXML
    private CategoryAxis xAxisCat;

    @FXML
    private NumberAxis yAxisCat;


    private Timeline clockTimeline;

    @FXML
    private ChoiceBox<String> departmentChoiceBox;

    @FXML
    private TableView<resolution> dashboardResolutionTable;

    @FXML
    private TableColumn<resolution, Integer> resolutionIDColumn;

    @FXML
    private TableColumn<resolution, String> resolutionDetailColumn;

    @FXML
    private TableColumn<resolution, String> resolutionStatusColumn;

    private ObservableList<resolution> resolutions = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        updatePendingTicketCount();
        updateTopDepartmentInfo();
        updateActiveTicketsByCategory();
        startClock();

        LocalDate now = LocalDate.now();
        String month = now.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toUpperCase();
        currentMonth.setText(month);

        updateResolvedTicketsChart();
        updateResolvedTicketsByCategoryChart();

        departmentChoiceBox.setOnAction(this::departmentChoiceChanged);
        departmentChoiceBox.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color:  #ffffff");


        // Initialize choice box with department names
        departmentChoiceBox.getItems().addAll(
                "Order Fulfillment", "Parcel Tracking", "Product Replacement", "Returns Management");

        // Set up table columns
        resolutionIDColumn.setCellValueFactory(new PropertyValueFactory<>("resolution_ID"));
        resolutionDetailColumn.setCellValueFactory(new PropertyValueFactory<>("resolution_Details"));
        resolutionStatusColumn.setCellValueFactory(new PropertyValueFactory<>("resolution_Status"));
        
        // Set default department selection if needed
        departmentChoiceBox.setValue("Order Fulfillment"); // Example default selection
        updateResolutionTable();
    }

    @FXML
    private void departmentChoiceChanged(ActionEvent event) {
        updateResolutionTable();
    }

    @FXML
    private void updateResolutionTable() {
        resolutions.clear(); // Clear existing data

        String selectedDepartment = departmentChoiceBox.getValue();
        String deptID = getDepartmentID(selectedDepartment);

        if (deptID == null) {
            return; // Handle if department ID is not found
        }

        String query = "SELECT r.resolution_ID, r.resolution_Details, r.resolution_Status " +
                    "FROM resolution r " +
                    "JOIN complaint_ticket ct ON r.compt_ID = ct.compt_ID " +
                    "WHERE r.dept_ID = ? " +
                    "      AND (r.resolution_Status = 'In Progress' OR r.resolution_Status = 'Waiting')";

        try (Connection connection = DbConnect.getConnect();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, deptID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int resolutionID = resultSet.getInt("resolution_ID");
                String resolutionDetail = resultSet.getString("resolution_Details");
                String resolutionStatus = resultSet.getString("resolution_Status");

                // Debug print statements for logging
                System.out.println("Resolution ID: " + resolutionID);
                System.out.println("Resolution Detail: " + resolutionDetail);
                System.out.println("Resolution Status: " + resolutionStatus);

                // Add to resolutions list
                resolutions.add(new resolution(resolutionID, 0, Integer.parseInt(deptID), "", resolutionDetail, resolutionStatus, null));
            }

            dashboardResolutionTable.setItems(resolutions);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    
    // Method to map department name to department ID
    private String getDepartmentID(String departmentName) {
        switch (departmentName) {
            case "Order Fulfillment":
                return "1";
            case "Parcel Tracking":
                return "2";
            case "Product Replacement":
                return "3";
            case "Returns Management":
                return "4";
            default:
                return null; // Handle unknown department
        }
    }
    
      
    private void updateResolvedTicketsChart() {
        resolvedTicketsChart.getData().clear();
    
        String query = "SELECT dept_ID, COUNT(*) AS count " +
                       "FROM resolution " +
                       "WHERE resolution_Status = 'Resolved' " +
                       "      AND MONTH(resolution_Date) = MONTH(CURRENT_DATE()) " +
                       "GROUP BY dept_ID";
    
        try (Connection connection = DbConnect.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
    
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
    
            while (resultSet.next()) {
                String deptID = resultSet.getString("dept_ID");
                int resolvedCount = resultSet.getInt("count");
    
                String deptName = getDepartmentName(deptID);
    
                series.getData().add(new XYChart.Data<>(deptName, resolvedCount));
            }
    
            // Add series to the chart
            resolvedTicketsChart.getData().add(series);
    
            // Customize chart appearance
            for (XYChart.Data<String, Integer> data : series.getData()) {
                if (data.getYValue() == 0) {
                    data.getNode().setVisible(false); // Hide bars with zero value
                }
                Node node = data.getNode();
                node.setStyle("-fx-bar-fill: #ff9900;");
            }
    
            // Set labels for axes
            resolvedTicketsChart.getXAxis().setLabel("Department");
            resolvedTicketsChart.getYAxis().setLabel("Resolved Tickets");
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void updateResolvedTicketsByCategoryChart() {
        resolvedTicketsByCategoryChart.getData().clear();

        String query = "SELECT ct.compt_Category, COUNT(*) AS count " +
                "FROM resolution r " +
                "JOIN complaint_ticket ct ON r.compt_ID = ct.compt_ID " +
                "WHERE r.resolution_Status = 'Resolved' " +
                "GROUP BY ct.compt_Category";

        try (Connection connection = DbConnect.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            while (resultSet.next()) {
                String category = resultSet.getString("compt_Category");
                int count = resultSet.getInt("count");
                series.getData().add(new XYChart.Data<>(category, count));
            }

            resolvedTicketsByCategoryChart.getData().add(series);

            // Customize bar styles
            for (XYChart.Data<String, Integer> data : series.getData()) {
                Node node = data.getNode();
                node.setStyle("-fx-bar-fill: #ff9900;");
            }

            // Set labels for axes
            resolvedTicketsByCategoryChart.getXAxis().setLabel("Category");
            resolvedTicketsByCategoryChart.getYAxis().setLabel("Resolved Tickets");
    

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private String getDepartmentName(String deptID) {
        
        switch (deptID) {
            case "1":
                return "Order Fulfillment";
            case "2":
                return "Parcel Tracking";
            case "3":
                return "Product Replacement";
            case "4":
                return "Returns Management";
            default:
                return "Unknown Department";
        }
    }


    private int getPendingTicketCount() {
        int pendingCount = 0;
        String query = "SELECT COUNT(*) AS count FROM complaint_ticket WHERE compt_status = 'Pending'";

        try (Connection connection = DbConnect.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                pendingCount = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pendingCount;
    }

    private void updatePendingTicketCount() {
        int pendingCount = getPendingTicketCount();
        pendingTickets.setText(String.valueOf(pendingCount));
    }

    private void updateTopDepartmentInfo() {
        LocalDate now = LocalDate.now();
        String month = now.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    
        String query = "SELECT dept_ID, COUNT(*) AS count FROM resolution WHERE resolution_Status = 'Resolved' AND MONTH(resolution_Date) = MONTH(CURRENT_DATE()) GROUP BY dept_ID ORDER BY count DESC LIMIT 1";
    
        String topDept = "";
        int resolvedCount = 0;
    
        try (Connection connection = DbConnect.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
    
            if (resultSet.next()) {
                int deptId = resultSet.getInt("dept_ID");
                resolvedCount = resultSet.getInt("count");
    
                // Mapping department IDs to department names
                switch (deptId) {
                    case 1:
                        topDept = "Order Fulfillment Department";
                        break;
                    case 2:
                        topDept = "Parcel Tracking Department";
                        break;
                    case 3:
                        topDept = "Product Replacement Department";
                        break;
                    case 4:
                        topDept = "Returns Management Department";
                        break;
                    default:
                        topDept = "Unknown Department";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        topMonth.setText("For the month of " + month);
        totalResolvedTixDept.setText(String.valueOf(resolvedCount));
        topDepartment.setText(topDept);
    }

    private void updateActiveTicketsByCategory() {
        String query = "SELECT ct.compt_Category, COUNT(*) AS count " +
                       "FROM resolution r " +
                       "JOIN complaint_ticket ct ON r.compt_ID = ct.compt_ID " +
                       "WHERE (r.resolution_Status = 'In Progress' OR r.resolution_Status = 'Waiting') " +
                       "GROUP BY ct.compt_Category";

        try (Connection connection = DbConnect.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String category = resultSet.getString("compt_Category");
                int count = resultSet.getInt("count");

                switch (category) {
                    case "Furniture":
                        activeFurniture.setText(String.valueOf(count));
                        break;
                    case "Electronics":
                        activeElectronics.setText(String.valueOf(count));
                        break;
                    case "Clothes":
                        activeClothes.setText(String.valueOf(count));
                        break;
                    case "Books":
                        activeBooks.setText(String.valueOf(count));
                        break;
                    default:
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void checkStatus(ActionEvent event) {
        String complaintID = searchComplaintID.getText();
        String status = getComplaintStatus(complaintID);
        statusComplaintID.setText(status);
    }

    private String getComplaintStatus(String complaintID) {
        String status = "";
        String query = "SELECT compt_Status FROM complaint_ticket WHERE compt_ID = ?";

        try (Connection connection = DbConnect.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, complaintID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    status = resultSet.getString("compt_Status");
                } else {
                    status = "No record found";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            status = "Error retrieving status";
        }
        return status;
    }

    private void startClock() {
        // Create a timeline to update the live clock
        clockTimeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), e -> {
            // Update the clock text with current date and time
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy hh:mm:ss a");
            String formattedDateTime = now.format(formatter);
            liveClock.setText(formattedDateTime);
        }));

        clockTimeline.setCycleCount(Animation.INDEFINITE);
        clockTimeline.play();
    }


    //BUTTONS

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
    public void gotoHistory(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Admin-History.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
