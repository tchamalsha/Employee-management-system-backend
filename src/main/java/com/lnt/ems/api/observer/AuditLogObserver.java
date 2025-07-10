package com.lnt.ems.api.observer;

import com.lnt.ems.api.model.Salary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AuditLogObserver implements SalaryCalculationObserver {
    
    private static final Logger log = LoggerFactory.getLogger(AuditLogObserver.class);
    
    @Override
    public void onSalaryCalculated(Salary salary) {
        log.info("Logging salary calculation to audit system - Employee ID: {}, Date: {}, Amount: {}", 
                salary.getId(), salary.getDate(), salary.getSalaryAmount());
        
        // TODO: Implement actual audit logging logic
        // auditService.logSalaryCalculation(salary.getId(), salary.getDate(), salary.getSalaryAmount());
    }
} 