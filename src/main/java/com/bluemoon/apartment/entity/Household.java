package com.bluemoon.apartment.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "household")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Household {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String householdCode;

    private String headName;

    private String apartmentNumber;
}