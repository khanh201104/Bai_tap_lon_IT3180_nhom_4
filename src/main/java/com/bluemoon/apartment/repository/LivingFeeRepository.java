package com.bluemoon.apartment.repository;

import com.bluemoon.apartment.entity.LivingFee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivingFeeRepository extends JpaRepository<LivingFee, Long> {
    List<LivingFee> findByHouseholdId(Long householdId);

    List<LivingFee> findByNameContainingIgnoreCase(String name);

    List<LivingFee> findByApartmentCodeContainingIgnoreCase(String apartmentCode);
}
