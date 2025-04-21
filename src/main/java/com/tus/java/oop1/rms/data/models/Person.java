package com.tus.java.oop1.rms.data.models;


import com.tus.java.oop1.rms.data.models.exceptions.PersonException;
import jakarta.persistence.MappedSuperclass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("JpaAttributeTypeInspection")
@MappedSuperclass
public abstract class Person {
    private String name;
    private String address;
    private List<String> phoneNumbers;
    private String email;

    public Person() {}

    public Person(String name, String address, String email, String... phoneNumbers) {
        this.name = name;
        this.address = address;
        this.phoneNumbers = new ArrayList<>(Arrays.asList(phoneNumbers));
        this.email = email;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumbers(String... phoneNumbers) {
        this.phoneNumbers = Arrays.asList(phoneNumbers);
    }

    public List<String> getPhoneNumbers() {
        return this.phoneNumbers;
    }

    // Example method using varargs
    public void addPhoneNumbers(String... phoneNumbers) {
        this.phoneNumbers.addAll(Arrays.asList(phoneNumbers));
    }

    public void printPhoneNumbers() {
        System.out.println("Phone Numbers: " + this.phoneNumbers);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void validate() throws PersonException {
        if (name == null || name.isEmpty()) {
            throw new PersonException("Name cannot be null or empty");
        }
        if (email == null || email.isEmpty()) {
            throw new PersonException("Email cannot be null or empty");
        }
    }
}

