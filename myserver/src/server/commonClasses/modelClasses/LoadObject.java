package server.commonClasses.modelClasses;

import java.util.ArrayList;

/**
 * Created by Cory on 3/19/17.
 * This class represents the format of the request data for the Load API
 * It includes arrays of the 3 main data types for our Family Map database.
 */
public class LoadObject {
    private ArrayList<Person> persons;
    private ArrayList<User> users;
    private ArrayList<Event> events;

    public LoadObject(ArrayList<Person> persons, ArrayList<User> users, ArrayList<Event> events) {
        this.persons = persons;
        this.users = users;
        this.events = events;
    }

    public ArrayList<Person> getPersons() { return persons; }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Event> getEvents() { return events; }
}
