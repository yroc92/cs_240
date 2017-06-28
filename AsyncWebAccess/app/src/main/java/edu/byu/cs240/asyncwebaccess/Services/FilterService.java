package edu.byu.cs240.asyncwebaccess.Services;

import java.util.List;

import edu.byu.cs240.asyncwebaccess.ModelClasses.Event;
import edu.byu.cs240.asyncwebaccess.ModelClasses.Person;

/**
 * Created by corycooper on 4/18/17.
 */

public enum  FilterService {
    FILTER_SERVICE;
    FilterService() {
        allEvents = FamilyMapDataService.FAMILY_MAP_DATA_SERVICE.getEventList();
    }

    private List<Event> allEvents;
    public List<Event> filteredEvents;

    public Boolean birth;
    public Boolean baptism;
    public Boolean marriage;
    public Boolean death;
    public Boolean fatherSide;
    public Boolean motherSide;
    public Boolean maleEvents;
    public Boolean femaleEvents;

    public List<Event> getAllEvents() {
        return allEvents;
    }



    public void setAllEvents(List<Event> allEvents) {
        this.allEvents = allEvents;

    }

    public List<Event> getFilteredEvents() {
        return filteredEvents;
    }

    public void setFilteredEvents(List<Event> filteredEvents) {
        this.filteredEvents = filteredEvents;
    }




    public void updateFilteredEvents() {
        Boolean[] options = { birth, baptism, marriage, death, fatherSide, motherSide, maleEvents, femaleEvents };
        filteredEvents = allEvents;
        for (int i = 0; i < allEvents.size(); i++) {
            if (allEvents.get(i).getEventType().toLowerCase().contains("birth") && !birth) {
                filteredEvents.remove(allEvents.get(i));
            }
            if (allEvents.get(i).getEventType().toLowerCase().contains("baptism") && !baptism) {
                filteredEvents.remove(allEvents.get(i));
            }
            if (allEvents.get(i).getEventType().toLowerCase().contains("marriage") && !marriage) {
                filteredEvents.remove(allEvents.get(i));
            }
            if (allEvents.get(i).getEventType().toLowerCase().contains("death") && !death) {
                filteredEvents.remove(allEvents.get(i));
            }
            Person thisPerson = FamilyMapDataService.FAMILY_MAP_DATA_SERVICE.getPersonById(allEvents.get(i).getPersonID());
            if (thisPerson.getGender().equals("m") && !maleEvents) {
                filteredEvents.remove(allEvents.get(i));
            }
            if (thisPerson.getGender().equals("f") && !femaleEvents) {
                filteredEvents.remove(allEvents.get(i));
            }
        }
    }
}
