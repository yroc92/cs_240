package server.serverSideOnlyClasses.handlers;

import javax.xml.ws.spi.http.HttpExchange;

/**
 * Created by Cory on 2/17/17.
 *
 * This class exists solely to distinguish its handle method from other handlers.
 */
public class DoubleValueHandler {
    /*
    Generic constructor
     */
    public DoubleValueHandler() {
    }

    /*
    Handles the event for a duplicate value in the database as a result of an HTTP exchange.
     */
    public void handle(HttpExchange exchange) {

    }
}
