package edu.byu.cs.familymap.model;

import android.graphics.AvoidXfermode;

import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.byu.cs.familymap.ui.recyclerview.EventChildListItem;
import edu.byu.cs.familymap.ui.recyclerview.PersonChildListItem;

/**
 * This represents a person.
 * Created by cole on 3/15/16.
 */
public class Person implements Serializable {
    public Person() {
    }

    public Person(
            String descendant,
            String personId,
            String firstName,
            String lastName,
            String gender
    ) {
        this.descendant = descendant;
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        if(Model.getCurrentPerson() != null) {
            if (Model.getCurrentPerson().login != null) {
                this.login = Model.getCurrentPerson().login;
            }
        }
        else {
            this.login = new Login();
        }
        this.children = new HashSet<>();
        this.family = new ArrayList<>();
        this.events = new ArrayList<>();
    }

    private String descendant;

    private String personId;

    private String firstName;

    private String lastName;

    private String gender;

    private String fatherId;

    private String motherId;

    private String spouseId;

    private Login login;

    private List<Object> family;

    private List<Object> events;

    private PolylineOptions spouseLine;

    private PolylineOptions familyTreeLine;

    private PolylineOptions lifeStoryLine;

    /**
     * A Set of PersonIds that are of type String.
     */
    private Set<String> children;

    public static Person parsePerson(JSONObject root) throws JSONException {
        String descendant = root.getString("descendant");
        String personId = root.getString("personID");
        String firstName = root.getString("firstName");
        String lastName = root.getString("lastName");
        String gender = root.getString("gender");

        Person person = new Person(
                descendant,
                personId,
                firstName,
                lastName,
                gender
        );

        if(root.has("father")) {
            String fatherId = root.getString("father");
            person.setFatherId(fatherId);
        }
        if(root.has("mother")) {
            String motherId = root.getString("mother");
            person.setMotherId(motherId);
        }
        if(root.has("spouse")) {
            String spouseId = root.getString("spouse");
            person.setSpouseId(spouseId);
        }

        return person;
    }

    public void generateFamily() {
        if(family.size() == 0) {
            if (fatherId != null) {
                PersonChildListItem father = new PersonChildListItem(
                        Model.getPeople().get(fatherId).getFullName(),
                        "Father",
                        true,
                        fatherId
                );
                family.add(father);
            }
            if (motherId != null) {
                PersonChildListItem mother = new PersonChildListItem(
                        Model.getPeople().get(motherId).getFullName(),
                        "Mother",
                        false,
                        motherId
                );
                family.add(mother);
            }
            if(spouseId != null) {
                PersonChildListItem spouse = new PersonChildListItem(
                        Model.getPeople().get(spouseId).getFullName(),
                        "Spouse",
                        Model.getPeople().get(spouseId).isMale(),
                        spouseId
                );
                family.add(spouse);
            }
            for (String childId : children) {
                PersonChildListItem child = new PersonChildListItem(
                        Model.getPeople().get(childId).getFullName(),
                        "Child",
                        Model.getPeople().get(childId).isMale(),
                        childId
                );
                family.add(child);
            }
        }
    }

    public void generateEvents() {
        if(events.size() == 0) {
            for (Map.Entry<String, Event> entry : Model.getEvents().entrySet()) {
                if (entry.getValue().getPersonId().equals(personId)) {
                    EventChildListItem event = new EventChildListItem(
                            entry.getValue().getDescription(),
                            entry.getValue().getCity() +
                                    ", " +
                                    entry.getValue().getCountry(),
                            entry.getValue().getYear(),
                            getFullName(),
                            entry.getKey()
                    );
                    events.add(event);
                }
            }
            Collections.sort(events, new Comparator<Object>() {
                @Override
                public int compare(Object event1, Object event2) {
                    EventChildListItem eventChildListItem1 = (EventChildListItem) event1;
                    EventChildListItem eventChildListItem2 = (EventChildListItem) event2;
                    return eventChildListItem1.compareTo(eventChildListItem2);

                }
            });
        }
    }

    public void generateLines(String eventId) {
        generateLifeStoryLine();
        generateFamilyTreeLine(eventId);
        generateSpouseLine(eventId);
    }

    private void generateLifeStoryLine() {
        PolylineOptions lifeStoryLine = new PolylineOptions();
        for(Object object : events) {
            EventChildListItem eventChildListItem = (EventChildListItem) object;
            Event event = Model.getEvents().get(eventChildListItem.getId());
            lifeStoryLine.add(event.getPosition())
                    .width(10f)
                    .color(Model.getSettings().getCurrentLifeStoryLineColor());
        }
        this.lifeStoryLine = lifeStoryLine;
    }

    private void generateFamilyTreeLine(String eventId) {
        PolylineOptions familyTreeLine = new PolylineOptions();
        familyTreeLine.add(Model.getEvents().get(eventId).getPosition())
                .color(Model.getSettings().getCurrentFamilyTreeLineColor());
        familyTreeHelper(this, familyTreeLine, 20);
        this.familyTreeLine = familyTreeLine;
    }

    private void familyTreeHelper(Person currentPerson, PolylineOptions familyTreeLine, float width) {
        if(currentPerson.fatherId != null) {
            Person father = Model.getPeople().get(currentPerson.fatherId);
            father.generateEvents();
            if(father.events.size() > 0) {
                EventChildListItem eventChildListItem = (EventChildListItem) father.getEvents().get(0);
                Event event = Model.getEvents().get(eventChildListItem.getId());
                familyTreeLine.add(event.getPosition())
                        .width(width);
            }
        }
        if(currentPerson.motherId != null) {
            Person mother = Model.getPeople().get(currentPerson.motherId);
            mother.generateEvents();
            if(mother.events.size() > 0) {
                EventChildListItem eventChildListItem = (EventChildListItem) mother.getEvents().get(0);
                Event event = Model.getEvents().get(eventChildListItem.getId());
                familyTreeLine.add(event.getPosition())
                        .width(width);
            }
        }
        if(currentPerson.fatherId != null) {
            if((width - 2) > 0) {
                width -= 2;
            }
            familyTreeHelper(
                    Model.getPeople().get(currentPerson.fatherId),
                    familyTreeLine,
                    width
            );
        }
        if(currentPerson.motherId != null) {
            if((width - 2) > 0) {
                width -= 2;
            }
            familyTreeHelper(
                    Model.getPeople().get(currentPerson.motherId),
                    familyTreeLine,
                    width
            );
        }
    }

    private void generateSpouseLine(String eventId) {
        if(spouseId != null) {
            Person spouse = Model.getPeople().get(spouseId);
            spouse.generateEvents();
            if(spouse.getEvents().size() > 0) {
                PolylineOptions spouseLine = new PolylineOptions();
                EventChildListItem eventChildListItem = (EventChildListItem) spouse.getEvents().get(0);
                Event event = Model.getEvents().get(eventChildListItem.getId());
                spouseLine.add(event.getPosition())
                        .width(10f)
                        .color(Model.getSettings().getCurrentSpouseLineColor());
                spouseLine.add(
                        Model.getEvents().get(eventId).getPosition()
                );
                this.spouseLine = spouseLine;
            }
        }
    }

    public boolean isMale() {
        return gender.equals("m");
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDescendant() {
        return descendant;
    }

    public String getPersonId() {
        return personId;
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public String getMotherId() {
        return motherId;
    }

    public void setMotherId(String motherId) {
        this.motherId = motherId;
    }

    public String getSpouseId() {
        return spouseId;
    }

    public void setSpouseId(String spouseId) {
        this.spouseId = spouseId;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public PolylineOptions getSpouseLine() {
        return spouseLine;
    }

    public PolylineOptions getFamilyTreeLine() {
        return familyTreeLine;
    }

    public PolylineOptions getLifeStoryLine() {
        return lifeStoryLine;
    }

    public List<Object> getFamily() {
        return family;
    }

    public List<Object> getEvents() {
        return events;
    }
}
