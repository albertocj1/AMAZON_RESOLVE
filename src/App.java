import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class App extends Application {

    @FXML
    private MediaView mediaView;

// 
    public static void main(String[] args) {

        launch(args);
    }   
    public void start(Stage stage) throws Exception {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/Admin-Dashboard.fxml"));
            Scene scene = new Scene(root);
            stage.getIcons().add(new Image("/Icons/Logo.png"));
            stage.setScene(scene);
            stage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 
