package euresty;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Euresty extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/PaintFX.fxml"));
        Scene scene = new Scene(root, 700, 700);
        stage.setTitle("PAINT");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
