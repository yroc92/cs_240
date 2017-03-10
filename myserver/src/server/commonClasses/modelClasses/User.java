package server.commonClasses.modelClasses;

import com.google.gson.JsonObject;

import java.io.InputStreamReader;

/**
 * Created by Cory on 2/17/17.
 *
 * Class that indicates a user and their corresponding login information.
 * This is required information for authentication.
 */
public class User {
    /*
    Username and password authentication
     */
    private String username;
    private String password;
    /*
    Email associated with the user
     */
    private String email;
    /*
    First and last given names of the user
     */
    private String firstName;
    private String lastName;
    /*
    The authentication token that allows the user to log in.
     */
    private String token;
    /*
    The gender and personal ID of the user.
    Gender can be male ("m") or female ("f")
     */
    private String gender;
    private String personID;

    /*
    The constructor for a user requires all the fields of the User class as parameters.
     */
    public User(String username, String password, String email, String firstName, String lastName, String token, String gender, String personID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.token = token;
        this.gender = gender;
        this.personID = personID;
    }

    // Constructor with JSON data provided in the form of an input stream reader
    public User(InputStreamReader inputStreamReader) {
//        Person person = (Person)gson.fromJson(inputStreamReader, Person.class);
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
