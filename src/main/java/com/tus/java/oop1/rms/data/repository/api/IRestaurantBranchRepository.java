package com.tus.java.oop1.rms.data.repository.api;

import com.tus.java.oop1.rms.data.models.RestaurantBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface IRestaurantBranchRepository extends JpaRepository<RestaurantBranch, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM RestaurantBranch b WHERE b.branchId = :branchId")
    void deleteById(@Param("branchId") Long branchId);

}