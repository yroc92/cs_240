package edu.byu.cs.familymap.model;

import com.google.android.gms.maps.model.Marker;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by cole on 3/31/16.
 */
public class Filter implements Serializable {

    public Filter(String eventType, String filterTitle, String filterSubTitle) {
        this.eventType = eventType;
        this.filterTitle = filterTitle;
        this.filterSubTitle= filterSubTitle;
        on = true;
    }

    private String eventType;

    private String filterTitle;

    private String filterSubTitle;

    private boolean on;

    /*public static boolean isEventFiltered(Event event) {
        for(Filter filter : Model.getFilters()) {
            if(filter.getEventType() == null) {
                if(!checkStandardFilters(event, filter)) {
                    return false;
                }
            }
        }
        return true;
    }*/

    public static void applyFilters() {
        for (Map.Entry<Marker, String> entry : Model.getEventMarkersToEvents().entrySet()) {
            for(Filter filter : Model.getFilters()) {
                Event event = Model.getEvents().get(entry.getValue());
                Marker marker = entry.getKey();
                if(filter.getEventType() == null) {
                    checkStandardFilters(event, filter, marker);
                }
                else if(isEventFilterApplicable(event, filter)) {
                    marker.setVisible(filter.isOn());
                    if(!filter.isOn()) {
                        break;
                    }
                }
            }
        }
    }

    public static void checkStandardFilters(Event event, Filter filter, Marker marker) {
        if(filter.getFilterTitle().equals("Male Events")) {
            if(isMaleEventApplicable(event)) {
                marker.setVisible(filter.isOn());
            }
        }
        if(filter.getFilterTitle().equals("Female Events")) {
            if(isFemaleEventApplicable(event)) {
                marker.setVisible(filter.isOn());
            }
        }
        if(filter.getFilterTitle().equals("Father's Side")) {
            if(isFathersSideApplicable(event)) {
                marker.setVisible(filter.isOn());
            }
        }
        if(filter.getFilterTitle().equals("Mother's Side")) {
            if(isMothersSideApplicable(event)) {
                marker.setVisible(filter.isOn());
            }
        }
    }

    /*private static boolean checkStandardFilters(Event event, Filter filter) {
        if(filter.getFilterTitle().equals("Male Events")) {
            if(isMaleEventApplicable(event) && filter.isOn()) {

            }
        }
        return true;
    }*/

    private static boolean isFathersSideApplicable(Event event) {
        return !Model.getPaternalAncestors().contains(event.getPersonId());
    }

    private static boolean isMothersSideApplicable(Event event) {
        return !Model.getMaternalAncestors().contains(event.getPersonId());
    }

    private static boolean isMaleEventApplicable(Event event) {
        if(Model.getPeople().get(event.getPersonId()) != null) {
            return Model.getPeople().get(event.getPersonId()).isMale();
        }
        else {
            return false;
        }
    }

    private static boolean isFemaleEventApplicable(Event event) {
        if(Model.getPeople().get(event.getPersonId()) != null) {
            return !Model.getPeople().get(event.getPersonId()).isMale();
        }
        else {
            return false;
        }
    }

    public static boolean isEventFilterApplicable(Event event, Filter filter) {
        return event.getDescription().equals(filter.getEventType());
    }

    public String getFilterTitle() {
        return filterTitle;
    }

    public void setFilterTitle(String filterTitle) {
        this.filterTitle = filterTitle;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public String getFilterSubTitle() {
        return filterSubTitle;
    }

    public void setFilterSubTitle(String filterSubTitle) {
        this.filterSubTitle = filterSubTitle;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
