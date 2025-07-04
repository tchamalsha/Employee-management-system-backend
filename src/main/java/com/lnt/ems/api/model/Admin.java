package com.lnt.ems.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Admin")
@Data
@NoArgsConstructor
public class Admin extends User {

    // Admin-specific fields can be added here if needed
    // For now, Admin inherits all fields from User

    public Admin(Integer id, String name, String email, String password, String position) {
        super(id, name, email, password, position, "ADMIN");
    }
}
