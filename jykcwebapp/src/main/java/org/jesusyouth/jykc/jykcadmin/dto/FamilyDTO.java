package org.jesusyouth.jykc.jykcadmin.dto;

import java.util.List;

public class FamilyDTO {

    private Integer familyInfoId;

    private List<FamilyMembers> familyMembers;

    public Integer getFamilyInfoId() {
        return familyInfoId;
    }

    public void setFamilyInfoId(Integer familyInfoId) {
        this.familyInfoId = familyInfoId;
    }

    public List<FamilyMembers> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(List<FamilyMembers> familyMembers) {
        this.familyMembers = familyMembers;
    }
}
