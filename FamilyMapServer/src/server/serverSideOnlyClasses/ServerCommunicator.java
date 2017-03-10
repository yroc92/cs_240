package server.serverSideOnlyClasses;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import server.commonClasses.modelClasses.AuthToken;
import server.commonClasses.modelClasses.Event;
import server.commonClasses.modelClasses.Person;
import server.commonClasses.modelClasses.User;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.util.ArrayList;

/**
 * Created by Cory on 2/17/17.
 *
 * This class is used to initiate communication with the Family Map Server
 */

public class ServerCommunicator {
    // TODO: You need public strings that have directory paths like HTTP_ROOT here
    public static final int SERVER_PORT_NUMBER = 8080;
    private static final int MAX_WAITING_CONNECTIONS = 10;
    private HttpServer server;

    /*
    Generic constructor.
     */
    public ServerCommunicator() {
    }

    private void run() {
        setupServer(SERVER_PORT_NUMBER, MAX_WAITING_CONNECTIONS);
        setupContext();
        server.start();
    }

    private void setupServer(int portNumber, int maxWaitingConnections) {
        try {
            server = HttpServer.create(new InetSocketAddress(portNumber), maxWaitingConnections);
        } catch (IOException e) {
            System.out.println("Couldn't create server " + e.getMessage());
            e.printStackTrace();
            return;
        }
        server.setExecutor(null);   // use default executor
    }

    private HttpHandler serverHandler = new HttpHandler() {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            System.out.println("Server up!");
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, -1);
        }
    };

    private void setupContext() {
        server.createContext("/", serverHandler);
    }
    /*
     Users must register in order to use Family Map.
     @param user is required for registration to complete.
     */
    public void registerUser(server.commonClasses.modelClasses.User user) {}

    /*
    A user can login by providing their credentials (username and password).
     */
    public void login(String username, String password) {}

    /*
    By providing personID, a person can be found in the database.
     */
    public Person getPerson(String personID) {
        return null;
    }

    /*
    Returns a list of all the family members of the given person per their personID
     */
    public ArrayList<Person> getAllFamilyMembers(String personID) {
        return null;
    }

    /*
     Deletes ALL data from the database, including user accounts, auth tokens, and
    generated person and event data.
     */
    public void clear() {
    }

    /*
    <p>Populates the server's database with generated data for the specified user name.
    The required "username" parameter must be a user already registered with the server. If there is
    any data in the database already associated with the given user name, it is deleted.</p>
     */
    public void fill(String username) {
    }

    /*
    Returns list of all events related to a person and their family members, by way of the person's ID
     */
    public ArrayList<Event> getAllEvents(String personID) {
        return null;
    }

    public static void main(String[] args) {
        new ServerCommunicator().run();
    }
}
