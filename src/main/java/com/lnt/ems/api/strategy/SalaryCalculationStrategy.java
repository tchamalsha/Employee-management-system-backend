package com.lnt.ems.api.strategy;

import java.util.Date;

public interface SalaryCalculationStrategy {
    Float calculateSalary(Integer employeeId, Date date);
    String getStrategyName();
} 