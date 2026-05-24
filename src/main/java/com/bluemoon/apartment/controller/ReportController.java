package com.bluemoon.apartment.controller;

import com.bluemoon.apartment.service.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public String feeStatistic(Model model) {
        model.addAttribute("stats", reportService.buildSummary());
        return "report/fee-statistic";
    }
}
