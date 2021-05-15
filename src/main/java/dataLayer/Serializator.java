package dataLayer;

import businessLayer.BaseProduct;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Serializator {

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

    public void deserializeMenuBaseProducts() {
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
        for(BaseProduct baseProduct : menuBaseProducts){
            System.out.println(baseProduct);
        }
    }



}
