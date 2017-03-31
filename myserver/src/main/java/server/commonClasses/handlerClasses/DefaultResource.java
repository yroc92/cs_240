package main.java.server.commonClasses.handlerClasses;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Cory on 3/21/17.
 */
public class DefaultResource {

    /**
     * This handler runs when no other handler is appropriate.
     * @param exchange
     * @throws IOException
     */
    public void handle(HttpExchange exchange) throws IOException {
        //How to get the path from the URI
        URI uri = exchange.getRequestURI();
        String pathString = uri.getPath();

        if (pathString.equals("/")) {
            pathString = DEFAULT_FILE_NAME;
        }

        int responseCode = 0;
        int bodyIsEmptyCode = 0;
        Path path = Paths.get(HTTP_ROOT + pathString);
        byte[] result = new byte[0];
        try {
            result = Files.readAllBytes(path);
            responseCode = HttpURLConnection.HTTP_OK;
            bodyIsEmptyCode = 0;
        } catch (IOException error404) {
            try {
                path = Paths.get(HTTP_ROOT + LOC_404);
                result = Files.readAllBytes(path);
                responseCode = HttpURLConnection.HTTP_NOT_FOUND;
                bodyIsEmptyCode = 0;
            } catch (IOException cantReadLoc_404File) {
                bodyIsEmptyCode = -1;
                responseCode = HttpURLConnection.HTTP_NOT_FOUND;
            }
        }

        exchange.sendResponseHeaders(responseCode, bodyIsEmptyCode);

        OutputStream os = exchange.getResponseBody();
        os.write(result);
        os.close();
    }

    private static final String DEFAULT_FILE_NAME = "/index.html";
    private static final String LOC_404 = "/HTML/404.html";
    private static final String HTTP_ROOT = "web";

}
