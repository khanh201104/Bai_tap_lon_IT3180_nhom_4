package com.bluemoon.apartment.dto.request;

import com.bluemoon.apartment.constant.FeeCalculationType;
import com.bluemoon.apartment.constant.FeeCycleType;
import com.bluemoon.apartment.constant.FeeGroup;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FeeTypeRequest {

    @NotBlank(message = "Tên loại phí không được để trống")
    private String name;

    private String description;

    @NotNull(message = "Nhóm phí không được để trống")
    private FeeGroup feeGroup;

    @NotNull(message = "Chu kỳ thu không được để trống")
    private FeeCycleType cycleType;

    @NotNull(message = "Cách tính phí không được để trống")
    private FeeCalculationType calculationType;

    @NotNull(message = "Đơn giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Đơn giá phải lớn hơn 0")
    private BigDecimal unitPrice;

    @NotNull(message = "Trạng thái áp dụng không được để trống")
    private Boolean active;
}