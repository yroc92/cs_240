package main.java.server.commonClasses.helperClasses;

/**
 * Created by Cory on 3/20/17.
 * This class is to structure the request body for a login request.
 */
public class LoginObject {
    private String userName;
    private String password;


    public LoginObject(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
