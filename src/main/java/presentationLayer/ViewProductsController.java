package presentationLayer;

import businessLayer.DeliveryService;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewProductsController implements Window, Initializable {
    public Button btnViewBaseProducts;
    public Button btnViewMenus;
    public ScrollPane scrollPaneProducts;
    public ListView listViewProducts;
    public ScrollPane scrollPaneMenus;
    public ListView listViewMenus;
    public Button btnGoBack;

    DeliveryService deliveryService = new DeliveryService();

    @Override
    public void create(Stage window, Scene scene) {
        window.setScene(scene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listViewProducts.getItems().addAll(deliveryService.viewProducts());
        listViewMenus.getItems().addAll(deliveryService.viewMenus());
    }

    public void viewBaseProducts(ActionEvent actionEvent) {
        scrollPaneProducts.setVisible(true);
        scrollPaneMenus.setVisible(false);
    }

    public void viewMenus(ActionEvent actionEvent) {
        scrollPaneProducts.setVisible(false);
        scrollPaneMenus.setVisible(true);
    }

    public void goBack(ActionEvent actionEvent) throws IOException { goToAdministratorOptionsWindow(); }

    private void goToAdministratorOptionsWindow() throws IOException { Start.openNextWindow("administrator", new AdministratorController()); }

}
