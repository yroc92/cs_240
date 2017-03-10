package server.serverSideOnlyClasses.handlers;

import com.sun.corba.se.spi.activation.Server;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import server.serverSideOnlyClasses.ServerCommunicator;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created by Cory on 2/27/17.
 */
public class DefaultHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        String path = uri.getPath();

        System.out.println("Path= " + path);

        String [] pathParts = path.split("/");
        StringBuilder pathSegments = new StringBuilder();
        for (int i = 0; i < pathParts.length; i++ ) {
            pathSegments.append(pathParts[i]);
            if (i < pathParts.length - 1) {
                pathSegments.append(", ");
            }
        }
        System.out.println("Path parts = " + pathSegments.toString());

        byte [] result = null;
        switch (path) {
            case "/":
                result = getBytesFromFile("/index.html");
                break;
            case "/css/main.css":
                // HTTP_ROOT is the directory of the root of your files
                result = getBytesFromFile(path);
                break;
            case "/favicon.ico":
                result = getBytesFromFile(path);
                break;
            default:
                result = getBytesFromFile("/404.html");
                System.out.println("Unknown path + " + path);
        }

        // 0 means something is coming back
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        OutputStream os = exchange.getResponseBody();
        os.write(result);
        os.close();
    }

    private byte[] getBytesFromFile(String pathString) {
        int responseCode = 0;
        int bodyIsEmptyCode = 0;
        Path path = Paths.get(ServerCommunicator.HTTP_ROOT + pathString);
        byte[] result = new byte[0];
//        StringBuilder result = new StringBuilder();
        try {
            result = Files.readAllBytes(path);
            responseCode = HttpURLConnection.HTTP_OK;
            bodyIsEmptyCode = 0;
//            Scanner scan  = new Scanner(new BufferedReader(new FileReader(file)));
//            while (scan.hasNextLine()) {
//                result.append(scan.nextLine() + "\n");
//            }
        } catch (IOException error404) {
            try {
                path = Paths.get(ServerCommunicator.HTTP_ROOT + LOC_404);
                result = Files.readAllBytes(path);
                responseCode = HttpURLConnection.HTTP_NOT_FOUND;
                bodyIsEmptyCode = 0;

            } catch (IOException error404) {
                bodyIsEmptyCode = -1;
                responseCode = HttpURLConnection.HTTP_NOT_FOUND;
            }
//            e.printStackTrace();
        }
        return result.toString().getBytes();
    }
}
