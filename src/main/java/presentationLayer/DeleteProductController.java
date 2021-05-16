package presentationLayer;

import businessLayer.BaseProduct;
import dataLayer.Serializator;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DeleteProductController implements Initializable {
    private static Stage nextWindow;
    public ComboBox cbProduct;
    public Button btnDeleteProduct;
    public Button btnGoBack;

    public static void create(Stage window, Scene scene){
        window.setScene(scene);
        window.show();
        nextWindow = window;
    }

    private boolean validate(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(cbProduct.getSelectionModel().isEmpty()){
            alert.setContentText("Please select a product to delete");
            alert.show();
            return false;
        }
        return true;
    }

    public void deleteProduct(ActionEvent actionEvent) throws IOException {
        if(validate()){
            List<BaseProduct> menuBaseProducts;
            menuBaseProducts = getProducts();
            menuBaseProducts.removeIf(product -> product.toString().equals(getSelectedProduct().toString()));
            new Serializator().serializeMenuBaseProducts(menuBaseProducts);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Product was deleted successfully");
            alert.show();
            URL url = new File("src\\main\\java\\presentationLayer\\fxmlFiles\\administrator.fxml").toURI().toURL();
            Scene scene = new Scene( FXMLLoader.load(url), 500, 500);
            AdministratorController.create(nextWindow, scene);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbProduct.getItems().addAll(getProducts());
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        URL url = new File("src\\main\\java\\presentationLayer\\fxmlFiles\\administrator.fxml").toURI().toURL();
        Scene scene = new Scene( FXMLLoader.load(url), 500, 500);
        AdministratorController.create(nextWindow, scene);
    }

    private List<BaseProduct> getProducts(){
        return new Serializator().deserializeMenuBaseProducts();
    }

    private BaseProduct getSelectedProduct() { return (BaseProduct) cbProduct.getValue(); }
}
