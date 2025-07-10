package com.lnt.ems.api.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.lnt.ems.api.model.Employee;
import com.lnt.ems.api.model.Salary;
import com.lnt.ems.api.model.SalaryData;
import com.lnt.ems.api.model.SalaryDetails;
import com.lnt.ems.api.repository.EmployeeRepository;
import com.lnt.ems.api.repository.SalaryDataRepository;
import com.lnt.ems.api.repository.SalaryRepository;
import com.lnt.ems.api.repository.SalaryDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PdfService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private SalaryDataRepository salaryDataRepository;

    @Autowired
    private SalaryDetailsRepository salaryDetailsRepository;

    public byte[] generateSalaryReport(String month) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Add title
            Paragraph title = new Paragraph("Salary Report - " + month)
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold();
            document.add(title);

            // Add date
            Paragraph date = new Paragraph("Generated on: " + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(date);

            // Add space
            document.add(new Paragraph("\n"));

            // Get all employees
            List<Employee> employees = employeeRepository.findAll();
            
            if (employees.isEmpty()) {
                Paragraph noEmployees = new Paragraph("No employees found in the system.")
                        .setFontSize(14)
                        .setTextAlignment(TextAlignment.CENTER);
                document.add(noEmployees);
                document.close();
                return baos.toByteArray();
            }

            // Create table for salary data
            Table table = new Table(UnitValue.createPercentArray(new float[]{20, 50, 30}))
                    .useAllAvailableWidth();

            // Add table headers
            table.addHeaderCell("Employee ID");
            table.addHeaderCell("Employee Name");
            table.addHeaderCell("Total Salary");

            // Add data rows
            for (Employee employee : employees) {
                try {
                    Float totalSalary = salaryRepository.getEmployeeSalary(month, employee.getId());
                    table.addCell(String.valueOf(employee.getId()));
                    table.addCell(employee.getName());
                    if (totalSalary != null) {
                        table.addCell(String.format("Rs. %.2f", totalSalary));
                    } else {
                        table.addCell("N/A");
                    }
                } catch (Exception e) {
                    table.addCell(String.valueOf(employee.getId()));
                    table.addCell(employee.getName());
                    table.addCell("Error");
                }
            }

            document.add(table);

            // Add summary
            document.add(new Paragraph("\n"));
            Paragraph summary = new Paragraph("Summary:")
                    .setFontSize(14)
                    .setBold();
            document.add(summary);

            // Calculate totals
            double totalBasicSalary = 0;
            double totalAllowances = 0;
            double totalDeductions = 0;
            double totalNetSalary = 0;
            int employeeCount = 0;

            for (Employee employee : employees) {
                try {
                    SalaryData salaryData = salaryDataRepository.getSalaryData(employee.getId(), month);
                    Float calculatedSalary = salaryRepository.getEmployeeSalary(month, employee.getId());
                    SalaryDetails salaryDetails = salaryDetailsRepository.findById(employee.getId()).orElse(null);

                    if (salaryData != null && calculatedSalary != null && salaryDetails != null) {
                        totalBasicSalary += salaryDetails.getBasicSalary();
                        totalAllowances += salaryDetails.getSpecialAllowance();
                        totalDeductions += 0; // Deductions not available in current model
                        totalNetSalary += calculatedSalary;
                        employeeCount++;
                    }
                } catch (Exception e) {
                    // Skip this employee in calculations if there's an error
                }
            }

            Paragraph totalEmployees = new Paragraph("Total Employees with Salary Data: " + employeeCount);
            Paragraph totalBasic = new Paragraph("Total Basic Salary: Rs. " + String.format("%.2f", totalBasicSalary));
            Paragraph totalAllow = new Paragraph("Total Allowances: Rs. " + String.format("%.2f", totalAllowances));
            Paragraph totalDeduct = new Paragraph("Total Deductions: Rs. " + String.format("%.2f", totalDeductions));
            Paragraph totalNet = new Paragraph("Total Net Salary: Rs. " + String.format("%.2f", totalNetSalary))
                    .setBold();

            document.add(totalEmployees);
            document.add(totalBasic);
            document.add(totalAllow);
            document.add(totalDeduct);
            document.add(totalNet);

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF report", e);
        }
    }

    public byte[] generateEmployeeSalaryReport(Integer employeeId, String month) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Get employee details
            Employee employee = employeeRepository.findEmployeeById(employeeId);
            if (employee == null) {
                // Create a simple PDF with error message
                Paragraph errorTitle = new Paragraph("Employee Not Found")
                        .setFontSize(20)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBold();
                document.add(errorTitle);
                
                Paragraph errorMsg = new Paragraph("Employee with ID " + employeeId + " was not found in the system.")
                        .setFontSize(14)
                        .setTextAlignment(TextAlignment.CENTER);
                document.add(errorMsg);
                
                document.close();
                return baos.toByteArray();
            }

            // Add title
            Paragraph title = new Paragraph("Employee Salary Report - " + month)
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold();
            document.add(title);

            // Add employee details
            document.add(new Paragraph("\n"));
            Paragraph employeeInfo = new Paragraph("Employee Information:")
                    .setFontSize(14)
                    .setBold();
            document.add(employeeInfo);

            Paragraph empId = new Paragraph("Employee ID: " + employee.getId());
            Paragraph empName = new Paragraph("Name: " + employee.getName());
            Paragraph empEmail = new Paragraph("Email: " + employee.getEmail());

            document.add(empId);
            document.add(empName);
            document.add(empEmail);

            // Get salary data
            SalaryData salaryData = null;
            Float calculatedSalary = null;
            SalaryDetails salaryDetails = null;
            
            try {
                salaryData = salaryDataRepository.getSalaryData(employeeId, month);
                calculatedSalary = salaryRepository.getEmployeeSalary(month, employeeId);
                salaryDetails = salaryDetailsRepository.findById(employeeId).orElse(null);
            } catch (Exception e) {
                // Handle any database errors gracefully
            }

            if (salaryData != null && calculatedSalary != null && salaryDetails != null) {
                document.add(new Paragraph("\n"));
                Paragraph salaryInfo = new Paragraph("Salary Details for " + month + ":")
                        .setFontSize(14)
                        .setBold();
                document.add(salaryInfo);

                // Create salary breakdown table
                Table salaryTable = new Table(UnitValue.createPercentArray(new float[]{50, 50}))
                        .useAllAvailableWidth();

                salaryTable.addHeaderCell("Component");
                salaryTable.addHeaderCell("Amount (Rs.)");

                salaryTable.addCell("Basic Salary");
                salaryTable.addCell(String.format("%.2f", salaryDetails.getBasicSalary()));

                salaryTable.addCell("Special Allowance");
                salaryTable.addCell(String.format("%.2f", salaryDetails.getSpecialAllowance()));

                salaryTable.addCell("Deductions");
                salaryTable.addCell("Rs. 0.00"); // Deductions not available in current model

                salaryTable.addCell("Net Salary");
                salaryTable.addCell(String.format("%.2f", calculatedSalary));

                document.add(salaryTable);

                // Add calculation details
                document.add(new Paragraph("\n"));
                Paragraph calcInfo = new Paragraph("Calculation Details:")
                        .setFontSize(12)
                        .setBold();
                document.add(calcInfo);

                double grossSalary = salaryDetails.getBasicSalary() + salaryDetails.getSpecialAllowance();
                Paragraph gross = new Paragraph("Gross Salary (Basic + Allowances): Rs. " + String.format("%.2f", grossSalary));
                Paragraph net = new Paragraph("Net Salary: Rs. " + String.format("%.2f", calculatedSalary));

                document.add(gross);
                document.add(net);

            } else {
                document.add(new Paragraph("\n"));
                Paragraph noData = new Paragraph("No salary data available for " + month)
                        .setFontSize(12)
                        .setTextAlignment(TextAlignment.CENTER);
                document.add(noData);
            }

            // Add footer
            document.add(new Paragraph("\n"));
            Paragraph footer = new Paragraph("Report generated on: " + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(footer);

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generating employee PDF report", e);
        }
    }
} 