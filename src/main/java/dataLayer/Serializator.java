package dataLayer;

import businessLayer.*;

import java.io.*;
import java.util.*;

public class Serializator {

    public void serializeUsers(ArrayList<User> users){
        try {
            FileOutputStream fileOut = new FileOutputStream("src\\main\\resources\\users.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(users);
            objectOut.flush();
            objectOut.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> deserializeUsers(){
        ArrayList<User> users = new ArrayList<>();
        try{
            FileInputStream fileIn = new FileInputStream("src\\main\\resources\\users.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            users = (ArrayList)objectIn.readObject();
            objectIn.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void serializeMenuBaseProducts(List<BaseProduct> menuBaseProducts) {
        try {
            FileOutputStream fileOut = new FileOutputStream("src\\main\\resources\\menuBaseProducts.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(menuBaseProducts);
            objectOut.flush();
            objectOut.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<BaseProduct> deserializeMenuBaseProducts() {
        List<BaseProduct> menuBaseProducts = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream("src\\main\\resources\\menuBaseProducts.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            menuBaseProducts = (ArrayList)objectIn.readObject();
            objectIn.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return menuBaseProducts;
    }

    public void serializeCompositeProducts(List<CompositeProduct> menuCompositeProducts) {
        try {
            FileOutputStream fileOut = new FileOutputStream("src\\main\\resources\\menuCompositeProducts.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(menuCompositeProducts);
            objectOut.flush();
            objectOut.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<CompositeProduct> deserializeCompositeProducts() {
        List<CompositeProduct> menuCompositeProducts = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream("src\\main\\resources\\menuCompositeProducts.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            menuCompositeProducts = (ArrayList)objectIn.readObject();
            objectIn.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return menuCompositeProducts;
    }

    public void serializeOrders(Map<Order, ArrayList<MenuItem>> ordersList) {
        try {
            FileOutputStream fileOut = new FileOutputStream("src\\main\\resources\\orders.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(ordersList);
            objectOut.flush();
            objectOut.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<Order, ArrayList<MenuItem>> deserializeOrders() {
        HashMap<Order, ArrayList<MenuItem>> ordersList = new HashMap<>();
        try {
            FileInputStream fileIn = new FileInputStream("src\\main\\resources\\orders.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            ordersList = (HashMap<Order, ArrayList<MenuItem>>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ordersList;
    }

}
