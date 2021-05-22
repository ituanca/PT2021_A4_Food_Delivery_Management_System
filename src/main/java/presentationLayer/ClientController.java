package presentationLayer;

import businessLayer.DeliveryService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClientController implements Window, Initializable {
    private static Stage nextWindow;
    public Button btnViewProducts;
    public Button btnViewMenus;
    public Button btnOpenCreateOrderWindow;
    public Button btnGoBack;
    public ScrollPane scrollPaneProducts;
    public ListView listViewProducts;
    public ScrollPane scrollPaneMenus;
    public ListView listViewMenus;
    public AnchorPane anchorPaneCreateOrder;
    public ScrollPane scrollPaneFinalOrder;
    public ListView listViewFinalOrder;
    public TextField tfOrderPrice;
    public Button btnFinalizeOrder;
    public Button btnSearchForProduct;
    public Button btnSearchForMenu;
    public AnchorPane anchorPaneSearchForProduct;
    public TextField tfTitleSearchForProduct;
    public TextField tfRating;
    public TextField tfCalories;
    public TextField tfProtein;
    public TextField tfFat;
    public TextField tfSodium;
    public TextField tfPriceSearchForProduct;
    public ScrollPane scrollPaneSearchForProduct;
    public ListView listViewSearchForProduct;
    public AnchorPane anchorPaneSearchForMenu;
    public ScrollPane scrollPaneSearchForMenu;
    public ListView listViewSearchForMenu;
    public TextField tfTitleMenuItem;
    public TextField tfPriceMenu;

    public Button btnSearchForProductInCreateOrder;
    public Button btnSearchForMenuInCreateOrder;
    public Label lblChooseSomeProducts;
    public Label lblChooseSomeMenus;
    public Button btnDeleteSelection;

    DeliveryService deliveryService = new DeliveryService();
    ArrayList<String> selectedProducts = new ArrayList<>();

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
        anchorPaneCreateOrder.setVisible(false);
    }

    public void viewMenus(ActionEvent actionEvent) {
        scrollPaneProducts.setVisible(false);
        scrollPaneMenus.setVisible(true);
        anchorPaneCreateOrder.setVisible(false);
    }

    public void openCreateOrderWindow(ActionEvent actionEvent) {
        scrollPaneProducts.setVisible(false);
        scrollPaneMenus.setVisible(false);
        anchorPaneCreateOrder.setVisible(true);
        anchorPaneSearchForProduct.setVisible(false);
        anchorPaneSearchForMenu.setVisible(false);
    }

    public void openSearchForProductWindow(ActionEvent actionEvent) {
        scrollPaneProducts.setVisible(false);
        scrollPaneMenus.setVisible(false);
        anchorPaneSearchForProduct.setVisible(true);
        lblChooseSomeProducts.setVisible(false);
        btnDeleteSelection.setVisible(false);
        scrollPaneSearchForProduct.setVisible(false);
        anchorPaneSearchForMenu.setVisible(false);
        selectMenuItem(listViewSearchForProduct);
    }

    private boolean validateSearchForProduct(){
        if( (!tfRating.getText().equals("") && getRating() == -1) || (!tfCalories.getText().equals("") && getCalories() == -1) || (!tfProtein.getText().equals("") && getProtein() == -1) ||
                (!tfFat.getText().equals("") && getFat() == -1) || (!tfSodium.getText().equals("") && getSodium() == -1) || (!tfPriceSearchForProduct.getText().equals("") && getPriceSearchForProduct() == -1)){
            showAlert(Alert.AlertType.ERROR, "Invalid data");
            return false;
        }
        return true;
    }

    public void searchForProductInCreateOrder(ActionEvent actionEvent) {
        if (validateSearchForProduct()) {
            listViewSearchForProduct.getItems().clear();
            lblChooseSomeProducts.setVisible(true);
            btnDeleteSelection.setVisible(true);
            scrollPaneSearchForProduct.setVisible(true);
            listViewSearchForProduct.getItems().addAll(deliveryService.searchForProduct(getTitleSearchForProduct(), getRating(), getCalories(), getProtein(), getFat(), getSodium(), getPriceSearchForProduct()));
        }
    }

    public void openSearchForMenuWindow(ActionEvent actionEvent) {
        scrollPaneProducts.setVisible(false);
        scrollPaneMenus.setVisible(false);
        anchorPaneSearchForProduct.setVisible(false);
        anchorPaneSearchForMenu.setVisible(true);
        lblChooseSomeMenus.setVisible(false);
        btnDeleteSelection.setVisible(false);
        scrollPaneSearchForMenu.setVisible(false);
        selectMenuItem(listViewSearchForMenu);
    }

    private boolean validateMenu(){
        if(!tfPriceMenu.getText().equals("") && getPriceMenu() == -1){
            showAlert(Alert.AlertType.ERROR, "Invalid data");
            return false;
        }
        return true;
    }

    public void searchForMenuInCreateOrder(ActionEvent actionEvent) {
        if (validateMenu()) {
            listViewSearchForMenu.getItems().clear();
            lblChooseSomeProducts.setVisible(true);
            btnDeleteSelection.setVisible(true);
            scrollPaneSearchForMenu.setVisible(true);
            listViewSearchForMenu.getItems().addAll(deliveryService.searchForMenu(getTitleMenuItem(), getPriceMenu()));
        }
    }

    public void selectMenuItem(ListView listView){
        listView.setOnMouseClicked(new ListViewHandler(){
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(listView.getSelectionModel().getSelectedItem() != null) {
                    listViewFinalOrder.getItems().add(listView.getSelectionModel().getSelectedItem());
                    deliveryService.addSelectedProductToArrayOfStrings(selectedProducts, listView.getSelectionModel().getSelectedItem().toString());
                    if (listViewFinalOrder.getItems().size() > 0) {
                        tfOrderPrice.setText(String.valueOf(deliveryService.computeOrderPrice(deliveryService.createArrayOfProductsOfOrder(selectedProducts))));
                    }
                }
            }
        });
    }

    public void deleteSelection(ActionEvent actionEvent) {
        deliveryService.createTheEntireMenu();
        if(listViewFinalOrder.getItems().size() > 0 && selectedProducts.size() > 0){
            listViewFinalOrder.getItems().remove(listViewFinalOrder.getItems().size() - 1);
            selectedProducts.remove(selectedProducts.size() - 1);
            tfOrderPrice.setText(String.valueOf(deliveryService.computeOrderPrice(deliveryService.createArrayOfProductsOfOrder(selectedProducts))));
        }
    }

    private boolean validateOrder(){
        if(listViewFinalOrder.getItems().size() == 0){
            showAlert(Alert.AlertType.ERROR, "Please select some products for your order");
            return false;
        }
        return true;
    }

    public void finalizeOrder(ActionEvent actionEvent) {
        if(validateOrder()){
            deliveryService.createOrder(deliveryService.createArrayOfProductsOfOrder(selectedProducts));
            showAlert(Alert.AlertType.CONFIRMATION, "Order was created successfully");
        }
    }

    private void showAlert(Alert.AlertType alertType, String message){
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
    }

    private String getTitleSearchForProduct() { return tfTitleSearchForProduct.getText(); }

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

    private Integer getPriceSearchForProduct() {
        try{
            return Integer.parseInt(tfPriceSearchForProduct.getText());
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
        Scene scene = new Scene( FXMLLoader.load(url), 1000, 640);
        Start.create(nextWindow, scene);
    }

}
