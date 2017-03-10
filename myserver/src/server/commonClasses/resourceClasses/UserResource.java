package server.commonClasses.resourceClasses;

import com.sun.net.httpserver.HttpExchange;
import server.commonClasses.DaoClasses.UserDAO;
import server.services.AuthService;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by Cory on 3/10/17.
 */
public class UserResource {
    private UserDAO dao = new UserDAO();
    private AuthService auth = new AuthService();

    public void handle(HttpExchange exchange, String[] pathParts) throws IOException {

        if ( pathParts.length < 3 ) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, -1);
            return;
        }
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

    private void registerUser(HttpExchange exchange) {
        //TODO - register user, build response send response
        //build response:
//        PrintWriter printWriter = new PrintWriter(exchange.getResponseBody());
//        gson.toJson(person, printWriter);
//
//        printWriter.close();
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
