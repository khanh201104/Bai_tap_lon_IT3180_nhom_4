package com.bluemoon.apartment.mapper;

import com.bluemoon.apartment.dto.response.FeeTypeResponse;
import com.bluemoon.apartment.entity.FeeType;
import org.springframework.stereotype.Component;

@Component
public class FeeTypeMapper {

    public FeeTypeResponse toResponse(FeeType feeType) {
        if (feeType == null) {
            return null;
        }

        return FeeTypeResponse.builder()
                .id(feeType.getId())
                .name(feeType.getName())
                .description(feeType.getDescription())
                .feeGroup(feeType.getFeeGroup())
                .cycleType(feeType.getCycleType())
                .calculationType(feeType.getCalculationType())
                .unitPrice(feeType.getUnitPrice())
                .active(feeType.getActive())
                .build();
    }
}