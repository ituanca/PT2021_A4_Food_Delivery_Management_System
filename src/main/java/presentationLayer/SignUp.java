package presentationLayer;

import businessLayer.User;
import dataLayer.FileManager;
import dataLayer.Serializator;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUp implements Initializable, Window {
    private static Stage nextWindow;
    public Button btnSignUp;
    public ComboBox cbUserType;
    public TextField tfUsername;
    public PasswordField pfPassword;
    public Button btnGoBack;

    @Override
    public void create(Stage window, Scene scene) {
        window.setScene(scene);
        window.show();
        nextWindow = window;
    }

    public boolean checkUsernameExistence(){
        try {
            FileReader file = new FileReader("src\\main\\resources\\users.txt");
            BufferedReader br = new BufferedReader(file);
            int line = 1;
            String data = br.readLine();
            while (data != null) {
                if(line % 3 == 1){
                    if(data.equals(getUsername())){
                        return true;
                    }
                }
                line++;
                data = br.readLine();
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    private boolean validate(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(cbUserType.getSelectionModel().isEmpty()){
            alert.setContentText("Please select user type");
            alert.show();
            return false;
        }
        if(getUsername().isEmpty() || getPassword().isEmpty()){
            alert.setContentText("Please fill in both fields");
            alert.show();
            return false;
        }
        if(checkUsernameExistence()){
            alert.setContentText("That username already exists");
            alert.show();
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbUserType.getItems().addAll("administrator", "employee", "client");
    }

    public void signUp(ActionEvent actionEvent) throws IOException {
        if(validate()){
            User user = new User(getUsername(), getPassword(), getUserType());
            new FileManager().writeNewUser(user);
            new Serializator().serializeUsers(new FileManager().readUsersFromFile());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("User created successfully");
            alert.show();
            if (getUserType().equals("administrator")) {
                Start.openNextWindow("administrator", new AdministratorController());
            } else if (getUserType().equals("client")) {
                Start.openNextWindow("client", new ClientController());
            }
        }
    }

    private String getUsername() { return tfUsername.getText(); }

    private String getPassword() { return pfPassword.getText(); }

    private String getUserType() { return (String) cbUserType.getValue(); }

    public void goBack(ActionEvent actionEvent) throws IOException {
        URL url = new File("src\\main\\java\\presentationLayer\\fxmlFiles\\sample.fxml").toURI().toURL();
        Scene scene = new Scene( FXMLLoader.load(url), 800, 500);
        Start.create(nextWindow, scene);
    }

}
