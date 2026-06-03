package com.bluemoon.apartment.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApartmentAreaResponse {

    private Long id;

    private String apartmentNumber;

    private Double area;

    private Double serviceFee;
}