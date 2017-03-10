package server.clientSideOnlyClasses;

import server.serverSideOnlyClasses.ServerCommunicator;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * Created by Cory on 2/17/17.
 */
public class ClientCommunicator {
    public static ClientCommunicator SINGLETON = new ClientCommunicator();
    public ClientCommunicator(){}

    public Object send() {
        // set up connction to server
        // send request (it may or may not work)
        // listen for response

        // 3 ways we send info
            // header : only contains auth token
            // request body
            // url
        Object response = null;
//        HttpURLConnection
        return null;
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
            result.connect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
    public static void main(String[] args) {
        ClientCommunicator.SINGLETON.send();
    }

    //Auxiliary Constants, Attributes, and Methods
    private static final String SERVER_HOST = "localhost";
    private static final String URL_PREFIX = "http://" + SERVER_HOST + ":" + ServerCommunicator.SERVER_PORT_NUMBER+"/person";
    private static final String HTTP_POST = "POST";
    private static final String COMMAND_HANDLER_DESIGNATOR = "/";
}
