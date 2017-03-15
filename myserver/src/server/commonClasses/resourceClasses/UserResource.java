package server.commonClasses.resourceClasses;


import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import server.Database;
import server.commonClasses.DaoClasses.UserDAO;
import server.commonClasses.GsonEncodeDecoder;
import server.commonClasses.modelClasses.Person;
import server.commonClasses.modelClasses.User;
import server.services.AuthService;
import server.services.IdGenerator;

import javax.xml.crypto.Data;
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
    private AuthService auth;
    private Gson gson;
    private Database db;

    public UserResource(Database db) {
        AuthService auth = new AuthService();
        gson = new Gson();
        this.db = db;
    }

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
            newUser.setPersonID(IdGenerator.ID_GENERATOR.getNewId());   // Give user an Id
            inputStreamReader.close();
            db.getUserDao().addUser(newUser);  // Add user to database
            db.getPersonDAO().addPersonFromUser(newUser);   // Create the corresponding person profile
//            createGenerations(newUser, 4); // Generate history of 4 generations
            PrintWriter printWriter = new PrintWriter(exchange.getResponseBody());
            gson.toJson(newUser, printWriter); // Write response
            printWriter.close();

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            return;
        } catch (SQLException e) {
            System.err.println("Trouble registering user");
            e.printStackTrace();
        }
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
