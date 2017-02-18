package server.commonClasses.modelClasses;

import java.util.ArrayList;

/**
 * Created by Cory on 2/17/17.
 *
 * Class representing a person in the family map database.
 */
public class Person {
    /*
    First and last name of the person.
     */
    private String firstName;
    private String lastName;
    /*
    Identification number of the person.
     */
    private String personID;
    /*
    Gender of the person.
     */
    private String gender;
    /*
    The following family relationships are all optional, and are initially null.
     */
    private Person father;
    private Person mother;
    private Person spouse;
    private ArrayList<String> children;

    /*
    Constructer that takes in all fields as parameters other than the family relationships.
     */
    public Person(String firstName, String lastName, String personID, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personID = personID;
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Person getFather() {
        return father;
    }

    public void setFather(Person father) {
        this.father = father;
    }

    public Person getMother() {
        return mother;
    }

    public void setMother(Person mother) {
        this.mother = mother;
    }

    public Person getSpouse() {
        return spouse;
    }

    public void setSpouse(Person spouse) {
        this.spouse = spouse;
    }

    public ArrayList<String> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<String> children) {
        this.children = children;
    }

}
