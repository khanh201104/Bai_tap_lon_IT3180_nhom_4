package com.bluemoon.apartment.repository;

import com.bluemoon.apartment.constant.VehicleType;
import com.bluemoon.apartment.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bluemoon.apartment.constant.VehicleStatus;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    boolean existsByLicensePlateIgnoreCase(String licensePlate);

    boolean existsByLicensePlateIgnoreCaseAndIdNot(String licensePlate, Long id);

    List<Vehicle> findByLicensePlateContainingIgnoreCaseOrOwnerNameContainingIgnoreCaseOrHouseholdApartmentNumberContainingIgnoreCase(
            String licensePlate,
            String ownerName,
            String apartmentNumber
    );

    List<Vehicle> findByHouseholdId(Long householdId);

    List<Vehicle> findByVehicleType(VehicleType vehicleType);

    List<Vehicle> findByHouseholdIdAndVehicleType(Long householdId, VehicleType vehicleType);

    List<Vehicle> findByHousehold_IdAndStatus(Long householdId, VehicleStatus status);
}