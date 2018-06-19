package org.jesusyouth.jykc.jykcadmin.model;

import javax.persistence.*;

@Entity(name = "religious_people")
public class ReligiousPeople {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String mail;
    private String phone;
    private String address;
    private String gender;
    private Integer zone;
    @Column(name = "zone_responsibility")
    private String zoneResponsibility;
    private boolean accomadation;
    private int age;

    public ReligiousPeople() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getZone() {
        return zone;
    }

    public void setZone(Integer zone) {
        this.zone = zone;
    }

    public String getZoneResponsibility() {
        return zoneResponsibility;
    }

    public void setZoneResponsibility(String zoneResponsibility) {
        this.zoneResponsibility = zoneResponsibility;
    }

    public boolean isAccomadation() {
        return accomadation;
    }

    public void setAccomadation(boolean accomadation) {
        this.accomadation = accomadation;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
