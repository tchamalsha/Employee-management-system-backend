package com.lnt.ems.api.observer;

import com.lnt.ems.api.model.Salary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationObserver implements SalaryCalculationObserver {
    
    private static final Logger log = LoggerFactory.getLogger(EmailNotificationObserver.class);
    
    @Override
    public void onSalaryCalculated(Salary salary) {
        log.info("Sending email notification for salary calculation - Employee ID: {}, Date: {}, Amount: {}", 
                salary.getId(), salary.getDate(), salary.getSalaryAmount());
        
        // TODO: Implement actual email sending logic
        // emailService.sendSalaryNotification(salary.getId(), salary.getSalaryAmount());
    }
} 