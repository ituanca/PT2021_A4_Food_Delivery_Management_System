package presentationLayer;

import businessLayer.BaseProduct;
import businessLayer.CompositeProduct;
import businessLayer.DeliveryService;
import dataLayer.Serializator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientController implements Window, Initializable {
    private static Stage nextWindow;
    public Button btnGoBack;
    public Button btnViewProducts;
    public Button btnViewMenus;
    public Button btnSearchForProduct;
    public Button btnCreateOrder;
    public ScrollPane scrollPaneProducts;
    public ListView listViewProducts;
    public ScrollPane scrollPaneMenus;
    public ListView listViewMenus;

    DeliveryService deliveryService = new DeliveryService();

    @Override
    public void create(Stage window, Scene scene) {
        window.setScene(scene);
        window.show();
        nextWindow = window;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeListViewProducts();
        initializeListViewMenus();
    }

    private void initializeListViewProducts(){
        ArrayList<String> stringProducts = new ArrayList<>();
        for(BaseProduct baseProduct: deliveryService.readBaseProducts()){
            stringProducts.add(baseProduct.toString());
        }
        listViewProducts.getItems().addAll(stringProducts);
    }

    private void initializeListViewMenus(){
        ArrayList<String> stringMenus = new ArrayList<>();
        for(CompositeProduct compositeProduct: deliveryService.readCompositeProducts()){
            stringMenus.add(compositeProduct.toString());
        }
        listViewMenus.getItems().addAll(stringMenus);
    }

    public void viewProducts(ActionEvent actionEvent) {
        scrollPaneProducts.setVisible(true);
        listViewProducts.setVisible(true);
        scrollPaneMenus.setVisible(false);
        listViewMenus.setVisible(false);
    }

    public void viewMenus(ActionEvent actionEvent) {
        scrollPaneProducts.setVisible(false);
        listViewProducts.setVisible(false);
        scrollPaneMenus.setVisible(true);
        listViewMenus.setVisible(true);
    }

    public void searchForProduct(ActionEvent actionEvent) {
    }

    public void createOrder(ActionEvent actionEvent) {
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        URL url = new File("src\\main\\java\\presentationLayer\\fxmlFiles\\sample.fxml").toURI().toURL();
        Scene scene = new Scene( FXMLLoader.load(url), 800, 500);
        Start.create(nextWindow, scene);
    }

}
