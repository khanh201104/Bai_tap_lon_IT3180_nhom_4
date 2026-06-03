package com.bluemoon.apartment.dto.request;

import lombok.Data;

@Data
public class HouseholdRequest {

    private String householdCode;
    private String headName;
    private String apartmentNumber;
}