package server.commonClasses.modelClasses;

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
    private String personID;
    /*
    Time and location of the event
     */
    private int year;
    private Double latitude;
    private Double longitude;
    private String country;
    private String city;
    /*
    A brief description of the event.
     */
    private String description;
    /*
    Name of the
     */
    private String descendant;
    /*
    Constructor that takes in all fields as a parameter to initialize an Event instance.
     */
    public Event(String eventID, String personID, int year, Double latitude, Double longitude,
                 String country, String city, String description, String descendant) {
        this.eventID = eventID;
        this.personID = personID;
        this.year = year;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.description = description;
        this.descendant = descendant;
    }

    public void setEventID(String eventID) {

        this.eventID = eventID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getEventID() {

        return eventID;
    }

    public String getPersonID() {
        return personID;
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

    public String getDescription() {
        return description;
    }

    public String getDescendant() {
        return descendant;
    }
}
