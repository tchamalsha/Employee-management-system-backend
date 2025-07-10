package com.lnt.ems.api.service.interfaces;

import com.lnt.ems.api.model.SalaryData;
import com.lnt.ems.api.model.SalaryDetails;

import java.util.Date;

public interface SalaryValidationService {
    boolean validateSalaryData(SalaryData data);
    boolean validateEmployeeExists(Integer employeeId);
    boolean validateSalaryDetails(SalaryDetails details);
    boolean validateDate(Date date);
    boolean validateSalaryAmount(Float amount);
} 