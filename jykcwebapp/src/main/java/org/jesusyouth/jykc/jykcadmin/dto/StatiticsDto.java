package org.jesusyouth.jykc.jykcadmin.dto;

public class StatiticsDto {
    private String name;
    private Integer age;
    private String gender;
    private String phone;
    private String groupLeaderName;
    private String groupLeaderPhone;
    private String groupCode;
    private boolean highLight;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGroupLeaderName() {
        return groupLeaderName;
    }

    public void setGroupLeaderName(String groupLeaderName) {
        this.groupLeaderName = groupLeaderName;
    }

    public String getGroupLeaderPhone() {
        return groupLeaderPhone;
    }

    public void setGroupLeaderPhone(String groupLeaderPhone) {
        this.groupLeaderPhone = groupLeaderPhone;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public boolean isHighLight() {
        return highLight;
    }

    public void setHighLight(boolean highLight) {
        this.highLight = highLight;
    }
}
