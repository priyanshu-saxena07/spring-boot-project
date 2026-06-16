package com.example.hibernate.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne; // Relationship ke liye naya import
import jakarta.persistence.JoinColumn; // Foreign Key ke liye naya import
import jakarta.persistence.CascadeType; // Automatic save ke liye naya import

@Entity
public class UserHibernate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    // =========================================================
    // NEW RELATIONSHIP FIELD ADDED HERE
    // =========================================================
    @OneToOne(cascade = CascadeType.ALL) // User save hoga to Passport bhi khud save ho jayega
    @JoinColumn(name = "passport_id")    // MySQL table me 'passport_id' naam ka column banega
    private Passport passport;

    // Your existing Parameterized Constructor
    public UserHibernate(long id, String name) {
        this.id = id;
        this.name = name;   
    }

    // Your existing Default Constructor (Hibernate ke liye zaroori hai)
    public UserHibernate() {
    }

    // Your existing Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // =========================================================
    // NEW GETTER AND SETTER FOR PASSPORT
    // =========================================================
    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }
}
