package com.bluemoon.apartment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "fee_payments")
@Getter
@Setter
public class FeePayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fee_type_id", nullable = false)
    private Long feeTypeId;

    @Column(name = "household_id", nullable = false)
    private Long householdId;

    @Column(length = 50)
    private String apartmentCode;

    @Column(length = 100)
    private String payerName;

    private Double amount;

    private LocalDate paymentDate;

    /** PAID | UNPAID | PENDING */
    @Column(length = 20)
    private String status = "UNPAID";
}
