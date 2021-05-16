package presentationLayer;

import businessLayer.User;
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

public class LogIn implements Initializable {
    private static Stage nextWindow;
    public ComboBox cbUserType;
    public TextField tfUsername;
    public PasswordField pfPassword;
    public Button btnLogIn;
    public Button btnGoBack;

    public static void create(Stage window, Scene scene){
        window.setScene(scene);
        window.show();
        nextWindow = window;
    }

    private boolean checkUserExistence(){
        try {
            FileReader file = new FileReader("src\\main\\resources\\users.txt");
            BufferedReader br = new BufferedReader(file);
            int line = 1;
            boolean foundUsername = false, correctPassword = false, correctUserType = false;
            String data = br.readLine();
            while (data != null) {
                if (line % 3 == 1 && getUsername().equals(data)) {
                    foundUsername = true;
                } else {
                    if(line % 3 == 2 && foundUsername){
                        if (getPassword().equals(data)) {
                            correctPassword = true;
                        }
                    }else{
                        if(foundUsername && correctPassword && getUserType().equals(data)){
                            correctUserType = true;
                        }
                    }
                }
                if (foundUsername && correctPassword && correctUserType) {
                    return true;
                }
                line++;
                data = br.readLine();
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkUserExistenceSerialization(){
        Serializator serializer = new Serializator();
        ArrayList<User> users  = serializer.deserializeUsers();
        for(User user: users){
            if(getUsername().equals(user.getUsername()) && getPassword().equals(user.getPassword()) && getUserType().equals(user.getUserType())){
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
        if(!checkUserExistenceSerialization()){
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
            if (getUserType().equals("administrator")) {
                URL url = new File("src\\main\\java\\presentationLayer\\fxmlFiles\\administrator.fxml").toURI().toURL();
                Scene scene = new Scene(FXMLLoader.load(url), 500, 500);
                AdministratorController.create(nextWindow, scene);
            } else if (getUserType().equals("client")) {
                URL url = new File("src\\main\\java\\presentationLayer\\fxmlFiles\\client.fxml").toURI().toURL();
                Scene scene = new Scene(FXMLLoader.load(url), 500, 500);
                ClientController.create(nextWindow, scene);
            }
        }
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        URL url = new File("src\\main\\java\\presentationLayer\\fxmlFiles\\sample.fxml").toURI().toURL();
        Scene scene = new Scene( FXMLLoader.load(url), 500, 500);
        Start.create(nextWindow, scene);
    }

    private String getUsername() { return tfUsername.getText(); }

    private String getPassword() { return pfPassword.getText(); }

    private String getUserType() { return (String) cbUserType.getValue(); }
}
