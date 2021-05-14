package businessLayer;

import java.util.ArrayList;
import java.util.Iterator;

public class CompositeProduct extends MenuItem {

    ArrayList<MenuItem> compositeProduct = new ArrayList<MenuItem>();

    public CompositeProduct(String title, int price) {
        this.title = title;
        this.price = price;
    }

    @Override
    public int computePrice() {
        int price = 0;
        for (MenuItem menuItem : compositeProduct) {
            price += menuItem.getPrice();
        }
        return price;
    }
}
