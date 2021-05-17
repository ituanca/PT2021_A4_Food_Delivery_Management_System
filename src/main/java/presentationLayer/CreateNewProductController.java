package presentationLayer;

import businessLayer.BaseProduct;
import businessLayer.DeliveryService;
import businessLayer.MenuItem;
import dataLayer.Serializator;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreateNewProductController implements Initializable, Window {

    public ComboBox cbNoOfDishes;
    public Label lblDish1;
    public Label lblDish2;
    public Label lblDish3;
    public Label lblDish4;
    public ComboBox cbDish1;
    public ComboBox cbDish2;
    public ComboBox cbDish3;
    public ComboBox cbDish4;
    public Button btnCreateProduct;
    public Button btnGoBack;

    ArrayList<MenuItem> listOfMenuItems = new ArrayList<>();

    @Override
    public void create(Stage window, Scene scene) {
        window.setScene(scene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbNoOfDishes.getItems().addAll("2", "3", "4");
        cbDish1.getItems().addAll(readProducts());
        cbDish2.getItems().addAll(readProducts());
        cbDish3.getItems().addAll(readProducts());
        cbDish4.getItems().addAll(readProducts());
    }

    public void selectNoOfDishes(ActionEvent actionEvent) {
        if(getSelectedNoOfDishes() == 2){
            lblDish1.setVisible(true);
            cbDish1.setVisible(true);
            lblDish2.setVisible(true);
            cbDish2.setVisible(true);
            lblDish3.setVisible(false);
            cbDish3.setVisible(false);
            lblDish4.setVisible(false);
            cbDish4.setVisible(false);
        }
        if(getSelectedNoOfDishes() == 3){
            lblDish1.setVisible(true);
            cbDish1.setVisible(true);
            lblDish2.setVisible(true);
            cbDish2.setVisible(true);
            lblDish3.setVisible(true);
            cbDish3.setVisible(true);
            lblDish4.setVisible(false);
            cbDish4.setVisible(false);
        }
        if(getSelectedNoOfDishes() == 4){
            lblDish1.setVisible(true);
            cbDish1.setVisible(true);
            lblDish2.setVisible(true);
            cbDish2.setVisible(true);
            lblDish3.setVisible(true);
            cbDish3.setVisible(true);
            lblDish4.setVisible(true);
            cbDish4.setVisible(true);
        }
        btnCreateProduct.setVisible(true);
    }

    private boolean validate() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(getSelectedNoOfDishes() == 2 && (cbDish1.getSelectionModel().isEmpty() || cbDish2.getSelectionModel().isEmpty())){
            alert.setContentText("Please select 2 dishes to create the menu");
            alert.show();
            return false;
        }
        if(getSelectedNoOfDishes() == 3 && (cbDish1.getSelectionModel().isEmpty() || cbDish2.getSelectionModel().isEmpty() || cbDish3.getSelectionModel().isEmpty())){
            alert.setContentText("Please select 3 dishes to create the menu");
            alert.show();
            return false;
        }
        if(getSelectedNoOfDishes() == 4 && (cbDish1.getSelectionModel().isEmpty() || cbDish2.getSelectionModel().isEmpty() || cbDish3.getSelectionModel().isEmpty() || cbDish4.getSelectionModel().isEmpty())){
            alert.setContentText("Please select 4 dishes to create the menu");
            alert.show();
            return false;
        }
        return true;
    }

    private boolean validateSelectedDishFor2DishesMenu(){
        BaseProduct selectedDish1 = getSelectedProduct(cbDish1);
        BaseProduct selectedDish2 = getSelectedProduct(cbDish2);
        if (selectedDish1.toString().equals(selectedDish2.toString())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select different products");
            alert.show();
            return false;
        }
        return true;
    }

    private boolean validateSelectedDishFor3DishesMenu(){
        BaseProduct selectedDish1 = getSelectedProduct(cbDish1);
        BaseProduct selectedDish2 = getSelectedProduct(cbDish2);
        BaseProduct selectedDish3 = getSelectedProduct(cbDish3);
        if (selectedDish1.toString().equals(selectedDish2.toString()) ||
                selectedDish1.toString().equals(selectedDish3.toString()) || selectedDish2.toString().equals(selectedDish3.toString())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select different products");
            alert.show();
            return false;
        }
        return true;
    }

    private boolean validateSelectedDishFor4DishesMenu(){
        BaseProduct selectedDish1 = getSelectedProduct(cbDish1);
        BaseProduct selectedDish2 = getSelectedProduct(cbDish2);
        BaseProduct selectedDish3 = getSelectedProduct(cbDish3);
        BaseProduct selectedDish4 = getSelectedProduct(cbDish4);
        if (selectedDish1.toString().equals(selectedDish2.toString()) || selectedDish1.toString().equals(selectedDish3.toString()) || selectedDish1.toString().equals(selectedDish4.toString()) ||
                selectedDish2.toString().equals(selectedDish3.toString()) || selectedDish2.toString().equals(selectedDish4.toString()) || selectedDish3.toString().equals(selectedDish4.toString())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select different products");
            alert.show();
            return false;
        }
        return true;
    }

    public void createProduct(ActionEvent actionEvent) throws IOException {
        if(validate()){
            if(getSelectedNoOfDishes() == 2  && validateSelectedDishFor2DishesMenu()) {
                listOfMenuItems.add(getSelectedProduct(cbDish1));
                listOfMenuItems.add(getSelectedProduct(cbDish2));
                addNewProductToMenu();
            }
            if(getSelectedNoOfDishes() == 3 && validateSelectedDishFor3DishesMenu()) {
                listOfMenuItems.add(getSelectedProduct(cbDish1));
                listOfMenuItems.add(getSelectedProduct(cbDish2));
                listOfMenuItems.add(getSelectedProduct(cbDish3));
                addNewProductToMenu();
            }
            if(getSelectedNoOfDishes() == 4 && validateSelectedDishFor4DishesMenu()) {
                listOfMenuItems.add(getSelectedProduct(cbDish1));
                listOfMenuItems.add(getSelectedProduct(cbDish2));
                listOfMenuItems.add(getSelectedProduct(cbDish3));
                listOfMenuItems.add(getSelectedProduct(cbDish4));
                addNewProductToMenu();
            }
        }
    }

    private void addNewProductToMenu() throws IOException {
        new DeliveryService().createCompositeProduct(listOfMenuItems);
        showConfirmation();
        goToAdministratorOptionsWindow();
    }

    private void showConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("New daily menu was created successfully");
        alert.show();
    }

    private List<BaseProduct> readProducts() { return new Serializator().deserializeMenuBaseProducts(); }

    public void goBack(ActionEvent actionEvent) throws IOException { goToAdministratorOptionsWindow(); }

    private void goToAdministratorOptionsWindow() throws IOException { Start.openNextWindow("administrator", new AddProductController()); }

    private Integer getSelectedNoOfDishes() { return Integer.parseInt((String) cbNoOfDishes.getValue()); }

    private BaseProduct getSelectedProduct(ComboBox cb) { return (BaseProduct) cb.getValue(); }

}
