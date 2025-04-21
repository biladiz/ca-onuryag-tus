package com.tus.java.oop1.rms.rest.controller;

import com.tus.java.oop1.rms.data.models.MenuItem;
import com.tus.java.oop1.rms.data.record.MenuItemRecord;
import com.tus.java.oop1.rms.data.service.MenuItemService;
import com.tus.java.oop1.rms.data.service.RestaurantBranchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;
import java.util.Locale;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuItemController {
    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private RestaurantBranchService restaurantBranchService;



    private static final Logger logger = LoggerFactory.getLogger(MenuItemController.class);

    @GetMapping("/{branchId}")
    public List<MenuItem> getMenuByBranch(@PathVariable Long branchId) {
        logger.info("Get Menu Item...");
        return menuItemService.getMenuItemsByBranch(restaurantBranchService.findByBranch(branchId));
    }


    @PostMapping
    public List<MenuItem> saveMenu(@RequestBody MenuItemRecord menuItemRecord) {
        logger.info("Add Menu Item...");

        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemRecord.name());
        menuItem.setPrice(menuItemRecord.price());
        menuItem.setBranch(restaurantBranchService.findByBranch(menuItemRecord.branchId()));
        menuItem.setIngredients(menuItemRecord.ingredients()); // Set ingredients
        return menuItemService.saveMenuItem(menuItem);
    }


}
