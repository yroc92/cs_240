package edu.byu.cs240.asyncwebaccess.ModelClasses;

/**
 * Created by corycooper on 3/26/17.
 */

public class LoginObject {
    private String userName;
    private String password;


    public LoginObject(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public LoginObject() {
        this.userName = null;
        this.password = null;
    }

    public boolean validLogin() {
        if (userName == null || password == null || userName.equals("") || password.equals("")) return false;
        return true;
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