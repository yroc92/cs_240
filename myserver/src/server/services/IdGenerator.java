package server.services;

import server.Database;

import java.io.*;
import java.sql.SQLException;

/**
 * Created by Cory on 3/14/17.
 *
 * Singleton used only to generate the personId for Users and Persons.
 * Writes this value to a file so that on reset, the same Id isn't used again.
 */
public enum IdGenerator {
    ID_GENERATOR;

    int id;

    /*
    The constructor starts the id at
     */
    IdGenerator() {
        // Assign id to be the last Id used, unless this is a fresh run.
        // In which case, let's start at 100.
        if (getLastId() == 0){
            id = 100;
        } else {
            id = getLastId();
        }
    }

    /*
    Increments the Id and returns it
     */
    public int getNewId() {
        id++;
        return id;
    }

    /*
    Stores the Id to a file so that it knows where the iterator
    is at upon a server restart.
     */
    private int getMaxId() throws SQLException {
        return
    }

}
