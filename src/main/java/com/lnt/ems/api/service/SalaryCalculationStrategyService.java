package com.lnt.ems.api.service;

import com.lnt.ems.api.strategy.SalaryCalculationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SalaryCalculationStrategyService {
    
    private final Map<String, SalaryCalculationStrategy> strategies;
    
    @Autowired
    public SalaryCalculationStrategyService(List<SalaryCalculationStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(
                        SalaryCalculationStrategy::getStrategyName,
                        Function.identity()
                ));
    }
    
    public Float calculateSalary(String strategyType, Integer employeeId, Date date) {
        SalaryCalculationStrategy strategy = strategies.get(strategyType.toUpperCase());
        if (strategy == null) {
            // Default to standard strategy if not found
            strategy = strategies.get("STANDARD");
        }
        return strategy.calculateSalary(employeeId, date);
    }
    
    public List<String> getAvailableStrategies() {
        return strategies.keySet().stream().collect(Collectors.toList());
    }
} 