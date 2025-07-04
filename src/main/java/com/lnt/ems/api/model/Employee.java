package com.lnt.ems.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Employee")
@Data
@NoArgsConstructor
public class Employee extends User {

    // Employee-specific field to track which admin created this employee
    @Column(name = "admin_id")
    private Integer adminId;

    public Employee(Integer id, String name, String email, String password, String position, Integer adminId) {
        super(id, name, email, password, position, "EMPLOYEE");
        this.adminId = adminId;
    }

    // Getter and Setter for adminId
    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }
}
