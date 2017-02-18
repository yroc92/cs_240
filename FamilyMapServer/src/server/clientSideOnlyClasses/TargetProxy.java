package server.clientSideOnlyClasses;

import server.commonClasses.modelClasses.AuthToken;
import server.commonClasses.modelClasses.Person;
import server.commonClasses.modelClasses.User;

/**
 * Created by Cory on 2/17/17.
 * This is the server facade. It is how the client will correspond with our server's API.
 * Notice that this class is essentially the same as our ServerCommunicator class.
 */
public class TargetProxy {

    /*
    Generic constructor.
     */
    public TargetProxy() {
    }

    /*
         Users must register in order to use Family Map.
         @param user is required for registration to complete.

         */
    public void registerUser(server.commonClasses.modelClasses.User user) {}

    /*
    A user can login by providing a user class object and the corresponding authentication token.
     */
    public void login(User user, AuthToken token) {}

    /*
    By providing a first name and a last name, a person can be found in the database.
     */
    public Person getPerson(String firstName, String lastName) {
        return null;
    }
}
