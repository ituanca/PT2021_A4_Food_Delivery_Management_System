package presentationLayer;

import businessLayer.BaseProduct;
import businessLayer.DeliveryService;
import dataLayer.Serializator;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DeleteProductController implements Initializable, Window {
    public ComboBox cbProduct;
    public Button btnDeleteProduct;
    public Button btnGoBack;

    @Override
    public void create(Stage window, Scene scene) {
        window.setScene(scene);
        window.show();
    }

    private boolean validate() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (cbProduct.getSelectionModel().isEmpty()) {
            alert.setContentText("Please select a product to delete");
            alert.show();
            return false;
        }
        return true;
    }

    public void deleteProduct(ActionEvent actionEvent) throws IOException {
        if (validate()) {
            new DeliveryService().deleteProduct(getSelectedProduct());
            showConfirmation();
            goToAdministratorOptionsWindow();
        }
    }

    private void showConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Product was deleted successfully");
        alert.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbProduct.getItems().addAll(readProducts());
    }

    private List<BaseProduct> readProducts() { return new Serializator().deserializeMenuBaseProducts(); }

    public void goBack(ActionEvent actionEvent) throws IOException { goToAdministratorOptionsWindow(); }

    private void goToAdministratorOptionsWindow() throws IOException { Start.openNextWindow("administrator", new AddProductController()); }

    private BaseProduct getSelectedProduct() { return (BaseProduct) cbProduct.getValue(); }

}
