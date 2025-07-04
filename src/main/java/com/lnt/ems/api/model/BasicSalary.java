package com.lnt.ems.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
    name = "salary_details",
    uniqueConstraints = @UniqueConstraint(
            columnNames = {"id"}
    )
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicSalary {

    @Id
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Integer basicSalary) {
        this.basicSalary = basicSalary;
    }

    public Float getOtRate() {
        return otRate;
    }

    public void setOtRate(Float otRate) {
        this.otRate = otRate;
    }

    public Integer getSpecialAllowance() {
        return specialAllowance;
    }

    public void setSpecialAllowance(Integer specialAllowance) {
        this.specialAllowance = specialAllowance;
    }

    private Integer basicSalary;
    private Float otRate;
    private Integer specialAllowance;
}
