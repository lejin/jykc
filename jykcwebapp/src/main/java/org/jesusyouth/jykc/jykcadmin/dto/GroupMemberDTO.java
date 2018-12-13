package org.jesusyouth.jykc.jykcadmin.dto;

import java.io.Serializable;
import java.security.SecureRandom;

public interface GroupMemberDTO {


        Integer getId();


    String getMinistry();


    String getName();


   String getGender();


    String getDob();



    public String getEmail();


    public String getPhone_number();


    public String getAddress();

    public String getZone();


    public String getCommitment();

    public String getCommitment_date();


    public String getCommitment_place();

    public String getLast_commitment_place();


    public String getFellowship();


    public Integer getZone_id();

    public boolean getIs_group_leader();

    public boolean getIs_group_member();

    public Integer getAge();

    public String getCategory();

    public boolean getPaid();

    String getPayment_remark();

    Integer getUid();


}
