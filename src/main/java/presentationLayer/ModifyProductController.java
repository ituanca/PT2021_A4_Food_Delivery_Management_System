package presentationLayer;

import businessLayer.BaseProduct;
import dataLayer.Serializator;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {
    private static Stage nextWindow;
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

    public static void create(Stage window, Scene scene){
        window.setScene(scene);
        window.show();
        nextWindow = window;
    }

    public void modifyProduct(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbProduct.getItems().addAll(getProducts());
    }

    public void selectProduct(ActionEvent actionEvent) {
        BaseProduct product = getSelectedProduct();
        lblTitle.setVisible(true);
        tfTitle.setVisible(true);
        tfTitle.setText(product.title);
        lblRating.setVisible(true);
        tfRating.setVisible(true);
        tfRating.setText(String.valueOf(product.rating));
        lblCalories.setVisible(true);
        tfCalories.setVisible(true);
        tfCalories.setText(String.valueOf(product.calories));
        lblProtein.setVisible(true);
        tfProtein.setVisible(true);
        tfProtein.setText(String.valueOf(product.protein));
        lblFat.setVisible(true);
        tfFat.setVisible(true);
        tfFat.setText(String.valueOf(product.fat));
        lblSodium.setVisible(true);
        tfSodium.setVisible(true);
        tfSodium.setText(String.valueOf(product.sodium));
        lblPrice.setVisible(true);
        tfPrice.setVisible(true);
        tfPrice.setText(String.valueOf(product.price));
        btnModifyProduct.setVisible(true);
    }

    private List<BaseProduct> getProducts(){
        return new Serializator().deserializeMenuBaseProducts();
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        goToAdministratorOptionsWindow();
    }

    private void goToAdministratorOptionsWindow() throws IOException {
        URL url = new File("src\\main\\java\\presentationLayer\\fxmlFiles\\administrator.fxml").toURI().toURL();
        Scene scene = new Scene( FXMLLoader.load(url), 500, 500);
        AdministratorController.create(nextWindow, scene);
    }

    private BaseProduct getSelectedProduct() { return (BaseProduct) cbProduct.getValue(); }


}
