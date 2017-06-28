package edu.byu.cs240.asyncwebaccess;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import edu.byu.cs240.asyncwebaccess.ModelClasses.AuthToken;
import edu.byu.cs240.asyncwebaccess.ModelClasses.ConnectionObject;

/**
 * Created by corycooper on 3/26/17.
 */

public enum ServerProxy {
    SERVER_PROXY;
    private Gson gson = new Gson();
    ServerProxy() {
    }

    //Auxiliary Constants, Attributes, and Methods
//    private static final String SERVER_HOST = "10.24.64.18";
    private String SERVER_HOST;
//    private static final String SERVER_PORT_NUMBER = "8080";
    private String SERVER_PORT_NUMBER;
    private static final String HTTP = "http://";
//    private static final String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT_NUMBER;
    private String URL_PREFIX;
//    private static final String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT_NUMBER + "/user/register" ;
    private static final String HTTP_POST = "POST";
    private static final String HTTP_GET = "GET";

    private static final String COMMAND_HANDLER_DESIGNATOR = "/";


    /**
     * Using the given connection object, set up the connection to a server.
     * @param connectionObject
     */
    public void setUpConnection(ConnectionObject connectionObject) {
        this.SERVER_HOST = connectionObject.getServerHost();
        this.SERVER_PORT_NUMBER = connectionObject.getServerPort();
        this.URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT_NUMBER;
    }

//    public void registerUser(User newUser, FragmentActivity activity) {
//        LoginRegisterRequest request = new LoginRegisterRequest("/user/register", activity);
//
//        Object registeredUser = null;
//        registeredUser = request.execute((Object) newUser);
//    }
//
//    public void loginUser(LoginObject loginObject, FragmentActivity activity) {
//        LoginRegisterRequest request = new LoginRegisterRequest("/user/login", activity);
//        Object loggedInUser = null;
//        request.execute(loginObject);
//    }

    public interface CallListener {
        void onComplete(JSONObject json);
    }


    public JSONObject sendPost(Object object, String url) {
        JSONObject response = null;
        HttpURLConnection connection =
                openConnection(url, HTTP_POST, true, object);
        response = getResponse(connection);
        Log.i("wake", "Response: " + response);
        return response;
    }

    public JSONObject sendGet(String authToken, String url) {
        JSONObject response = null;
        HttpURLConnection connection =
                openConnection(url, HTTP_GET, true, null, authToken);
        response = getResponse(connection);
        Log.i("wake", "response: " + response.toString());
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

    /**
     * Overloaded HttpUrlConnection method to take in auth tokens
     * @param contextIdentifier
     * @param requestMethod
     * @param sendingSomethingToServer
     * @param objectToSend
     * @param authToken
     * @return
     */
    private HttpURLConnection openConnection(String contextIdentifier,
                                             String requestMethod,
                                             boolean sendingSomethingToServer,
                                             Object objectToSend,
                                             String authToken)
    {
        HttpURLConnection result = null;
        try {
            URL url = new URL(URL_PREFIX + contextIdentifier);
            result = (HttpURLConnection)url.openConnection();
            result.setRequestProperty("Authorization", authToken);
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

    // This will only return auth tokens
    private JSONObject getResponse(HttpURLConnection connection) {
        AuthToken result = null;
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
                    StringBuilder stringBuilder = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String response = null;
                    while ((response = bufferedReader.readLine()) != null) {
                        stringBuilder.append(response + "\n");
                    }
                    bufferedReader.close();

                    Log.d("test", stringBuilder.toString());



//                    result = gson.fromJson(inputStreamReader, AuthToken.class);

                    /******************************************/
                    inputStreamReader.close();
                    Log.e("MainActivity", "Reponse body was not empty");
                    return new JSONObject(stringBuilder.toString());
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
        return null;
    }






}
