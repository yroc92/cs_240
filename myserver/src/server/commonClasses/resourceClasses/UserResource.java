package server.commonClasses.resourceClasses;


import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import server.commonClasses.DaoClasses.PersonDAO;
import server.commonClasses.DaoClasses.UserDAO;
import server.commonClasses.GsonEncodeDecoder;
import server.commonClasses.modelClasses.Person;
import server.commonClasses.modelClasses.User;
import server.services.AuthService;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Cory on 3/10/17.
 */
public class UserResource {
    private UserDAO userDao = new UserDAO();
    private AuthService auth = new AuthService();
    private Gson gson = new Gson();

    public void handle(HttpExchange exchange, String[] pathParts) throws IOException {

        // If they have an invalid number of path parts, send HTTP_NOT_FOUND
        if ( pathParts.length != 3 ) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, -1);
            return;
        }
        // Distinguish the different paths
        switch (pathParts[2]) {
            case "register":
                registerUser(exchange);
                return;
            case "login":
                loginUser(exchange);
                return;
            default:
                // 404 error
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, -1);
                return;

        }

    }

    private void registerUser(HttpExchange exchange) throws IOException {
        //TODO - register user, build response send response
        //build response:
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(exchange.getRequestBody());
            User newUser = gson.fromJson(inputStreamReader, User.class);
            inputStreamReader.close();
            userDao.addUser(newUser); // Add user to database

//            createGenerations(newUser); // Generate history of 4 generations
            PrintWriter printWriter = new PrintWriter(exchange.getResponseBody());
            gson.toJson(newUser, printWriter); // Write response
            printWriter.close();

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            return;

        } catch (SQLException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_CONFLICT, -1);
            System.out.println("Failed to insert new user in DB");
            e.printStackTrace();
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_CONFLICT, -1);
            System.out.println("Failed to register new user");
            e.printStackTrace();
        } catch (NullPointerException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
            e.printStackTrace();
        }
        catch (Exception e) {
            System.out.println("Some exception was found");
            e.printStackTrace();
        }
    }

    private void createGenerations(User newUser) {
        // Add user as person to database
        PersonDAO personDao = new PersonDAO();
        //TODO Add parent ID's before you insert newUser into database
        personDao.addPerson(new Person(newUser.getFirstName(), newUser.getLastName(), null, newUser.getGender()));
        int generation = 1;
    }

    private ArrayList<String> createParents() {
        ArrayList<String> parentIDs = new ArrayList<>();
        return parentIDs;
    }

    private void loginUser(HttpExchange exchange) {
        //TODO - register user, build response send response
        //build response:
//        PrintWriter printWriter = new PrintWriter(exchange.getResponseBody());
//        gson.toJson(person, printWriter);
//
//        printWriter.close();

    }

}
