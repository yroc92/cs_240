package test.java.server;

import main.java.server.commonClasses.DaoClasses.PersonDAO;
import org.testng.annotations.Test;
import main.java.server.*;
import main.java.server.commonClasses.DaoClasses.AuthTokenDAO;
import main.java.server.commonClasses.modelClasses.AuthToken;
import main.java.server.commonClasses.modelClasses.Event;
import main.java.server.commonClasses.modelClasses.Person;
import main.java.server.commonClasses.modelClasses.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Cory on 3/21/17.
 */
class DAOTest {

    @Test
    public void testAddOneOfEachModelToDatabase() {
        Database db = new Database();
        // Create objects
        AuthToken token = new AuthToken("abc124", "xyz999", "userName99");
        Event event = new Event("eventID", "personID", 1999, 30.0, -6.1,
                "country", "city","eventType", "userName99");
        Person person = new Person("Cory", "Cooper", "xyz999", "m", "userName99",
                "father111", "mother111", "spouse111");
        User user = new User("userName99", "mySecret", "mail@mail.com",
                "Cory", "Cooper", "m");

        // Run database add methods
        AuthTokenDAO authTokenDAO = new AuthTokenDAO(db);
        try {
            authTokenDAO.addAuthToken(token);
            db.getEventDAO().addEvent(event);
            db.getPersonDAO().addPerson(person);
            db.getUserDao().addUser(user);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testGeneratePerson() {
        Database db = new Database();
        // GENERATE A PERSON:
        Person person = db.getPersonDAO().generatePerson("m", "descendant101");
        assert person.getDescendant() == "descendant101";
        assert person.getGender() == "m";
        assert person.getFather() == "";
        assert person.getMother() == "";
        assert person.getSpouse() == "";
        assert person.getFirstName() != "";
        assert person.getLastName() != "";
        assert person.getPersonID().getClass() == String.class;
    }

    @Test
    public void testGenerateEvents() {
        Database db = new Database();
        // GENERATE AN ARRAY OF EVENTS:
        Person person = db.getPersonDAO().generatePerson("m", "descendant102");
        ArrayList<Event> events = db.getEventDAO().generateEvents(person, null);

        for (Event event : events) {
            assert event.getDescendant() == "descendant102";
            assert event.getPersonID() == person.getPersonID() && event.getPersonID().getClass() == String.class;
            assert event.getCity() != "";
            assert event.getCountry() != "";
            assert event.getEventID() != "" && event.getEventID().getClass() == String.class;
            assert event.getLatitude().getClass() == double.class;
            assert event.getLongitude().getClass() == double.class;
        }
    }

    @Test
    public void testGetPersonReturnsPerson() {
        Database db = new Database();
        Person newPerson = null;
        newPerson = db.getPersonDAO().generatePerson("m", "descendant");
        assert newPerson.getClass() == Person.class;
        String personID = newPerson.getPersonID();
        assert personID.getClass() == String.class;
        Person foundPerson = null;
        try {
            foundPerson = db.getPersonDAO().getPersonByPersonID(personID);
            assert foundPerson.getClass() == Person.class;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (foundPerson != null) {
                db.getPersonDAO().addPerson(foundPerson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}