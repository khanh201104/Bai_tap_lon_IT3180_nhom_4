package com.bluemoon.apartment.dto.request;

import com.bluemoon.apartment.constant.VehicleStatus;
import com.bluemoon.apartment.constant.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class VehicleRequest {

    @NotBlank(message = "Biển số xe không được để trống")
    private String licensePlate;

    @NotNull(message = "Loại phương tiện không được để trống")
    private VehicleType vehicleType;

    private String brand;

    private String color;

    private String ownerName;

    @NotNull(message = "Ngày đăng ký không được để trống")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate registeredDate;

    @NotNull(message = "Trạng thái không được để trống")
    private VehicleStatus status = VehicleStatus.ACTIVE;

    private String note;

    @NotNull(message = "Hộ khẩu không được để trống")
    private Long householdId;
}