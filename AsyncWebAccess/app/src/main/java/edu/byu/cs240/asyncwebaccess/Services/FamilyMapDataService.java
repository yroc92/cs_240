package edu.byu.cs240.asyncwebaccess.Services;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs240.asyncwebaccess.ModelClasses.Event;
import edu.byu.cs240.asyncwebaccess.ModelClasses.Person;
import edu.byu.cs240.asyncwebaccess.ModelClasses.User;

/**
 * Created by corycooper on 4/18/17.
 */

public enum FamilyMapDataService {
    FAMILY_MAP_DATA_SERVICE;

    public List<Person> personList = new ArrayList<>();
    public List<Event> eventList = new ArrayList<>();
    public User currentUser;

    FamilyMapDataService() {
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public Person getPersonById(String personId) {
        for (Person p : personList) {
            if (p.getPersonID().equals(personId)) {
                return p;
            }
        }
        return null;
    }
}
