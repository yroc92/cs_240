package server.commonClasses.resourceClasses;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import server.Database;
import server.commonClasses.modelClasses.Event;
import server.commonClasses.modelClasses.Person;
import server.commonClasses.modelClasses.User;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.sql.SQLException;


/**
 * Created by Cory on 3/19/17.
 * Load handler to perform the actions on the '/load' endpoint.
 * Will clear the database and then insert what is requested.
 */
public class LoadResource {

    private Database db;
    private Gson gson;

    public LoadResource(Database db) {
        this.db = db;
        gson = new Gson();
    }

    public void handle(HttpExchange exchange, String[] pathParts) throws IOException {
        // Be sure the client request is a correct path
        if (pathParts.length != 2) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, -1);
            return;
        }
        // Clear database
        boolean clearedDatabase = clearDatabase(exchange);
        if (clearedDatabase) {
            loadDatabase(exchange);
        }

    }


    /**
     * Clears the database.
     * @param exchange: the HTTP exchange.
     * return boolean: true if succeeded, false if failed.
     */
    private boolean clearDatabase(HttpExchange exchange) throws IOException {
        String[] clearStatments = {"DROP TABLE IF EXISTS 'user'", "DROP TABLE IF EXISTS 'person'",
                "DROP TABLE IF EXISTS 'event'", "DROP TABLE IF EXISTS 'auth_token'"};
        try {
            for (String statement : clearStatments) {
                Database.conn.prepareStatement(statement).execute();
                db.commitSqlStatement();
            }
            db.createAllTables();

        } catch (Exception e) {
            db.sendResponse(exchange, e.getMessage(), HttpURLConnection.HTTP_CONFLICT, 0);
            return false;
        }
        return true;
    }

    /**
     * Sorts through the JSON response to add all the new content.
     * @param exchange: the HTTP exchange.
     */
    private void loadDatabase(HttpExchange exchange) throws IOException {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(exchange.getRequestBody());
            User[] usersArray = gson.fromJson(inputStreamReader, User[].class);
            Person[] personsArray = gson.fromJson(inputStreamReader, Person[].class);
            Event[] eventsArray = gson.fromJson(inputStreamReader, Event[].class);
            inputStreamReader.close();
            // Add the arrays of the objects to the database
            db.getUserDao().addArrayOfUsers(usersArray);
            db.getPersonDAO().addArrayOfPersons(personsArray);
            db.getEventDAO().addArrayOfEvents(eventsArray);
            String responseMessage = "Successfully added " + usersArray.length + " users, " + personsArray.length +
                    " persons, and " + eventsArray.length + " events to the database.";
            db.sendResponse(exchange, responseMessage, HttpURLConnection.HTTP_ACCEPTED, 0);
        } catch (IOException e) {
            db.sendResponse(exchange, e.getMessage(), HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            e.printStackTrace();
        } catch (SQLException e) {
            db.sendResponse(exchange, e.getMessage(), HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            e.printStackTrace();
        }

    }
}