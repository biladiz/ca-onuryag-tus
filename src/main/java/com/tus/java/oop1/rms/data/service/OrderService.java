package com.tus.java.oop1.rms.data.service;

import com.tus.java.oop1.rms.data.models.Order;
import com.tus.java.oop1.rms.data.repository.api.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private IOrderRepository orderRepository;

    // Create a new order
    @Transactional
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    // Retrieve an order by ID
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    // Retrieve all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Delete an order by ID
    @Transactional
    public void deleteOrder(Long orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
        } else {
            throw new RuntimeException("Order not found with id " + orderId);
        }
    }
}

