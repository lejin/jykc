package org.jesusyouth.jykc.jykcadmin.model;

import javax.persistence.*;

@Entity(name = "committed_members")
public class CommittedMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String ministry;
    private String name;
    private String gender;
    private String dob;
    private String email;
    @Column(name = "phone_number")
    private String phone;
    private String address;
    private String zone;
    private String commitment;
    private String commitment_date;
    private String commitment_place;
    private String last_commitment_place;
    private String fellowship;
    @Column(name = "zone_id")
    private Integer zoneId;
    @Column(name = "is_group_leader")
    private boolean groupLeader;
    @Column(name = "is_group_member")
    private boolean groupMember;
    private Integer age;

    public CommittedMember() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMinistry() {
        return ministry;
    }

    public void setMinistry(String ministry) {
        this.ministry = ministry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public boolean isGroupLeader() {
        return groupLeader;
    }

    public void setGroupLeader(boolean groupLeader) {
        this.groupLeader = groupLeader;
    }

    public boolean isGroupMember() {
        return groupMember;
    }

    public void setGroupMember(boolean groupMember) {
        this.groupMember = groupMember;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
