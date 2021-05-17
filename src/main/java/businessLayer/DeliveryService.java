package businessLayer;

import dataLayer.Serializator;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeliveryService implements IDeliveryServiceProcessing{

    Map<Order, ArrayList<MenuItem>> ordersList = new HashMap<Order, ArrayList<MenuItem>>();     // stores the order related information
    List<MenuItem> menu = new ArrayList<MenuItem>();                          // saves the menu (all the products) provided by the catering company
    List<BaseProduct> menuBaseProducts = new ArrayList<>();            // saves the base products from the menu
    List<CompositeProduct> menuCompositeProducts = new ArrayList<>();  // saves the composite products from the menu

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
        writeBaseProducts();
        br.close();
    }

    private final Function<String, BaseProduct> mapToItem = (line) -> {
        String[] p = line.split(",");
        return new BaseProduct(p[0], Double.parseDouble(p[1]), Integer.parseInt(p[2]), Integer.parseInt(p[3]), Integer.parseInt(p[4]), Integer.parseInt(p[5]), Integer.parseInt(p[6]));
    };

    private void printMenuBaseProducts(){
        int i = 1;
        for(BaseProduct baseProduct : menuBaseProducts) {
            System.out.println("id: " + i + " title: " + baseProduct.title + " rating: " + baseProduct.rating + " calories: " + baseProduct.calories +
                    " protein: " + baseProduct.protein + " fat: " + baseProduct.fat + " sodium: " + baseProduct.sodium +  " price: " + baseProduct.price);
            i++;
        }
    }

    private void printCompositeProducts(){
        int productNo = 1;
        menuCompositeProducts = readCompositeProducts();
        for(CompositeProduct compositeProduct : menuCompositeProducts) {
            System.out.println("title: " + compositeProduct.title + " price: " + compositeProduct.price);
            int dishNo = 1;
            for(MenuItem menuItem: compositeProduct.listOfMenuItems){
                System.out.println("    dish " + dishNo + ": " + menuItem.toString());
                dishNo++;
            }
            productNo++;
        }
    }

    @Override
    public void addProduct(String title, double rating, int calories, int protein, int fat, int sodium, int price) {
        menuBaseProducts = readBaseProducts();
        addToList(menuBaseProducts, Stream.of( new BaseProduct(title, rating, calories, protein, fat, sodium, price)));
        writeBaseProducts();
    }

    public static<T> void addToList(List<T> target, Stream<T> source) {
        source.collect(Collectors.toCollection(() -> target));
    }

    @Override
    public void deleteProduct(BaseProduct productToDelete) {
        menuBaseProducts = readBaseProducts();
        menuCompositeProducts = readCompositeProducts();
        menuBaseProducts.removeIf(product -> product.toString().equals(productToDelete.toString()));
        menu.removeIf(product -> product.toString().equals(productToDelete.toString()));
        writeBaseProducts();
        writeCompositeProducts();
    }

    @Override
    public void modifyProduct(BaseProduct selectedProduct, String title, double rating, int calories, int protein, int fat, int sodium, int price) {
        menuBaseProducts = readBaseProducts();
        menuCompositeProducts = readCompositeProducts();
        for(BaseProduct product: menuBaseProducts){
            if(product.toString().equals(selectedProduct.toString())){
                product.setTitle(title);
                product.setRating(rating);
                product.setCalories(calories);
                product.setProtein(protein);
                product.setFat(fat);
                product.setSodium(sodium);
                product.setPrice(price);
                break;
            }
        }
        writeBaseProducts();
        writeCompositeProducts();
    }

    @Override
    public void createCompositeProduct(ArrayList<MenuItem> listOfMenuItems) {
        CompositeProduct compositeProduct = new CompositeProduct("Daily menu " + computeMenuNumber(), 0, listOfMenuItems);
        compositeProduct.setPrice(compositeProduct.computePrice());
        menuCompositeProducts = readCompositeProducts();
        addToList(menuCompositeProducts, Stream.of(compositeProduct));
        writeCompositeProducts();
        printCompositeProducts();
    }

    private Integer computeMenuNumber(){
        int menuNo = 1;
        menuCompositeProducts = readCompositeProducts();
        for(CompositeProduct compositeProduct: menuCompositeProducts){
            menuNo++;
        }
        return menuNo;
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

    private List<BaseProduct> readBaseProducts(){
        return new Serializator().deserializeMenuBaseProducts();
    }

    private void writeBaseProducts(){ new Serializator().serializeMenuBaseProducts(menuBaseProducts); }

    private List<CompositeProduct> readCompositeProducts(){
        return new Serializator().deserializeCompositeProducts();
    }

    private void writeCompositeProducts(){ new Serializator().serializeCompositeProducts(menuCompositeProducts); }

}
