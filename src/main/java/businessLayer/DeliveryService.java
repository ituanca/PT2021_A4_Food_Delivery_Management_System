package businessLayer;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DeliveryService implements IDeliveryServiceProcessing{

    Map<Order, ArrayList<MenuItem>> ordersList = new HashMap<Order, ArrayList<MenuItem>>();
    ArrayList<MenuItem> menu = new ArrayList<MenuItem>();
    List<BaseProduct> menuBaseProducts;

    @Override
    public void importProducts() throws IOException {
        File inputFile = new File("src\\main\\resources\\products.csv");
        InputStream inputFileStream = new FileInputStream(inputFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputFileStream));
        menuBaseProducts = br.lines()
                .skip(1)
                .map(mapToItem)
                .distinct()
                .collect(Collectors.toList());
        br.close();
        printMenu(menuBaseProducts);

        Collection<String> list = Arrays.asList("A", "B", "C", "D", "A", "B", "C");
        List<String> distinctElements = list.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println(distinctElements);
    }

    private void printMenu(List<BaseProduct> menuBaseProducts){
        int i = 1;
        for(BaseProduct baseProduct : menuBaseProducts) {
            System.out.println("id: " + i + " title: " + baseProduct.title + " rating: " + baseProduct.rating + " calories: " + baseProduct.calories + " price: " + baseProduct.price);
            System.out.println();
            i++;
            if(i == 2000){
                break;
            }
        }
    }

    private Function<String, BaseProduct> mapToItem = (line) -> {
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
    public void addProduct() {

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
