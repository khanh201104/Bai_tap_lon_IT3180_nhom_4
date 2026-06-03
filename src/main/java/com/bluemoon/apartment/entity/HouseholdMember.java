package com.bluemoon.apartment.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "household_member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseholdMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String relationship;

    @ManyToOne
    @JoinColumn(name = "household_id")
    private Household household;
}