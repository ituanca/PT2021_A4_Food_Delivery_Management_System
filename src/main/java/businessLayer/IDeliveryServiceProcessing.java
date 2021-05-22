package businessLayer;

import javafx.scene.control.ListView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface IDeliveryServiceProcessing {
    void importProducts() throws IOException;
    void addProduct(String title, double rating, int calories, int protein, int fat, int sodium, int price);
    void deleteProduct(BaseProduct productToDelete);
    void modifyProduct(BaseProduct selectedProduct, String title, double rating, int calories, int protein, int fat, int sodium, int price);
    void createCompositeProduct(ArrayList<MenuItem> listOfMenuItems);
    List<String> generateReportOfOrders(String startHour, String endHour);
    List<String> generateReportOfProducts(int noOfTimes);
    List<String> generateReportOfClients(int noOfClients, int amountOfOrder);
    ArrayList<String> viewProducts();
    ArrayList<String> viewMenus();
    ArrayList<String> searchForProduct(String title, double rating, int calories, int protein, int fat, int sodium, int price);
    ArrayList<String> searchForMenu(String itemTitle, int price);
    void createOrder(ArrayList<MenuItem> selectedProducts);
}
