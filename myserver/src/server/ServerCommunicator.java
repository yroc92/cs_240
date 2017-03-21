package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import server.commonClasses.resourceClasses.*;

public class ServerCommunicator {

    public static final int SERVER_PORT_NUMBER = 8080;
    private static final int MAX_WAITING_CONNECTIONS = 10;
    private HttpServer server;
    private UserResource userResource;
    private ClearResource clearResource;
    private FillResource fillResource;
    private LoadResource loadResource;
    private PersonResource personResource;
    private EventResource eventResource;
    private DefaultResource defaultResource;

    ServerCommunicator() {
        userResource = new UserResource(new Database());
        clearResource = new ClearResource(new Database());
        fillResource = new FillResource(new Database());
        loadResource = new LoadResource(new Database());
        personResource = new PersonResource(new Database());
        eventResource = new EventResource(new Database());
        defaultResource = new DefaultResource();
    }

    private void run() {
        Database.init();

        setUpServer(SERVER_PORT_NUMBER, MAX_WAITING_CONNECTIONS);
        setupContext();
        server.start();
    }

    private void setUpServer(int portNumber, int maxWaitingConnections) {
        try {
            server = HttpServer.create(new InetSocketAddress(portNumber),
                    maxWaitingConnections);
        } catch (IOException e) {
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

            String[] pathParts = path.split("/");
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

            // Case statements for the different endpoints
            if (pathParts.length < 2) {
                defaultResource.handle(exchange);
                return;
            }
            switch (pathParts[1]) {
                case "user":
                    userResource.handle(exchange, pathParts);
                    break;

                case "clear":
                    clearResource.handle(exchange, pathParts);
                    break;

                case "fill":
                    fillResource.handle(exchange, pathParts);
                    break;

                case "load":
                    loadResource.handle(exchange, pathParts);
                    break;

                case "person":
                    personResource.handle(exchange, pathParts);
                    break;

                case "event":
                    eventResource.handle(exchange, pathParts);
                    break;

                default:
                    // 404 error
                    defaultResource.handle(exchange);
            }

            exchange.close();
        }
    };


    private void setupContext() {
        server.createContext("/", serverHandler);
    }

    public static void main(String[] args) {
        new ServerCommunicator().run();
    }

}
