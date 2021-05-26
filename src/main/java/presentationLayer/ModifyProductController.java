package presentationLayer;

import businessLayer.BaseProduct;
import businessLayer.DeliveryService;
import dataLayer.Serializator;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable, Window {
    public ComboBox cbProduct;
    public TextField tfTitle;
    public TextField tfRating;
    public TextField tfCalories;
    public TextField tfProtein;
    public TextField tfFat;
    public TextField tfSodium;
    public TextField tfPrice;
    public Button btnModifyProduct;
    public Button btnGoBack;
    public Label lblTitle;
    public Label lblRating;
    public Label lblCalories;
    public Label lblProtein;
    public Label lblFat;
    public Label lblSodium;
    public Label lblPrice;

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
        if(getRating() == -1 || getCalories() == -1 || getProtein() == -1 || getFat() == -1 || getSodium() == -1 || getPrice() == -1){
            alert.setContentText("Invalid data");
            alert.show();
            return false;
        }
        return true;
    }

    public void modifyProduct(ActionEvent actionEvent) throws IOException {
        if(validate()){
            new DeliveryService().modifyProduct(getSelectedProduct(), getTitle(), getRating(), getCalories(), getProtein(), getFat(), getSodium(), getPrice());
            showConfirmation();
            goToAdministratorOptionsWindow();
        }
    }

    private void showConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Product was modified successfully");
        alert.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbProduct.getItems().addAll(getProducts());
    }

    public void selectProduct(ActionEvent actionEvent) {
        BaseProduct product = getSelectedProduct();
        setLabelAndTextField(lblTitle, tfTitle, product.title);
        setLabelAndTextField(lblRating, tfRating, String.valueOf(product.rating));
        setLabelAndTextField(lblCalories, tfCalories, String.valueOf(product.calories));
        setLabelAndTextField(lblProtein,tfProtein,String.valueOf(product.protein));
        setLabelAndTextField(lblFat,tfFat,String.valueOf(product.fat));
        setLabelAndTextField(lblSodium, tfSodium, String.valueOf(product.sodium));
        setLabelAndTextField(lblPrice, tfPrice, String.valueOf(product.price));
        btnModifyProduct.setVisible(true);
    }

    private void setLabelAndTextField(Label lbl, TextField tf, String string){
        lbl.setVisible(true);
        tf.setVisible(true);
        tf.setText(string);
    }

    private List<BaseProduct> getProducts(){
        return new Serializator().deserializeMenuBaseProducts();
    }

    public void goBack(ActionEvent actionEvent) throws IOException { goToAdministratorOptionsWindow(); }

    private void goToAdministratorOptionsWindow() throws IOException { Controller.openNextWindow("administrator", new AdministratorController()); }

    private BaseProduct getSelectedProduct() { return (BaseProduct) cbProduct.getValue(); }

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
}
