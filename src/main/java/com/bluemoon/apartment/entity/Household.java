package com.bluemoon.apartment.entity;

import com.bluemoon.apartment.constant.HouseholdStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "households")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Household {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "household_code", nullable = false, unique = true, length = 50)
    private String householdCode;

    @Column(name = "apartment_number", nullable = false, length = 50)
    private String apartmentNumber;

    @Column(name = "address", length = 255)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "head_resident_id", nullable = false)
    private Resident headResident;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private HouseholdStatus status;

    @Column(name = "apartment_area")
    private Double apartmentArea;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HouseholdMember> members = new ArrayList<>();
}