package com.tus.java.oop1.rms.data.repository.api;

import com.tus.java.oop1.rms.data.models.MenuItem;
import com.tus.java.oop1.rms.data.record.RestaurantBranchRecord;

import java.util.List;

public interface CustomMenuItemRepo {
    List<MenuItem> findByBranch(RestaurantBranchRecord restaurantBranchRecord);
}