package org.jesusyouth.jykc.jykcadmin.repository;

import org.jesusyouth.jykc.jykcadmin.dto.HomeStatus;
import org.jesusyouth.jykc.jykcadmin.model.CommittedMember;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommittedMembersRepo extends CrudRepository<CommittedMember,Integer> {
    List<CommittedMember> findCommittedMemberByZoneIdAndGroupLeaderAndGroupMember(Integer zoneId,boolean groupLeader,boolean groupMember);

    CommittedMember findCommittedMemberByZoneIdAndEmailLikeOrPhoneLike(Integer zoneId,String email,String phoneNumber);

    CommittedMember findFirstCommittedMemberByEmailLikeOrPhoneLike(String email , String phone);

    @Modifying
    @Transactional
    @Query(value = "update committed_members set is_group_member=?1 where id=?2",nativeQuery = true)
    void updateIsGroupMember(Integer isGroupMember,Integer memberID);

    List<CommittedMember> findAllByZoneIdEquals(Integer zoneId);

    @Modifying
    @Transactional
    @Query(value = "update committed_members set is_group_leader=?1 where id=?2",nativeQuery = true)
    void updateIsGroupLeader(Integer isGroupLeader,Integer memberID);

    @Modifying
    @Transactional
    @Query(value = "update committed_members set is_group_leader=?1, age=?2, zone_id=?3 where id=?4",nativeQuery = true)
    void updateIsGroupLeaderAndAge(Integer isGroupLeader,Integer age,Integer zoneId,Integer memberID);

    @Modifying
    @Transactional
    @Query(value = "update committed_members set is_group_member=?1, age=?2, zone_id=?3 where id=?4",nativeQuery = true)
    void updateIsGroupMemberAndAge(Integer isGroupMember,Integer age,Integer zoneId,Integer memberID);

    @Modifying
    @Transactional
    @Query(value = "update `committed_members` set `email`=?1,`phone_number`=?2 where id=?3",nativeQuery = true)
    void updateMemberInfo(String mail,String phone,Integer memberID);

    CommittedMember findFirstByIdEquals(Integer id);

    Integer countCommittedMemberByZoneIdEquals(Integer zoneId);
}
