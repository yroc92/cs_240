package main.java.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class ServerCommunicator {

    public static final int SERVER_PORT_NUMBER = 8080;
    private static final int MAX_WAITING_CONNECTIONS = 10;
    private HttpServer server;
    private main.java.server.commonClasses.resourceClasses.UserResource userResource;
    private main.java.server.commonClasses.resourceClasses.ClearResource clearResource;
    private main.java.server.commonClasses.resourceClasses.FillResource fillResource;
    private main.java.server.commonClasses.resourceClasses.LoadResource loadResource;
    private main.java.server.commonClasses.resourceClasses.PersonResource personResource;
    private main.java.server.commonClasses.resourceClasses.EventResource eventResource;
    private main.java.server.commonClasses.resourceClasses.DefaultResource defaultResource;

    ServerCommunicator() {
        userResource = new main.java.server.commonClasses.resourceClasses.UserResource(new Database());
        clearResource = new main.java.server.commonClasses.resourceClasses.ClearResource(new Database());
        fillResource = new main.java.server.commonClasses.resourceClasses.FillResource(new Database());
        loadResource = new main.java.server.commonClasses.resourceClasses.LoadResource(new Database());
        personResource = new main.java.server.commonClasses.resourceClasses.PersonResource(new Database());
        eventResource = new main.java.server.commonClasses.resourceClasses.EventResource(new Database());
        defaultResource = new main.java.server.commonClasses.resourceClasses.DefaultResource();
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
            System.out.println("Could not create HTTP main.server: " + e.getMessage());
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
                    // In any other case:
                    defaultResource.handle(exchange);
            }

            exchange.close();
        }
    };

    /**
     * Create a context for our server.
     */
    private void setupContext() {
        server.createContext("/", serverHandler);
    }

    /**
     * Main function runs the server.
     * @param args
     */
    public static void main(String[] args) {
        new ServerCommunicator().run();
    }

}
