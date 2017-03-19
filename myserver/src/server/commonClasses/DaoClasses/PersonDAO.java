package server.commonClasses.DaoClasses;

import server.Database;
import server.commonClasses.modelClasses.Person;
import server.commonClasses.modelClasses.User;
import server.services.GetJson;
import server.services.IdGenerator;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by Cory on 3/11/17.
 */
public class PersonDAO {

    private Database db;

    public PersonDAO(Database db) {
        this.db = db;
    }

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

            insert.execute();
            db.commitSqlStatement(insert);
        } catch (SQLException e) {
            System.err.println("Could not add person");
            e.printStackTrace();
            db.rollback();
        }
    }

    // Create a Person object out of a User object
    public Person convertUserToPerson(User newUser) {
        return new Person(newUser.getFirstName(), newUser.getLastName(), newUser.getPersonID(),
                newUser.getGender(), newUser.getUsername());
    }

    // Using the given JSON data, create a randomly generated Person object
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

    private Person finishCreatingPerson(Person p) {
        //TODO: implement this
        return p;
    }

    public void addArrayOfPersons(Person[] personsArray) throws SQLException {
        for (Person person : personsArray) {
            Person newPerson = new Person(person.getFirstName(), person.getLastName(), person.getPersonID(),
                    person.getGender(), person.getDescendant());
            addPerson(newPerson);
        }
    }

//    public void updateFatherID(String personID, String fatherID) {
//        String sqlStatement = "update Person set 'fatherID' ='" + fatherID + "' where personID = '" + personID + "'";
//        try {
//            Database.executeSqlStatement(sqlStatement);
//        } catch (SQLException e) {
//            System.out.println("Couldn't update fatherID");
//            e.printStackTrace();
//        }
//    }
    //TODO: updateMotherID
    //TODO: updateSpouseID
}