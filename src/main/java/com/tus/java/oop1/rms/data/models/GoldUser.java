package com.tus.java.oop1.rms.data.models;

import java.util.List;

public final class GoldUser extends User implements GoldUserIdentity {

    public GoldUser(User user) {
        super(user.getName(), user.getAddress(), user.getAddress(), user.getPhoneNumbers().toArray(new String[0]), user.getOrderHistory(), true);
    }

    public GoldUser(String name, String address, String email, String[] phoneNumbers, List<Order> orderHistory, boolean isGoldUser) {
        super(name, address, email, phoneNumbers, orderHistory, true);
    }

    public GoldUser(String name, String address, String email, String[] phoneNumbers, List<Order> orderHistory) {
        super(name, address, email, phoneNumbers, orderHistory, true);
    }

    @Override
    public double extraDiscount() {
        return 0.8;
    }
}
