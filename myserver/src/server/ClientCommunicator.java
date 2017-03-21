package server;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import server.commonClasses.modelClasses.Person;
import server.commonClasses.modelClasses.User;
import sun.plugin2.util.PojoUtil;

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
		System.out.println(ClientCommunicator.SINGLETON.send());
	}
		

//Auxiliary Constants, Attributes, and Methods
	private static final String SERVER_HOST = "localhost";
	private static final String URL_PREFIX = "http://" + SERVER_HOST + ":" + ServerCommunicator.SERVER_PORT_NUMBER + "/user/register" ;
	private static final String HTTP_POST = "POST";
	private static final String COMMAND_HANDLER_DESIGNATOR = "/";
}
