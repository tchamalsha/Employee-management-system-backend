package com.lnt.ems.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"id","date"}
        )
)

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryData {

    @Id
    private Integer id;
    private Date date;
    private Float noPayDays;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getNoPayDays() {
        return noPayDays;
    }

    public void setNoPayDays(Float noPayDays) {
        this.noPayDays = noPayDays;
    }

    public Float getOverTimeHours() {
        return overTimeHours;
    }

    public void setOverTimeHours(Float overTimeHours) {
        this.overTimeHours = overTimeHours;
    }

    public Integer getAttendanceBonus() {
        return attendanceBonus;
    }

    public void setAttendanceBonus(Integer attendanceBonus) {
        this.attendanceBonus = attendanceBonus;
    }

    private Float overTimeHours;
    private Integer attendanceBonus;



}
