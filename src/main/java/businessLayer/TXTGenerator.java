package businessLayer;

import dataLayer.Serializator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TXTGenerator {

    public void generateTXT(Order order, int orderPrice, ArrayList<MenuItem> products){
        File file = new File("src\\main\\java\\txt\\bill" + order.getOrderID() +".txt");
        boolean result;
        try {
            result = file.createNewFile();
            if(result){
                System.out.println("file created " + file.getCanonicalPath());
            }else{
                System.out.println("file already exists at location " + file.getCanonicalPath());
            }
            FileWriter fw = new FileWriter(file);
            fw.write("orderID: " + order.getOrderID() + "\n");
            fw.write("clientID: " + order.getClientID() + "\n");
            for(User user : readUsers()){
                if(user.getId() == order.getClientID()){
                    fw.write("client username: " + user.getUsername() + "\n");
                    break;
                }
            }
            fw.write("ordered products: \n");
            for(MenuItem product : products){
                fw.write(" " + product.toString() + "\n");
            }
            fw.write("total price: " + orderPrice);
            fw.close();
        } catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    public ArrayList<User> readUsers(){ return new Serializator().deserializeUsers(); }
}
