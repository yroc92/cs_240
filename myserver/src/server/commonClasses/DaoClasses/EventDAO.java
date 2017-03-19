package server.commonClasses.DaoClasses;

import server.Database;
import server.commonClasses.modelClasses.Event;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Cory on 3/19/17.
 */
public class EventDAO {
    private Database db;

    public EventDAO(Database db) {
        this.db = db;
    }

    public void addEvent(Event newEvent) {

        try {
            String sqlStatement = "INSERT INTO event values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insert = this.db.prepare(sqlStatement);
            insert.setInt(1, newEvent.getEventID());
            insert.setInt(2, newEvent.getPersonID());
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

    public void addArrayOfEvents(Event[] events) throws SQLException {
        for (Event event : events) {
            Event newEvent = new Event(event.getEventID(), event.getPersonID(), event.getYear(),
                    event.getLatitude(), event.getLongitude(), event.getCountry(), event.getCity(),
                    event.getEventType(), event.getDescendant());
            addEvent(newEvent);
        }
    }
}
