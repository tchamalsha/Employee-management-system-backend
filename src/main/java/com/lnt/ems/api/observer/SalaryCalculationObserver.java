package com.lnt.ems.api.observer;

import com.lnt.ems.api.model.Salary;

public interface SalaryCalculationObserver {
    void onSalaryCalculated(Salary salary);
} 