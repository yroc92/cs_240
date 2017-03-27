package edu.byu.cs240.asyncwebaccess;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
    private static final String SERVER_HOST = "localhost";
    private static final String SERVER_PORT_NUMBER = "8080";
    private static final String HTTP = "http://";
    private static final String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT_NUMBER;
//    private static final String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT_NUMBER + "/user/register" ;
    private static final String HTTP_POST = "POST";
    private static final String COMMAND_HANDLER_DESIGNATOR = "/";


    public void registerUser(User newUser) {

        sendPost("/user/register", "", newUser);

    }


//    public User loginUser(String userName, String password) {
//
//    }

    private Object sendPost(String requestString, String authToken, Object object) {
        HttpURLConnection connection = openConnection(requestString, "POST", authToken, true);
        sendToServerCommunicator(connection, object);
//        getResult(connection, null);

        return getResult(connection, Object.class);
    }

    private HttpURLConnection openConnection(String contextIdentifier,
                                             String requestMethod,
                                             String authorizationCode,
                                             boolean sendingSomethingToServer)
    {
        HttpURLConnection result = null;
        try {
            URL url = new URL(URL_PREFIX + contextIdentifier);
            result = (HttpURLConnection)url.openConnection();
            result.setRequestMethod(requestMethod);
            result.setDoOutput(sendingSomethingToServer);
            result.setRequestProperty("authorization", authorizationCode);
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
            gson.toJson(objectToSend, printWriter);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(printWriter != null) {
                printWriter.close();
            }
        }
    }

    private Object getResult(HttpURLConnection connection, Class<?> klass) {
        Object result = null;
        try {
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //0 means the body response length == 0; it was empty
                if(connection.getContentLength() == 0) {
                    System.out.println("Response body was empty");
                } else if(connection.getContentLength() == -1) {
                    System.out.println("ERROR: The response body should have been empty");
                }
            } else {
                throw new Exception(String.format("http code %d",	connection.getResponseCode()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }



//    public String getUrl(URL url) {
//        try {
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.connect();
//
//            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                // Get response body input stream
//                InputStream responseBody = connection.getInputStream();
//
//                // Read response body bytes
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                byte[] buffer = new byte[1024];
//                int length = 0;
//                while ((length = responseBody.read(buffer)) != -1) {
//                    baos.write(buffer, 0, length);
//                }
//
//                // Convert response body bytes to a string
//                String responseBodyData = baos.toString();
//                return responseBodyData;
//            }
//        }
//        catch (Exception e) {
//            Log.e("HttpClient", e.getMessage(), e);
//        }
//
//        return null;
//    }



}


package main.java.server;

        import com.google.gson.Gson;
        import com.google.gson.JsonIOException;
        import com.google.gson.JsonSyntaxException;

        import java.io.IOException;
        import java.io.PrintWriter;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;
//import progressiveClientServer.helloworld.ServerCommmunicator;


public class ClientCommunicator {
    //Singleton
    public static ClientCommunicator SINGLETON = new ClientCommunicator();
    public static ServerCommunicator ServerSingleton = new ServerCommunicator();

//Attributes

    //Constructors
    public ClientCommunicator() {}

    //Commands
    public Object send() {
        Object response = null;
        HttpURLConnection connection =
                openConnection(COMMAND_HANDLER_DESIGNATOR, HTTP_POST, true);
        response = getResponse(connection);

        return response;
    }

    private HttpURLConnection openConnection(String contextIdentifier,
                                             String requestMethod,
                                             boolean sendingSomethingToServer)
    {
        HttpURLConnection result = null;
        try {
            URL url = new URL(URL_PREFIX + contextIdentifier);
            result = (HttpURLConnection)url.openConnection();
            result.setRequestMethod(requestMethod);
            result.setDoOutput(sendingSomethingToServer);
//			sendToServerCommunicator(result, new User("yroc92", "pass1", "cory@mail.com", "Cory", "Cooper", "abc123", "m"));
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
                    System.out.println("Response body was empty");
                } else if(connection.getContentLength() == -1) {
                    //-1 means the body was not empty but has an unknown about of information
                    System.out.println("ERROR: The response body should have been empty");
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

    public static void main(String[] args) {
        System.out.println(ClientCommunicator.SINGLETON.send());
    }


    //Auxiliary Constants, Attributes, and Methods
    private static final String SERVER_HOST = "localhost";
    private static final String URL_PREFIX = "http://" + SERVER_HOST + ":" + ServerCommunicator.SERVER_PORT_NUMBER + "/user/register" ;
    private static final String HTTP_POST = "POST";
    private static final String COMMAND_HANDLER_DESIGNATOR = "/";
}
