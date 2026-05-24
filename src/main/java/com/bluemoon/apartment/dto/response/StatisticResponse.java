package com.bluemoon.apartment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class StatisticResponse {
    private final long totalHouseholds;
    private final long totalResidentsEstimate;
    private final double totalCollected;
    private final double totalUncollected;
    private final List<UnpaidRow> unpaidHouseholds;

    @Getter
    @AllArgsConstructor
    public static class UnpaidRow {
        private final Long householdId;
        private final String apartmentCode;
        private final String feeName;
        private final double amountDue;
    }
}
