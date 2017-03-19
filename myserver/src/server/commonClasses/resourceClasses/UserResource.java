package server.commonClasses.resourceClasses;


import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import server.Database;
import server.commonClasses.modelClasses.ResponseMessage;
import server.commonClasses.modelClasses.Person;
import server.commonClasses.modelClasses.User;
import server.services.AuthService;
import server.services.FamilyService;
import server.services.IdGenerator;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.sql.SQLException;

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
        //TODO - register user
        //build response:
        PrintWriter printWriter = new PrintWriter(exchange.getResponseBody());
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(exchange.getRequestBody());
            User newUser = gson.fromJson(inputStreamReader, User.class);
            inputStreamReader.close();

            newUser.setPersonID(IdGenerator.ID_GENERATOR.getNewId());   // Give user an Id
            db.getUserDao().addUser(newUser);  // Add user to database

            Person thisPerson = db.getPersonDAO().convertUserToPerson(newUser);
            // Use the family service to create 4 generations of family. Pass in a converted Person object
            FamilyService.FAMILY_SERVICE.createGenerations(thisPerson, 4);
            db.getPersonDAO().addPerson(thisPerson);

            gson.toJson(newUser, printWriter); // Write response
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            return;
        } catch (SQLException e) {
//            System.err.println("error message" +e.getMessage());
//            e.printStackTrace();

            gson.toJson(new ResponseMessage(e.getMessage()), printWriter); // Write response
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_CONFLICT, 0);
        } finally {
            printWriter.close();
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
