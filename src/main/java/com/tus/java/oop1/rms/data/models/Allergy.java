package com.tus.java.oop1.rms.data.models;

import jakarta.persistence.*;

@Entity
@Table(name = "allergies")
public class Allergy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Allergies allergyType;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Allergies getAllergyType() {
        return allergyType;
    }

    public void setAllergyType(Allergies allergyType) {
        this.allergyType = allergyType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}