package presentationLayer;

import businessLayer.DeliveryService;
import businessLayer.User;
import businessLayer.UserSession;
import dataLayer.Serializator;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LogIn implements Initializable, Window {
    private static Stage nextWindow;
    public ComboBox cbUserType;
    public TextField tfUsername;
    public PasswordField pfPassword;
    public Button btnLogIn;
    public Button btnGoBack;
    int id;

    @Override
    public void create(Stage window, Scene scene) {
        window.setScene(scene);
        window.show();
        nextWindow = window;
    }

    private boolean checkUserExistenceAndCreateUser(){
        ArrayList<User> users  = readUsers();
        for(User user: users){
            if(getUsername().equals(user.getUsername()) && getPassword().equals(user.getPassword()) && getUserType().equals(user.getUserType())){
                id = user.getId();
                return true;
            }
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
        if(!checkUserExistenceAndCreateUser()){
            alert.setContentText("User not found");
            alert.show();
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbUserType.getItems().addAll("administrator", "employee", "client");
    }

    public void logIn(ActionEvent actionEvent) throws IOException {
        if(validate()) {
            UserSession.setInstance(null);
            UserSession.getInstance(id, getUsername(), getPassword(), getUserType());
            if (getUserType().equals("administrator")) {
                Start.openNextWindow("administrator", new AdministratorController());
            } else if (getUserType().equals("client")) {
                Start.openNextWindow("client", new ClientController());
            }else if (getUserType().equals("employee")){
                Start.openNextWindow("employee", new EmployeeController());
            }
        }
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        URL url = new File("src\\main\\java\\presentationLayer\\fxmlFiles\\sample.fxml").toURI().toURL();
        Scene scene = new Scene( FXMLLoader.load(url), 1000, 640);
        Start.create(nextWindow, scene);
    }

    private String getUsername() { return tfUsername.getText(); }

    private String getPassword() { return pfPassword.getText(); }

    private String getUserType() { return (String) cbUserType.getValue(); }

    public ArrayList<User> readUsers(){ return new Serializator().deserializeUsers(); }

}
