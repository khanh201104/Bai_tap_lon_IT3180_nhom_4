package com.bluemoon.apartment.mapper;

import com.bluemoon.apartment.dto.response.FeePaymentResponse;
import com.bluemoon.apartment.entity.FeePayment;
import com.bluemoon.apartment.entity.FeeType;
import com.bluemoon.apartment.entity.Household;
import com.bluemoon.apartment.entity.Resident;
import org.springframework.stereotype.Component;

@Component
public class FeePaymentMapper {

    public FeePaymentResponse toResponse(FeePayment feePayment) {
        if (feePayment == null) {
            return null;
        }

        Household household = feePayment.getHousehold();
        FeeType feeType = feePayment.getFeeType();

        Resident headResident = household != null ? household.getHeadResident() : null;

        return FeePaymentResponse.builder()
                .id(feePayment.getId())

                .householdId(household != null ? household.getId() : null)
                .householdCode(household != null ? household.getHouseholdCode() : null)
                .apartmentNumber(household != null ? household.getApartmentNumber() : null)
                .headResidentName(headResident != null ? headResident.getFullName() : null)

                .feeTypeId(feeType != null ? feeType.getId() : null)
                .feeTypeName(feeType != null ? feeType.getName() : null)
                .feeGroup(feeType != null ? feeType.getFeeGroup() : null)
                .cycleType(feeType != null ? feeType.getCycleType() : null)
                .calculationType(feeType != null ? feeType.getCalculationType() : null)

                .period(feePayment.getPeriod())
                .amount(feePayment.getAmount())
                .dueDate(feePayment.getDueDate())
                .paidDate(feePayment.getPaidDate())
                .status(feePayment.getStatus())
                .note(feePayment.getNote())
                .createdAt(feePayment.getCreatedAt())
                .build();
    }
}