package server.commonClasses.DaoClasses;

import server.Database;
import server.commonClasses.modelClasses.Person;
import server.commonClasses.modelClasses.User;
import server.services.GetJson;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
            insert.setInt(5, newPerson.getPersonID());
            insert.setInt(6, newPerson.getFather());
            insert.setInt(7, newPerson.getMother());
            insert.setInt(8, newPerson.getSpouse());

            insert.execute();
            db.commitSqlStatement(insert);
            insert.close();
        } catch (SQLException e) {
            System.err.println("Could not add person");
            e.printStackTrace();
            db.rollback();
        }
    }

    public void addPersonFromUser(User newUser) {
        Person newPerson = new Person(newUser.getFirstName(), newUser.getLastName(), newUser.getPersonID(),
                newUser.getGender(), newUser.getUsername());
        addPerson(newPerson);
    }

    private Person finishCreatingPerson(Person p) {
        //TODO: implement this
        return p;
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