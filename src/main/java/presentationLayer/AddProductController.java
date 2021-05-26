package presentationLayer;

import businessLayer.DeliveryService;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddProductController implements Window{
    public TextField tfTitle;
    public TextField tfRating;
    public TextField tfCalories;
    public TextField tfProtein;
    public TextField tfFat;
    public TextField tfSodium;
    public TextField tfPrice;
    public Button btnAddProduct;
    public Button btnGoBack;

    @Override
    public void create(Stage window, Scene scene) {
        window.setScene(scene);
        window.show();
    }

    private boolean validate(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(getTitle().isEmpty() || tfRating.getText().equals("") || tfCalories.getText().equals("") || tfProtein.getText().equals("") || tfFat.getText().equals("") ||
                tfSodium.getText().equals("") || tfPrice.getText().equals("")){
            alert.setContentText("Please fill in all the fields");
            alert.show();
            return false;
        }
        if(getRating() == -1 || getCalories() == -1 || getProtein() == -1 || getFat() == -1 || getSodium() == -1 || getPrice() == -1 || !getTitle().matches("^(?![\\s.]+$)[a-zA-Z\\s.]*$")){
            alert.setContentText("Invalid data");
            alert.show();
            return false;
        }
        return true;
    }

    public void addProduct(ActionEvent actionEvent) throws IOException {
        if(validate()){
            new DeliveryService().addProduct(getTitle(), getRating(), getCalories(), getProtein(), getFat(), getSodium(), getPrice());
            showConfirmation();
            goToAdministratorOptionsWindow();
        }
    }

    private void showConfirmation(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Product was added successfully");
        alert.show();
    }

    private String getTitle() { return tfTitle.getText(); }

    private Double getRating() {
       try{
           return Double.parseDouble(tfRating.getText());
       }catch(NumberFormatException nfe){
            return (double) -1;
       }
    }

    private Integer getCalories() {
        try{
            return Integer.parseInt(tfCalories.getText());
        }catch(NumberFormatException nfe){
            return -1;
        }
    }

    private Integer getProtein() {
        try{
            return Integer.parseInt(tfProtein.getText());
        }catch(NumberFormatException nfe){
            return -1;
        }
    }

    private Integer getFat() {
        try{
            return Integer.parseInt(tfFat.getText());
        }catch(NumberFormatException nfe){
            return -1;
        }
    }

    private Integer getSodium() {
        try{
            return Integer.parseInt(tfSodium.getText());
        }catch(NumberFormatException nfe){
            return -1;
        }
    }

    private Integer getPrice() {
        try{
            return Integer.parseInt(tfPrice.getText());
        }catch(NumberFormatException nfe){
            return -1;
        }
    }

    public void goBack(ActionEvent actionEvent) throws IOException { goToAdministratorOptionsWindow(); }

    private void goToAdministratorOptionsWindow() throws IOException { Controller.openNextWindow("administrator", new AdministratorController()); }

}
