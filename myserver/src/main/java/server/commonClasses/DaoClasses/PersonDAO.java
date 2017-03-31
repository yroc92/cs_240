package main.java.server.commonClasses.DaoClasses;

import main.java.server.Database;
import main.java.server.services.GetJson;
import main.java.server.commonClasses.modelClasses.Person;
import main.java.server.commonClasses.modelClasses.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Cory on 3/11/17.
 *
 * DAO for the Person Model
 */
public class PersonDAO {

    private Database db;

    public PersonDAO(Database db) {
        this.db = db;
    }

    /**
     * Adds a person object to the database.
     * @param newPerson The new person to be added to the database.
     */
    public void addPerson(Person newPerson) {

        try {
            String sqlStatement = "INSERT INTO person values (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insert = this.db.prepare(sqlStatement);
            insert.setString(1, newPerson.getFirstName());
            insert.setString(2, newPerson.getLastName());
            insert.setString(3, newPerson.getGender());
            insert.setString(4, newPerson.getDescendant());
            insert.setString(5, newPerson.getPersonID());
            insert.setString(6, newPerson.getFather());
            insert.setString(7, newPerson.getMother());
            insert.setString(8, newPerson.getSpouse());

            insert.executeUpdate();
            db.commitSqlStatement(insert);
        } catch (SQLException e) {
            System.err.println("Could not add person");
            e.printStackTrace();
            db.rollback();
        }
    }

    /**
     * Retrieves a person object from the database given their personID
     * @param personID
     * @return The found person object.
     * @throws SQLException
     */
    public Person getPersonByPersonID(String personID) throws SQLException {
        String findPersonSql = "SELECT * FROM person WHERE personID = ?";
        PreparedStatement findPersonStmt = null;
        ResultSet findPersonResults;
        // Search the database for qualifying person.
        try {
            findPersonStmt = db.prepare(findPersonSql);
            findPersonStmt.setString(1, personID);
            findPersonResults = findPersonStmt.executeQuery();

            // Return the result set object if it is valid.
            if (findPersonResults.next()) {
                Person foundPerson = new Person(findPersonResults.getString("firstName"),
                        findPersonResults.getString("lastName"),
                        findPersonResults.getString("personID"),
                        findPersonResults.getString("gender"),
                        findPersonResults.getString("descendant"),
                        findPersonResults.getString("fatherID"),
                        findPersonResults.getString("motherID"),
                        findPersonResults.getString("spouseID"));
                return foundPerson;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Failed to find person, " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (findPersonStmt != null) {
                findPersonStmt.close();
            }
        }
        return null;
    }

    /**
     * Create a Person object out of a User object
     * @param newUser User object to be converted.
     * @return a new-found person object.
     */
    public Person convertUserToPerson(User newUser) {
        return new Person(newUser.getFirstName(), newUser.getLastName(), newUser.getPersonID(),
                newUser.getGender(), newUser.getUsername());
    }

    /**
     * Using the given JSON data, create a randomly generated Person object.
     * @param gender The determined gender of this new person.
     * @param descendant The originator of this person.
     * @return A newly created person.
     */
    public Person generatePerson(String gender, String descendant) {
        String firstname;
        if (gender == "m") {
            firstname = GetJson.GET_JSON_SINGLETON.getRandomMaleName();
        } else {
            firstname = GetJson.GET_JSON_SINGLETON.getRandomFemaleName();
        }
        return new Person(firstname, GetJson.GET_JSON_SINGLETON.getRandomSurname(),
                UUID.randomUUID().toString(), gender, descendant);
    }

    /**
     * Simply iterates over an array of persons and adds them all to the database.
     * @param personsArray an array of person objects.
     * @throws SQLException in case there's an error with adding to the database.
     */
    public void addArrayOfPersons(ArrayList<Person> personsArray) throws SQLException {
        for (Person person : personsArray) {
            addPerson(person);
        }
    }
}