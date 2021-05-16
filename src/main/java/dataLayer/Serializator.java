package dataLayer;

import businessLayer.BaseProduct;
import businessLayer.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
            FileOutputStream fileOut = new FileOutputStream("src\\main\\resources\\menu.ser");
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
            FileInputStream fileIn = new FileInputStream("src\\main\\resources\\menu.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            menuBaseProducts = (ArrayList)objectIn.readObject();
            objectIn.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return menuBaseProducts;
    }

}
