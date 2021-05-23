package businessLayer.reports;

import businessLayer.BaseProduct;
import businessLayer.CompositeProduct;
import businessLayer.MenuItem;
import businessLayer.Order;
import dataLayer.Serializator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductsOrderedInADayReport {

    public static List<String> getStringProductsOrderedInADay(LocalDate day, Map<Order, ArrayList<MenuItem>> ordersList){
        return getProductsOrderedInADay(day, ordersList).stream()
                .map( product -> "product: " + product.getTitle() + "\n  price: " + product.getPrice() + "\n  No of times it was ordered on " + day.toString() + ": "
                        + getNoOfTimesProductWasOrderedInADay(product, day, ordersList))
                .collect(Collectors.toList());
    }

    private static List<MenuItem> getProductsOrderedInADay(LocalDate day, Map<Order, ArrayList<MenuItem>> ordersList){
        List<MenuItem> orderedProducts = getOrderedProducts(ordersList);
        List<MenuItem> productsOrderedInADay = new ArrayList<>();
        ordersList.forEach((key, value) -> {
            if(key.getOrderDate().toLocalDate().equals(day)){
                value.forEach(menuItem -> {
                    orderedProducts.forEach(product -> {
                        if(menuItem.toString().equals(product.toString()) && !productsOrderedInADay.contains(menuItem)){
                            productsOrderedInADay.add(menuItem);
                        }
                    });

                });
            }
        });
        return productsOrderedInADay;
    }

    private static int getNoOfTimesProductWasOrderedInADay( MenuItem product, LocalDate day, Map<Order, ArrayList<MenuItem>> ordersList){
        final int[] no = {0};
        ordersList.forEach((key, value) -> {
            if(key.getOrderDate().toLocalDate().equals(day)){
                value.forEach(menuItem -> {
                    if(product == menuItem){
                        no[0]++;
                    }
                });
            }
        });
        return no[0];
    }

    private static List<MenuItem> getOrderedProducts(Map<Order, ArrayList<MenuItem>> ordersList){
        List<MenuItem> products = new ArrayList<>();
        createTheEntireMenu(products);
        List<MenuItem> orderedProducts = new ArrayList<MenuItem>();
        ordersList.forEach((key, value) -> {
            value.forEach(thisOrderMenuItem -> {
                products.forEach(menuItem ->{
                    if(menuItem.toString().equals(thisOrderMenuItem.toString()) && !orderedProducts.contains(menuItem)){
                        orderedProducts.add(menuItem);
                    }
                });
            });
        });
        return orderedProducts;
    }

    public static void createTheEntireMenu(List<MenuItem> menu){
        menu.addAll(readBaseProducts());
        menu.addAll(readCompositeProducts());
    }

    public static List<BaseProduct> readBaseProducts(){ return new Serializator().deserializeMenuBaseProducts(); }

    public static List<CompositeProduct> readCompositeProducts(){ return new Serializator().deserializeCompositeProducts(); }
}
