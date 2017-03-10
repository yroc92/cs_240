package edu.byu.cs.familymap.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This object will store the log in credentials for a user.
 * Created by cole on 3/11/16.
 */
public class Login implements Serializable {
    public Login() {
        this.userName = "Ex: hanSolo<3Leia";
        this.password = "Ex: chewie";
        this.serverHost = "Ex: api.milleniumfallcan.com";
        this.serverPort = 0;
        this.personId = new String();
    }

    public Login(String userName, String password, String serverHost, int serverPort) {
        this.userName = userName;
        this.password = password;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public static void parseLogin(Login login, JSONObject root) throws JSONException {
        String authToken = root.getString("Authorization");
        String personId = root.getString("personId");

        login.setAuthToken(authToken);
        login.setPersonId(personId);
    }

    private String userName;

    private String password;

    private String serverHost;

    private int serverPort;

    private String authToken;

    private String personId;

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

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String toJSON() {
        return "{ " +
                        "username:\"" + userName + "\"," +
                        "password:\"" + password + "\"" +
                "}";
    }

    @Override
    public String toString() {
        return String.format(
                "User name: %s \nPassword: %s \nServer Host: %s \nServer Port: %d",
                userName,
                password,
                serverHost,
                serverPort);
    }
}
