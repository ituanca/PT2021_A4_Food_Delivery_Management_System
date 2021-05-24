package presentationLayer;

import businessLayer.DeliveryService;
import businessLayer.User;
import businessLayer.UserSession;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        ArrayList<User> usersList = readUsers();
        for(User user : usersList){
            if(user.getUsername().equals(getUsername())){
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
            ArrayList<User> usersList = readUsers();
            int id = computeUserID(usersList);

            UserSession.setInstance(null);
            UserSession.getInstance(id, getUsername(), getPassword(), getUserType());

            User user = new User(id, getUsername(), getPassword(), getUserType());
            new FileManager().writeNewUser(user);
            addToList(usersList, Stream.of(user));
            writeUsers(usersList);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("User created successfully");
            alert.show();
            if (getUserType().equals("administrator")) {
                Start.openNextWindow("administrator", new AdministratorController());
            } else if (getUserType().equals("client")) {
                Start.openNextWindow("client", new ClientController());
            } else if (getUserType().equals("employee")){
                Start.openNextWindow("employee", new EmployeeController());
            }
            System.out.println(user.getId());
        }
    }

    private Integer computeUserID(ArrayList<User> usersList){
        int userID = 1;
        for(User ignored : usersList){
            userID++;
        }
        return userID;
    }

    public static<T> void addToList(List<T> target, Stream<T> source) {
        source.collect(Collectors.toCollection(() -> target));
    }

    private String getUsername() { return tfUsername.getText(); }

    private String getPassword() { return pfPassword.getText(); }

    private String getUserType() { return (String) cbUserType.getValue(); }

    public void goBack(ActionEvent actionEvent) throws IOException {
        URL url = new File("src\\main\\java\\presentationLayer\\fxmlFiles\\sample.fxml").toURI().toURL();
        Scene scene = new Scene( FXMLLoader.load(url), 1000, 640);
        Start.create(nextWindow, scene);
    }

     public ArrayList<User> readUsers(){ return new Serializator().deserializeUsers(); }

     private void writeUsers(ArrayList<User> usersList){ new Serializator().serializeUsers(usersList); }

}
