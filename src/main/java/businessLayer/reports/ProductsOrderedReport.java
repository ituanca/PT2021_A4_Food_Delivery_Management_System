package businessLayer.reports;

import businessLayer.BaseProduct;
import businessLayer.CompositeProduct;
import businessLayer.MenuItem;
import businessLayer.Order;
import dataLayer.Serializator;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ProductsOrderedReport {

    public static List<String> getProductsOrderedMoreThanNoOfTimes(int noOfTimes, Map<Order, ArrayList<MenuItem>> ordersList){
        return getOrderedProducts(ordersList).stream()
                .filter( product -> getNoOfTimesProductWasOrdered(ordersList, product) > noOfTimes)
                .map( product -> "product: " + product.getTitle() + "\n  price: " + product.getPrice() + "\n  No of times it was ordered: " + getNoOfTimesProductWasOrdered(ordersList, product))
                .collect(Collectors.toList());
    }

    private static int getNoOfTimesProductWasOrdered(Map<Order, ArrayList<MenuItem>> ordersList, MenuItem product){
        final int[] no = {0};
        ordersList.forEach((key, value) -> {
            value.forEach(menuItem -> {
                if(menuItem.toString().equals(product.toString())){
                    no[0]++;
                }
            });
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
