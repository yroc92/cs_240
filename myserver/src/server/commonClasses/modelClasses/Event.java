package server.commonClasses.modelClasses;

import server.commonClasses.GsonEncodeDecoder;


/**
 * Created by Cory on 2/17/17.
 *
 * Represents an event that occurred in the lifetime of a person.
 */
public class Event {
    /*
    Identification values for the event itself and the corresponding person.
     */
    private String eventID;
    private Person person;

    /*
    Time and location of the event
     */
    private int year;
    private Double latitude;
    private Double longitude;
    private String country;
    private String city;

    

    /*
    A brief EventType of the event.
     */
    private String eventType;
    /*
    User to which this person belongs.
     */
    private User descendant;
    /*
    Constructor that takes in all fields as a parameter to initialize an Event instance.
     */
    public Event(String eventID, Person person, int year, Double latitude, Double longitude,
                 String country, String city, String eventType, User descendant) {
        this.eventID = eventID;
        this.person = person;
        this.year = year;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.descendant = descendant;

    }

    public static void parseEvent(GsonEncodeDecoder source)  {

    }

    public void setEventID(String eventID) {

        this.eventID = eventID;
    }

    public void setperson(Person person) {
        this.person = person;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEventType(String EventType) {
        this.eventType = EventType;
    }

    public void setDescendant(User descendant) {
        this.descendant = descendant;
    }

    public String getEventID() {

        return eventID;
    }

    public Person getperson() {
        return person;
    }

    public int getYear() {
        return year;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getEventType() {
        return eventType;
    }

    public User getDescendant() {
        return descendant;
    }
}
