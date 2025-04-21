package com.tus.java.oop1.rms.data.service;

import com.tus.java.oop1.rms.data.models.RestaurantBranch;
import com.tus.java.oop1.rms.data.record.RestaurantBranchRecord;
import com.tus.java.oop1.rms.data.repository.api.IRestaurantBranchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantBranchService {

    private static final Logger log = LoggerFactory.getLogger(RestaurantBranchService.class);

    @Autowired
    private IRestaurantBranchRepository branchRepository;

    @Transactional
    public RestaurantBranch addBranch(RestaurantBranch restaurantBranch) {
        return branchRepository.saveAndFlush(restaurantBranch);
    }

    // Returns the branch if found, otherwise returns null
    public RestaurantBranchRecord findByBranch(Long branchId) {
        //LVTI - Readability
        var branch = branchRepository.findById(branchId);
        if(branch.isPresent()) {
            RestaurantBranch restaurantBranch = branch.get();
            return new RestaurantBranchRecord(restaurantBranch.getBranchId(),
                    restaurantBranch.getAddress(), restaurantBranch.getProperties());
        }
        return null;
    }

    @Transactional public void deleteById(Long branchId) {
        branchRepository.deleteById(branchId);
    }

    public List<RestaurantBranch> getAllBranches() {
        return branchRepository.findAll();
    }
}

