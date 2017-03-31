package main.java.server.services;

import main.java.server.Database;
import main.java.server.commonClasses.modelClasses.Event;
import main.java.server.commonClasses.modelClasses.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Cory on 3/13/17.
 * This singleton class deals with the various operations concerning adding, removing, and
 * updating family relationships in the database. It cleans up complicated logic from other classes.
 */
public enum FamilyService {
    FAMILY_SERVICE;

    Database db;
    FamilyService() {
        db = new Database();
    }

    /**
     * Recursively generates parents for a given number of generations
     * @param child
     * @param numGenerations
     */
    public void createGenerations(Person child, int numGenerations) {
        // Base case: if no more generations are needed, return.
        if (numGenerations == 0) return;

        Person father = db.getPersonDAO().generatePerson("m", child.getDescendant()); // Create father
        Person mother = db.getPersonDAO().generatePerson("f", child.getDescendant()); // Create mother
        father.setSpouse(mother.getPersonID());
        mother.setSpouse(father.getPersonID());
        child.setFather(father.getPersonID());
        child.setMother(mother.getPersonID());

        // Recurse to make grandparents, etc.
        createGenerations(father, numGenerations - 1);
        db.getPersonDAO().addPerson(father);
        createGenerations(mother, numGenerations - 1);
        db.getPersonDAO().addPerson(mother);
        try {
            db.getEventDAO().addArrayOfEvents(db.getEventDAO().generateEvents(father, mother));
        } catch (SQLException e) {
            System.err.println("Failed to create events for new generations");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Returns a list of persons that have a common descendant.
     * @param descendant
     * @return
     * @throws SQLException
     */
    public ArrayList<Person> getRelativePersons(String descendant) throws SQLException {
        String findPersonsSql = "SELECT * FROM person WHERE descendant = ?";
        PreparedStatement findPersonsStmt = null;
        ResultSet findPersonResults;
        ArrayList<Person> relativePersons = new ArrayList<>();

        // Search the database for qualifying persons.
        try {
            findPersonsStmt = db.prepare(findPersonsSql);
            findPersonsStmt.setString(1, descendant);
            findPersonResults = findPersonsStmt.executeQuery();

            // Return the result set object if it is valid.
            while (findPersonResults.next()) {
                Person foundPerson = new Person(findPersonResults.getString("firstName"),
                        findPersonResults.getString("lastName"),
                        findPersonResults.getString("personID"),
                        findPersonResults.getString("gender"),
                        findPersonResults.getString("descendant"),
                        findPersonResults.getString("fatherID"),
                        findPersonResults.getString("motherID"),
                        findPersonResults.getString("spouseID"));
                relativePersons.add(foundPerson);
            }
            return relativePersons;
        } catch (SQLException e) {
            System.err.println("Failed to find persons, " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (findPersonsStmt != null) {
                findPersonsStmt.close();
            }
        }
        // If everything else fails...
        return null;
    }

    /**
     * Returns a list of events that share a common descendant.
     * @param descendant
     * @return
     * @throws SQLException
     */
    public ArrayList<Event> getRelativeEvents(String descendant) throws SQLException {
        String findEventsSql = "SELECT * FROM event WHERE descendant = ?";
        PreparedStatement findEventsStmt = null;
        ResultSet findEventResults;
        ArrayList<Event> relativeEvents = new ArrayList<>();

        // Search the database for qualifying persons.
        try {
            findEventsStmt = db.prepare(findEventsSql);
            findEventsStmt.setString(1, descendant);
            findEventResults = findEventsStmt.executeQuery();

            // Return the result set object if it is valid.
            while (findEventResults.next()) {
                Event foundEvent = new Event(findEventResults.getString("eventID"),
                        findEventResults.getString("personID"),
                        findEventResults.getInt("year"),
                        findEventResults.getDouble("latitude"),
                        findEventResults.getDouble("longitude"),
                        findEventResults.getString("country"),
                        findEventResults.getString("city"),
                        findEventResults.getString("eventType"),
                        findEventResults.getString("descendant"));
                relativeEvents.add(foundEvent);
            }
            return relativeEvents;
        } catch (SQLException e) {
            System.err.println("Failed to find events, " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (findEventsStmt != null) {
                findEventsStmt.close();
            }
        }
        // If everything else fails...
        return null;
    }

    /**
     * Removes all persons with a common descendant.
     * @param descendant
     * @throws SQLException
     */
    public void removeRelatives(String descendant) throws SQLException {
        // If the user isn't found in the database, stop this.
        if (db.getUserDao().getUserByUserName(descendant) == null) {
            return;
        }

        // Prepare to remove anything related to the descendant
        String[] sqlStrings = {
                " DELETE FROM 'person' WHERE descendant = ? ",
                " DELETE FROM 'event' WHERE descendant = ? "
        };
        try {
            for (String sql : sqlStrings) {
                PreparedStatement preparedStatement = db.prepare(sql);
                preparedStatement.setString(1, descendant);
                preparedStatement.executeUpdate();
                db.commitSqlStatement(preparedStatement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
