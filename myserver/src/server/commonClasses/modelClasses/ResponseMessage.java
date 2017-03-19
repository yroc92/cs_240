package server.commonClasses.modelClasses;

/**
 * Created by Cory on 3/15/17.
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
