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

    private String printListOfMenuItems(){
       if(listOfMenuItems.size() == 2){
           return "   " + listOfMenuItems.get(0) + "\n" + "   " + listOfMenuItems.get(1);
       }else {
           if (listOfMenuItems.size() == 3) {
               return "   " + listOfMenuItems.get(0) + "\n" + "   " + listOfMenuItems.get(1) + "\n" + "   " + listOfMenuItems.get(2);
           }else{
               return "   " + listOfMenuItems.get(0) + "\n" + "   " + listOfMenuItems.get(1) + "\n" + "   " + listOfMenuItems.get(2) + "\n" + "   " + listOfMenuItems.get(3) ;
           }
       }
    }

    @Override
    public String toString() {
        return  title + ", price=" + price + "\n" + printListOfMenuItems();
    }
}
