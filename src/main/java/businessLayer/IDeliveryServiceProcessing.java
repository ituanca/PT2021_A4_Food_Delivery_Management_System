package businessLayer;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IDeliveryServiceProcessing {
    void importProducts() throws IOException;
    void addProduct(String title, double rating, int calories, int protein, int fat, int sodium, int price);
    void deleteProduct();
    void modifyProduct();
    void createComposedProduct();
    void generateReports();
    void viewMenu();
    void searchForProducts();
    void createOrder();
}
