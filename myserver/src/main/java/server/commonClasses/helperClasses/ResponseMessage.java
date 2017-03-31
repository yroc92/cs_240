package main.java.server.commonClasses.helperClasses;

/**
 * Created by Cory on 3/15/17.
 * Used for formatting messages in response bodies.
 */
public class ResponseMessage {
    private String message;

    public ResponseMessage(String message) {
        setMessage(message);
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
