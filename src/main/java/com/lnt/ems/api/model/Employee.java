package com.lnt.ems.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"id"}
        )
)

@Entity @Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        private Integer cid;
        private String fullName;
        private String initials;

        private String nid;
        private String contact;
        private String address;
        private Integer basic;
        private String emergencyContact;
        private Date dateHired;
        private String password;
        private String position;
        private String department;

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public String getFullName() {
                return fullName;
        }

        public void setFullName(String fullName) {
                this.fullName = fullName;
        }

        public Integer getCid() {
                return cid;
        }

        public void setCid(Integer cid) {
                this.cid = cid;
        }

        public String getInitials() {
                return initials;
        }

        public void setInitials(String initials) {
                this.initials = initials;
        }

        public String getDepartment() {
                return department;
        }

        public void setDepartment(String department) {
                this.department = department;
        }

        public String getPosition() {
                return position;
        }

        public void setPosition(String position) {
                this.position = position;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public Date getDateHired() {
                return dateHired;
        }

        public void setDateHired(Date dateHired) {
                this.dateHired = dateHired;
        }

        public String getEmergencyContact() {
                return emergencyContact;
        }

        public void setEmergencyContact(String emergencyContact) {
                this.emergencyContact = emergencyContact;
        }

        public Integer getBasic() {
                return basic;
        }

        public void setBasic(Integer basic) {
                this.basic = basic;
        }

        public String getAddress() {
                return address;
        }

        public void setAddress(String address) {
                this.address = address;
        }

        public String getContact() {
                return contact;
        }

        public void setContact(String contact) {
                this.contact = contact;
        }

        public String getNid() {
                return nid;
        }

        public void setNid(String nid) {
                this.nid = nid;
        }



}
