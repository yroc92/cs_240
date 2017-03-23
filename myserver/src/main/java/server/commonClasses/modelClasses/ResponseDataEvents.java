package main.java.server.commonClasses.modelClasses;

import java.util.ArrayList;

/**
 * Created by Cory on 3/21/17.
 * Used for formatting data arrays for response bodies.
 */
public class ResponseDataEvents {
    private ArrayList<Event> data;

    public ResponseDataEvents(ArrayList<Event> events) {
        this.data = events;
    }

    public ArrayList<Event> getData() {
        return data;
    }

    public void setData(ArrayList<Event> events) {
        this.data = events;
    }

    public void addEventToData(Event event) {
        data.add(event);
    }

    public void clearData() {
        data.clear();
    }


}
