package com.bluemoon.apartment.dto.response;

import com.bluemoon.apartment.constant.FeeCalculationType;
import com.bluemoon.apartment.constant.FeeCycleType;
import com.bluemoon.apartment.constant.FeeGroup;
import com.bluemoon.apartment.constant.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class FeePaymentResponse {

    private Long id;

    private Long householdId;

    private String householdCode;

    private String apartmentNumber;

    private String headResidentName;

    private Long feeTypeId;

    private String feeTypeName;

    private FeeGroup feeGroup;

    private FeeCycleType cycleType;

    private FeeCalculationType calculationType;

    private String period;

    private BigDecimal amount;

    private LocalDate dueDate;

    private LocalDate paidDate;

    private PaymentStatus status;

    private String note;

    private LocalDateTime createdAt;
}