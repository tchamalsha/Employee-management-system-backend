package com.lnt.ems.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Column;

@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"id","date"}
        )
)

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salary {

    @Id
    private Integer id;
    
    @Column(length = 7) // YYYY-MM format (7 characters)
    private String date;
    
    private Float salaryAmount;

    public Float getSalaryAmount() {
        return salaryAmount;
    }

    public void setSalaryAmount(Float salary) {
        this.salaryAmount = salary;
    }
}
