package com.lnt.ems.api.service;

import com.lnt.ems.api.model.SalaryData;
import com.lnt.ems.api.repository.SalaryDataRepository;
import com.lnt.ems.api.repository.SalaryDetailsRepository;
import com.lnt.ems.api.repository.SalaryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SalaryServiceImplTest {
    @Mock
    private SalaryRepository salaryRepository;
    @Mock
    private SalaryDetailsRepository salaryDetailsRepository;
    @Mock
    private SalaryDataRepository salaryDataRepository;

    @InjectMocks
    private SalaryServiceImpl salaryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculateSalary() {
        // Arrange
        int id = 1;
        String date = "2024-01";
        SalaryData salaryData = new SalaryData();
        salaryData.setId(id);
        salaryData.setDate(date);
        salaryData.setNoPayDays(2.0f);
        salaryData.setOverTimeHours(10.0f);
        salaryData.setAttendanceBonus(500);
        when(salaryDetailsRepository.getBasicSalary(id)).thenReturn(50000);
        when(salaryDetailsRepository.getOtRate(id)).thenReturn(500.0f);
        when(salaryDataRepository.getSalaryData(id, date)).thenReturn(salaryData);

        // Act
        Float result = salaryService.calculateSalary(id, date);

        // Assert
        assertNotNull(result);
        assertEquals(51500.0f, result, 0.01f);
    }

    @Test
    void testGetSalary() {
        // Arrange
        int id = 1;
        String date = "2024-01";
        when(salaryRepository.getEmployeeSalary(date, id)).thenReturn(52000.0f);

        // Act
        Float result = salaryService.getSalary(id, date);

        // Assert
        assertNotNull(result);
        assertEquals(52000.0f, result);
    }
} 