package businessLayer;

import java.util.ArrayList;

public class CompositeProduct extends MenuItem {

    public ArrayList<MenuItem> listOfMenuItems;

    public CompositeProduct(String title, int price, ArrayList<MenuItem> listOfMenuItems) {
        super(title, price);
        this.listOfMenuItems = listOfMenuItems;
    }

    @Override
    public int computePrice() {
        int price = 0;
        for (MenuItem menuItem : listOfMenuItems) {
            price += menuItem.getPrice();
        }
        return price;
    }
}
