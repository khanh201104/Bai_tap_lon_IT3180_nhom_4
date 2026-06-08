package com.bluemoon.apartment.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FeePaymentRequest {

    @NotNull(message = "Hộ khẩu không được để trống")
    private Long householdId;

    @NotNull(message = "Loại phí không được để trống")
    private Long feeTypeId;

    @NotBlank(message = "Kỳ thu không được để trống")
    private String period;

    @NotNull(message = "Số tiền không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Số tiền phải lớn hơn 0")
    private BigDecimal amount;

    @NotNull(message = "Hạn đóng không được để trống")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dueDate;

    private String note;
}