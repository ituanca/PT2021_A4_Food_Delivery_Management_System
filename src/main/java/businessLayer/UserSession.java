package businessLayer;

public final class UserSession {
    private static UserSession instance;
    private int id;
    private String userName, password, userType;

    private UserSession(int id, String userName, String password, String userType) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.userType = userType;
    }

    private UserSession() {
    }

    public static UserSession getInstance(int id, String userName, String password, String userType) {
        if(instance == null){
            instance = new UserSession(id, userName, password, userType);
        }
        return instance;
    }

    public static UserSession getInstance() {
        if(instance == null){
            instance = new UserSession();
        }
        return instance;
    }

    public static void setInstance(UserSession instance) { UserSession.instance = instance; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getUserType() { return userType; }

    public void setUserType(String userType) { this.userType = userType; }

    public void cleanUserSession(){
        id = 0;
        userName = "";
        password = "";
        userType = "";
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}
