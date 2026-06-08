package com.bluemoon.apartment.repository;

import com.bluemoon.apartment.entity.Household;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseholdRepository extends JpaRepository<Household, Long> {

    boolean existsByHouseholdCode(String householdCode);

    boolean existsByHouseholdCodeAndIdNot(String householdCode, Long id);

    List<Household> findByHouseholdCodeContainingIgnoreCaseOrApartmentNumberContainingIgnoreCaseOrHeadResidentFullNameContainingIgnoreCase(
            String householdCode,
            String apartmentNumber,
            String headResidentName
    );
}