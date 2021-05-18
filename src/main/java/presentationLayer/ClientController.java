package presentationLayer;

import businessLayer.DeliveryService;
import businessLayer.SearchingForProduct;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
    public AnchorPane anchorPaneSearchForProduct;
    public Label lblSearchBy;
    public Label lblTitle;
    public Label lblRating;
    public Label lblCalories;
    public Label lblProtein;
    public Label lblFat;
    public Label lblSodium;
    public Label lblPrice;
    public TextField tfTitle;
    public TextField tfRating;
    public TextField tfCalories;
    public TextField tfProtein;
    public TextField tfFat;
    public TextField tfSodium;
    public TextField tfPrice;
    public Button btnSearch;
    public ScrollPane scrollPaneSearchForProduct;
    public ListView listViewSearchForProduct;
    public AnchorPane anchorPaneSearchForMenu;
    public TextField tfTitleMenuItem;
    public TextField tfPriceMenu;
    public Button btnSearchForMenu;
    public ScrollPane scrollPaneSearchForMenu;
    public ListView listViewSearchForMenu;
    public Button btnSearchMenu;
    public AnchorPane anchorPaneCreateOrder;
    public ScrollPane scrollPaneProductsForOrder;
    public ListView listViewProductsForOrder;
    public ScrollPane scrollPaneFinalOrder;
    public ListView listViewFinalOrder;
    public TextField tfOrderPrice;
    public Button btnFinalizeOrder;

    DeliveryService deliveryService = new DeliveryService();

    @Override
    public void create(Stage window, Scene scene) {
        window.setScene(scene);
        window.show();
        nextWindow = window;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listViewProducts.getItems().addAll(deliveryService.viewProducts());
        listViewMenus.getItems().addAll(deliveryService.viewMenus());
    }

    public void viewProducts(ActionEvent actionEvent) {
        scrollPaneProducts.setVisible(true);
        scrollPaneMenus.setVisible(false);
        anchorPaneSearchForProduct.setVisible(false);
        anchorPaneSearchForMenu.setVisible(false);
        anchorPaneCreateOrder.setVisible(false);
    }

    public void viewMenus(ActionEvent actionEvent) {
        scrollPaneProducts.setVisible(false);
        scrollPaneMenus.setVisible(true);
        anchorPaneSearchForProduct.setVisible(false);
        anchorPaneSearchForMenu.setVisible(false);
        anchorPaneCreateOrder.setVisible(false);
    }

    private boolean validate(){
        if( (!tfRating.getText().equals("") && getRating() == -1) || (!tfCalories.getText().equals("") && getCalories() == -1) || (!tfProtein.getText().equals("") && getProtein() == -1) ||
                (!tfFat.getText().equals("") && getFat() == -1) || (!tfSodium.getText().equals("") && getSodium() == -1) || (!tfPrice.getText().equals("") && getPrice() == -1)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid data");
            alert.show();
            return false;
        }
        return true;
    }

    public void searchForProduct(ActionEvent actionEvent) {
        scrollPaneProducts.setVisible(false);
        scrollPaneMenus.setVisible(false);
        anchorPaneSearchForProduct.setVisible(true);
        scrollPaneSearchForProduct.setVisible(false);
        anchorPaneSearchForMenu.setVisible(false);
        anchorPaneCreateOrder.setVisible(false);
    }

    public void search(ActionEvent actionEvent) {
        if (validate()) {
            listViewSearchForProduct.getItems().clear();
            scrollPaneSearchForProduct.setVisible(true);
            listViewSearchForProduct.getItems().addAll(deliveryService.searchForProduct(getTitle(), getRating(), getCalories(), getProtein(), getFat(), getSodium(), getPrice()));
        }
    }

    private boolean validateMenu(){
        if(!tfPriceMenu.getText().equals("") && getPriceMenu() == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid data");
            alert.show();
            return false;
        }
        return true;
    }

    public void searchForMenu(ActionEvent actionEvent) {
        scrollPaneProducts.setVisible(false);
        scrollPaneMenus.setVisible(false);
        anchorPaneSearchForProduct.setVisible(false);
        anchorPaneSearchForMenu.setVisible(true);
        anchorPaneCreateOrder.setVisible(false);
    }

    public void searchMenu(ActionEvent actionEvent) {
        if (validateMenu()) {
            listViewSearchForMenu.getItems().clear();
            scrollPaneSearchForMenu.setVisible(true);
            listViewSearchForMenu.getItems().addAll(deliveryService.searchForMenu(getTitleMenuItem(), getPriceMenu()));
        }
    }

    public void createOrder(ActionEvent actionEvent) {
        scrollPaneProducts.setVisible(false);
        scrollPaneMenus.setVisible(false);
        anchorPaneSearchForProduct.setVisible(false);
        anchorPaneSearchForMenu.setVisible(false);
        anchorPaneCreateOrder.setVisible(true);
        listViewProductsForOrder.getItems().addAll(deliveryService.createMenu());
    }

    // nu se selecteaza
    public void selectProductForOrder(ListView.EditEvent editEvent) {
        listViewFinalOrder.getItems().addAll(deliveryService.createClientOrder(listViewProductsForOrder.getSelectionModel().getSelectedItem().toString()));
    }

    public void finalizeOrder(ActionEvent actionEvent) {
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

    private String getTitleMenuItem() { return tfTitleMenuItem.getText(); }

    private Integer getPriceMenu() {
        try{
            return Integer.parseInt(tfPriceMenu.getText());
        }catch(NumberFormatException nfe){
            return -1;
        }
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        URL url = new File("src\\main\\java\\presentationLayer\\fxmlFiles\\sample.fxml").toURI().toURL();
        Scene scene = new Scene( FXMLLoader.load(url), 800, 640);
        Start.create(nextWindow, scene);
    }


}
