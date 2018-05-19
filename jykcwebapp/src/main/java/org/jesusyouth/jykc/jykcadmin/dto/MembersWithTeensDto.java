package org.jesusyouth.jykc.jykcadmin.dto;

import java.io.Serializable;

public class MembersWithTeensDto implements Serializable {
    private Integer id;

    private String Ministry;

    private  String Name;

    private  String dob;

    private String gender;

    private String email;

    private String phone_number;

    private String address;

    private String zone;

    private String commitment;

    private String commitment_date;

    private String commitment_place;

    private String last_commitment_place;

    private String fellowship;

    private Integer zone_id;

    private boolean is_group_leader;

    private boolean is_group_member;

    private Integer age;

    private String category;

    private Integer teenId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMinistry() {
        return Ministry;
    }

    public void setMinistry(String ministry) {
        Ministry = ministry;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getCommitment() {
        return commitment;
    }

    public void setCommitment(String commitment) {
        this.commitment = commitment;
    }

    public String getCommitment_date() {
        return commitment_date;
    }

    public void setCommitment_date(String commitment_date) {
        this.commitment_date = commitment_date;
    }

    public String getCommitment_place() {
        return commitment_place;
    }

    public void setCommitment_place(String commitment_place) {
        this.commitment_place = commitment_place;
    }

    public String getLast_commitment_place() {
        return last_commitment_place;
    }

    public void setLast_commitment_place(String last_commitment_place) {
        this.last_commitment_place = last_commitment_place;
    }

    public String getFellowship() {
        return fellowship;
    }

    public void setFellowship(String fellowship) {
        this.fellowship = fellowship;
    }

    public Integer getZone_id() {
        return zone_id;
    }

    public void setZone_id(Integer zone_id) {
        this.zone_id = zone_id;
    }

    public boolean isIs_group_leader() {
        return is_group_leader;
    }

    public void setIs_group_leader(boolean is_group_leader) {
        this.is_group_leader = is_group_leader;
    }

    public boolean isIs_group_member() {
        return is_group_member;
    }

    public void setIs_group_member(boolean is_group_member) {
        this.is_group_member = is_group_member;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getTeenId() {
        return teenId;
    }

    public void setTeenId(Integer teenId) {
        this.teenId = teenId;
    }
}
