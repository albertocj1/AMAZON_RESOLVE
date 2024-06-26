package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

public class A_Login {

    @FXML
    private ImageView image1;

    @FXML
    private MediaView mediaView;

    @FXML
    TextField usernameField;

    @FXML
    PasswordField passwordField;

    @FXML
    public Text errorText;

    public void initialize() {

    }
    
    public void login(ActionEvent event) throws IOException {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.equals("admin001") && password.equals("admin123!@#")) {

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/View/Admin-Dashboard.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } else if (username.equals("dept001") && password.equals("order_dept123!@#")) {


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/View/Order_Dept.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } else if (username.equals("dept002") && password.equals("parcel_dept123!@#")) {


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/View/Parcel_Dept.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } else if (username.equals("dept003") && password.equals("product_dept123!@#")) {


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/View/Product_Dept.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        
        } else if (username.equals("dept004") && password.equals("returns_dept123!@#")) {


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/View/Returns_Dept.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } else {
            errorText.setVisible(true);
        }
    }
}
