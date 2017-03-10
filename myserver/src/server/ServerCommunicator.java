package server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import server.commonClasses.resourceClasses.UserResource;

public class ServerCommunicator {

	public static final int SERVER_PORT_NUMBER = 8080;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	private HttpServer server;
	private UserResource userResource = new UserResource();

	ServerCommunicator() {}

	private void run() {
		setUpServer(SERVER_PORT_NUMBER, MAX_WAITING_CONNECTIONS);
		setupContext();
		server.start();
	}
	
	private void setUpServer(int portNumber, int maxWaitingConnections) {
		try {
			server = HttpServer.create(new InetSocketAddress(portNumber),
									   maxWaitingConnections);
		} 
		catch (IOException e) {
			System.out.println("Could not create HTTP server: " + e.getMessage());
			e.printStackTrace();
			return;
		}

		server.setExecutor(null); // use the default executor
	}
	
	private HttpHandler serverHandler = new HttpHandler() {
		@Override
		public void handle(HttpExchange exchange) throws IOException {

			URI uri = exchange.getRequestURI();
			String path = uri.getPath();

			System.out.println("Path= " + path);

			String [] pathParts = path.split("/");
//			StringBuilder pathSegments = new StringBuilder();
//			for (int i = 0; i < pathParts.length; i++ ) {
//				pathSegments.append(pathParts[i]);
//				if (i < pathParts.length - 1) {
//					pathSegments.append(", ");
//				}
//			}
//			System.out.println("Path parts = " + pathSegments.toString());

			// Get auth key
			// TODO: handle authorization
//			Headers headers = exchange.getRequestHeaders().getFirst(ClientCommunicator.AUTHORIZATION_KEY);
//			String authorizationCode = headers.getFirst(ClientCommunicator.AUTHORIZATION_KEY);
//			System.out.println("The authorization code in sendingObjectsHandler = " + authorizationCode);

			InputStreamReader inputStreamReader = new InputStreamReader(exchange.getRequestBody());

			inputStreamReader.close();

			// Case statements for the different endpoints
			if ( pathParts.length < 2 ) {
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, -1);
				return;
			}
			switch (pathParts[1]) {
				case "user":
					userResource.handle(exchange,pathParts);

					return;

				case "clear":
					break;

				case  "fill":
					break;

				case "load":
					break;

				case "person":
					break;

				case "event":
					break;

				default:
					// 404 error
					System.out.println("Unknown path + " + path);
					exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, -1);
			}




//			System.out.println("Hello World");
//			Object response = "funnest ever";
//			//-1 means the response body is empty
//			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, -1);
		}
	};
	
	
	private void setupContext() {
		server.createContext("/", serverHandler);
	}

	/*
 Users must register in order to use Family Map.
 @param user is required for registration to complete.
 */


	/*
//    A user can login by providing their credentials (username and password).
//     */
//	public void login(String username, String password) {}
//
//	/*
//    By providing personID, a person can be found in the database.
//     */
//	public Person getPerson(String personID) {
//		return null;
//	}
//
//	/*
//    Returns a list of all the family members of the given person per their personID
//     */
//	public ArrayList<Person> getAllFamilyMembers(String personID) {
//		return null;
//	}
//
//	/*
//     Deletes ALL data from the database, including user accounts, auth tokens, and
//    generated person and event data.
//     */
//	public void clear() {
//	}
//
//	/*
//    <p>Populates the server's database with generated data for the specified user name.
//    The required "username" parameter must be a user already registered with the server. If there is
//    any data in the database already associated with the given user name, it is deleted.</p>
//     */
//	public void fill(String username) {
//	}
//
//	/*
//    Returns list of all events related to a person and their family members, by way of the person's ID
//     */
//	public ArrayList<Event> getAllEvents(String personID) {
//		return null;
//	}
	
	public static void main(String[] args) {
		new ServerCommunicator().run();
	}

}
