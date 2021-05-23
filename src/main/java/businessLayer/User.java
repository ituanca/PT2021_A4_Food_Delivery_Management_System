package businessLayer;

import java.io.Serializable;

public class User implements Serializable {
    int id;
    String username, password, userType;

    public User(int id, String username, String password, String userType) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public User() {
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public void setUserType(String userType) { this.userType = userType; }

    public String getUserType() { return userType; }

    @Override
    public String toString() {
        return "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userType='" + userType + '\'' ;
    }
}
