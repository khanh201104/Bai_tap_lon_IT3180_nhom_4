package com.bluemoon.apartment.dto.response;

import com.bluemoon.apartment.constant.HouseholdStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class HouseholdResponse {

    private Long id;

    private String householdCode;

    private String apartmentNumber;

    private String address;

    private Long headResidentId;

    private String headResidentName;

    private LocalDate createdDate;

    private HouseholdStatus status;

    private String note;

    private int memberCount;

    private Double apartmentArea;

    private List<HouseholdMemberResponse> members;
}