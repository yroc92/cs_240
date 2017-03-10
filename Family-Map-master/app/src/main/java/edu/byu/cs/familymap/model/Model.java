package edu.byu.cs.familymap.model;

import android.graphics.Color;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.byu.cs.familymap.data.DataImporter;
import edu.byu.cs.familymap.httpclient.Server;
import edu.byu.cs.familymap.ui.activities.MainActivity;

/**
 * This is my holder for all of the static members.
 * Created by cole on 3/12/16.
 */
public class Model {
    public static void init(MainActivity ma) {
        mainActivity = ma;
        server = new Server();
        currentPerson = new Person();
        people = new HashMap<>();
        maternalAncestors = new HashSet<>();
        paternalAncestors = new HashSet<>();
        eventTypes = new HashSet<>();
        events = new HashMap<>();
        eventMarkersToEvents = new HashMap<>();
        peopleToEventMarkers = new HashMap<>();
        dataImporter = new DataImporter();
        filters = new ArrayList<>();
        settings = new Settings();
    }

    private static MainActivity mainActivity;

    private static Server server;

    /**
     * The current logged in user.
     */
    private static Person currentPerson;

    /**
     * A Map of all of the people.
     * @key PersonId as type String
     * @value Person
     */
    private static Map<String, Person> people;

    /**
     * A Set of PersonIds (type String) that represent all of the ancestors related to
     * the user's mother.
     */
    private static Set<String> maternalAncestors;

    /**
     * A Set of PersonId (type String) that represent all of the ancestors related to
     * the user's father.
     */
    private static Set<String> paternalAncestors;

    /**
     * A Set of Event are the types of events that are present.
     */
    private static Set<String> eventTypes;

    /**
     * A Map that represents all of the events.
     * @key EventId as type String
     * @value Event
     */
    private static Map<String, Event> events;

    /**
     * A Map that represents the relationship of event markers to people.
     * @key Marker
     * @value EventId as type String
     */
    private static Map<Marker, String> eventMarkersToEvents;

    /**
     * A Map that represents the relationship of people to event markers.
     * @key PersonId as type String
     * @value MarkerOptions
     */
    private static Map<String, Marker> peopleToEventMarkers;

    /**
     * This is the static version of the DataImporter.
     */
    private static DataImporter dataImporter;

    /**
     * This represents the filters that are available.
     */
    private static List<Filter> filters;

    /**
     * This represents the person that is currently selected by the user.
     */
    private static Person focusedPerson;

    /**
     * This holds all of the current settings for the user.
     */
    private static Settings settings;

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public static Map<String, Person> getPeople() {
        return people;
    }

    public static void setPeople(Map<String, Person> people) {
        Model.people = people;
    }

    public static Person getCurrentPerson() {
        return currentPerson;
    }

    public static void setCurrentPerson(Person currentPerson) {
        Model.currentPerson = currentPerson;
    }

    public static Server getServer() {
        return server;
    }

    public static Set<String> getMaternalAncestors() {
        return maternalAncestors;
    }

    public static void setMaternalAncestors(Set<String> maternalAncestors) {
        Model.maternalAncestors = maternalAncestors;
    }

    public static Set<String> getPaternalAncestors() {
        return paternalAncestors;
    }

    public static void setPaternalAncestors(Set<String> paternalAncestors) {
        Model.paternalAncestors = paternalAncestors;
    }

    public static void setEventTypes(Set<String> eventTypes) {
        Model.eventTypes = eventTypes;
    }

    public static Set<String> getEventTypes() {
        return eventTypes;
    }

    public static Map<String, Event> getEvents() {
        return events;
    }

    public static void setEvents(Map<String, Event> events) {
        Model.events = events;
    }

    public static Map<Marker, String> getEventMarkersToEvents() {
        return eventMarkersToEvents;
    }

    public static Map<String, Marker> getPeopleToEventMarkers() {
        return peopleToEventMarkers;
    }

    public static DataImporter getDataImporter() {
        return dataImporter;
    }


    public static List<Filter> getFilters() {
        return filters;
    }

    public static Settings getSettings() {
        return settings;
    }

    public static void setSettings(Settings settings) {
        Model.settings = settings;
    }

    public static Person getFocusedPerson() {
        return focusedPerson;
    }

    public static void setFocusedPerson(Person focusedPerson) {
        Model.focusedPerson = focusedPerson;
    }

}
