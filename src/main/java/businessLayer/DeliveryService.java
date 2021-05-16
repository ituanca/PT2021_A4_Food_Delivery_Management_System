package businessLayer;

import dataLayer.Serializator;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeliveryService implements IDeliveryServiceProcessing{

    Map<Order, ArrayList<MenuItem>> ordersList = new HashMap<Order, ArrayList<MenuItem>>();
    ArrayList<MenuItem> menu = new ArrayList<MenuItem>();
    List<BaseProduct> menuBaseProducts = new ArrayList<>();

    @Override
    public void importProducts() throws IOException {
        File inputFile = new File("src\\main\\resources\\products.csv");
        InputStream inputFileStream = new FileInputStream(inputFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputFileStream));
        menuBaseProducts = br.lines()
                .skip(1)
                .distinct()
                .map(mapToItem)
                .collect(Collectors.toList());
        br.close();
        new Serializator().serializeMenuBaseProducts(menuBaseProducts);
//        List<BaseProduct> menuBaseProductsAfterDeserialization = new ArrayList<>();
//        menuBaseProductsAfterDeserialization = new Serializator().deserializeMenuBaseProducts();
//        for(BaseProduct baseProduct : menuBaseProductsAfterDeserialization){
//            System.out.println(baseProduct);
//        }
    }

    private final Function<String, BaseProduct> mapToItem = (line) -> {
        String[] p = line.split(",");
        return new BaseProduct(p[0], Double.parseDouble(p[1]), Integer.parseInt(p[2]), Integer.parseInt(p[3]), Integer.parseInt(p[4]), Integer.parseInt(p[5]), Integer.parseInt(p[6]));
    };

    private void printMenu(List<BaseProduct> menuBaseProducts){
        int i = 1;
        for(BaseProduct baseProduct : menuBaseProducts) {
            System.out.println("id: " + i + " title: " + baseProduct.title + " rating: " + baseProduct.rating + " calories: " + baseProduct.calories +
                    " protein: " + baseProduct.protein + " fat: " + baseProduct.fat + " sodium: " + baseProduct.sodium +  " price: " + baseProduct.price);
            i++;
        }
    }

    private void printItem(BaseProduct item) {
        System.out.println("title: " + item.title + " rating: " + item.rating + " calories: " + item.calories +
                " protein: " + item.protein + " fat: " + item.fat + " sodium: " + item.sodium + " price: " + item.price);
    }

    @Override
    public void addProduct(String title, double rating, int calories, int protein, int fat, int sodium, int price) {
        BaseProduct item = new BaseProduct(title, rating, calories, protein, fat, sodium, price);
        addToList(menuBaseProducts, Stream.of(item));
        printItem(item);
    }

    public static<T> void addToList(List<T> target, Stream<T> source) {
        source.collect(Collectors.toCollection(() -> target));
    }

    @Override
    public void deleteProduct() {

    }

    @Override
    public void modifyProduct() {

    }

    @Override
    public void createComposedProduct() {

    }

    @Override
    public void generateReports() {

    }

    @Override
    public void viewMenu() {

    }

    @Override
    public void searchForProducts() {

    }

    @Override
    public void createOrder() {

    }
}
