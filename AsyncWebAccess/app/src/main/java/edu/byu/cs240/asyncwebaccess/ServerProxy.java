package edu.byu.cs240.asyncwebaccess;

import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import edu.byu.cs240.asyncwebaccess.ModelClasses.LoginObject;
import edu.byu.cs240.asyncwebaccess.ModelClasses.User;

/**
 * Created by corycooper on 3/26/17.
 */

public enum ServerProxy {
    SERVER_PROXY;
    private Gson gson = new Gson();
    ServerProxy() {
    }

    //Auxiliary Constants, Attributes, and Methods
    private static final String SERVER_HOST = "10.24.64.18";
    private static final String SERVER_PORT_NUMBER = "8080";
    private static final String HTTP = "http://";
    private static final String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT_NUMBER;
//    private static final String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT_NUMBER + "/user/register" ;
    private static final String HTTP_POST = "POST";
    private static final String COMMAND_HANDLER_DESIGNATOR = "/";


    public void registerUser(User newUser, FragmentActivity activity) {
        PostRequest request = new PostRequest("/user/register", activity);

        Object registeredUser = null;
        registeredUser = request.execute(newUser);
    }

    public void loginUser(LoginObject loginObject, FragmentActivity activity) {
        PostRequest request = new PostRequest("/user/login", activity);
        Object loggedInUser = null;
        request.execute(loginObject);
    }

    public Object send(Object object, String url) {
        Object response = null;
        HttpURLConnection connection =
                openConnection(url, HTTP_POST, true, object);
        response = getResponse(connection);
        return response;
    }

    private HttpURLConnection openConnection(String contextIdentifier,
                                             String requestMethod,
                                             boolean sendingSomethingToServer,
                                             Object objectToSend)
    {
        HttpURLConnection result = null;
        try {
            URL url = new URL(URL_PREFIX + contextIdentifier);
            result = (HttpURLConnection)url.openConnection();
            result.setRequestMethod(requestMethod);
            result.setDoOutput(sendingSomethingToServer);
			sendToServerCommunicator(result, objectToSend);
            result.connect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private void sendToServerCommunicator(HttpURLConnection connection,
                                          Object objectToSend)
    {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(connection.getOutputStream());
            //Encoding in JSON
            Gson gson = new Gson();
            gson.toJson(objectToSend, printWriter);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(printWriter != null) {
                printWriter.close();
            }
        }
    }

    private Object getResponse(HttpURLConnection connection) {
        Object result = null;
        try {
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //0 means the body response length == 0; it was empty
                if(connection.getContentLength() == 0) {
                    Log.e("MainActivity", "Response body was empty");
                } else if(connection.getContentLength() == -1) {
                    //-1 means the body was not empty but has an unknown about of information
//                    System.out.println("ERROR: The response body should have been empty");
                    InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                    /******************************************/
                    result = gson.fromJson(inputStreamReader, User.class);
                    /******************************************/
                    inputStreamReader.close();
                    Log.e("MainActivity", "Reponse body was not empty");
                }
            } else {
                throw new Exception(String.format("http code %d",	connection.getResponseCode()));
            }
        } catch (JsonSyntaxException | JsonIOException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.getInputStream().close();
            } catch (IOException e) {/*Ignore*/}
        }
        return result;
    }






}
