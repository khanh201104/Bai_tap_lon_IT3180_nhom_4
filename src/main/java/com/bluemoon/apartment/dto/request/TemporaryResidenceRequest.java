package com.bluemoon.apartment.dto.request;

import com.bluemoon.apartment.constant.TemporaryType;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemporaryResidenceRequest {
    private Long residentId;
    private TemporaryType type;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
}
