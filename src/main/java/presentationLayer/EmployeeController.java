package presentationLayer;

import businessLayer.MenuItem;
import businessLayer.Order;
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
import java.util.*;

public class EmployeeController implements Observer, Window, Initializable {
    private static Stage nextWindow;
    public Button btnViewOrders;
    public ScrollPane scrollPaneOrders;
    public ListView listViewOrders;
    public Button btnGoBack;
    public Label lblRecentOrder;
    public Button btnProcessOrder;
    public Label lblOrderID;

    @Override
    public void create(Stage window, Scene scene) {
        window.setScene(scene);
        window.show();
        nextWindow = window;
    }

    public void viewOrders(ActionEvent actionEvent) {
        lblRecentOrder.setVisible(false);
        scrollPaneOrders.setVisible(true);
        lblOrderID.setVisible(true);
        btnProcessOrder.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listViewOrders.getItems().addAll(convertOrdersListToString(readOrders()));
    }

    @Override
    public void update(Object ordersList) {
        System.out.println(((Map<Order, ArrayList<MenuItem>>) ordersList).size());
    }

    public void setInCaseOfUpdate(){
        lblRecentOrder.setVisible(true);
    }

    public List<String> convertOrdersListToString(Map<Order, ArrayList<MenuItem>> ordersList){
        List<String> stringOrdersList = new ArrayList<>();
        ordersList.forEach((key, value) -> stringOrdersList.add("orderID: " + key.getOrderID() + ", clientID:" + key.getClientID() + ", order date: " + key.getOrderDate()));
        return stringOrdersList;
    }

    public void processOrder(ActionEvent actionEvent) {
        if(validate()){
            Map<Order, ArrayList<MenuItem>> ordersList = readOrders();
            String selectedItem = listViewOrders.getSelectionModel().getSelectedItem().toString();
            String delims = "[ ,]+";
            String[] tokens = selectedItem.split(delims);
            extractOrder(Integer.parseInt(tokens[1]), ordersList);
            writeOrders(ordersList);
            listViewOrders.getItems().addAll(convertOrdersListToString(ordersList));
        }
    }

    private void extractOrder(int id, Map<Order, ArrayList<MenuItem>> ordersList){
        ordersList.forEach( (key, value) -> {
            if(key.getOrderID() == id){
                ordersList.remove(key);
            }
        });
    }

    private boolean validate(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(listViewOrders.getSelectionModel().getSelectedItem() == null){
            alert.setContentText("Select one order to process");
            alert.show();
            return false;
        }
        return true;
    }

    private HashMap<Order, ArrayList<MenuItem>> readOrders(){ return new Serializator().deserializeOrders(); }

    private void writeOrders(Map<Order, ArrayList<MenuItem>> ordersList){ new Serializator().serializeOrders(ordersList); }

    public void goBack(ActionEvent actionEvent) throws IOException {
        URL url = new File("src\\main\\java\\presentationLayer\\fxmlFiles\\sample.fxml").toURI().toURL();
        Scene scene = new Scene( FXMLLoader.load(url), 1000, 640);
        Controller.create(nextWindow, scene);
    }


}
