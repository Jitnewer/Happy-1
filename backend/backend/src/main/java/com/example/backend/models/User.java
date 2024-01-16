package com.example.backend.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

/**
 * Represents a user entity with various attributes such as profile picture, name, email, etc.
 */
@NamedQuery(name = "find_User_By_Id", query = "SELECT U from User U WHERE U.mail = ?1")
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "mail")})
public class User implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private long id;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @JsonIgnore
    private Set<UserEvent> userEvents = new HashSet<>();

    /**
     * Gets the unique identifier of the user.
     *
     * @return The user's identifier.
     */
    @Override
    public long getId() {
        return this.id;
    }

    /**
     * Sets the unique identifier of the user.
     *
     * @param id The user's identifier.
     */
    @Override
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Enumeration representing different types of users.
     */
    public enum UserType {
        ADMIN,
        ENTREPRENEUR,
        PARTNER,
        SUPERUSER
    }

    /**
     * Enumeration representing different statuses of users.
     */
    public enum UserStatus {
        ACTIVE,
        INACTIVE,
        BANNED,
        UNBANNED
    }

    private String profilePic;
    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    @Column(unique = true, nullable = false)
    private String mail;
    private String gender;
    private int age;
    private String companyType;
    private String tag;
    private UserStatus status;
    @Column(nullable = false)
    private UserType userType;
    private String postalCode;
    @Column(nullable = false)
    private String password;
    private String companyName;

    /**
     * Default constructor for User.
     */
    public User() {

    }

    /**
     * Constructor for creating a user with specified parameters.
     *
     * @param profilePic   The profile picture of the user.
     * @param firstname    The first name of the user.
     * @param lastname     The last name of the user.
     * @param mail         The email address of the user.
     * @param gender       The gender of the user.
     * @param age          The age of the user.
     * @param companyType  The type of the user's company.
     * @param tag          The tag associated with the user.
     * @param status       The status of the user.
     * @param userType     The type of the user.
     * @param postalCode   The postal code of the user.
     * @param password     The password of the user.
     * @param companyName  The name of the user's company.
     */
    public User(String profilePic, String firstname, String lastname, String mail, String gender, int age, String companyType, String tag, UserStatus status, UserType userType, String postalCode, String password, String companyName) {
        this.profilePic = profilePic;
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
        this.gender = gender;
        this.age = age;
        this.companyType = companyType;
        this.tag = tag;
        this.status = status;
        this.userType = userType;
        this.postalCode = postalCode;
        this.password = password;
        this.companyName = companyName;
    }

    /**
     * Gets the profile picture of the user.
     *
     * @return The profile picture filename.
     */
    public String getProfilePic() {
        return profilePic;
    }

    /**
     * Gets the email address of the user.
     *
     * @return The email address.
     */
    public String getMail() {
        return mail;
    }

    /**
     * Gets the set of user events associated with the user.
     *
     * @return The set of user events.
     */
    public Set<UserEvent> getUserEvents() {
        return userEvents;
    }

    /**
     * Gets the gender of the user.
     *
     * @return The gender of the user.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Gets the age of the user.
     *
     * @return The age of the user.
     */
    public int getAge() {
        return age;
    }

    /**
     * Gets the company type of the user.
     *
     * @return The company type of the user.
     */
    public String getCompanyType() {
        return companyType;
    }

    /**
     * Gets the tag associated with the user.
     *
     * @return The tag associated with the user.
     */
    public String getTag() {
        return tag;
    }

    /**
     * Gets the status of the user.
     *
     * @return The status of the user.
     */
    public UserStatus getStatus() {
        return status;
    }

    /**
     * Gets the type of the user.
     *
     * @return The type of the user.
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * Gets the postal code of the user.
     *
     * @return The postal code of the user.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Gets the name of the user's company.
     *
     * @return The name of the user's company.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Gets the first name of the user.
     *
     * @return The first name of the user.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Gets the last name of the user.
     *
     * @return The last name of the user.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Creates a new user with the same attributes as the given user.
     *
     * @param user The user to copy.
     * @return A new user with the same attributes.
     */
    public static User copyConstructor(User user) {
        if (user == null) return null;

        return new User(
                user.profilePic,
                user.firstname,
                user.lastname,
                user.mail,
                user.gender,
                user.age,
                user.companyType,
                user.tag,
                user.status,
                user.userType,
                user.postalCode,
                user.password,
                user.companyName
        );
    }

    /**
     * Gets the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Creates a sample user with random attributes.
     *
     * @return A sample user.
     */
    public static User createSampleUser() {
        Random random = new Random();
        String image = "profilepic.png";

        String[] firstnames = {"Tim", "Mylo", "Pim", "Eva"};
        String[] lastnames = {"Groot", "van Dijk", "de Jong", "de Graaf"};
        int randomIndex = random.nextInt(firstnames.length);
        String randomFirstname = firstnames[randomIndex];
        String randomLastname = lastnames[randomIndex];

        randomIndex = random.nextInt(firstnames.length);
        UserType[] userTypeValues = {UserType.ADMIN, UserType.ENTREPRENEUR, UserType.PARTNER, UserType.SUPERUSER};
        UserType randomUserType = userTypeValues[randomIndex];

        String mail = randomFirstname.toLowerCase() + randomLastname.replaceAll("\\s", "").toLowerCase() + "@gmail.com";
        String gender = "male";
        String tag = (randomUserType.equals(UserType.PARTNER)) ? "Chain-partner" : "N/A";

        double randomStatusNum = random.nextDouble();
        UserStatus status = (randomStatusNum < 0.5) ? UserStatus.ACTIVE : UserStatus.INACTIVE;
        int age = random.nextInt(50) + 50;
        String companyType = "Catering";
        String postalCode = "1242 DA";

        String[] companyName = {"Uber", "Albert Heijn", "McDonalds", "Thuisbezorgd"};
        String randomCompanyName = companyName[randomIndex];

        return new User(image, randomFirstname, randomLastname, mail, gender, age, companyType, tag, status, randomUserType, postalCode, "test", randomCompanyName);
    }

    /**
     * Provides a string representation of the User object.
     *
     * @return A string representation of the User.
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", profilePic='" + profilePic + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", mail='" + mail + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", companyType='" + companyType + '\'' +
                ", tag='" + tag + '\'' +
                ", status='" + status + '\'' +
                ", userType='" + userType + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
