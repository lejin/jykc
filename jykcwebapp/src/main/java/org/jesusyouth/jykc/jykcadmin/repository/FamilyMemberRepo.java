package org.jesusyouth.jykc.jykcadmin.repository;

import org.jesusyouth.jykc.jykcadmin.dto.FamilyMembers;
import org.jesusyouth.jykc.jykcadmin.dto.FamilyStatiInterface;
import org.jesusyouth.jykc.jykcadmin.model.Familymember;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FamilyMemberRepo extends CrudRepository<Familymember,Integer> {

    @Query(value = "select * from `family_members`inner join `family` on `family`.`family_uid`=`family_members`.`family_info_id` where `family`.`family_head_id`=?1",nativeQuery = true)
    List<FamilyMembers> getByFamily(Integer familyInfoId);

    @Transactional
    void deleteFamilymembersByFamilyInfoIdEquals(Integer familyInfoId);

    @Query(value = "select `family_members`.`family_member_name` as name,`family_members`.`family_member_age` as age,`family_members`.`family_member_relation` as relation,`family_members`.`family_member_gender` as gender,`family`.`family_head_id` as head,`group_info`.`group_id`as groupCode,`committed_members`.`name` as groupLeader,`committed_members`.`phone_number` as groupLeaderPhone,`family_members`.`family_info_id` as info from `family_members` inner join family on `family`.`family_uid`=`family_members`.`family_info_id` inner join `group_info` on `group_info`.`gid`=`family`.`family_group_id` inner join `committed_members` on `committed_members`.`id`=`group_info`.`group_leader` where `group_info`.group_zone=?1 order by `group_info`.`group_id`",nativeQuery = true)
    List<FamilyStatiInterface> getallbyzone(Integer zone);

    @Query(value = "select `family_members`.`family_member_name` as name,`family_members`.`family_member_age` as age,`family_members`.`family_member_relation` as relation,`family_members`.`family_member_gender` as gender,`family`.`family_head_id` as head,`group_info`.`group_id`as groupCode,`committed_members`.`name` as groupLeader,`committed_members`.`phone_number` as groupLeaderPhone,`family_members`.`family_info_id` as info,zone.`name` as zone from `family_members` inner join family on `family`.`family_uid`=`family_members`.`family_info_id` inner join `group_info` on `group_info`.`gid`=`family`.`family_group_id` inner join `committed_members` on `committed_members`.`id`=`group_info`.`group_leader` inner join zone on zone.`id`=`group_info`.`group_zone` order by zone.name,`group_info`.`group_id`",nativeQuery = true)
    List<FamilyStatiInterface> getall();

}
