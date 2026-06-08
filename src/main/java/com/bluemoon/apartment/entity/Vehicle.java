package com.bluemoon.apartment.entity;

import com.bluemoon.apartment.constant.VehicleStatus;
import com.bluemoon.apartment.constant.VehicleType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "license_plate", nullable = false, unique = true, length = 30)
    private String licensePlate;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false, length = 50)
    private VehicleType vehicleType;

    @Column(name = "brand", length = 100)
    private String brand;

    @Column(name = "color", length = 50)
    private String color;

    @Column(name = "owner_name", length = 100)
    private String ownerName;

    @Column(name = "registered_date", nullable = false)
    private LocalDate registeredDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private VehicleStatus status;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "household_id", nullable = false)
    private Household household;
}