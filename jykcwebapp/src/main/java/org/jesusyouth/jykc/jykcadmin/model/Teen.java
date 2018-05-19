package org.jesusyouth.jykc.jykcadmin.model;

import javax.persistence.*;

@Entity(name = "teens")
public class Teen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teen_id")
    private Integer teenId;

    @Column(name = "group_id")
    private Integer teenGroupId;

    @Column(name = "teen_name")
    private String teenName;

    @Column(name = "teen_age")
    private Integer teenAge;

    @Column(name = "teen_gender")
    private String teenGender;

    @Column(name = "teen_phone")
    private String teenPhone;

    @Column(name = "teen_email")
    private String teenEmail;

    public Integer getTeenId() {
        return teenId;
    }

    public void setTeenId(Integer teenId) {
        this.teenId = teenId;
    }

    public Integer getTeenGroupId() {
        return teenGroupId;
    }

    public void setTeenGroupId(Integer teenGroupId) {
        this.teenGroupId = teenGroupId;
    }

    public String getTeenName() {
        return teenName;
    }

    public void setTeenName(String teenName) {
        this.teenName = teenName;
    }

    public Integer getTeenAge() {
        return teenAge;
    }

    public void setTeenAge(Integer teenAge) {
        this.teenAge = teenAge;
    }

    public String getTeenGender() {
        return teenGender;
    }

    public void setTeenGender(String teenGender) {
        this.teenGender = teenGender;
    }

    public String getTeenPhone() {
        return teenPhone;
    }

    public void setTeenPhone(String teenPhone) {
        this.teenPhone = teenPhone;
    }

    public String getTeenEmail() {
        return teenEmail;
    }

    public void setTeenEmail(String teenEmail) {
        this.teenEmail = teenEmail;
    }
}
