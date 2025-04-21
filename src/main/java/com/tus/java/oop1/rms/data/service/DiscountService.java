package com.tus.java.oop1.rms.data.service;

import com.tus.java.oop1.rms.data.models.MenuItem;
import com.tus.java.oop1.rms.rest.controller.func.DiscountPredicate;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    private static final double DISCOUNT_THRESHOLD = 100.0;
    private static final double DISCOUNT_RATE = 0.10; // 10% discount

    private final DiscountPredicate discountPredicate = price -> price >= DISCOUNT_THRESHOLD;

    /**
     * Calculates the discount for a given menu item based on its price.
     *
     * @param menuItem The menu item for which to calculate the discount.
     * @return The discount amount.
     */
    public double calculateDiscount(MenuItem menuItem) {
        // Use the DiscountPredicate to check eligibility
        if (discountPredicate.isEligible(menuItem.getPrice().intValue())) {
            return menuItem.getPrice() * DISCOUNT_RATE;
        }
        return 0.0;
    }
}