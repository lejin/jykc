package org.jesusyouth.jykc.jykcadmin.repository;

import org.jesusyouth.jykc.jykcadmin.dto.GroupInfoDto;
import org.jesusyouth.jykc.jykcadmin.dto.MemberDto;
import org.jesusyouth.jykc.jykcadmin.model.GroupInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GroupInfoRepo extends CrudRepository<GroupInfo,Integer> {
    @Query(value = "select * from group_info INNER join committed_members on committed_members.id=group_info.group_leader WHERE group_info.group_zone=?1",nativeQuery = true)
    List<GroupInfoDto> findGroupInfoByZone(Integer zone);

    GroupInfo findByGroupLeaderEquals(Integer leaderId);

    GroupInfo findFirstByGidEquals(Integer groupId);

}
