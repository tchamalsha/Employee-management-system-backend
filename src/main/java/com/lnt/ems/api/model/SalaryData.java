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
@IdClass(SalaryData.SalaryDataId.class)
public class SalaryData {

    @Id
    private Integer id;
    
    @Id
    @Column(length = 7) // YYYY-MM format (7 characters)
    private String date;
    
    private Float noPayDays;
    private Float overTimeHours;
    private Integer attendanceBonus;

    // Composite primary key class
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SalaryDataId implements Serializable {
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
}
