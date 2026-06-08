package com.bluemoon.apartment.dto.request;

import com.bluemoon.apartment.constant.HouseholdStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class HouseholdRequest {

    @NotBlank(message = "Mã hộ khẩu không được để trống")
    private String householdCode;

    @NotBlank(message = "Số căn hộ không được để trống")
    private String apartmentNumber;

    private String address;

    @NotNull(message = "Chủ hộ không được để trống")
    private Long headResidentId;

    @NotNull(message = "Ngày lập hộ khẩu không được để trống")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate createdDate;

    @NotNull(message = "Trạng thái hộ khẩu không được để trống")
    private HouseholdStatus status;

    @NotNull(message = "Diện tích căn hộ không được để trống")
    @Positive(message = "Diện tích căn hộ phải lớn hơn 0")
    private Double apartmentArea;

    private String note;
}