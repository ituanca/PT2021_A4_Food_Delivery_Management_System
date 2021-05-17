package presentationLayer;

import dataLayer.FileManager;
import dataLayer.Serializator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class InterfaceLoader extends Application {

    Stage window;

    @Override
    public void start(Stage stage) throws Exception {
        URL url = new File("src\\main\\java\\presentationLayer\\fxmlFiles\\sample.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        stage.setTitle("Food delivery management system");
        Scene scene = new Scene(root, 800, 500);
        window = stage;
        Serializator serializer = new Serializator();
        serializer.serializeUsers(new FileManager().readUsersFromFile());
        Start.create(window, scene);
    }
}
