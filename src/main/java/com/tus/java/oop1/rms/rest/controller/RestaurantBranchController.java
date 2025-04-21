package com.tus.java.oop1.rms.rest.controller;

import com.tus.java.oop1.rms.data.models.RestaurantBranch;
import com.tus.java.oop1.rms.data.service.RestaurantBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

    @RestController
    @RequestMapping("/branches")
    public class RestaurantBranchController {
        @Autowired
        private RestaurantBranchService branchService;

        @GetMapping
        public List<RestaurantBranch> listBranches() {
            return branchService.getAllBranches();
        }

        @PostMapping
        @Transactional
        public RestaurantBranch saveBranch(@RequestBody RestaurantBranch restaurantBranch) {
            try {
                return branchService.addBranch(restaurantBranch);
            } catch (DataIntegrityViolationException e) {
                throw e;
            }
        }
    }
