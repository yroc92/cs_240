package server.commonClasses.resourceClasses;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import server.Database;
import server.commonClasses.modelClasses.ResponseMessage;
import server.services.FamilyService;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;


/**
 * Created by Cory on 3/15/17.
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
        String username = pathParts[2];
        // If username is in the database, go on with the fill handler function.
        if (db.getUserDao().getUserByUserName(username) != null) {
            // Check if the generations path part exists
            if (pathParts.length == 3) {
                // Use the fill handler without the generations path
                fill(exchange, username, 0);
                return;
            }
            if (pathParts.length == 4) {
                if (Integer.parseInt(pathParts[3]) >= 0) {
                    // Use the fill handler with generations path included.
                    fill(exchange, username, Integer.parseInt(pathParts[3]));
                    return;
                } else {
                    db.sendResponse(exchange, "Can't have negative generations.", HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    return;
                }
            }
        } else {
            // For when the user doesn't exist in the database.
            db.sendResponse(exchange, "Username " + username + " does not exist.", HttpURLConnection.HTTP_NOT_FOUND, 0);
            return;
        }
    }


    private void fill(HttpExchange exchange, String username, int generations) {
        FamilyService.FAMILY_SERVICE.removeRelatives(username);
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(exchange.getRequestBody());
    //        User newUser = gson.fromJson(inputStreamReader, User.class);
            System.out.println(inputStreamReader.toString());
            inputStreamReader.close();
            //TODO: incorporate generations function
//            FamilyService.FAMILY_SERVICE.createGenerations(generations);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

    }

    private void fill(HttpExchange exchange, String[] pathParts) throws IOException {
        PrintWriter printWriter = new PrintWriter(exchange.getResponseBody());
        String username = pathParts[1];
        try {
            if (pathParts.length == 2) {
//                FamilyService.FAMILY_SERVICE.updateGenerations();
            } else if (pathParts.length == 3) {
//                FamilyService.FAMILY_SERVICE.removeRelatives(username);
            }


            gson.toJson(new ResponseMessage(""), printWriter); // Write response
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        } catch (Exception e) {
            gson.toJson(new ResponseMessage(e.getMessage()), printWriter); // Write response
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_CONFLICT,0);
        } finally {
            printWriter.close();
        }
        return;
    }
}
