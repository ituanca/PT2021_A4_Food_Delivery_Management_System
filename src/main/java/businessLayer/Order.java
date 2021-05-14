package businessLayer;

import java.util.Date;

public class Order {
    public int orderID;
    public int clientID;
    public Date orderDate;

//    public int hashCode(){
//        int value;
//
//        return value;
//    }

    public int getOrderID() { return orderID; }

    public void setOrderID(int orderID) { this.orderID = orderID; }

    public int getClientID() { return clientID; }

    public void setClientID(int clientID) { this.clientID = clientID; }

    public Date getOrderDate() { return orderDate; }

    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
}
