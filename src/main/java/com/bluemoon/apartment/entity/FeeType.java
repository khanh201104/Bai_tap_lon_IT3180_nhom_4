package com.bluemoon.apartment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fee_types")
@Getter
@Setter
public class FeeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    private Double amount;

    /** 0: bắt buộc, 1: tự nguyện */
    private Integer mandatory = 0;

    /** per_apartment | per_resident | per_vehicle */
    @Column(length = 30)
    private String chargeType = "per_apartment";

    @Column(length = 20)
    private String frequency = "one_time";

    private Double vehicleRateMotorcycle;
    private Double vehicleRateCar;
    private Double vehicleRateBicycle;
}
