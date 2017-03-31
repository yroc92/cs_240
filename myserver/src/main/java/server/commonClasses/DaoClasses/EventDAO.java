package main.java.server.commonClasses.DaoClasses;

import com.google.gson.JsonObject;
import main.java.server.Database;
import main.java.server.commonClasses.modelClasses.Event;
import main.java.server.services.GetJson;
import main.java.server.commonClasses.modelClasses.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Cory on 3/19/17.
 *
 * DAO for the Event Model.
 */
public class EventDAO {
    private Database db;

    public EventDAO(Database db) {
        this.db = db;
    }

    /**
     * Add an event to the database.
     * @param newEvent
     */
    public void addEvent(Event newEvent) {

        try {
            String sqlStatement = "INSERT INTO event values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insert = this.db.prepare(sqlStatement);
            insert.setString(1, newEvent.getEventID());
            insert.setString(2, newEvent.getPersonID());
            insert.setInt(3, newEvent.getYear());
            insert.setDouble(4, newEvent.getLatitude());
            insert.setDouble(5, newEvent.getLongitude());
            insert.setString(6, newEvent.getCountry());
            insert.setString(7, newEvent.getCity());
            insert.setString(8, newEvent.getEventType());
            insert.setString(9, newEvent.getDescendant());

            insert.execute();
            db.commitSqlStatement(insert);
        } catch (SQLException e) {
            System.err.println("Could not add event");
            e.printStackTrace();
            db.rollback();
        }
    }

    /**
     * Add an array of events to the database.
     * @param events
     * @throws SQLException
     */
    public void addArrayOfEvents(ArrayList<Event> events) throws SQLException {
        for (Event event : events) {
            addEvent(event);
        }
    }

    /**
     * Given a single person (and, optionally, their spouse),
     * generate realistic, random event data.
     * @param person
     * @param spouse
     * @return
     */
    public ArrayList<Event> generateEvents(Person person, Person spouse) {
        ArrayList<Event> events = new ArrayList<>();
        String[] eventTypes = {"birth", "baptism", "marriage", "death"};
        // Create a birth year. We'll make succeed events occur after this time.
        int birthYear = (int)(Math.random() * 405 + 1600);
        // The marriage location will be shared with the spouse.
        JsonObject marriageLocation = GetJson.GET_JSON_SINGLETON.getRandomLocation();

        for (int i = 0; i < eventTypes.length; i++) {
            JsonObject location = GetJson.GET_JSON_SINGLETON.getRandomLocation();
            if (i == 2) location = marriageLocation;
            events.add(new Event(UUID.randomUUID().toString(), person.getPersonID(), birthYear + (11 * i),
                    location.get("latitude").getAsDouble(), location.get("longitude").getAsDouble(),
                    location.get("country").getAsString(), location.get("city").getAsString(), eventTypes[i],
                    person.getDescendant()));
        }

        // If no spouse is provided, we're finished.
        if (spouse == null) {
            return events;
        }

        // Do the same algorithm for the spouse.
        for (int i = 0; i < eventTypes.length; i++) {
            JsonObject location = GetJson.GET_JSON_SINGLETON.getRandomLocation();
            if (i == 2) location = marriageLocation;
            events.add(new Event(UUID.randomUUID().toString(), spouse.getPersonID(), birthYear + (11 * i),
                    location.get("latitude").getAsDouble(), location.get("longitude").getAsDouble(),
                    location.get("country").getAsString(), location.get("city").getAsString(), eventTypes[i],
                    spouse.getDescendant()));
        }

        return events;
    }

    /**
     * Return an event by searching for it by it's ID
     * @param eventID
     * @return
     * @throws SQLException
     */
    public Event getEventByEventID(String eventID) throws SQLException {
        String findEventSql = "SELECT * FROM event WHERE eventID = ?";
        PreparedStatement findEventStmt = null;
        ResultSet findEventResults;
        // Search the database for qualifying person.
        try {
            findEventStmt = db.prepare(findEventSql);
            findEventStmt.setString(1, eventID);
            findEventResults = findEventStmt.executeQuery();

            // Return the result set object if it is valid.
            if (findEventResults.next()) {
                Event foundEvent = new Event(findEventResults.getString("eventID"),
                        findEventResults.getString("personID"),
                        findEventResults.getInt("year"),
                        findEventResults.getDouble("latitude"),
                        findEventResults.getDouble("longitude"),
                        findEventResults.getString("country"),
                        findEventResults.getString("city"),
                        findEventResults.getString("eventType"),
                        findEventResults.getString("descendant"));
                return foundEvent;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Failed to find event, " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (findEventStmt != null) {
                findEventStmt.close();
            }
        }
        return null;
    }
}
