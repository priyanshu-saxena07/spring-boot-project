package com.example.hibernate.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "passports")
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "passport_number", nullable = false)
    private String passportNumber;

    // Default Constructor
    public Passport() {}

    // Parameterized Constructor for easy object creation
    public Passport(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPassportNumber() { return passportNumber; }
    public void setPassportNumber(String passportNumber) { this.passportNumber = passportNumber; }
}




