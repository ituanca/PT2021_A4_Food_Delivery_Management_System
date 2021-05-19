package businessLayer;

import dataLayer.Serializator;

import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeliveryService implements IDeliveryServiceProcessing{

    Map<Order, ArrayList<MenuItem>> ordersList = new HashMap<>();     // stores the order related information
    List<MenuItem> menu = new ArrayList<MenuItem>();                  // saves the menu (all the products) provided by the catering company
    List<BaseProduct> menuBaseProducts = new ArrayList<>();                                     // saves the base products from the menu
    List<CompositeProduct> menuCompositeProducts = new ArrayList<>();                           // saves the composite products from the menu

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
        menuBaseProducts.removeIf(product -> product.toString().equals(productToDelete.toString()));
        writeBaseProducts();
    }

    @Override
    public void modifyProduct(BaseProduct selectedProduct, String title, double rating, int calories, int protein, int fat, int sodium, int price) {
        menuBaseProducts = readBaseProducts();
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
    }

    @Override
    public void createCompositeProduct(ArrayList<MenuItem> listOfMenuItems) {
        CompositeProduct compositeProduct = new CompositeProduct("Daily menu " + computeMenuNumber(), 0, listOfMenuItems);
        compositeProduct.setPrice(compositeProduct.computePrice());
        menuCompositeProducts = readCompositeProducts();
        addToList(menuCompositeProducts, Stream.of(compositeProduct));
        writeCompositeProducts();
    }

    private Integer computeMenuNumber(){
        int menuNo = 1;
        menuCompositeProducts = readCompositeProducts();
        for(CompositeProduct ignored : menuCompositeProducts){
            menuNo++;
        }
        return menuNo;
    }

    @Override
    public void generateReports() {

    }

    @Override
    public ArrayList<String> viewProducts() {
        ArrayList<String> stringProducts = new ArrayList<>();
        for(BaseProduct baseProduct: readBaseProducts()){
            stringProducts.add(baseProduct.toString());
        }
        return stringProducts;
    }

    @Override
    public ArrayList<String> viewMenus() {
        ArrayList<String> stringMenus = new ArrayList<>();
        for(CompositeProduct compositeProduct: readCompositeProducts()){
            stringMenus.add(compositeProduct.toString());
        }
        return stringMenus;
    }

    @Override
    public ArrayList<String> searchForProduct(String title, double rating, int calories, int protein, int fat, int sodium, int price) {
        menuBaseProducts = readBaseProducts();
        return getListOfProducts(
                menuBaseProducts,
                (BaseProduct product) ->
                        (product.title.contains(title)) && ((product.rating == rating || rating == -1) && (calories == -1 || product.calories == calories) &&
                                (product.protein == protein || protein == -1) && (product.fat == fat || fat == -1) && (product.sodium == sodium || sodium == -1) &&
                                (product.price == price || price == -1))
                       );
    }

    public ArrayList<String> getListOfProducts(List<BaseProduct> roster, SearchingForProduct tester) {
        ArrayList<String> resultedArrayList = new ArrayList<>();
        for (BaseProduct p : roster) {
            if (tester.test(p)) {
                resultedArrayList.add(p.toString());
            }
        }
        return resultedArrayList;
    }

    @Override
    public ArrayList<String> searchForMenu(String itemTitle, int price) {
        menuCompositeProducts = readCompositeProducts();
        return getListOfCompositeProducts(
                menuCompositeProducts,
                (CompositeProduct product) -> {
                    for(MenuItem menuItem : product.listOfMenuItems) {
                        if (menuItem.title.contains(itemTitle) && (price == -1 || product.price == price)) {
                            return true;
                        }
                    }
                    return false;
                });
    }

    public ArrayList<String> getListOfCompositeProducts(List<CompositeProduct> roster, SearchingForCompositeProduct tester) {
        ArrayList<String> resultedArrayList = new ArrayList<>();
        for (CompositeProduct p : roster) {
            if (tester.test(p)) {
                resultedArrayList.add(p.toString());
            }
        }
        return resultedArrayList;
    }

    public List<MenuItem> createTheEntireMenu(){
        menuBaseProducts = readBaseProducts();
        menuCompositeProducts = readCompositeProducts();
        menu.addAll(menuBaseProducts);
        menu.addAll(menuCompositeProducts);
        return menu;
    }

    @Override
    public void createOrder(ArrayList<MenuItem> selectedProducts) {
        ordersList = readOrders();
        UserSession instance = UserSession.getInstance();
        System.out.println(instance.getId() + " " + instance.getUserName() + " " + instance.getPassword() + " " + instance.getUserType());
        Order order = new Order(computeOrderId(), instance.getId(), LocalDateTime.now());
        System.out.println("order date: " + order.orderDate);
        ordersList.put(order, selectedProducts);
        System.out.println("Deserialized HashMap:");
        Set set = ordersList.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            System.out.println("key: " + entry.getKey() + ", value: " + entry.getValue());
        }
        writeOrders();
    }

    private Integer computeOrderId(){
        int orderNo = 1;
        Set set = ordersList.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();
            orderNo++;
        }
        return orderNo;
    }

    public Integer computeOrderPrice(ArrayList<MenuItem> orderProducts){
        int orderPrice = 0;
        for(MenuItem menuItem : orderProducts){
            orderPrice += menuItem.getPrice();
        }
        return orderPrice;
    }

    public void addSelectedProductToArrayOfStrings(ArrayList<String> selectedProducts, String product){
        selectedProducts.add(product);
    }

    public ArrayList<MenuItem> createArrayOfProductsOfOrder(ArrayList<String> selectedProducts){
        ArrayList<MenuItem> productsOfOrder = new ArrayList<>();
        for(String selectedProduct: selectedProducts){
            productsOfOrder.add(getCorrespondingMenuItem(selectedProduct));
        }
        return productsOfOrder;
    }

    public MenuItem getCorrespondingMenuItem(String stringMenuItem){
        for(MenuItem menuItem : menu){
            if(menuItem.toString().equals(stringMenuItem)){
                return menuItem;
            }
        }
        return null;
    }

    public List<BaseProduct> readBaseProducts(){
        return new Serializator().deserializeMenuBaseProducts();
    }

    private void writeBaseProducts(){ new Serializator().serializeMenuBaseProducts(menuBaseProducts); }

    public List<CompositeProduct> readCompositeProducts(){ return new Serializator().deserializeCompositeProducts(); }

    private void writeCompositeProducts(){ new Serializator().serializeCompositeProducts(menuCompositeProducts); }

    public  HashMap<Order, ArrayList<MenuItem>> readOrders(){ return new Serializator().deserializeOrders(); }

    private void writeOrders(){ new Serializator().serializeOrders(ordersList); }

}
