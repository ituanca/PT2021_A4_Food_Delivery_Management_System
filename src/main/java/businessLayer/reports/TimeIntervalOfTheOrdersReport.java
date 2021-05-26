package businessLayer.reports;

import businessLayer.MenuItem;
import businessLayer.Order;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TimeIntervalOfTheOrdersReport {

    public static List<String> getOrdersFromTimeInterval(String startHour, String endHour, Map<Order, ArrayList<MenuItem>> ordersList){
        LocalDateTime startTime = getTimeFromString(startHour);
        LocalDateTime endTime = getTimeFromString(endHour);
        return ordersList.entrySet().stream()
                            .filter(entry ->    (startTime.getHour() < endTime.getHour() &&
                                            entry.getKey().getOrderDate().getHour() > startTime.getHour() - 1 && entry.getKey().getOrderDate().getMinute() > startTime.getMinute() &&
                                            entry.getKey().getOrderDate().getHour() < endTime.getHour()) ||
                                                (startTime.getHour() > endTime.getHour() &&
                                            (entry.getKey().getOrderDate().getHour() > startTime.getHour() - 1 && entry.getKey().getOrderDate().getMinute() > startTime.getMinute() ||
                                             entry.getKey().getOrderDate().getHour() < endTime.getHour())))
                            .map(entry -> entry.getKey() + "\n" + getStringOfProductsOfOrder(entry.getValue()))
                            .collect(Collectors.toList());
    }

    private static String getStringOfProductsOfOrder(ArrayList<MenuItem> productsOfOrder){
        StringBuilder listOfProducts = new StringBuilder();
        for (MenuItem menuItem : productsOfOrder) {
            assert false;
            listOfProducts.append("   ").append(menuItem).append("\n");
        }
        assert false;
        return listOfProducts.toString();
    }

    private static LocalDateTime getTimeFromString(String stringHour){
        if(stringHour.length() == 1) {
            return LocalDateTime.parse("2000-01-01T0" + stringHour + ":00:00");
        }else{
            return LocalDateTime.parse("2000-01-01T" + stringHour + ":00:00");
        }
    }

}
