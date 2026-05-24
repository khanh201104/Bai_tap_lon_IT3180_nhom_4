package com.bluemoon.apartment.service;

import com.bluemoon.apartment.dto.response.StatisticResponse;

public interface ReportService {
    StatisticResponse buildSummary();
}
