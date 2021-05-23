package businessLayer.reports;

import businessLayer.MenuItem;
import businessLayer.Order;
import businessLayer.User;
import dataLayer.Serializator;

import java.util.*;
import java.util.stream.Collectors;

public class ClientsReport {

    public static List<String> getClientsThatHaveOrderedMoreThanNoOfTimes(int noOfTimes, int priceOfOrder, Map<Order, ArrayList<MenuItem>> ordersList){
        return getClientsThatOrdered(ordersList).stream()
                .filter( client -> getNoOfTimesUserOrderedMoreThanPrice(priceOfOrder, ordersList, client) > noOfTimes)
                .map( client -> "id: " + client.getId() + ", username: " + client.getUsername() + ",  number of orders " + getNoOfTimesUserOrderedMoreThanPrice(priceOfOrder, ordersList, client))
                .collect(Collectors.toList());
    }

    private static List<User> getClientsThatOrdered(Map<Order, ArrayList<MenuItem>> ordersList){
        List<User> users = readUsers();
        List<User> clientsThatOrdered = new ArrayList<>();
        ordersList.forEach((key, value) -> {
            users.forEach(user ->{
                if(user.getId() == getClientIdFromKey(key) && !clientsThatOrdered.contains(user)){
                    clientsThatOrdered.add(user);
                }
            });
        });
        return clientsThatOrdered;
    }

    private static int getNoOfTimesUserOrderedMoreThanPrice(int priceOfOrder, Map<Order, ArrayList<MenuItem>> ordersList, User user){
        return (int) ordersList.entrySet()
                .stream()
                .filter(entry -> user.getId() == getClientIdFromKey(entry.getKey()) && getOrderPrice(entry.getValue()) > priceOfOrder)
                .count();
    }

    private static int getOrderPrice(ArrayList<MenuItem> listOfProducts){
        return listOfProducts.stream().mapToInt(MenuItem::getPrice).sum();
    }

    private static int getClientIdFromKey(Order order){
        return order.getClientID();
    }

    private static ArrayList<User> readUsers(){ return new Serializator().deserializeUsers(); }

}
