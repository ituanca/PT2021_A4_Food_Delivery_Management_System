package businessLayer;

import javafx.scene.control.ListView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface IDeliveryServiceProcessing {
    void importProducts() throws IOException;
    void addProduct(String title, double rating, int calories, int protein, int fat, int sodium, int price);
    void deleteProduct(BaseProduct productToDelete);
    void modifyProduct(BaseProduct selectedProduct, String title, double rating, int calories, int protein, int fat, int sodium, int price);
    void createCompositeProduct(ArrayList<MenuItem> listOfMenuItems);
    void generateReports();
    ArrayList<String> viewProducts();
    ArrayList<String> viewMenus();
    ArrayList<String> searchForProduct(String title, double rating, int calories, int protein, int fat, int sodium, int price);
    void createOrder();
}
