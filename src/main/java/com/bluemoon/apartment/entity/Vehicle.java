package com.bluemoon.apartment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "household_id", nullable = false)
    private Long householdId;

    @Column(length = 50)
    private String apartmentCode;

    /** motorcycle | car | bicycle */
    @Column(nullable = false, length = 20)
    private String type;

    @Column(length = 20)
    private String plate;

    @Column(length = 255)
    private String note;
}
