package com.example.backend.models;

public class User {
    private static long idCounter = 3001;

    private long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private boolean admin;
    private String password; // Added hashed password field

    public User(String firstname, String lastname, String email, String phoneNumber, String password, boolean admin) {
        this.id = idCounter++;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.admin = admin;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isAdmin() {
        return admin;
    }


    public String getPassword() {
        return password;
    }
}
