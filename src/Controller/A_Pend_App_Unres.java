package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Model.DbConnect;
import Model.admin;
import Model.complainant;
import Model.complaint_ticket;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Observable;
import java.util.regex.Pattern;

import com.mysql.cj.xdevapi.Statement;


public class A_Pend_App_Unres {

    @FXML
    private Text errorContactNum, errorEmailAdd, errorOrderID, approve_ticketDept; 

    @FXML
    private Label approve_ticketNum;

    @FXML
    private TextField searchTextField;

    @FXML
    private TextField addFName, addMName, addLName, addContactNum, addEmailAddress, addHouseNum, addBrgy, addStreet, addCity;

    @FXML
    private ChoiceBox <String> addSubject;

    @FXML
    private ChoiceBox <String> addDept;

    @FXML
    private ChoiceBox <String> addCategory;

    @FXML
    private DatePicker addDate;

    @FXML
    private ChoiceBox <String> approveSubject;

    @FXML
    private ChoiceBox <String> approveDept;

    @FXML
    private ChoiceBox <String> approveCategory;

    @FXML
    private DatePicker approveDate;

    @FXML
    private ChoiceBox <String> pendingSubject;

    @FXML
    private ChoiceBox <String> pendingDept;

    @FXML
    private ChoiceBox <String> pendingCategory;

    @FXML
    private DatePicker pendingDate;

    @FXML
    private TextField addOrderID, addDesc;

    @FXML
    private TableView <complaint_ticket> pendingTable;

    @FXML
    private TableColumn <complaint_ticket, Integer> PcomplaintIDColumn;

    @FXML
    private TableColumn <complaint_ticket, Integer> PcomplainantIDColumn;

    @FXML
    private TableColumn <complaint_ticket, String> PsubjectColumn;

    @FXML
    private TableColumn <complaint_ticket, String> PdescriptionColumn;

    @FXML
    private TableColumn <complaint_ticket, Integer> PorderIDColumn;

    @FXML
    private TableColumn <complaint_ticket, String> PcategoryColumn;

    @FXML
    private TableColumn <complaint_ticket, Date> PcreatedDateColumn;

    @FXML
    private TableColumn <complaint_ticket, String> PdepartmentColumn;

    @FXML
    private TableView <complaint_ticket> approveTable;

    @FXML
    private TableColumn <complaint_ticket, Integer> AcomplaintIDColumn;

    @FXML
    private TableColumn <complaint_ticket, Integer> AcomplainantIDColumn;

    @FXML
    private TableColumn <complaint_ticket, String> AsubjectColumn;

    @FXML
    private TableColumn <complaint_ticket, String> AdescriptionColumn;

    @FXML
    private TableColumn <complaint_ticket, Integer> AorderIDColumn;

    @FXML
    private TableColumn <complaint_ticket, String> AcategoryColumn;

    @FXML
    private TableColumn <complaint_ticket, Date> AcreatedDateColumn;

    @FXML
    private TableColumn <complaint_ticket, String> AdepartmentColumn;

    @FXML 
    private Label updateFirstName, updateMiddleName, updateLastName, UpdateContactNumber, updateEmailAdd, updateHouseNum, updateBrgy, updateStreet, updateCity;

    @FXML
    private Label updateOrderID, updateCategory, updateCreatedDate;

    @FXML
    private ChoiceBox <String> updateDept, updateSubject;

    @FXML
    private TextField updateDesc;

    @FXML
    private Label updateComplaintID;


    @FXML
    Pane pendingpane, approvedpane, unresolvedpane, pending_preview, unresolved_preview, confirmation_approval, confirmation_delete, newTicket1, newTicket2;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    complaint_ticket complaint_ticket = null;

    ObservableList<complaint_ticket> complaint_ticketList = null;
    private int complainantID;
    private int adminID = 1;


    public void initialize(){
        loadAData();
        refreshApprovedTable();
        loadPData();
        refreshPendingTable();

        addDate.setValue(LocalDate.now());
        addDate.setEditable(false);
        addSubject.setItems(FXCollections.observableArrayList("Delayed Delivery", "Wrong Item Shipped", "Missing Package", "Tracking Information Inaccuracies", "Defective Product", "Incorrect Product Received", "Refund Request", "Return Shipping Issues" ));
        addDept.setItems(FXCollections.observableArrayList("Order Fulfillment Department", "Parcel Tracking Department", "Product Replacement Department", "Returns Management Department"));
        addCategory.setItems(FXCollections.observableArrayList("Electronics", "Clothes", "Furniture", "Books"));


        // Initialize approve filters
        approveSubject.setItems(FXCollections.observableArrayList("Delayed Delivery", "Wrong Item Shipped", "Missing Package", "Tracking Information Inaccuracies", "Defective Product", "Incorrect Product Received", "Refund Request", "Return Shipping Issues"));
        approveDept.setItems(FXCollections.observableArrayList("Order Fulfillment Department", "Parcel Tracking Department", "Product Replacement Department", "Returns Management Department"));
        approveCategory.setItems(FXCollections.observableArrayList("Electronics", "Clothes", "Furniture", "Books"));

        // Initialize pending filters
        pendingSubject.setItems(FXCollections.observableArrayList("Delayed Delivery", "Wrong Item Shipped", "Missing Package", "Tracking Information Inaccuracies", "Defective Product", "Incorrect Product Received", "Refund Request", "Return Shipping Issues"));
        pendingDept.setItems(FXCollections.observableArrayList("Order Fulfillment Department", "Parcel Tracking Department", "Product Replacement Department", "Returns Management Department"));
        pendingCategory.setItems(FXCollections.observableArrayList("Electronics", "Clothes", "Furniture", "Books"));

        pendingTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        pendingTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                printSelectedTicketData(newSelection);
                bindSelectedTicketData(newSelection);
    
                // Fetch and bind complainant data
                complainant comp = getComplainantData(newSelection.getComplainant_ID());
                if (comp != null) {
                    bindSelectedComptData(comp);
                }
            }
        });
    }

    public void printSelectedComptData(complainant selectedRow){
        System.out.println("Selected row data: " + selectedRow.getComplainant_ID() + ", " + selectedRow.getComplainant_FName() + ", " + selectedRow.getComplainant_MName() + ", " + selectedRow.getComplainant_LName() + ", " + selectedRow.getComplainant_ContactNum() + ", " + selectedRow.getComplainant_EmailAdd() + ", " + selectedRow.getComplainant_HouseNum() + ", " + selectedRow.getComplainant_Brgy() + ", " + selectedRow.getComplainant_Street() + ", " + selectedRow.getComplainant_City());
    }

    public void printSelectedTicketData(complaint_ticket selectedRow) {
        System.out.println("Selected row data: " + selectedRow.getCompt_ID() + ", " + selectedRow.getComplainant_ID() + ", " + selectedRow.getCompt_Subject() + ", " + selectedRow.getCompt_Desc() + ", " + selectedRow.getCompt_OrderID() + ", " + selectedRow.getCompt_Category() + ", " + selectedRow.getCompt_CreatedDate() + ", " + selectedRow.getCompt_Dept());
    }
    
    public void bindSelectedComptData(complainant selectedRow){
        updateFirstName.setText(selectedRow.getComplainant_FName());
        updateMiddleName.setText(selectedRow.getComplainant_MName());
        updateLastName.setText(selectedRow.getComplainant_LName());
        UpdateContactNumber.setText(selectedRow.getComplainant_ContactNum());
        updateEmailAdd.setText(selectedRow.getComplainant_EmailAdd());
        updateHouseNum.setText(selectedRow.getComplainant_HouseNum());
        updateBrgy.setText(selectedRow.getComplainant_Brgy());
        updateStreet.setText(selectedRow.getComplainant_Street());
        updateCity.setText(selectedRow.getComplainant_City());
    }

    private void bindSelectedTicketData(complaint_ticket selectedRow) {
        updateComplaintID.setText(String.valueOf(selectedRow.getCompt_ID()));
        updateSubject.setValue(selectedRow.getCompt_Subject());
        updateDesc.setText(selectedRow.getCompt_Desc());
        updateOrderID.setText(selectedRow.getCompt_OrderID());
        updateCategory.setText(selectedRow.getCompt_Category());
        updateCreatedDate.setText(selectedRow.getCompt_CreatedDate().toString());
        updateDept.setValue(selectedRow.getCompt_Dept());
        
    }

    public complainant getComplainantData(int complainantID) {
        complainant comp = null;
        try {
            query = "SELECT * FROM complainant WHERE complainant_ID = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, complainantID);
            resultSet = preparedStatement.executeQuery();
    
            if (resultSet.next()) {
                comp = new complainant(
                    resultSet.getInt("complainant_ID"),
                    resultSet.getString("complainant_FName"),
                    resultSet.getString("complainant_MName"),
                    resultSet.getString("complainant_LName"),
                    resultSet.getString("complainant_ContactNum"),
                    resultSet.getString("complainant_EmailAdd"),
                    resultSet.getString("complainant_HouseNum"),
                    resultSet.getString("complainant_Brgy"),
                    resultSet.getString("complainant_Street"),
                    resultSet.getString("complainant_City")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comp;
    }
    
    public void loadAData() {
        connection = DbConnect.getConnect();

        AcomplaintIDColumn.setCellValueFactory(new PropertyValueFactory<>("compt_ID"));
        AcomplainantIDColumn.setCellValueFactory(new PropertyValueFactory<>("complainant_ID"));
        AsubjectColumn.setCellValueFactory(new PropertyValueFactory<>("compt_Subject"));
        AdescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("compt_Desc"));
        AorderIDColumn.setCellValueFactory(new PropertyValueFactory<>("compt_OrderID"));
        AcategoryColumn.setCellValueFactory(new PropertyValueFactory<>("compt_Category"));
        AcreatedDateColumn.setCellValueFactory(new PropertyValueFactory<>("compt_CreatedDate"));
        AdepartmentColumn.setCellValueFactory(new PropertyValueFactory<>("compt_Dept"));

    }

    public void refreshApprovedTable() {
        connection = DbConnect.getConnect();
        ObservableList<complaint_ticket> approvedTicketList = FXCollections.observableArrayList();
    
        try {
            String query = "SELECT compt_ID, complainant_ID, compt_Subject, compt_Desc, compt_OrderID, compt_Category, compt_CreatedDate, compt_Dept FROM complaint_ticket WHERE compt_Status = 'Approved'";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {
                approvedTicketList.add(new complaint_ticket(
                    resultSet.getInt("compt_ID"),
                    resultSet.getInt("complainant_ID"),
                    1, // admin_ID placeholder
                    resultSet.getString("compt_Subject"),
                    resultSet.getString("compt_Desc"),
                    resultSet.getString("compt_OrderID"),
                    resultSet.getString("compt_Category"),
                    "", // compt_ProdInfo placeholder
                    0, // compt_CustServRate placeholder
                    "Approved", // compt_Status placeholder
                    resultSet.getDate("compt_CreatedDate"),
                    resultSet.getString("compt_Dept")
                ));
            }
            
            // Set items to the approveTable
            approveTable.setItems(approvedTicketList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
   

    @FXML
    public void loadPData() {
        connection = DbConnect.getConnect();
        
        PcomplaintIDColumn.setCellValueFactory(new PropertyValueFactory<>("compt_ID"));
        PcomplainantIDColumn.setCellValueFactory(new PropertyValueFactory<>("complainant_ID"));
        PsubjectColumn.setCellValueFactory(new PropertyValueFactory<>("compt_Subject"));
        PdescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("compt_Desc"));
        PorderIDColumn.setCellValueFactory(new PropertyValueFactory<>("compt_OrderID"));
        PcategoryColumn.setCellValueFactory(new PropertyValueFactory<>("compt_Category"));
        PcreatedDateColumn.setCellValueFactory(new PropertyValueFactory<>("compt_CreatedDate"));
        PdepartmentColumn.setCellValueFactory(new PropertyValueFactory<>("compt_Dept"));
    }

    public void refreshPendingTable() {
        connection = DbConnect.getConnect();
        ObservableList<complaint_ticket> pendingTicketList = FXCollections.observableArrayList();
    
        try {
            String query = "SELECT compt_ID, complainant_ID, compt_Subject, compt_Desc, compt_OrderID, compt_Category, compt_CreatedDate, compt_Dept FROM complaint_ticket WHERE compt_Status = 'Pending'";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {
                pendingTicketList.add(new complaint_ticket(
                    resultSet.getInt("compt_ID"),
                    resultSet.getInt("complainant_ID"),
                    1, // admin_ID placeholder
                    resultSet.getString("compt_Subject"),
                    resultSet.getString("compt_Desc"),
                    resultSet.getString("compt_OrderID"),
                    resultSet.getString("compt_Category"),
                    "", // compt_ProdInfo placeholder
                    0, // compt_CustServRate placeholder
                    "Pending", // compt_Status placeholder
                    resultSet.getDate("compt_CreatedDate"),
                    resultSet.getString("compt_Dept")
                ));
            }
            
            // Set items to the pendingTable
            pendingTable.setItems(pendingTicketList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    public void createComplainant(ActionEvent event) {
        try (Connection connection = DbConnect.getConnect()) {
            gotoTicket1();
            System.out.println("Connected to database.");
            String defaultInsertQuery = "INSERT INTO complainant (complainant_FName, complainant_MName, complainant_LName, complainant_ContactNum, complainant_EmailAdd, complainant_HouseNum, complainant_Brgy, complainant_Street, complainant_City) VALUES ('Default', 'Default', 'Default', '0000000000', 'default@example.com', '0', 'Default', 'Default', 'Default')";

            try (PreparedStatement statement = connection.prepareStatement(defaultInsertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statement.executeUpdate();
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    complainantID = generatedKeys.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Complainant ID: " + complainantID);
    }

    @FXML
public void addComplaint(ActionEvent event) {
    try {
        if (!validateComplainantData() || !validateComplaintData()) {
            return; // Do not proceed if validation fails
        }

        String FName = addFName.getText();
        String MName = addMName.getText();
        String LName = addLName.getText();
        String ContactNum = addContactNum.getText();
        String EmailAddress = addEmailAddress.getText();
        String HouseNum = addHouseNum.getText();
        String Brgy = addBrgy.getText();
        String Street = addStreet.getText();
        String City = addCity.getText();
        String Subject = addSubject.getValue();
        String Dept = addDept.getValue();
        String Description = addDesc.getText();
        String OrderID = addOrderID.getText();
        String Category = addCategory.getValue();
        LocalDate Date = LocalDate.now();

        // Convert Department names to IDs
        switch (Dept) {
            case "Order Fulfillment Department":
                Dept = "1";
                break;
            case "Parcel Tracking Department":
                Dept = "2";
                break;
            case "Product Replacement Department":
                Dept = "3";
                break;
            case "Returns Management Department":
                Dept = "4";
                break;
        }

        try (Connection connection = DbConnect.getConnect()) {
            // Update complainant details
            String complainantUpdateQuery = "UPDATE complainant SET complainant_FName = ?, complainant_MName = ?, complainant_LName = ?, complainant_ContactNum = ?, complainant_EmailAdd = ?, complainant_HouseNum = ?, complainant_Brgy = ?, complainant_Street = ?, complainant_City = ? WHERE complainant_ID = ?";
            try (PreparedStatement complainantStatement = connection.prepareStatement(complainantUpdateQuery)) {
                complainantStatement.setString(1, FName);
                complainantStatement.setString(2, MName);
                complainantStatement.setString(3, LName);
                complainantStatement.setString(4, ContactNum);
                complainantStatement.setString(5, EmailAddress);
                complainantStatement.setString(6, HouseNum);
                complainantStatement.setString(7, Brgy);
                complainantStatement.setString(8, Street);
                complainantStatement.setString(9, City);
                complainantStatement.setInt(10, complainantID);
                complainantStatement.executeUpdate();
            }

            // Insert complaint ticket
            String complaintTicketInsertQuery = "INSERT INTO complaint_ticket (complainant_ID, admin_ID, compt_Subject, compt_Desc, compt_OrderID, compt_Category, compt_ProdInfo, compt_CustServRate, compt_Status, compt_CreatedDate, compt_Dept) VALUES (?, ?, ?, ?, ?, ?, ?, 0, 'Approved', ?, ?)";
            try (PreparedStatement complaintTicketStatement = connection.prepareStatement(complaintTicketInsertQuery)) {
                complaintTicketStatement.setInt(1, complainantID);
                complaintTicketStatement.setInt(2, adminID);
                complaintTicketStatement.setString(3, Subject);
                complaintTicketStatement.setString(4, Description);
                complaintTicketStatement.setString(5, OrderID);
                complaintTicketStatement.setString(6, Category);
                complaintTicketStatement.setString(7, "Product Info");
                complaintTicketStatement.setDate(8, java.sql.Date.valueOf(Date));
                complaintTicketStatement.setString(9, Dept);

                // Debugging: Print SQL and values
                System.out.println("SQL: " + complaintTicketInsertQuery);
                System.out.println("Values: [" + complainantID + ", " + adminID + ", " + Subject + ", " + Description + ", " + OrderID + ", " + Category + ", " + "Product Info" + ", " + Date + ", " + Dept + "]");

                complaintTicketStatement.executeUpdate();
            }

            // Query to fetch the last inserted complaint ID
            String lastInsertIDQuery = "SELECT LAST_INSERT_ID() AS new_compt_id";
            try (PreparedStatement lastInsertIDStatement = connection.prepareStatement(lastInsertIDQuery)) {
                ResultSet resultSet = lastInsertIDStatement.executeQuery();
                if (resultSet.next()) {
                    int newComplaintID = resultSet.getInt("new_compt_id");
                    // Concatenate "Ticket #" with newComplaintID
                    String ticketNumber = "Ticket #" + newComplaintID;
                    // Set the fetched complaint ID with "Ticket #" prefix to your label
                    approve_ticketNum.setText(ticketNumber);

                // Insert into resolution table
                String resolutionInsertQuery = "INSERT INTO resolution (compt_ID, complainant_ID, dept_ID, resolution_Details, resolution_Status, resolution_Date) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement resolutionStatement = connection.prepareStatement(resolutionInsertQuery)) {
                    resolutionStatement.setInt(1, newComplaintID);
                    resolutionStatement.setInt(2, complainantID);
                    resolutionStatement.setInt(3, Integer.parseInt(Dept));
                    resolutionStatement.setString(4, "Complaint received and initial assessment started");
                    resolutionStatement.setString(5, "In Progress");
                    resolutionStatement.setDate(6, java.sql.Date.valueOf(Date));
                    resolutionStatement.executeUpdate();
                }
            }
        }

        }
            refreshApprovedTable();
            approve_ticketDept.setText(convertDeptIDToName(Dept));
            confirmation_approval.setVisible(true);

        } catch (NullPointerException e) {
            e.printStackTrace();
            // Handle the exception, e.g., display an error message to the user
            System.out.println("Error: Some fields are null. Please fill in all required fields.");
        } catch (Exception e) {
            e.printStackTrace();
            // Handle other exceptions
        }
    }

    
    private String convertDeptIDToName(String deptID) {
        String deptName = null;
    
        switch (deptID) {
            case "1":
                deptName = "Order Fulfillment";
                break;
            case "2":
                deptName = "Parcel Tracking";
                break;
            case "3":
                deptName = "Product Replacement";
                break;
            case "4":
                deptName = "Returns Management";
                break;
            default:
                // Handle unknown department IDs
                deptName = "Unknown Department";
                break;
        }
    
        return deptName;
    }
    

    public boolean validateComplainantData() {
        String FName = addFName.getText();
        String MName = addMName.getText();
        String LName = addLName.getText();
        String ContactNum = addContactNum.getText();
        String EmailAddress = addEmailAddress.getText();
        String HouseNum = addHouseNum.getText();
        String Brgy = addBrgy.getText();
        String Street = addStreet.getText();
        String City = addCity.getText();
    
        // Reset error messages visibility
        errorEmailAdd.setVisible(false);
        errorContactNum.setVisible(false);
    
        boolean isValid = true;
    
        // Check if any field is empty
        if (FName.isEmpty() || LName.isEmpty() || ContactNum.isEmpty() || 
            EmailAddress.isEmpty() || HouseNum.isEmpty() || Brgy.isEmpty() || Street.isEmpty() || City.isEmpty()) {
            System.out.println("All fields must be filled.");
            isValid = false;
        }
    
        // Validate email format and domain
        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@gmail\\.com$", EmailAddress)) {
            System.out.println("Invalid email address. It should be in the format 'example@gmail.com'.");
            errorEmailAdd.setVisible(true);
            isValid = false;
        }
    
        // Validate contact number (assuming it should be 11 digits)
        if (!Pattern.matches("^\\d{11}$", ContactNum)) {
            System.out.println("Invalid contact number. It should be 11 digits.");
            errorContactNum.setVisible(true);
            isValid = false;
        }
    
        return isValid;
    }

    public boolean validateComplaintData() {
        String Subject = addSubject.getValue();
        String Dept = addDept.getValue();
        String Description = addDesc.getText();
        String OrderID = addOrderID.getText();
        String Category = addCategory.getValue();
        LocalDate Date = addDate.getValue();
    
        boolean isValid = true;
    
        // Check if any of the fields is null or empty
        if (Subject == null || Dept == null || Description.isEmpty() || OrderID.isEmpty() || Category == null || Date == null) {
            System.out.println("All complaint fields must be filled.");
            isValid = false;
        }

        // Validate Order ID format: should start with '#' followed by 6 digits
        if (!OrderID.matches("^#\\d{6}$")) {
            System.out.println("Invalid Order ID format. It should start with '#' followed by 6 digits.");
            errorOrderID.setVisible(true);
            isValid = false;
        }

        return isValid;
    }

    private void updateDepartmentBasedOnSubject(String selectedSubject) {
        String department = null;
    
        // Determine the department based on the selected subject
        switch (selectedSubject) {
            case "Delayed Delivery":
            case "Wrong Item Shipped":
                department = "Order Fulfillment Department";
                break;
            case "Missing Package":
            case "Tracking Information Inaccuracies":
                department = "Parcel Tracking Department";
                break;
            case "Defective Product":
            case "Incorrect Product Received":
                department = "Product Replacement Department";
                break;
            case "Refund Request":
            case "Return Shipping Issues":
                department = "Returns Management Department";
                break;
            default:
                break;
        }
    
        if (department != null) {
            addDept.setValue(department);
        }
    }
    
    @FXML
    public void clearFieldsAndNavigateToApproved() {
        // Clear text fields
        addFName.clear();
        addMName.clear();
        addLName.clear();
        addContactNum.clear();
        addEmailAddress.clear();
        addHouseNum.clear();
        addBrgy.clear();
        addStreet.clear();
        addCity.clear();
        addOrderID.clear();
        addDesc.clear();

        // Set default values for choice boxes
        addSubject.setValue(null);
        addDept.setValue(null);
        addCategory.setValue(null);


        confirmation_approval.setVisible(false);
        newTicket2.setVisible(false);
        newTicket1.setVisible(false);
        pendingpane.setVisible(false);
        approvedpane.setVisible(true);

        refreshApprovedTable();
    }

    @FXML
    private void handleSubjectSelection(ActionEvent event) {
        String selectedSubject = addSubject.getValue();
        if (selectedSubject != null) {
            updateDepartmentBasedOnSubject(selectedSubject);
        }
    }
    
    //APPROVE TABLE FILTERS
    @FXML
    public void handleApproveSubjectSelection(ActionEvent event) {
        filterApprovedTable();
    }

    @FXML
    public void handleApproveDeptSelection(ActionEvent event) {
        filterApprovedTable();
    }

    @FXML
    public void handleApproveCategorySelection(ActionEvent event) {
        filterApprovedTable();
    }

    @FXML
    public void handleApproveDateSelection(ActionEvent event) {
        filterApprovedTable();
    }

    private String getDeptID(String deptName) {
        switch (deptName) {
            case "Order Fulfillment Department":
                return "1";
            case "Parcel Tracking Department":
                return "2";
            case "Product Replacement Department":
                return "3";
            case "Returns Management Department":
                return "4";
            default:
                return null;
        }
    }
    
    public void filterApprovedTable() {
        connection = DbConnect.getConnect();
        ObservableList<complaint_ticket> approvedTicketList = FXCollections.observableArrayList();
    
        try {
            String query = "SELECT compt_ID, complainant_ID, compt_Subject, compt_Desc, compt_OrderID, compt_Category, compt_CreatedDate, compt_Dept FROM complaint_ticket WHERE compt_Status = 'Approved'";
            
            // Apply filters if selections are made
            String subjectFilter = approveSubject.getValue();
            String deptFilter = approveDept.getValue();
            String categoryFilter = approveCategory.getValue();
            LocalDate dateFilter = approveDate.getValue();
    
            boolean hasFilter = false;
    
            if (subjectFilter != null && !subjectFilter.isEmpty()) {
                query += " AND compt_Subject = ?";
                hasFilter = true;
            }
            if (deptFilter != null && !deptFilter.isEmpty()) {
                deptFilter = getDeptID(deptFilter); // Convert department name to ID
                query += " AND compt_Dept = ?";
                hasFilter = true;
            }
            if (categoryFilter != null && !categoryFilter.isEmpty()) {
                query += " AND compt_Category = ?";
                hasFilter = true;
            }
            if (dateFilter != null) {
                query += " AND compt_CreatedDate = ?";
                hasFilter = true;
            }
    
            preparedStatement = connection.prepareStatement(query);
    
            int paramIndex = 1;
    
            if (subjectFilter != null && !subjectFilter.isEmpty()) {
                preparedStatement.setString(paramIndex++, subjectFilter);
            }
            if (deptFilter != null && !deptFilter.isEmpty()) {
                preparedStatement.setString(paramIndex++, deptFilter);
            }
            if (categoryFilter != null && !categoryFilter.isEmpty()) {
                preparedStatement.setString(paramIndex++, categoryFilter);
            }
            if (dateFilter != null) {
                preparedStatement.setDate(paramIndex++, java.sql.Date.valueOf(dateFilter));
            }
    
            resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {
                approvedTicketList.add(new complaint_ticket(
                    resultSet.getInt("compt_ID"),
                    resultSet.getInt("complainant_ID"),
                    1, // admin_ID placeholder
                    resultSet.getString("compt_Subject"),
                    resultSet.getString("compt_Desc"),
                    resultSet.getString("compt_OrderID"),
                    resultSet.getString("compt_Category"),
                    "", // compt_ProdInfo placeholder
                    0, // compt_CustServRate placeholder
                    "Approved", // compt_Status placeholder
                    resultSet.getDate("compt_CreatedDate"),
                    resultSet.getString("compt_Dept")
                ));
            }
    
            // Set items to the approveTable
            approveTable.setItems(approvedTicketList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void handleSearch(ActionEvent event) {
    }

    // PENDING TABLE FILTERS
    @FXML
    public void handlePendingSubjectSelection(ActionEvent event) {
        filterPendingTable();
    }

    @FXML
    public void handlePendingDeptSelection(ActionEvent event) {
        filterPendingTable();
    }

    @FXML
    public void handlePendingCategorySelection(ActionEvent event) {
        filterPendingTable();
    }

    @FXML
    public void handlePendingDateSelection(ActionEvent event) {
        filterPendingTable();
    }

    public void filterPendingTable() {
        connection = DbConnect.getConnect();
        ObservableList<complaint_ticket> pendingTicketList = FXCollections.observableArrayList();

        try {
            String query = "SELECT compt_ID, complainant_ID, compt_Subject, compt_Desc, compt_OrderID, compt_Category, compt_CreatedDate, compt_Dept FROM complaint_ticket WHERE compt_Status = 'Pending'";

            // Apply filters if selections are made
            String subjectFilter = pendingSubject.getValue();
            String deptFilter = pendingDept.getValue();
            String categoryFilter = pendingCategory.getValue();
            LocalDate dateFilter = pendingDate.getValue();

            boolean hasFilter = false;

            if (subjectFilter != null && !subjectFilter.isEmpty()) {
                query += " AND compt_Subject = ?";
                hasFilter = true;
            }
            if (deptFilter != null && !deptFilter.isEmpty()) {
                deptFilter = getDeptID(deptFilter); // Convert department name to ID
                query += " AND compt_Dept = ?";
                hasFilter = true;
            }
            if (categoryFilter != null && !categoryFilter.isEmpty()) {
                query += " AND compt_Category = ?";
                hasFilter = true;
            }
            if (dateFilter != null) {
                query += " AND compt_CreatedDate = ?";
                hasFilter = true;
            }

            preparedStatement = connection.prepareStatement(query);

            int paramIndex = 1;

            if (subjectFilter != null && !subjectFilter.isEmpty()) {
                preparedStatement.setString(paramIndex++, subjectFilter);
            }
            if (deptFilter != null && !deptFilter.isEmpty()) {
                preparedStatement.setString(paramIndex++, deptFilter);
            }
            if (categoryFilter != null && !categoryFilter.isEmpty()) {
                preparedStatement.setString(paramIndex++, categoryFilter);
            }
            if (dateFilter != null) {
                preparedStatement.setDate(paramIndex++, java.sql.Date.valueOf(dateFilter));
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                pendingTicketList.add(new complaint_ticket(
                    resultSet.getInt("compt_ID"),
                    resultSet.getInt("complainant_ID"),
                    1, // admin_ID placeholder
                    resultSet.getString("compt_Subject"),
                    resultSet.getString("compt_Desc"),
                    resultSet.getString("compt_OrderID"),
                    resultSet.getString("compt_Category"),
                    "", // compt_ProdInfo placeholder
                    0, // compt_CustServRate placeholder
                    "Pending", // compt_Status placeholder
                    resultSet.getDate("compt_CreatedDate"),
                    resultSet.getString("compt_Dept")
                ));
            }

            // Set items to the pendingTable
            pendingTable.setItems(pendingTicketList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //BUTTON FUNCTIONS
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
        // unresolved_preview.setVisible(false);
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
    public void gotoTicket1(){
        pendingpane.setVisible(false);
        newTicket1.setVisible(true);
    }

    @FXML
    public void gotoTicket2(ActionEvent event) throws IOException {
        if (!validateComplainantData()) {
            return; // Do not proceed if validation fails
        }
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