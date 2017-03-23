package main.java.server.commonClasses.modelClasses;

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
    The string refers to their unique identifiers.
     */
    private String father;
    private String mother;
    private String spouse;

    // User to which this person belongs
    private String descendant;

    /**
     * Constructor for person with only the necessary fields.
     * @param firstName
     * @param lastName
     * @param personID
     * @param gender
     * @param userName
     */
    public Person(String firstName, String lastName, String personID, String gender, String userName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personID = personID;
        this.gender = gender;
        this.descendant = userName;
        this.father = "";
        this.mother = "";
        this.spouse = "";
    }

    /**
     * Constructor to create a person with all fields.
     * @param firstName
     * @param lastName
     * @param personID
     * @param gender
     * @param userName
     * @param father
     * @param mother
     * @param spouse
     */
    public Person(String firstName, String lastName, String personID, String gender, String userName,
                  String father, String mother, String spouse) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personID = personID;
        this.gender = gender;
        this.descendant = userName;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
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

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }
}
