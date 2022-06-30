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
public class Salary {

    @Id
    private Integer id;
    private Date date;
    private Float salary;

        public Float getSalary() {
                return salary;
        }

        public void setSalary(Float salary) {
                this.salary = salary;
        }
}
