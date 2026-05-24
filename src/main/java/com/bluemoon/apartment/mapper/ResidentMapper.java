package com.bluemoon.apartment.mapper;

import com.bluemoon.apartment.dto.request.ResidentRequest;
import com.bluemoon.apartment.dto.response.ResidentResponse;
import com.bluemoon.apartment.entity.Resident;
import org.springframework.stereotype.Component;

@Component
public class ResidentMapper {

    public Resident toEntity(ResidentRequest request) {
        if (request == null) return null;
        return Resident.builder()
                .fullName(request.getFullName())
                .citizenId(request.getCitizenId())
                .phoneNumber(request.getPhoneNumber())
                .gender(request.getGender())
                .dateOfBirth(request.getDateOfBirth())
                .status(request.getStatus())
                .build();
    }

    public ResidentResponse toResponse(Resident resident) {
        if (resident == null) return null;
        return ResidentResponse.builder()
                .id(resident.getId())
                .fullName(resident.getFullName())
                .citizenId(resident.getCitizenId())
                .phoneNumber(resident.getPhoneNumber())
                .gender(resident.getGender())
                .dateOfBirth(resident.getDateOfBirth())
                .status(resident.getStatus())
                .build();
    }

    public void updateEntity(Resident resident, ResidentRequest request) {
        if (request == null) return;
        resident.setFullName(request.getFullName());
        resident.setCitizenId(request.getCitizenId());
        resident.setPhoneNumber(request.getPhoneNumber());
        resident.setGender(request.getGender());
        resident.setDateOfBirth(request.getDateOfBirth());
        resident.setStatus(request.getStatus());
    }
}
