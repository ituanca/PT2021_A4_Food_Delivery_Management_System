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
        //printMenu(menuBaseProducts);
        Serializator serializer = new Serializator();
        serializer.serializeMenuBaseProducts(menuBaseProducts);
        serializer.deserializeMenuBaseProducts();
    }

    private void printMenu(List<BaseProduct> menuBaseProducts){
        int i = 1;
        for(BaseProduct baseProduct : menuBaseProducts) {
            System.out.println("id: " + i + " title: " + baseProduct.title + " rating: " + baseProduct.rating + " calories: " + baseProduct.calories +
                    " protein: " + baseProduct.protein + " fat: " + baseProduct.fat + " sodium: " + baseProduct.sodium +  " price: " + baseProduct.price);
            i++;
        }
    }

    private void printItem(BaseProduct item) {
        System.out.println("title: " + item.title + " rating: " + item.rating + " calories: " + item.calories + " protein: " + item.protein + " fat: " + item.fat + " sodium: " + item.sodium + " price: " + item.price);
    }

    private final Function<String, BaseProduct> mapToItem = (line) -> {
        String[] p = line.split(",");
        BaseProduct item = new BaseProduct();
        item.setTitle(p[0]);
        item.setRating(Double.parseDouble(p[1]));
        item.setCalories(Integer.parseInt(p[2]));
        item.setProtein(Integer.parseInt(p[3]));
        item.setFat(Integer.parseInt(p[4]));
        item.setSodium(Integer.parseInt(p[5]));
        item.setPrice(Integer.parseInt(p[6]));
        return item;
    };

    @Override
    public void addProduct(String title, double rating, int calories, int protein, int fat, int sodium, int price) {
        BaseProduct item = new BaseProduct();
        item.setTitle(title);
        item.setRating(rating);
        item.setCalories(calories);
        item.setProtein(protein);
        item.setFat(fat);
        item.setSodium(sodium);
        item.setPrice(price);
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
