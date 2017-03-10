import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class ServerCommunicator {

	public static final int SERVER_PORT_NUMBER = 8080;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	
	private HttpServer server;
	
	private ServerCommunicator() {}

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
	
	private HttpHandler helloWorldHandler = new HttpHandler() {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			System.out.println("Hello World");
			
			//-1 means the response body is empty
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, -1);
		}
	};
	
	
	private void setupContext() {
		server.createContext("/", helloWorldHandler);
	}
	
	public static void main(String[] args) {
		new ServerCommunicator().run();
	}

}
