package main.java.server.commonClasses.modelClasses;

/**
 * Created by Cory on 2/17/17.
 *
 * The AuthToken class specifies a token a user will utilize to log in and authenticate to our main.test.java.server.
 */
public class AuthToken {
    /**
     * The authentication token code that a user actually uses.
     */
    private String authToken;

    /**
     * The personID associated with the user
     */
    private String personID;

    /**
     * Username of the owner of the token
     */
    private String userName;

    /**
     * Constructor requires all fields for initialization.
     * @param authToken The actual token.
     * @param personID The related personID.
     * @param userName The related userName.
     */
    public AuthToken(String authToken, String personID, String userName) {
        this.authToken = authToken;
        this.personID = personID;
        this.userName = userName;
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

    public String getUsername() { return userName; }

    public void setUsername(String userName) { this.userName = userName; }

    /**
     * Determines whether two auth tokens are equal. In combination with the
     * "loggedInService", this helps determine if a user can authenticate.
     * @param token1 first auth token
     * @param token2 second auth token
     * @return boolean
     */
    public boolean areEqual(AuthToken token1, AuthToken token2) {
        if (token1.getAuthToken() != token2.getAuthToken()) return false;
        if (token1.getPersonID() != token2.getPersonID()) return false;
        return token1.getUsername() == token2.getUsername();
    }
}
