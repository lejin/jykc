package org.jesusyouth.jykc.jykcadmin.repository;

import org.jesusyouth.jykc.jykcadmin.dto.GroupMemberDTO;
import org.jesusyouth.jykc.jykcadmin.model.CommittedMember;
import org.jesusyouth.jykc.jykcadmin.model.GroupMembers;
import org.jesusyouth.jykc.jykcadmin.dto.MemberDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GroupMembersRepo extends CrudRepository<GroupMembers,Integer> {
    @Query(value = "select * from group_members INNER join committed_members on committed_members.id=group_members.member where group_members.group_id=?1",nativeQuery = true)
    List<MemberDto> findGroupMembersByGroupId(Integer groupid);

    @Modifying
    @Transactional
    @Query(value = "delete from group_members where group_id=?1 ",nativeQuery = true)
    void deleteByGroupIdEquals(Integer groupId);

    @Modifying
    @Transactional
    @Query(value = "delete from group_members where member=?1 AND group_id=?2",nativeQuery = true)
    void deleteByMemberEquals(Integer member,Integer groupId);

    @Query(value = "select * from group_members inner join committed_members on committed_members.id=group_members.member where group_members.group_id=?1",nativeQuery = true)
    List<GroupMemberDTO> getAllMembers(Integer groupid);

    Integer countAllByGroupIdEquals(Integer groupId);

    Integer countAllByGroupIdEqualsAndCategoryEquals(Integer groupId,String category);

    @Transactional
    void deleteGroupMembersByTeenIdEquals(Integer teenId);

    GroupMembers findFirstByMemberEquals(Integer member);

    List<GroupMembers> getAllByCategoryEquals(String category);

    List<GroupMembers> getAllByGroupIdEquals(Integer groupId);


    @Modifying
    @Transactional
    @Query(value = "update group_members set category=?1 where member=?2",nativeQuery = true)
    void updateMemberCategory(String category,Integer member);

    @Transactional
    void deleteGroupMembersByMemberEquals(Integer member);
}
