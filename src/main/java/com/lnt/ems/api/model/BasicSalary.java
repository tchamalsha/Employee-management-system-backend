package com.lnt.ems.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"id"}
        )
)

@Entity
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

    private Integer basicSalary;
    private Float otRate;
}
