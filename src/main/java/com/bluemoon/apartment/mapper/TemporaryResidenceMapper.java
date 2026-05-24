package com.bluemoon.apartment.mapper;

import com.bluemoon.apartment.dto.request.TemporaryResidenceRequest;
import com.bluemoon.apartment.dto.response.TemporaryResidenceResponse;
import com.bluemoon.apartment.entity.TemporaryResidence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TemporaryResidenceMapper {

    private final ResidentMapper residentMapper;

    public TemporaryResidence toEntity(TemporaryResidenceRequest request) {
        if (request == null) return null;
        return TemporaryResidence.builder()
                .type(request.getType())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .reason(request.getReason())
                .build();
    }

    public TemporaryResidenceResponse toResponse(TemporaryResidence entity) {
        if (entity == null) return null;
        return TemporaryResidenceResponse.builder()
                .id(entity.getId())
                .resident(residentMapper.toResponse(entity.getResident()))
                .type(entity.getType())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .reason(entity.getReason())
                .build();
    }

    public void updateEntity(TemporaryResidence entity, TemporaryResidenceRequest request) {
        if (request == null) return;
        entity.setType(request.getType());
        entity.setStartDate(request.getStartDate());
        entity.setEndDate(request.getEndDate());
        entity.setReason(request.getReason());
    }
}
