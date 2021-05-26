package businessLayer;

import businessLayer.reports.ClientsReport;
import businessLayer.reports.ProductsOrderedInADayReport;
import businessLayer.reports.ProductsOrderedReport;
import businessLayer.reports.TimeIntervalOfTheOrdersReport;
import dataLayer.Serializator;
import presentationLayer.EmployeeController;
import presentationLayer.Observer;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeliveryService extends Observable implements IDeliveryServiceProcessing {

    Map<Order, ArrayList<MenuItem>> ordersList = new HashMap<>();            // stores the order related information
    List<MenuItem> menu = new ArrayList<MenuItem>();                                // saves the menu (all the products) provided by the catering company
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
    public List<String> generateReportOfOrders(String startHour, String endHour) {
        return TimeIntervalOfTheOrdersReport.getOrdersFromTimeInterval(startHour, endHour, readOrders());
    }

    @Override
    public List<String> generateReportOfProducts(int noOfTimes) {
        return ProductsOrderedReport.getProductsOrderedMoreThanNoOfTimes(noOfTimes, readOrders());
    }

    @Override
    public List<String> generateReportOfClients(int noOfTimes, int priceOfOrder) {
        return ClientsReport.getClientsThatHaveOrderedMoreThanNoOfTimes(noOfTimes, priceOfOrder, readOrders());
    }

    @Override
    public List<String> generateReportOfProductsOrderedInADay(LocalDate day) {
        return ProductsOrderedInADayReport.getStringProductsOrderedInADay(day, readOrders());
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
    public List<String> searchForProduct(String title, double rating, int calories, int protein, int fat, int sodium, int price) {
        return readBaseProducts().stream()
               .filter(product ->  (product.title.contains(title)) && ((product.rating == rating || rating == -1) && (calories == -1 || product.calories == calories) &&
                       (product.protein == protein || protein == -1) && (product.fat == fat || fat == -1) && (product.sodium == sodium || sodium == -1) &&
                       (product.price == price || price == -1)))
               .map(BaseProduct::toString)
               .collect(Collectors.toList());

//        return getListOfProducts(
//                menuBaseProducts,
//                (BaseProduct product) ->
//                        (product.title.contains(title)) && ((product.rating == rating || rating == -1) && (calories == -1 || product.calories == calories) &&
//                                (product.protein == protein || protein == -1) && (product.fat == fat || fat == -1) && (product.sodium == sodium || sodium == -1) &&
//                                (product.price == price || price == -1))
//                       );
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
    public List<String> searchForMenu(String itemTitle, int price) {
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

    private List<MenuItem> createTheEntireMenu(){
        menu.addAll(readBaseProducts());
        menu.addAll(readCompositeProducts());
        return menu;
    }

    @Override
    public void createOrder(ArrayList<MenuItem> selectedProducts) {
        ordersList = readOrders();
        UserSession instance = UserSession.getInstance();
        System.out.println(instance);
        Order order = new Order(computeOrderId(), instance.getId(), LocalDateTime.now());
        ordersList.put(order, selectedProducts);
        new TXTGenerator().generateTXT(order, getOrderPrice(selectedProducts), selectedProducts);
        findEmployeesAndCreateObservers();
        notifyObservers(ordersList);
        writeOrders();
    }

    private static int getOrderPrice(ArrayList<MenuItem> listOfProducts){
        return listOfProducts.stream().mapToInt(MenuItem::getPrice).sum();
    }

    public void findEmployeesAndCreateObservers(){
        ArrayList<User> users = readUsers();
        for(User user : users){
            if(user.getUserType().equals("employee")){
                Observer observer = new EmployeeController();
                addObserver(observer);
            }
        }
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
        for(MenuItem menuItem : createTheEntireMenu()){
            if(menuItem.toString().equals(stringMenuItem)){
                return menuItem;
            }
        }
        return null;
    }

    private List<BaseProduct> readBaseProducts(){ return new Serializator().deserializeMenuBaseProducts(); }

    private void writeBaseProducts(){ new Serializator().serializeMenuBaseProducts(menuBaseProducts); }

    private List<CompositeProduct> readCompositeProducts(){ return new Serializator().deserializeCompositeProducts(); }

    private void writeCompositeProducts(){ new Serializator().serializeCompositeProducts(menuCompositeProducts); }

    private  HashMap<Order, ArrayList<MenuItem>> readOrders(){ return new Serializator().deserializeOrders(); }

    private void writeOrders(){ new Serializator().serializeOrders(ordersList); }

    private ArrayList<User> readUsers(){ return new Serializator().deserializeUsers(); }
}
