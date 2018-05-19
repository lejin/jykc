package org.jesusyouth.jykc.jykcadmin.model;

import java.io.Serializable;

public class AuthUser implements Serializable {
    private Integer authid;
    private Integer userid;
    private String role;
    private Integer zone;
    private boolean approved;
    private Integer groupId;
    private GroupInfo groupInfo;

    public AuthUser(Integer authid, Integer userid ,String role,Integer groupId) {
        this.authid = authid;
        this.userid = userid;
        this.role=role;
        this.groupId=groupId;
    }

    public AuthUser() {
    }

    public Integer getAuthid() {
        return authid;
    }

    public void setAuthid(Integer authid) {
        this.authid = authid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getZone() {
        return zone;
    }

    public void setZone(Integer zone) {
        this.zone = zone;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public GroupInfo getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(GroupInfo groupInfo) {
        this.groupInfo = groupInfo;
    }
}
