package businessLayer;

public abstract class MenuItem {

    public String title;
    public Integer price;

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
