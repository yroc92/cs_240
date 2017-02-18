package server.serverSideOnlyClasses;

import server.commonClasses.modelClasses.AuthToken;
import server.commonClasses.modelClasses.Event;
import server.commonClasses.modelClasses.Person;
import server.commonClasses.modelClasses.User;

import java.util.ArrayList;

/**
 * Created by Cory on 2/17/17.
 *
 * This class is used to initiate communication with the Family Map Server
 */
public class ServerCommunicator {
    /*
    Generic constructor.
     */
    public ServerCommunicator() {
    }

    /*
     Users must register in order to use Family Map.
     @param user is required for registration to complete.
     */
    public void registerUser(server.commonClasses.modelClasses.User user) {}

    /*
    A user can login by providing their credentials (username and password).
     */
    public void login(String username, String password) {}

    /*
    By providing personID, a person can be found in the database.
     */
    public Person getPerson(String personID) {
        return null;
    }

    /*
    Returns a list of all the family members of the given person per their personID
     */
    public ArrayList<Person> getAllFamilyMembers(String personID) {
        return null;
    }

    /*
     Deletes ALL data from the database, including user accounts, auth tokens, and
    generated person and event data.
     */
    public void clear() {
    }

    /*
    <p>Populates the server's database with generated data for the specified user name.
    The required "username" parameter must be a user already registered with the server. If there is
    any data in the database already associated with the given user name, it is deleted.</p>
     */
    public void fill(String username) {
    }

    /*
    Returns list of all events related to a person and their family members, by way of the person's ID
     */
    public ArrayList<Event> getAllEvents(String personID) {
        return null;
    }
}
