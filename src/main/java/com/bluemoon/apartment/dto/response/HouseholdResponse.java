package com.bluemoon.apartment.dto.response;

import lombok.Data;

@Data
public class HouseholdResponse {

    private Long id;
    private String householdCode;
    private String headName;
    private String apartmentNumber;
}