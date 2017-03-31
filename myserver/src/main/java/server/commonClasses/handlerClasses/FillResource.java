package main.java.server.commonClasses.handlerClasses;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import main.java.server.Database;
import main.java.server.commonClasses.modelClasses.Person;
import main.java.server.commonClasses.modelClasses.User;
import main.java.server.services.FamilyService;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.SQLException;


/**
 * Created by Cory on 3/15/17.
 *
 * Handler class that represents to the "/fill/[username]" endpoint.
 */
public class FillResource {
    private Database db;
    private Gson gson;

    public FillResource(Database db) {
        this.db = db;
        gson = new Gson();
    }

    public void handle(HttpExchange exchange, String[] pathParts) throws IOException {
        // If they have an invalid number of path parts, send HTTP_NOT_FOUND
        if (pathParts.length == 2 || pathParts.length > 4) {
            db.sendResponse(exchange, "Invalid path", HttpURLConnection.HTTP_NOT_FOUND, 0);
            return;
        }
        // Does the user already exist in the database?
        String userName = pathParts[2];
        try {
            if (pathParts.length == 3) fill(exchange, db.getUserDao().getUserByUserName(userName), 4);
            if (pathParts.length == 4) {
                if (Integer.parseInt(pathParts[3]) < 0) {
                    db.sendResponse(exchange, "Can't have negative generations.",
                            HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    return;
                } else {
                    fill(exchange, db.getUserDao().getUserByUserName(userName), Integer.parseInt(pathParts[3]));
                }
            }
        } catch (SQLException e) {
            db.sendResponse(exchange, e.getMessage(), HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            e.printStackTrace();
        }
    }


    /**
     * This method fills the database with new information relative to the username
     * @param exchange
     * @param user
     * @param generations
     * @throws IOException
     */
    private void fill(HttpExchange exchange, User user, int generations) throws IOException {
        // Does the
        try {
            // Delete the associated information of the user.
            if (user != null) FamilyService.FAMILY_SERVICE.removeRelatives(user.getUsername());
            // Send the response if the user is not in the database.
            else {
                db.sendResponse(exchange, "User must exist in the database.", HttpURLConnection.HTTP_NOT_FOUND, 0);
                return;
            }
            // Create a fresh person object out of the user
            Person newPerson = db.getPersonDAO().convertUserToPerson(user);
            // Add this new person to the database
            db.getPersonDAO().addPerson(newPerson);
            db.getEventDAO().generateEvents(newPerson, null);
            // Create the indicated number of generations.
            FamilyService.FAMILY_SERVICE.createGenerations(newPerson, generations);
            // Send the response
            String responseMessage = "Successfully added " +
                    (int) (Math.pow(2, generations + 1) - 1)  + " persons and " +
                    4 * (int) (Math.pow(2, generations + 1) - 1) + " events to the database.";
            db.sendResponse(exchange, responseMessage, HttpURLConnection.HTTP_OK, 0);
            return;
        } catch (SQLException e) {
            db.sendResponse(exchange, e.getMessage(), HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            e.printStackTrace();
        }

    }


}
