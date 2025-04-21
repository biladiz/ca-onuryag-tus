package com.tus.java.oop1.rms.data.record;

import com.tus.java.oop1.rms.data.models.Order;

import java.util.List;

public record UserRecord(
        Long userId,
        String name,
        String address,
        String phone,
        String email,
        List<Order> orderHistory
) {}
