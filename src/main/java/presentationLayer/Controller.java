package presentationLayer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Controller {
    private static Stage nextWindow;
    public Button btnLogIn;
    public Button btnSignUp;

    public void start(){ new Thread(() -> Application.launch(InterfaceLoader.class)).start(); }

    public static void create(Stage window, Scene scene) {
        window.setScene(scene);
        window.show();
        nextWindow = window;
    }

    public void logIn(ActionEvent actionEvent) throws IOException {
        openNextWindow("logIn", new LogIn());
    }

    public void signUp(ActionEvent actionEvent) throws IOException {
        openNextWindow("signUp", new SignUp());
    }

    public static void openNextWindow(String fileName, Window window) throws IOException {
        URL url = new File("src\\main\\java\\presentationLayer\\fxmlFiles\\" + fileName + ".fxml").toURI().toURL();
        Scene scene = new Scene( FXMLLoader.load(url), 1000, 640);
        window.create(nextWindow, scene);
    }

}
