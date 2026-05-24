package com.bluemoon.apartment.repository;

import com.bluemoon.apartment.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByHouseholdId(Long householdId);

    List<Vehicle> findByPlateContainingIgnoreCase(String plate);

    List<Vehicle> findByApartmentCodeContainingIgnoreCase(String apartmentCode);
}
