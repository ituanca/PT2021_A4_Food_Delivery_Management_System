package businessLayer;

import java.io.Serializable;

public abstract class MenuItem implements Serializable {

    public String title;
    public Integer price;

    public MenuItem(String title, Integer price) {
        this.title = title;
        this.price = price;
    }

    public abstract int computePrice();

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public Integer getPrice() { return price; }

    public void setPrice(Integer price) { this.price = price; }

    @Override
    public String toString() {
        return  "title=" + title +
                ", price=" + price;
    }
}
