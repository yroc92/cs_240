package main.java.server.commonClasses.handlerClasses;


import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import main.java.server.Database;
import main.java.server.commonClasses.helperClasses.LoginObject;
import main.java.server.commonClasses.modelClasses.*;
import main.java.server.services.FamilyService;
import main.java.server.commonClasses.DaoClasses.AuthTokenDAO;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Cory on 3/10/17.
 *
 * This handler class deals with requests that begin with "/user"
 */
public class UserResource {
    private Gson gson;
    private Database db;

    public UserResource(Database db) {
        gson = new Gson();
        this.db = db;
    }

    public void handle(HttpExchange exchange, String[] pathParts) throws IOException {
        // If they have an invalid number of path parts, send HTTP_NOT_FOUND
        if (pathParts.length != 3) {
            db.sendResponse(exchange, "Bad pathway", HttpURLConnection.HTTP_NOT_FOUND, 0);
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
                db.sendResponse(exchange, "Bad pathway", HttpURLConnection.HTTP_NOT_FOUND, 0);
                return;
        }

    }

    /**
     * This method is called when the client requests "/user/register"
     * @param exchange
     * @throws IOException
     */
    private void registerUser(HttpExchange exchange) throws IOException {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(exchange.getRequestBody());
            User newUser = gson.fromJson(inputStreamReader, User.class);
            inputStreamReader.close();

            newUser.setPersonID(UUID.randomUUID().toString());  // Give user an Id
            newUser.setToken(UUID.randomUUID().toString());     // Give user an auth token
            db.getUserDao().addUser(newUser);  // Add user to database

            Person newPerson = db.getPersonDAO().convertUserToPerson(newUser);
            // Use the family service to create 4 generations of family. Pass in a converted Person object.
            FamilyService.FAMILY_SERVICE.createGenerations(newPerson, 4);
            db.getPersonDAO().addPerson(newPerson);
            ArrayList<Event> newPersonEvents = db.getEventDAO().generateEvents(newPerson, null);
            db.getEventDAO().addArrayOfEvents(newPersonEvents);

            // Create the response (an auth token) and add Auth token to the auth_token table.
            AuthToken newAuthToken = new AuthToken(newUser.getToken(), newUser.getPersonID(), newUser.getUsername());
            AuthTokenDAO authTokenDAO = new AuthTokenDAO(db);
            authTokenDAO.addAuthToken(newAuthToken);
            // Send the currently logged in user with this auth token.
            db.sendResponse(exchange, newAuthToken, HttpURLConnection.HTTP_OK, 0); // write response
            return;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            db.sendResponse(exchange, e.getMessage(), HttpURLConnection.HTTP_CONFLICT, 0);
        }
    }


    /**
     * This method is called when the client requests "/user/login"
     * @param exchange
     * @throws IOException
     */
    private void loginUser(HttpExchange exchange) throws IOException {
        // Import the request in the form of a login object.
        InputStreamReader inputStreamReader = new InputStreamReader(exchange.getRequestBody());
        LoginObject loginObject = gson.fromJson(inputStreamReader, LoginObject.class);
        inputStreamReader.close();

        try {
            User foundUser = db.getUserDao().login(loginObject.getUserName(), loginObject.getPassword());
            if (foundUser != null) {
                AuthToken returnedToken = new AuthToken(foundUser.getToken(), foundUser.getPersonID(), foundUser.getUsername());
                // Send the currently logged in user with this auth token.
                db.sendResponse(exchange, returnedToken, HttpURLConnection.HTTP_OK, 0);
            } else {
                db.sendResponse(exchange, "User not found.", HttpURLConnection.HTTP_NOT_FOUND, 0);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            db.sendResponse(exchange, e.getMessage(), HttpURLConnection.HTTP_NOT_FOUND, 0);
        }
    }

}
