package server.commonClasses.modelClasses;

/**
 * Created by Cory on 2/17/17.
 *
 * The AuthToken class specifies a token a user will utilize to log in and authenticate to our server.
 */
public class AuthToken {
    /*
    The authentication token code that a user actually uses.
     */
    private String authToken;

    /*
    The personID associated with the user
     */
    private String personID;

    /*
    Constructor requires all fields for initialization.
     */
    public AuthToken(String authToken, String personID) {
        this.authToken = authToken;
        this.personID = personID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
