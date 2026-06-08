package com.bluemoon.apartment.dto.request;

import com.bluemoon.apartment.constant.RelationshipType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class HouseholdMemberRequest {

    @NotNull(message = "Cư dân không được để trống")
    private Long residentId;

    @NotNull(message = "Quan hệ với chủ hộ không được để trống")
    private RelationshipType relationship;

    @NotNull(message = "Ngày tham gia hộ khẩu không được để trống")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate joinedDate;
}