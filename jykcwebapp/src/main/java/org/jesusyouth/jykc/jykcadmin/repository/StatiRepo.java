package org.jesusyouth.jykc.jykcadmin.repository;

import org.jesusyouth.jykc.jykcadmin.dto.GroupMemberDTO;
import org.jesusyouth.jykc.jykcadmin.dto.StatiticsDto;
import org.jesusyouth.jykc.jykcadmin.dto.StatiticsInterface;
import org.jesusyouth.jykc.jykcadmin.model.Teen;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface StatiRepo extends CrudRepository<Teen,Integer> {

    @Query(value = "select teens.`teen_name` as name,teens.`teen_age` as age,teens.`teen_gender` as gender,teens.`teen_phone` as phone,`committed_members`.`name` as groupLeaderName,`committed_members`.`phone_number` as groupLeaderPhone,`group_info`.`group_id` as groupCode from `teens` inner join group_info on group_info.`gid`=teens.`group_id` inner join `committed_members` on `committed_members`.id=`group_info`.`group_leader`  where `group_info`.`group_zone`=?1 order by groupCode",nativeQuery = true)
    List<StatiticsInterface> getTeensByZone(Integer zoneId);

    @Query(value = "select teens.`teen_name` as name,teens.`teen_age` as age,teens.`teen_gender` as gender,teens.`teen_phone` as phone,`committed_members`.`name` as groupLeaderName,`committed_members`.`phone_number` as groupLeaderPhone,`group_info`.`group_id` as groupCode,`group_info`.`gid` from `teens` inner join group_info on group_info.`gid`=teens.`group_id` inner join `committed_members` on `committed_members`.id=`group_info`.`group_leader` order by groupCode",nativeQuery = true)
    List<StatiticsInterface> getTeensByZone();

}
