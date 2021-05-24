package presentationLayer;

import businessLayer.DeliveryService;
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

    @Override
    public void create(Stage window, Scene scene) {
        window.setScene(scene);
        window.show();
        nextWindow = window;
    }

    public void viewOrders(ActionEvent actionEvent) {
        lblRecentOrder.setVisible(false);
        scrollPaneOrders.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listViewOrders.getItems().addAll(convertOrdersListToString(readOrders()));
        setInCaseOfUpdate();
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

    public HashMap<Order, ArrayList<MenuItem>> readOrders(){ return new Serializator().deserializeOrders(); }

    public void goBack(ActionEvent actionEvent) throws IOException {
        URL url = new File("src\\main\\java\\presentationLayer\\fxmlFiles\\sample.fxml").toURI().toURL();
        Scene scene = new Scene( FXMLLoader.load(url), 1000, 640);
        Start.create(nextWindow, scene);
    }

}
