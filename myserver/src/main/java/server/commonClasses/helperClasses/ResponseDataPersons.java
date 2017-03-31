package main.java.server.commonClasses.helperClasses;

import main.java.server.commonClasses.modelClasses.Person;

import java.util.ArrayList;

/**
 * Created by Cory on 3/21/17.
 * Used for formatting person data arrays for response bodies.
 */
public class ResponseDataPersons {
    private ArrayList<Person> data;

    public ResponseDataPersons(ArrayList<Person> persons) {
        this.data = persons;
    }

    public ArrayList<Person> getData() {
        return data;
    }

    public void setData(ArrayList<Person> persons) {
        this.data = persons;
    }

    public void addPersonToData(Person person) {
        data.add(person);
    }

    public void clearData() {
        data.clear();
    }


}
