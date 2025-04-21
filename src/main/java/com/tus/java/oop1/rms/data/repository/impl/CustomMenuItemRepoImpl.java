package com.tus.java.oop1.rms.data.repository.impl;

import com.tus.java.oop1.rms.data.models.MenuItem;
import com.tus.java.oop1.rms.data.record.RestaurantBranchRecord;
import com.tus.java.oop1.rms.data.repository.api.CustomMenuItemRepo;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CustomMenuItemRepoImpl implements CustomMenuItemRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<MenuItem> findByBranch(RestaurantBranchRecord restaurantBranchRecord) {
        String jpql = "SELECT m FROM MenuItem m WHERE m.branch.branchId = :branchId";
        TypedQuery<MenuItem> query = entityManager.createQuery(jpql, MenuItem.class);
        query.setParameter("branchId", restaurantBranchRecord.branchId());
        return query.getResultList();
    }
}