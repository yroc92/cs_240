package server.services;

import server.Database;
import java.sql.SQLException;

/**
 * Created by Cory on 3/14/17.
 *
 * Singleton used only to generate the personId for Users and Persons.
 * Writes this value to a file so that on reset, the same Id isn't used again.
 */
public enum IdGenerator {
    // IdGenerator singleton
    ID_GENERATOR;

    // id is a generated PersonID for the Person table.
    private int id;
    private Database db;
    /*
    The constructor starts the id at
     */
    IdGenerator() {
        // Assign id to be the highest Id in the database.
        // If the highest is 0, then start our id counter at 100.
        this.db = new Database();
        try {
            int maxPersonID = this.db.getMaxPersonID();
            if (maxPersonID == 0){
                id = 100;
            } else {
                this.id = maxPersonID;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    Increments the Id and returns it
     */
    public int getNewId() {
        this.id++;
        return this.id;
    }

    /*
    Stores the Id to a file so that it knows where the iterator
    is at upon a server restart.
     */
    private int getMaxId() throws SQLException {
        return this.db.getMaxPersonID();
    }

}
