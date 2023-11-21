package com.example.backend.models;

import java.util.Random;

public class User {
    public enum UserType {
        ADMIN,
        ENTREPRENEUR,
        PARTNER,
        SUPERUSER
    }
    public enum UserStatus {
        ACTIVE,
        INACTIVE,
        BANNED,
        UNBANNED
    }
    private int id;
    private static int idCounter = 3001;
    private String profilePic;
    private String firstname;
    private String lastname;
    private String mail;
    private String gender;
    private int age;
    private String companyType;
    private String tag;
    private UserStatus status;
    private UserType userType;
    private String postalCode;
    private String password;

    public int getId() {
        return id;
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public String getProfilePic() {
        return profilePic;
    }



    public String getMail() {
        return mail;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getCompanyType() {
        return companyType;
    }

    public String getTag() {
        return tag;
    }

    public UserStatus getStatus() {
        return status;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public User(int id, String profilePic, String firstname, String lastname, String mail, String gender, int age, String companyType, String tag, UserStatus status, UserType userType, String postalCode, String password) {
        if (id == 0){
            this.id = idCounter++;
        } else {
            this.id = id;
        }
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
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public static User copyConstructor(User user) {
        if (user == null) return null;

        return new User(
                user.id,
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
                user.password
        );
    }

    public String getPassword() {
        return password;
    }

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

        return new User(0, image, randomFirstname, randomLastname, mail, gender, age, companyType, tag, status, randomUserType, postalCode, "test");
    }
}
