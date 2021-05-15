package dataLayer;

import java.io.BufferedWriter;
import java.io.IOException;

public class FileWriter {

    public void writeNewUser(String username, String password, String userType) throws IOException{
        try (BufferedWriter bw = new BufferedWriter(new java.io.FileWriter("src\\main\\resources\\users.txt", true))) {
            bw.write(username);
            bw.newLine();
            bw.write(password);
            bw.newLine();
            bw.write(userType);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
