package test.java.server;

import org.testng.annotations.Test;
import main.java.server.Database;
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
    public void testAddOneOfEachModelToDatabase() throws SQLException {
        Database db = new Database();
        // Create objects
        AuthToken token = new AuthToken("abc124", "xyz999", "userName99");
        Event event = new Event("eventID", "personID", 1999, 30.0, -6.1,
                "country", "city","eventType", "userName99");
        Person person = new Person("Cory", "Cooper", "xyz999", "m", "userName99",
                "father111", "mother111", "spouse111");
        User user = new User("userName99", "mySecret", "mail@mail.com",
                "Cory", "Cooper", "m");

        // Run database add functions
        AuthTokenDAO authTokenDAO = new AuthTokenDAO(db);
        authTokenDAO.addAuthToken(token);
        db.getEventDAO().addEvent(event);
        db.getPersonDAO().addPerson(person);
        db.getUserDao().addUser(user);

        String[] tables = {"auth_token", "event", "person", "user"};
        String tablesSql = "SELECT * FROM ?";
        PreparedStatement countStmt = null;
        ResultSet rs = null;

        for (int i = 0; i < tables.length; i++) {
            countStmt = db.prepare(tablesSql);
            countStmt.setString(1, tables[i]);
            rs = countStmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
            }
            // There should be exactly one thing added to this table.
            assert count == 1;
            countStmt.close();
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
        Person person = db.getPersonDAO().generatePerson("m", "descendant101");
        ArrayList<Event> events = db.getEventDAO().generateEvents(person, null);

        for (Event event : events) {
            assert event.getDescendant() == "descendant101";
            assert event.getPersonID() == person.getPersonID() && event.getPersonID().getClass() == String.class;
            assert event.getCity() != "";
            assert event.getCountry() != "";
            assert event.getEventID() != "" && event.getEventID().getClass() == String.class;
            assert event.getLatitude().getClass() == double.class;
            assert event.getLongitude().getClass() == double.class;
        }
    }
        // GENERATE AN ARRAY OF EVENTS
//        ArrayList<Event> events = db.getEventDAO().generateEvents(person, null);
    @Test
    public void testGetPersonReturnsPerson() {
        Database db = new Database();
        String personID = "abcde";
        try {
            assert db.getPersonDAO().getPersonByPersonID(personID).getClass() == Person.class;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}