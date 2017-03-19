package server.commonClasses.DaoClasses;

import java.util.UUID;

/**
 * Created by Cory on 3/14/17.
 *
 * This class generates auth tokens for user login authentication.
 */
public class AuthTokenDAO {

    public AuthTokenDAO() {
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }
}
