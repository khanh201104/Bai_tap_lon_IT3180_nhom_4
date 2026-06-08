package com.bluemoon.apartment.dto.response;

import com.bluemoon.apartment.constant.RelationshipType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class HouseholdMemberResponse {

    private Long id;

    private Long residentId;

    private String residentFullName;

    private String citizenId;

    private String phoneNumber;

    private RelationshipType relationship;

    private LocalDate joinedDate;
}