package server.services;

/**
 * Created by Cory on 3/14/17.
 */
public enum IdGenerator {
    ID_GENERATOR;

    int id;

    IdGenerator() {
        id = 0;
    }

    public int getNewId() {
        return id + 1;
    }

}
