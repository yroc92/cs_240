package server.services;

import server.Database;
import server.commonClasses.modelClasses.Person;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Cory on 3/13/17.
 * This service enumerated class deals with the various operations concerning adding, removing, and
 * updating family relationships in the database.
 */
public enum FamilyService {
    FAMILY_SERVICE;

    Database db;
    private FamilyService() {
        db = new Database();
    }

    public void createGenerations(Person child, int numGenerations) {
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
    }

    public ArrayList<String> createParents() {
        ArrayList<String> parentIDs = new ArrayList<>();
        return parentIDs;
    }

    public void updateGenerations(Person child, int numGenerations) {
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
    }

//    public String[] getRelatives(String personID) {
//
//    }

    public void removeRelatives(String descendant) {
        String[] sqlStrings = {
                "DELETE FROM 'user' WHERE 'username' = ?",
                "DELETE FROM 'person' WHERE 'descendant' = ?",
                "DELETE FORM 'event' WHERE 'descendant' = ?"
        };
        try {
            for (String sql : sqlStrings) {
                PreparedStatement preparedStatement = db.prepare(sql);
                preparedStatement.setString(1, descendant);
                db.commitSqlStatement(preparedStatement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public Person generate();

}
