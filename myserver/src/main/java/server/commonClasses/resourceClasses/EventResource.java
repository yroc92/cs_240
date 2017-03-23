package main.java.server.commonClasses.resourceClasses;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import main.java.server.Database;
import main.java.server.commonClasses.modelClasses.Event;
import main.java.server.commonClasses.modelClasses.ResponseDataEvents;
import main.java.server.commonClasses.modelClasses.User;
import main.java.server.services.FamilyService;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Cory on 3/21/17.
 */
public class EventResource {
    private Database db;
    private Gson gson;
    public EventResource(Database database) {
        this.db = database;
        this.gson = new Gson();
    }

    /**
     * Handle the request for the path /event/
     * @param exchange
     * @param pathParts
     * @throws IOException
     */
    public void handle(HttpExchange exchange, String[] pathParts) throws IOException {
        if (pathParts.length == 3) {
            getEventByID(exchange, pathParts[2]);
            return;
        } else if (pathParts.length == 2) {
            getFamilyMemberEvents(exchange);
            return;
        } else {
            // If they have an invalid number of path parts, send HTTP_NOT_FOUND
            db.sendResponse(exchange, "Invalid path", HttpURLConnection.HTTP_NOT_FOUND, 0);
            return;
        }
    }

    /**
     * Called when the client requests all events for the current user.
     * @param exchange
     * @throws IOException
     */
    private void getFamilyMemberEvents(HttpExchange exchange) throws IOException {
        // Check to see if this user is allowed to view info
        Headers headers = exchange.getRequestHeaders();
        String clientAuthorizationCode = headers.getFirst("Authorization");
        try {
            User clientUser = db.getAuthTokenDAO().getUserByAuthToken(clientAuthorizationCode);
            if (clientUser != null) {
                // Get the events from the database.
                ArrayList<Event> relativeEvents = FamilyService.FAMILY_SERVICE.getRelativeEvents(clientUser.getUsername());
                ResponseDataEvents responseData = new ResponseDataEvents(relativeEvents);
                db.sendResponse(exchange, responseData, HttpURLConnection.HTTP_OK, 0);
            } else {
                db.sendResponse(exchange, "Invalid auth token.", HttpURLConnection.HTTP_UNAUTHORIZED, 0);
            }

        } catch (SQLException e) {
            db.sendResponse(exchange, e.getMessage(), HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            e.printStackTrace();
        }
    }

    /**
     * Called when the client requests a single event by its ID number
     * @param exchange
     * @param eventID
     * @throws IOException
     */
    private void getEventByID(HttpExchange exchange, String eventID) throws IOException {
        // Check to see if this user is allowed to view info
        Headers headers = exchange.getRequestHeaders();
        String clientAuthorizationCode = headers.getFirst("Authorization");
        try {
            // Get current user
            User clientUser = db.getAuthTokenDAO().getUserByAuthToken(clientAuthorizationCode);
            Event requestedEvent = db.getEventDAO().getEventByEventID(eventID);
            if (requestedEvent != null) {
                // Verify that user is allowed to see this event
                if (clientUser.getUsername().equals(requestedEvent.getDescendant())) {
                    db.sendResponse(exchange, requestedEvent, HttpURLConnection.HTTP_OK, 0);
                } else {
                    db.sendResponse(exchange, "You are not authorized to view this event.",
                            HttpURLConnection.HTTP_UNAUTHORIZED, 0);
                }
            } else {
                db.sendResponse(exchange, "Could not find event.", HttpURLConnection.HTTP_NOT_FOUND, 0);
            }

        } catch (SQLException e) {
            db.sendResponse(exchange, e.getMessage(), HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            e.printStackTrace();
        }
    }

}
