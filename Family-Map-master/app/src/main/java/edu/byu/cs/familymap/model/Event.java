package edu.byu.cs.familymap.model;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * This represents an event.
 * Created by cole on 3/26/16.
 */
public class Event implements Serializable {
    public Event(
            String eventId,
            String personId,
            double latitude,
            double longitude,
            String country,
            String city,
            String description,
            String year,
            String descendant
    ) {
        this.eventId = eventId;
        this.personId = personId;
        this.position = new LatLng(latitude, longitude);
        this.country = country;
        this.city = city;
        this.description = description;
        this.year = year;
        this.descendant = descendant;
    }

    private String eventId;

    private String personId;

    private LatLng position;

    private String country;

    private String city;

    private String description;

    private String year;

    private String descendant;

    public static Event parseEvent(JSONObject root) throws JSONException {
        String eventId = root.getString("eventID");
        String personId = root.getString("personID");
        double latitude = root.getDouble("latitude");
        double longitude = root.getDouble("longitude");
        String country = root.getString("country");
        String city = root.getString("city");
        String description = root.getString("description");
        String year = root.getString("year");
        String descendant = root.getString("descendant");

        Event event = new Event(
                eventId,
                personId,
                latitude,
                longitude,
                country,
                city,
                description,
                year,
                descendant
        );

        return event;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }
}
