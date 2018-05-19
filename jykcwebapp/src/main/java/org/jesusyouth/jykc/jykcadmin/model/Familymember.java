package org.jesusyouth.jykc.jykcadmin.model;

import javax.persistence.*;

@Entity(name = "family_members")
public class Familymember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "family_member_id")
    private Integer familyMemberId;

    @Column(name = "family_info_id")
    private Integer familyInfoId;

    @Column(name = "family_member_name")
    private String familyMemberName;

    @Column(name = "family_member_age")
    private Integer FamilymemberAge;

    @Column(name = "family_member_gender")
    private String FamilyMemberGender;

    @Column(name = "family_member_relation")
    private String FamilyMemberRelation;

    public Integer getFamilyMemberId() {
        return familyMemberId;
    }

    public void setFamilyMemberId(Integer familyMemberId) {
        this.familyMemberId = familyMemberId;
    }

    public Integer getFamilyInfoId() {
        return familyInfoId;
    }

    public void setFamilyInfoId(Integer familyInfoId) {
        this.familyInfoId = familyInfoId;
    }

    public String getFamilyMemberName() {
        return familyMemberName;
    }

    public void setFamilyMemberName(String familyMemberName) {
        this.familyMemberName = familyMemberName;
    }

    public Integer getFamilymemberAge() {
        return FamilymemberAge;
    }

    public void setFamilymemberAge(Integer familymemberAge) {
        FamilymemberAge = familymemberAge;
    }

    public String getFamilyMemberGender() {
        return FamilyMemberGender;
    }

    public void setFamilyMemberGender(String familyMemberGender) {
        FamilyMemberGender = familyMemberGender;
    }

    public String getFamilyMemberRelation() {
        return FamilyMemberRelation;
    }

    public void setFamilyMemberRelation(String familyMemberRelation) {
        FamilyMemberRelation = familyMemberRelation;
    }
}
