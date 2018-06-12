package org.jesusyouth.jykc.jykcadmin.repository;

import org.jesusyouth.jykc.jykcadmin.dto.GroupInfoDto;
import org.jesusyouth.jykc.jykcadmin.dto.HomeStatus;
import org.jesusyouth.jykc.jykcadmin.dto.MemberDto;
import org.jesusyouth.jykc.jykcadmin.dto.RegistrationStatus;
import org.jesusyouth.jykc.jykcadmin.model.GroupInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GroupInfoRepo extends CrudRepository<GroupInfo,Integer> {
    @Query(value = "select * from group_info INNER join committed_members on committed_members.id=group_info.group_leader WHERE group_info.group_zone=?1",nativeQuery = true)
    List<GroupInfoDto> findGroupInfoByZone(Integer zone);

    @Query(value = "select group_info.`group_id`,`group_info`.`gid`,`committed_members`.`name`,`committed_members`.`phone_number`,`group_info`.`status`,`group_info`.`group_fee` ,(select COUNT(*) from `group_members` where `group_members`.`group_id`=`group_info`.`gid`) as membercount from group_info INNER join committed_members on committed_members.id=group_info.group_leader inner join users on users.`member_id`=`group_info`.`group_leader`WHERE group_info.group_zone=11 and users.`approved`=1",nativeQuery = true)
    List<GroupInfoDto> findGroupInfoWithMembersCount(Integer zone);

    GroupInfo findByGroupLeaderEquals(Integer leaderId);

    GroupInfo findFirstByGidEquals(Integer groupId);

    @Modifying
    @Transactional
    @Query(value = "update group_info set status='submitted',group_is_editable=0 where gid=?1",nativeQuery = true)
    void updateGroupInfoById(Integer groupId);

    @Query(value = "select zone.name as 'zone',count(*) as 'count' from group_info inner join zone on group_info.group_zone=zone.id group by group_info.group_zone",nativeQuery = true)
    List<RegistrationStatus> getRegistrationStatus();

    @Query(value = "select count(*) AS 'groupCount' from group_info where group_info.group_zone=?1",nativeQuery = true)
    HomeStatus getTotalGroupsCount(Integer zone);

}
