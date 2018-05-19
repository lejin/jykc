package org.jesusyouth.jykc.jykcadmin.dto;

public interface MemberDto {
    Integer getUid();
    String getName();
    String getEmail();
    String getPhone_number();
    String getGender();
    Integer getId();
    boolean getIs_group_leader();
    boolean getIs_group_member();
}
