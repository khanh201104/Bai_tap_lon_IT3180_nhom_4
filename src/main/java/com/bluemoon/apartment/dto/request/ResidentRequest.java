package com.bluemoon.apartment.dto.request;

import com.bluemoon.apartment.constant.ResidentStatus;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResidentRequest {
    private String fullName;
    private String citizenId;
    private String phoneNumber;
    private String gender;
    private LocalDate dateOfBirth;
    private ResidentStatus status;
}
