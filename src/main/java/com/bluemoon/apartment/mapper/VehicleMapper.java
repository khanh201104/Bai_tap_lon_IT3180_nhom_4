package com.bluemoon.apartment.mapper;

import com.bluemoon.apartment.constant.VehicleStatus;
import com.bluemoon.apartment.dto.request.VehicleRequest;
import com.bluemoon.apartment.dto.response.VehicleResponse;
import com.bluemoon.apartment.entity.Household;
import com.bluemoon.apartment.entity.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    public Vehicle toEntity(VehicleRequest request) {
        if (request == null) {
            return null;
        }

        return Vehicle.builder()
                .licensePlate(request.getLicensePlate())
                .vehicleType(request.getVehicleType())
                .brand(request.getBrand())
                .color(request.getColor())
                .ownerName(request.getOwnerName())
                .registeredDate(request.getRegisteredDate())
                .status(request.getStatus() == null ? VehicleStatus.ACTIVE : request.getStatus())
                .note(request.getNote())
                .build();
    }

    public VehicleResponse toResponse(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }

        Household household = vehicle.getHousehold();

        return VehicleResponse.builder()
                .id(vehicle.getId())
                .licensePlate(vehicle.getLicensePlate())
                .vehicleType(vehicle.getVehicleType())
                .vehicleTypeName(vehicle.getVehicleType() != null ? vehicle.getVehicleType().getDisplayName() : null)
                .monthlyFee(vehicle.getVehicleType() != null ? vehicle.getVehicleType().getMonthlyFee() : null)
                .brand(vehicle.getBrand())
                .color(vehicle.getColor())
                .ownerName(vehicle.getOwnerName())
                .registeredDate(vehicle.getRegisteredDate())
                .status(vehicle.getStatus())
                .statusName(vehicle.getStatus() != null ? vehicle.getStatus().getDisplayName() : null)
                .note(vehicle.getNote())
                .householdId(household != null ? household.getId() : null)
                .householdCode(household != null ? household.getHouseholdCode() : null)
                .apartmentNumber(household != null ? household.getApartmentNumber() : null)
                .build();
    }

    public void updateEntity(Vehicle vehicle, VehicleRequest request) {
        if (vehicle == null || request == null) {
            return;
        }

        vehicle.setLicensePlate(request.getLicensePlate());
        vehicle.setVehicleType(request.getVehicleType());
        vehicle.setBrand(request.getBrand());
        vehicle.setColor(request.getColor());
        vehicle.setOwnerName(request.getOwnerName());
        vehicle.setRegisteredDate(request.getRegisteredDate());
        vehicle.setStatus(request.getStatus() == null ? VehicleStatus.ACTIVE : request.getStatus());
        vehicle.setNote(request.getNote());
    }
}