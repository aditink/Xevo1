package xevo.xevo1.Database.DatabaseModels;

import android.net.Uri;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by aditi on 1/7/18.
 */

@IgnoreExtraProperties
public class User {

    private String username;
    private String email;
    private boolean isConsultant;
    private String firstName;
    private String lastName;

    /**
     * Default constructor required for calls to DataSnapshot.getValue(User.class)
     * Setting all variables to empty string / default value.
     */
    public User() {
        username = "";
        email = "";
        isConsultant = false;
        firstName = "";
        lastName = "";
    }

    /**
     * Constructor.
     * @param username : String
     * @param email : String
     */
    public User(String username, String email, boolean isConsultant, String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.isConsultant = isConsultant;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Getter for username: required by Firebase
     * @return username : String
     */
    public String getUsername() {
        return username;
    }

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
    public boolean getIsConsultant() {
        return isConsultant;
    }
    /**
     * Getter for firstName: required by Firebase
     * @return firstName : String
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * Getter for lastName: required by Firebase
     * @return lastName : String
     */
    public String getLastName() {
        return lastName;
    }
}