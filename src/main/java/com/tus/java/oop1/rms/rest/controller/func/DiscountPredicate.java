package com.tus.java.oop1.rms.rest.controller.func;

@FunctionalInterface
public interface DiscountPredicate {
    boolean isEligible(int totalPrice);
}
