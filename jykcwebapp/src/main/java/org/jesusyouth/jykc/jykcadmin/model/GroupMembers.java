package org.jesusyouth.jykc.jykcadmin.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "group_members")
public class GroupMembers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;

    private Integer member;


    @Column(name = "group_id")
    private Integer groupId;

    private boolean accomadation;

    private String category;

    @Transient
    private String message;

    @Column(name = "teen_id")
    private Integer teenId;

    @Transient
    private GroupInfo groupInfo;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }


    public Integer getMember() {
        return member;
    }

    public void setMember(Integer member) {
        this.member = member;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public boolean isAccomadation() {
        return accomadation;
    }

    public void setAccomadation(boolean accomadation) {
        this.accomadation = accomadation;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTeenId() {
        return teenId;
    }

    public void setTeenId(Integer teenId) {
        this.teenId = teenId;
    }

    public GroupInfo getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(GroupInfo groupInfo) {
        this.groupInfo = groupInfo;
    }
}
