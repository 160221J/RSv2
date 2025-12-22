package com.shop.controller;

import com.shop.dto.DailyReportResponse;
import com.shop.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/daily")
    public DailyReportResponse getDailyProfit(@RequestParam(required = false) String date) {

        LocalDate reportDate;

        // If no date provided → use today
        if (date == null || date.isEmpty()) {
            reportDate = LocalDate.now();
        } else {
            reportDate = LocalDate.parse(date);
        }

        return reportService.generateReport(reportDate);
    }
}
