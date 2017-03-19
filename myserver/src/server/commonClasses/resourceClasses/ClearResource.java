package server.commonClasses.resourceClasses;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import server.Database;
import server.commonClasses.modelClasses.ResponseMessage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Cory on 3/13/17.
 * This is the handler for the 'clear endpoint'. Deals with clearing the entire database.
 */
public class ClearResource {

    private Database db;

    public ClearResource(Database db) {
        this.db = db;
    }

    public void handle(HttpExchange exchange, String[] pathParts) throws IOException {
        // If they have an invalid number of path parts, send HTTP_NOT_FOUND
        if (pathParts.length != 2) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, -1);
            return;
        }
        clearDatabase(exchange);
    }

    private void clearDatabase(HttpExchange exchange) throws IOException {
        PrintWriter printWriter = new PrintWriter(exchange.getResponseBody());
        Gson gson = new Gson();
        String[] clearStatments = {"DROP TABLE IF EXISTS 'user'", "DROP TABLE IF EXISTS 'person'",
                "DROP TABLE IF EXISTS 'event'", "DROP TABLE IF EXISTS 'auth_token'"};

        try {
            for (String statement : clearStatments) {
                Database.conn.prepareStatement(statement).execute();
                db.commitSqlStatement();
            }
            db.createAllTables();
            gson.toJson(new ResponseMessage("Clear Succeeded"), printWriter); // Write response
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