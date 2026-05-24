package com.bluemoon.apartment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "living_fees")
@Getter
@Setter
public class LivingFee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    /** electric | water | general */
    @Column(length = 20)
    private String category = "general";

    @Column(name = "household_id", nullable = false)
    private Long householdId;

    @Column(length = 50)
    private String apartmentCode;

    @Column(name = "billing_month", length = 7)
    private String billingMonth;

    private Double amount;
}
