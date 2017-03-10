package edu.byu.cs.familymap.ui.recyclerview;

/**
 * Created by cole on 4/6/16.
 */
public class PersonChildListItem extends AbstractChildListItem {
    public PersonChildListItem(String name, String relation, boolean gender, String personId) {
        this.name = name;
        this.relation = relation;
        this.gender = gender;
        this.personId = personId;
    }

    public String name;

    public String relation;

    public String personId;

    /**
     * @value True if male, False if female.
     */
    public boolean gender;

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getSubTitle() {
        return relation;
    }

    @Override
    public String getId() {
        return personId;
    }
}
