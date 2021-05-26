package presentationLayer;

import businessLayer.DeliveryService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AdministratorController implements Window{
    private static Stage nextWindow;
    public Button btnImport;
    public Button btnAdd;
    public Button btnDelete;
    public Button btnModify;
    public Button btnViewProducts;
    public Button btnGenerateReports;
    public Button btnCreate;
    public Button btnGoBack;

    DeliveryService deliveryService = new DeliveryService();

    @Override
    public void create(Stage window, Scene scene) {
        window.setScene(scene);
        window.show();
        nextWindow = window;
    }

    public void importProducts(ActionEvent actionEvent) throws IOException {
        deliveryService.importProducts();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Data was imported successfully");
        alert.show();
    }

    public void addProduct(ActionEvent actionEvent) throws IOException {
        Controller.openNextWindow("addProduct", new AddProductController());
    }

    public void deleteProduct(ActionEvent actionEvent) throws IOException {
        Controller.openNextWindow("deleteProduct", new DeleteProductController());
    }

    public void modifyProduct(ActionEvent actionEvent) throws IOException {
        Controller.openNextWindow("modifyProduct", new ModifyProductController());
    }

    public void createNewProduct(ActionEvent actionEvent) throws IOException {
        Controller.openNextWindow("createNewProduct", new CreateNewProductController());
    }

    public void viewProducts(ActionEvent actionEvent) throws IOException {
        Controller.openNextWindow("viewProducts", new ViewProductsController());
    }

    public void generateReports(ActionEvent actionEvent) throws IOException {
        Controller.openNextWindow("generateReports", new GenerateReportsController());
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        URL url = new File("src\\main\\java\\presentationLayer\\fxmlFiles\\sample.fxml").toURI().toURL();
        Scene scene = new Scene( FXMLLoader.load(url), 1000, 640);
        Controller.create(nextWindow, scene);
    }


}
