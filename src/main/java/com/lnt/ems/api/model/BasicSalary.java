package com.lnt.ems.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Column;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;

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

    private Integer basicSalary;

    public Float getOtRate() {
        return otRate;
    }

    public void setOtRate(Float otRate) {
        this.otRate = otRate;
    }

    private Float otRate;

    private Integer specialAllowance;

    @NotNull
    @Column(nullable = false)
    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
