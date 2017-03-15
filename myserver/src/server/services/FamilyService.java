package server.services;

import server.Database;
import server.commonClasses.DaoClasses.PersonDAO;
import server.commonClasses.modelClasses.Person;
import server.commonClasses.modelClasses.User;

import java.util.ArrayList;

/**
 * Created by Cory on 3/13/17.
 */
public class FamilyService {
    public FamilyService() {
    }

    public void createGenerations(User newUser, int numGenerations) {
        // Add user as person to database
        Database db = new Database();
//        db.getPersonDAO().addPerson();

        //TODO Add parent ID's before you insert newUser into database
    }

    public ArrayList<String> createParents() {
        ArrayList<String> parentIDs = new ArrayList<>();
        return parentIDs;
    }

//    public Person generate();

}
