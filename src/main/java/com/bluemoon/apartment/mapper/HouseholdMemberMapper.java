package com.bluemoon.apartment.mapper;

import com.bluemoon.apartment.dto.response.HouseholdMemberResponse;
import com.bluemoon.apartment.entity.HouseholdMember;
import com.bluemoon.apartment.entity.Resident;
import org.springframework.stereotype.Component;

@Component
public class HouseholdMemberMapper {

    public HouseholdMemberResponse toResponse(HouseholdMember member) {
        if (member == null) {
            return null;
        }

        Resident resident = member.getResident();

        return HouseholdMemberResponse.builder()
                .id(member.getId())
                .residentId(resident != null ? resident.getId() : null)
                .residentFullName(resident != null ? resident.getFullName() : null)
                .citizenId(resident != null ? resident.getCitizenId() : null)
                .phoneNumber(resident != null ? resident.getPhoneNumber() : null)
                .relationship(member.getRelationship())
                .joinedDate(member.getJoinedDate())
                .build();
    }
}