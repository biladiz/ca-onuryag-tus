package com.tus.java.oop1.rms.data.models;


import com.tus.java.oop1.rms.data.models.exceptions.EmployeeException;
import com.tus.java.oop1.rms.data.models.exceptions.PersonException;
import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee extends Person {
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private RestaurantBranch branch;

    public Employee() {}


    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    public Employee(String name, String address, String phone, String email, RestaurantBranch branch) {
        super(name, address, phone, email);
        this.branch = branch;
    }

    // Getters and Setters
    public RestaurantBranch getBranch() {
        return branch;
    }

    public void setBranch(RestaurantBranch branch) {
        this.branch = branch;
    }

    @Override
    public void validate() throws PersonException {
        super.validate();
        if (branch == null) {
            throw new EmployeeException("Branch cannot be null for an Employee");
        }
    }
}
