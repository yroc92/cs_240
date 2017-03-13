package server.commonClasses;

import com.google.gson.Gson;
import com.oracle.javafx.jmx.json.JSONDocument;
import server.commonClasses.modelClasses.User;

import java.io.InputStreamReader;

/**
 * Created by Cory on 2/17/17.
 *
 * This class includes methods that convert JSON into Java Objects and vice-versa.
 */
public class GsonEncodeDecoder {

    private Gson gson;

    /*
    Generic constructor
     */
    public GsonEncodeDecoder() {
    }

    /*
    Given a Java object parameter, encode it to JSON and return it as a string.
     */
    public String encodeToJSON(Object o) {
        return gson.toJson(o);
    }

    /*
    Given a JSON document object, return a Java object that we can use in code.
     */
    public Object decodeFromJSON(InputStreamReader inputStreamReader, Class<Object> objectClass) {
        return gson.fromJson(inputStreamReader, objectClass);
    }
}
