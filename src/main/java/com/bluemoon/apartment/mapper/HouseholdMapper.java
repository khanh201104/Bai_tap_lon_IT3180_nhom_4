package com.bluemoon.apartment.mapper;

import com.bluemoon.apartment.dto.response.HouseholdMemberResponse;
import com.bluemoon.apartment.dto.response.HouseholdResponse;
import com.bluemoon.apartment.entity.Household;
import com.bluemoon.apartment.entity.HouseholdMember;
import com.bluemoon.apartment.entity.Resident;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HouseholdMapper {

    private final HouseholdMemberMapper householdMemberMapper;

    public HouseholdResponse toResponse(Household household) {
        if (household == null) {
            return null;
        }

        Resident headResident = household.getHeadResident();

        List<HouseholdMember> memberEntities = household.getMembers();

        List<HouseholdMemberResponse> members = memberEntities == null
                ? List.of()
                : memberEntities.stream()
                  .map(householdMemberMapper::toResponse)
                  .toList();

        return HouseholdResponse.builder()
                .id(household.getId())
                .householdCode(household.getHouseholdCode())
                .apartmentNumber(household.getApartmentNumber())
                .address(household.getAddress())
                .headResidentId(headResident != null ? headResident.getId() : null)
                .headResidentName(headResident != null ? headResident.getFullName() : null)
                .createdDate(household.getCreatedDate())
                .status(household.getStatus())
                .note(household.getNote())
                .memberCount(members.size())
                .members(members)
                .apartmentArea(household.getApartmentArea())
                .build();
    }
}