package com.tus.java.oop1.rms.data.service;

import com.tus.java.oop1.rms.data.record.RestaurantBranchRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import com.tus.java.oop1.rms.data.models.MenuItem;
import com.tus.java.oop1.rms.data.repository.api.IMenuItemRepo;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;


@Service
public class MenuItemService {

    @Autowired
    private IMenuItemRepo menuItemInterface;

    @Autowired
    private MessageSource messageSource;

    public List<MenuItem> getMenuItemsByBranch(RestaurantBranchRecord restaurantBranchRecord) {
        return menuItemInterface.findByBranch(restaurantBranchRecord);
    }

    public List<MenuItem> saveMenuItem(MenuItem menuItem) {
        try {
            List<MenuItem> items = Arrays.asList(menuItem);
            return menuItemInterface.saveAll(items);
        } catch (ObjectOptimisticLockingFailureException e) {
            String errorMessage = messageSource.getMessage("error.optimisticLock", null, Locale.getDefault());
            throw new RuntimeException(errorMessage, e);
        }
    }



}
