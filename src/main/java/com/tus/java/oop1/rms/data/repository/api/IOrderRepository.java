package com.tus.java.oop1.rms.data.repository.api;

import com.tus.java.oop1.rms.data.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {
    // Additional query methods can be defined here if needed
}
