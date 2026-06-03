package com.bluemoon.apartment.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "apartment_area")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApartmentArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String apartmentNumber;

    private Double area;

    private Double serviceFee;
}