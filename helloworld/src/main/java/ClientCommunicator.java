import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class ClientCommunicator {
//Singleton
	public static ClientCommunicator SINGLETON = new ClientCommunicator();
	
//Attributes
	
//Constructors
	public ClientCommunicator() {}
		
//Commands
	public Object send() {
		Object response = null;
		HttpURLConnection connection =
			openConnection(COMMAND_HANDLER_DESIGNATOR, HTTP_POST, false);
		response = getResponse(connection);

		return response;
	}
	
	private HttpURLConnection openConnection(String contextIdentifier,
											 String requestMethod,
			                                 boolean sendingSomthingToServer)
	{
		HttpURLConnection result = null;
		try {
			URL url = new URL(URL_PREFIX + contextIdentifier);
			result = (HttpURLConnection)url.openConnection();
			result.setRequestMethod(requestMethod);
			result.setDoOutput(sendingSomthingToServer);
			result.connect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.getInputStream().close();
			} catch (IOException e) {/*Ignore*/}
		}
		return result;
	}
	
	public static void main(String[] args) {
		ClientCommunicator.SINGLETON.send();
	}
		

//Auxiliary Constants, Attributes, and Methods
	private static final String SERVER_HOST = "localhost";
	private static final String URL_PREFIX = "http://" + SERVER_HOST + ":" + ServerCommunicator.SERVER_PORT_NUMBER;
	private static final String HTTP_POST = "POST";
	private static final String COMMAND_HANDLER_DESIGNATOR = "/";
}
