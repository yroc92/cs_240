package server.commonClasses.modelClasses;

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
    private int personID;
    /*
    Gender of the person.
     */
    private String gender;
    /*
    The following family relationships are all optional, and are initially null.
    The string refers to their unique identifiers.
     */
    private int father;
    private int mother;
    private int spouse;

    // User to which this person belongs
    private String descendant;

    /*
    Constructor that takes in all fields as parameters other than the family relationships.
     */
    public Person(String firstName, String lastName, int personID, String gender, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personID = personID;
        this.gender = gender;
        this.descendant = username;
        this.father = 0;
        this.mother = 0;
        this.spouse = 0;
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

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public int getFather() {
        return father;
    }

    public void setFather(int father) {
        this.father = father;
    }

    public int getMother() {
        return mother;
    }

    public void setMother(int mother) {
        this.mother = mother;
    }

    public int getSpouse() {
        return spouse;
    }

    public void setSpouse(int spouse) {
        this.spouse = spouse;
    }
}
