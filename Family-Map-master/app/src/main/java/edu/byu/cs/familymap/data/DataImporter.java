package edu.byu.cs.familymap.data;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.byu.cs.familymap.model.Event;
import edu.byu.cs.familymap.model.Filter;
import edu.byu.cs.familymap.model.Model;
import edu.byu.cs.familymap.model.Person;

/**
 * This class will generate various parts of the model such as the ancestors.
 * Created by cole on 3/26/16.
 */
public class DataImporter {
    public void generateData() {
        generateEventTypes();
        generateFilters();
        generateAncestors();
    }

    public Set<String> generateEventTypes() {
        for(Map.Entry<String, Event> entry : Model.getEvents().entrySet()) {
            Model.getEventTypes().add(entry.getValue().getDescription());
        }
        return Model.getEventTypes();
    }

    private void generateFilters() {
        for(String eventType : Model.getEventTypes()) {
            String filterTitle = eventType.substring(0, 1).toUpperCase() +
                    eventType.substring(1) +
                    " Events";
            String filterSubTitle = ("Filter by " + filterTitle).toUpperCase();
            Filter filter = new Filter(eventType.toLowerCase(), filterTitle, filterSubTitle);
            Model.getFilters().add(filter);
        }
        Filter fathersSideFilter = new Filter(
                null,
                "Father's Side",
                ("Filter by Father's side of family").toUpperCase()
        );
        Model.getFilters().add(fathersSideFilter);

        Filter mothersSideFilter = new Filter(
                null,
                "Mother's Side",
                ("Filter by Mother's side of family").toUpperCase()
        );
        Model.getFilters().add(mothersSideFilter);

        Filter maleEventsFilter = new Filter(
                null,
                "Male Events",
                ("Filter events based on gender").toUpperCase()
        );
        Model.getFilters().add(maleEventsFilter);

        Filter femaleEventsFilter = new Filter(
                null,
                "Female Events",
                ("Filter events based on gender").toUpperCase()
        );
        Model.getFilters().add(femaleEventsFilter);
    }

    private void generateAncestors() {
        if(Model.getCurrentPerson().getFatherId() != null) {
            Set<String> paternalAncestors = new HashSet<>();
            /*paternalAncestors =*/ generateAncestorsHelper(
                    Model.getCurrentPerson().getFatherId(),
                    paternalAncestors
            );
            Model.setPaternalAncestors(paternalAncestors);
        }

        if(Model.getCurrentPerson().getMotherId() != null) {
            Set<String> maternalAncestors = new HashSet<>();
            maternalAncestors = generateAncestorsHelper(
                    Model.getCurrentPerson().getMotherId(),
                    maternalAncestors
            );
            Model.setMaternalAncestors(maternalAncestors);
        }
    }

    private Set<String> generateAncestorsHelper(String parentId, Set<String> ancestors) {
        ancestors.add(parentId);

        Person parent = Model.getPeople().get(parentId);

        if(parent != null) {
            if (parent.getFatherId() != null) {
                generateAncestorsHelper(parent.getFatherId(), ancestors);
            }

            if (parent.getMotherId() != null) {
                generateAncestorsHelper(parent.getMotherId(), ancestors);
            }
        }

        return ancestors;
    }
}
