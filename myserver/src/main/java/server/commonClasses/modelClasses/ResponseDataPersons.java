package main.java.server.commonClasses.modelClasses;

import java.util.ArrayList;

/**
 * Created by Cory on 3/21/17.
 * Used for formatting data arrays for response bodies.
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
