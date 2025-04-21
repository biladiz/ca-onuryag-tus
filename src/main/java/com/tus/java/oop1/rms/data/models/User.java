package com.tus.java.oop1.rms.data.models;

import com.tus.java.oop1.rms.data.models.exceptions.PersonException;
import com.tus.java.oop1.rms.data.models.exceptions.UserException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends Person {

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orderHistory;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private boolean isGoldUser;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_allergies", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "allergy")
    private Set<Allergies> allergies;

    // Getters and Setters
    public Set<Allergies> getAllergies() {
        return allergies;
    }

    public void setAllergies(Set<Allergies> allergies) {
        this.allergies = allergies;
    }

    public User() {}

    public User(String name, String address, String email, String[] phoneNumbers) {
        this(name, address, email, phoneNumbers, new ArrayList<Order>(), false);
    }

    public User(String name, String address, String email, String[] phoneNumbers, List<Order> orderHistory, boolean isGoldUser) {
        super(name, address, email, phoneNumbers);
        this.orderHistory = orderHistory;
    }

    public User(String name, String address, String email, String[] phoneNumbers, boolean isGoldUser) {
        super(name, address, email, phoneNumbers);
        this.isGoldUser = isGoldUser;
    }

    public boolean isGoldUser() {
        return isGoldUser;
    }

    public void setGoldUser(boolean isGoldUser) {
        this.isGoldUser = isGoldUser;
    }

    // Getters and Setters
    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }

    @Override
    public void validate() throws PersonException {
        super.validate();
        if (this.getPhoneNumbers() == null || this.getPhoneNumbers().isEmpty()) {
            throw new UserException("Phone cannot be null or empty for a User");
        }
    }
}
