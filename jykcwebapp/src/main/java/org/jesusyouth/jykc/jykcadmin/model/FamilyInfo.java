package org.jesusyouth.jykc.jykcadmin.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "family")
public class FamilyInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "family_uid")
    private Integer familyId;

    @Column(name = "family_head_id")
    private Integer familyElderId;

    @Column(name = "family_zone_id")
    private Integer familyZoneId;

    @Column(name = "family_group_id")
    private Integer familygroupId;

    public Integer getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Integer familyId) {
        this.familyId = familyId;
    }

    public Integer getFamilyElderId() {
        return familyElderId;
    }

    public void setFamilyElderId(Integer familyElderId) {
        this.familyElderId = familyElderId;
    }

    public Integer getFamilyZoneId() {
        return familyZoneId;
    }

    public void setFamilyZoneId(Integer familyZoneId) {
        this.familyZoneId = familyZoneId;
    }

    public Integer getFamilygroupId() {
        return familygroupId;
    }

    public void setFamilygroupId(Integer familygroupId) {
        this.familygroupId = familygroupId;
    }
}
