package edu.byu.cs240.asyncwebaccess.ModelClasses;

import java.util.UUID;

/**
 * Created by corycooper on 3/26/17.
 */

public class User {

    /**
     * Username and password authentication
     */
    private String userName;
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
    public User(String userName, String password, String email, String firstName, String lastName, String gender) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.token = UUID.randomUUID().toString();
        this.gender = gender;
        this.personID = UUID.randomUUID().toString();
    }

    /**
     * The constructor for a user requires all the fields of the User class as parameters.
     */
    public User(String userName, String password, String email, String firstName, String lastName, String gender,
                String token, String personID) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.token = token;
        this.personID = personID;
    }

    public User() {
        this.userName = "";
        this.password = "";
        this.email = "";
        this.firstName = "";
        this.lastName = "";
        this.token = "";
        this.gender = "";
        this.personID = "";
    }

    public boolean validRegistration() {
        if (userName.equals("") || password.equals("") || firstName.equals("") || lastName.equals("") || email.equals("") || gender.equals("")) return  false;
        else return true;
    }

    public String getUsername() { return userName; }

    public void setUsername(String userName) {
        this.userName = userName;
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
