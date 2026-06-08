package com.bluemoon.apartment.mapper;

import com.bluemoon.apartment.constant.ResidentStatus;
import com.bluemoon.apartment.dto.request.ResidentRequest;
import com.bluemoon.apartment.dto.response.ResidentResponse;
import com.bluemoon.apartment.entity.Resident;
import org.springframework.stereotype.Component;

@Component
public class ResidentMapper {

    public Resident toEntity(ResidentRequest request) {
        Resident resident = new Resident();

        resident.setFullName(request.getFullName());
        resident.setCitizenId(request.getCitizenId());
        resident.setPhoneNumber(request.getPhoneNumber());
        resident.setGender(request.getGender());
        resident.setDateOfBirth(request.getDateOfBirth());

        if (request.getStatus() != null && !request.getStatus().isBlank()) {
            resident.setStatus(ResidentStatus.valueOf(request.getStatus()));
        }

        return resident;
    }

    public ResidentResponse toResponse(Resident resident) {
        if (resident == null) {
            return null;
        }

        ResidentResponse response = new ResidentResponse();

        response.setId(resident.getId());
        response.setFullName(resident.getFullName());
        response.setCitizenId(resident.getCitizenId());
        response.setPhoneNumber(resident.getPhoneNumber());
        response.setGender(resident.getGender());
        response.setDateOfBirth(resident.getDateOfBirth());

        if (resident.getStatus() != null) {
            response.setStatus(ResidentStatus.valueOf(resident.getStatus().name()));
        }

        return response;
    }

    public void updateEntity(Resident resident, ResidentRequest request) {
        resident.setFullName(request.getFullName());
        resident.setCitizenId(request.getCitizenId());
        resident.setPhoneNumber(request.getPhoneNumber());
        resident.setGender(request.getGender());
        resident.setDateOfBirth(request.getDateOfBirth());

        if (request.getStatus() != null && !request.getStatus().isBlank()) {
            resident.setStatus(ResidentStatus.valueOf(request.getStatus()));
        }
    }
}