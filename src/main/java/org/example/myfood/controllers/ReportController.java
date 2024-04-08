package org.example.myfood.controllers;

import org.example.myfood.DTO.ReportDTO;
import org.example.myfood.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {
    @Autowired
    ReportService reportService;
    @GetMapping("/getReport")
    public ReportDTO getReport(@RequestParam String firstDate, @RequestParam String lastDate){
        return reportService.getCashedReport(firstDate, lastDate);
    }
}
