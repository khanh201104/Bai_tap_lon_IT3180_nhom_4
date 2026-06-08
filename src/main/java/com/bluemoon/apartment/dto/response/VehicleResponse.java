package com.bluemoon.apartment.dto.response;

import com.bluemoon.apartment.constant.VehicleStatus;
import com.bluemoon.apartment.constant.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleResponse {

    private Long id;

    private String licensePlate;

    private VehicleType vehicleType;

    private String vehicleTypeName;

    private Integer monthlyFee;

    private String brand;

    private String color;

    private String ownerName;

    private LocalDate registeredDate;

    private VehicleStatus status;

    private String statusName;

    private String note;

    private Long householdId;

    private String householdCode;

    private String apartmentNumber;
}