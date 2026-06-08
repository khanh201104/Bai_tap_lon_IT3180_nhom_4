package com.bluemoon.apartment.dto.request;

import com.bluemoon.apartment.constant.TemporaryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class TemporaryResidenceRequest {

    @NotNull(message = "Cư dân không được để trống")
    private Long residentId;

    @NotNull(message = "Loại tạm trú/tạm vắng không được để trống")
    private TemporaryType type;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDate;

    @NotNull(message = "Ngày kết thúc không được để trống")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate endDate;

    @NotBlank(message = "Lý do không được để trống")
    private String reason;
}