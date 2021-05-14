package presentationLayer;

import businessLayer.DeliveryService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AdministratorController {
    private static Stage nextWindow;
    public Button btnImport;
    public Button btnAdd;
    public Button btnDelete;
    public Button btnModify;
    public Button btnGenerateReports;
    public Button btnCreate;
    public Button btnGoBack;
    DeliveryService deliveryService = new DeliveryService();

    public static void create(Stage window, Scene scene){
        window.setScene(scene);
        window.show();
        nextWindow = window;
    }

    public void importProducts(ActionEvent actionEvent) throws IOException {
        deliveryService.importProducts();
    }

    public void addProduct(ActionEvent actionEvent) throws IOException {
        URL url = new File("src\\main\\java\\presentationLayer\\fxmlFiles\\addProduct.fxml").toURI().toURL();
        Scene scene = new Scene( FXMLLoader.load(url), 500, 500);
        AddProductController.create(nextWindow, scene);
    }

    public void deleteProduct(ActionEvent actionEvent) throws IOException {
        URL url = new File("src\\main\\java\\presentationLayer\\fxmlFiles\\deleteProduct.fxml").toURI().toURL();
        Scene scene = new Scene( FXMLLoader.load(url), 500, 500);
        DeleteProductController.create(nextWindow, scene);
    }

    public void modifyProduct(ActionEvent actionEvent) throws IOException {
        URL url = new File("src\\main\\java\\presentationLayer\\fxmlFiles\\modifyProduct.fxml").toURI().toURL();
        Scene scene = new Scene( FXMLLoader.load(url), 500, 500);
        AdministratorController.create(nextWindow, scene);
    }

    public void createNewProduct(ActionEvent actionEvent) throws IOException {
        URL url = new File("src\\main\\java\\presentationLayer\\fxmlFiles\\createNewProduct.fxml").toURI().toURL();
        Scene scene = new Scene( FXMLLoader.load(url), 500, 500);
        CreateNewProductController.create(nextWindow, scene);
    }

    public void generateReports(ActionEvent actionEvent) {

    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        URL url = new File("src\\main\\java\\presentationLayer\\fxmlFiles\\sample.fxml").toURI().toURL();
        Scene scene = new Scene( FXMLLoader.load(url), 500, 500);
        Start.create(nextWindow, scene);
    }
}
