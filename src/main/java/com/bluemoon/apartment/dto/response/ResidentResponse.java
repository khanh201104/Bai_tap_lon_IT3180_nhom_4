package com.bluemoon.apartment.dto.response;

import com.bluemoon.apartment.constant.ResidentStatus;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResidentResponse {
    private Long id;
    private String fullName;
    private String citizenId;
    private String phoneNumber;
    private String gender;
    private LocalDate dateOfBirth;
    private ResidentStatus status;
}
