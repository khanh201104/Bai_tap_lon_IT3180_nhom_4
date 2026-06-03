package com.bluemoon.apartment.repository;

import com.bluemoon.apartment.entity.Household;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseholdRepository extends JpaRepository<Household, Long> {

    List<Household> findByHeadNameContaining(String headName);

    List<Household> findByApartmentNumberContaining(String apartmentNumber);
}