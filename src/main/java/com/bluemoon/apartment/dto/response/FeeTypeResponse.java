package com.bluemoon.apartment.dto.response;

import com.bluemoon.apartment.constant.FeeCalculationType;
import com.bluemoon.apartment.constant.FeeCycleType;
import com.bluemoon.apartment.constant.FeeGroup;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class FeeTypeResponse {

    private Long id;

    private String name;

    private String description;

    private FeeGroup feeGroup;

    private FeeCycleType cycleType;

    private FeeCalculationType calculationType;

    private BigDecimal unitPrice;

    private Boolean active;
}