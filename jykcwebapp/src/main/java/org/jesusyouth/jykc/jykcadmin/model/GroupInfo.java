package org.jesusyouth.jykc.jykcadmin.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "group_info")
public class GroupInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gid;

    @Column(name = "group_leader")
    private Integer groupLeader;

    @Column(name = "group_zone")
    private Integer groupZone;

    private String status;

    @Column(name = "group_id")
    private String groupID;

    @Column(name = "group_fee")
    private Integer groupFee;

    @Column(name = "group_is_editable")
    private boolean editable;

    @Transient
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getGroupLeader() {
        return groupLeader;
    }

    public void setGroupLeader(Integer groupLeader) {
        this.groupLeader = groupLeader;
    }

    public Integer getGroupZone() {
        return groupZone;
    }

    public void setGroupZone(Integer groupZone) {
        this.groupZone = groupZone;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Integer getGroupFee() {
        return groupFee;
    }

    public void setGroupFee(Integer groupFee) {
        this.groupFee = groupFee;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
