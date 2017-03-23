package main.java.server.commonClasses.resourceClasses;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import main.java.server.Database;
import main.java.server.commonClasses.modelClasses.Person;
import main.java.server.commonClasses.modelClasses.ResponseDataPersons;
import main.java.server.commonClasses.modelClasses.User;
import main.java.server.services.FamilyService;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Cory on 3/20/17.
 */
public class PersonResource {

    private Database db;
    private Gson gson;
    public PersonResource(Database database) {
        this.db = database;
        this.gson = new Gson();
    }

    public void handle(HttpExchange exchange, String[] pathParts) throws IOException {
        if (pathParts.length == 3) {
                getPersonByID(exchange, pathParts[2]);
                return;
            } else if (pathParts.length == 2) {
                getFamilyMembers(exchange);
                return;
            } else {
                // If they have an invalid number of path parts, send HTTP_NOT_FOUND
                db.sendResponse(exchange, "Invalid path", HttpURLConnection.HTTP_NOT_FOUND, 0);
                return;
            }
    }

    private void getFamilyMembers(HttpExchange exchange) throws IOException {
        // Check to see if this user is allowed to view info
        Headers headers = exchange.getRequestHeaders();
        String clientAuthorizationCode = headers.getFirst("Authorization");
        try {
            User clientUser = db.getAuthTokenDAO().getUserByAuthToken(clientAuthorizationCode);
            if (clientUser != null) {
                // Get the persons from the database.
                ArrayList<Person> familyMembers = FamilyService.FAMILY_SERVICE.getRelativePersons(clientUser.getUsername());
                ResponseDataPersons responseData = new ResponseDataPersons(familyMembers);
                db.sendResponse(exchange, responseData, HttpURLConnection.HTTP_OK, 0);
            } else {
                db.sendResponse(exchange, "Invalid auth token.", HttpURLConnection.HTTP_FORBIDDEN, 0);
            }
        } catch (SQLException e) {
            db.sendResponse(exchange, "Couldn't find your auth token", HttpURLConnection.HTTP_UNAUTHORIZED, 0);
            e.printStackTrace();
        }
    }

    private void getPersonByID(HttpExchange exchange, String personID) throws IOException {
        // Check to see if this user is allowed to view info
        Headers headers = exchange.getRequestHeaders();
        String clientAuthorizationCode = headers.getFirst("Authorization");
        try {
            // Get current user
            User clientUser = db.getAuthTokenDAO().getUserByAuthToken(clientAuthorizationCode);
            Person foundPerson = db.getPersonDAO().getPersonByPersonID(personID);

            if (foundPerson.getDescendant().equals(clientUser.getUsername())) {
                // Get the person from the database.
                if (foundPerson != null) {
                    // Send the response with the person object.
                    db.sendResponse(exchange, foundPerson, HttpURLConnection.HTTP_OK, 0);
                } else {
                    db.sendResponse(exchange, "PersonID: " + personID + " was not found.",
                            HttpURLConnection.HTTP_NOT_FOUND, 0);
                }
            } else {
                db.sendResponse(exchange, "Invalid auth token.", HttpURLConnection.HTTP_UNAUTHORIZED, 0);
            }

        } catch (SQLException e) {
            db.sendResponse(exchange, e.getMessage(), HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            e.printStackTrace();
        }
    }

}
