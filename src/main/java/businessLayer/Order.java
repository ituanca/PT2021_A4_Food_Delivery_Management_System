package businessLayer;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Order implements Serializable {
    public int orderID;
    public int clientID;
    public LocalDateTime orderDate;

    public Order(int orderID, int clientID, LocalDateTime orderDate) {
        this.orderID = orderID;
        this.clientID = clientID;
        this.orderDate = orderDate;
    }

    @Override
    public int hashCode() {
        int value = orderDate != null ? 1 : orderDate.hashCode();
        value = 17 * value + orderID == 0 ? 0 : 13 * orderID + 19 * value + clientID == 0 ? 2 : 7 * clientID;
        return value;
        //return Objects.hash(getOrderID(), getClientID(), getOrderDate());
    }

    public int getOrderID() { return orderID; }

    public void setOrderID(int orderID) { this.orderID = orderID; }

    public int getClientID() { return clientID; }

    public void setClientID(int clientID) { this.clientID = clientID; }

    public LocalDateTime getOrderDate() { return orderDate; }

    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof Order)){
            return false;
        }
        Order order = (Order) o;
        return getOrderID() == order.getOrderID() && getClientID() == order.getClientID() && getOrderDate().equals(order.getOrderDate());
    }

    @Override
    public String toString() {
        return "orderID=" + orderID +
                ", clientID=" + clientID +
                ", orderDate=" + orderDate.getYear() + '-' + orderDate.getMonth() + '-' + orderDate.getDayOfMonth() + ", " + orderDate.toLocalTime();
    }
}
