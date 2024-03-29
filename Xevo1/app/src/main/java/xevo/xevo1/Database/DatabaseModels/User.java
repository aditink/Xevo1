package xevo.xevo1.Database.DatabaseModels;

import android.net.Uri;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import xevo.xevo1.enums.Consultant;

/**
 * Created by aditi on 1/7/18.
 */

@IgnoreExtraProperties
public class User {

    private String username; //insist on uniqueness and use in place of id?
    private String email;
    private Consultant isConsultant;
    private String firstName;
    private String lastName;
    private List<String> subjects;
    private String userId;
    private String device; //Firebase device ID TODO: handle multiple devices for single user
    private int casesAnswered;
    private float rating;

    /**
     * Default constructor required for calls to DataSnapshot.getValue(User.class)
     * Setting all variables to empty string / default value.
     */
    public User() {
        username = "";
        email = "";
        isConsultant = Consultant.NONE;
        firstName = "";
        lastName = "";
        subjects = new ArrayList<String>();
        userId = "";
        device = "";
    }

    /**
     * Constructor.
     * @param username : String
     * @param email : String
     */
    public User(String username, String email, Consultant isConsultant, String firstName,
                String lastName, List<String> subjects, String userId, String device) {
        this.username = username;
        this.email = email;
        this.isConsultant = isConsultant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subjects = subjects;
        this.userId = userId;
        this.device = device;
        casesAnswered = 0;
        rating = -1;
    }

    /**
     * Getter for username: required by Firebase
     * @return username : String
     */
    public String getUsername() {
        return username;
    }

    public String getDevice() {
        return device;
    }

    public String getUserId() {return userId; }

    /**
     * Getter for email
     * @return email : String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter for isConsultant: required by Firebase
     * @return isConsultant : boolean
     */
    public Consultant getIsConsultant() {
        return isConsultant;
    }

    /**
     * Getter for firstName: required by Firebase
     * @return firstName : String
     */
    public String getFirstName() {
        return firstName;
    }

    public int getCasesAnswered() {
        return casesAnswered;
    }

    /**
     * Getter for lastName: required by Firebase
     * @return lastName : String
     */
    public String getLastName() {
        return lastName;
    }

    public float getRating() {
        return rating;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setDevice(String devices) {
        this.device = device;
    }

    public void setCasesAnswered(int casesAnswered) {
        this.casesAnswered = casesAnswered;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}