package dataLayer;

import businessLayer.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {

    public void writeNewUser(User user) throws IOException{
        try (BufferedWriter bw = new BufferedWriter(new java.io.FileWriter("src\\main\\resources\\users.txt", true))) {
            bw.write(user.getId());
            bw.newLine();
            bw.write(user.getUsername());
            bw.newLine();
            bw.write(user.getPassword());
            bw.newLine();
            bw.write(user.getUserType());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> readUsersFromFile() {
        ArrayList<User> users = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader("src\\main\\resources\\users.txt");
            BufferedReader br = new BufferedReader(fileReader);
            String data = br.readLine();
            int line = 1;
            int id = 1;
            String  username = null, password = null, userType;
            while (data != null) {
                if (line % 4 == 1) {
                    id = Integer.parseInt(data);
                } else {
                    if (line % 4 == 2) {
                        username = data;
                    } else {
                        if(line%4 == 3){
                            password = data;
                        }else{
                            userType = data;
                            User user = new User(id, username, password, userType);
                            users.add(user);
                        }
                    }
                }
                line++;
                data = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

}
