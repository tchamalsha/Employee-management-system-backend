package com.lnt.ems.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(Salary.SalaryId.class)
public class Salary {

    @Id
    private Integer id;
    
    @Id
    @Column(length = 7) // YYYY-MM format (7 characters)
    private String date;
    
    private Float salaryAmount;

    // Composite primary key class
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SalaryId implements Serializable {
        private Integer id;
        private String date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getSalaryAmount() {
        return salaryAmount;
    }

    public void setSalaryAmount(Float salary) {
        this.salaryAmount = salary;
    }
}
