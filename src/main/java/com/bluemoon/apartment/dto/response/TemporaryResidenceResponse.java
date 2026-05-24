package com.bluemoon.apartment.dto.response;

import com.bluemoon.apartment.constant.TemporaryType;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemporaryResidenceResponse {
    private Long id;
    private ResidentResponse resident;
    private TemporaryType type;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
}
