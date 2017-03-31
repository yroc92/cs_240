package test.java.server;

import com.google.gson.JsonObject;
import main.java.server.Database;
import main.java.server.commonClasses.handlerClasses.ClearResource;
import main.java.server.commonClasses.modelClasses.Event;
import main.java.server.commonClasses.modelClasses.Person;
import main.java.server.services.FamilyService;
import main.java.server.services.GetJson;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by corycooper on 3/29/17.
 */
public class ServicesTest {
    private Database db;
    private ClearResource clear;
    private Person person = null;

    public ServicesTest() {
        db = new Database();
        clear = new ClearResource(db);
        try {
            person = db.getPersonDAO().generatePerson("m", "funUser");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeMethod
    public void setUp() throws Exception {
        db.getPersonDAO().addPerson(person);
        FamilyService.FAMILY_SERVICE.createGenerations(person, 5);
    }

    @Test
    public void verifyJsonReturnsProperType() {
        assert GetJson.GET_JSON_SINGLETON.getRandomMaleName().getClass() == String.class;
        assert GetJson.GET_JSON_SINGLETON.getRandomFemaleName().getClass() == String.class;
        assert GetJson.GET_JSON_SINGLETON.getRandomLocation().getClass() == JsonObject.class;
        assert GetJson.GET_JSON_SINGLETON.getRandomSurname().getClass() == String.class;
    }

    @Test
    public void getRelatedData() {
        try {
            ArrayList<Person> personArrayList = FamilyService.FAMILY_SERVICE.getRelativePersons(person.getDescendant());
            ArrayList<Event> eventArrayList = FamilyService.FAMILY_SERVICE.getRelativeEvents(person.getDescendant());
            for (Person obj : personArrayList) {
                assert obj.getClass() == Person.class;
            }
            for (Event obj : eventArrayList) {
                assert obj.getClass() == Event.class;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removeRelativesTest() {
        try {
            FamilyService.FAMILY_SERVICE.removeRelatives(person.getDescendant());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void tearDown() throws Exception {
        clear.publicClear();
    }
}
