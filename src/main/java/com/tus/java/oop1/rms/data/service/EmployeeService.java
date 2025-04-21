package com.tus.java.oop1.rms.data.service;

import com.tus.java.oop1.rms.data.models.Employee;
import com.tus.java.oop1.rms.data.repository.api.IEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private IEmployeeRepository employeeRepository;

    @Transactional
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @Transactional
    public void deleteEmployeeById(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}

