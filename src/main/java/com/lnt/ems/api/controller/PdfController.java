package com.lnt.ems.api.controller;

import com.lnt.ems.api.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @GetMapping("/salary-report/{month}")
    public ResponseEntity<byte[]> downloadSalaryReport(@PathVariable String month) {
        try {
            byte[] pdfBytes = pdfService.generateSalaryReport(month);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "salary-report-" + month + ".pdf");
            
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employee-salary-report/{employeeId}/{month}")
    public ResponseEntity<byte[]> downloadEmployeeSalaryReport(
            @PathVariable Integer employeeId, 
            @PathVariable String month) {
        try {
            byte[] pdfBytes = pdfService.generateEmployeeSalaryReport(employeeId, month);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "employee-" + employeeId + "-salary-" + month + ".pdf");
            
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
} 