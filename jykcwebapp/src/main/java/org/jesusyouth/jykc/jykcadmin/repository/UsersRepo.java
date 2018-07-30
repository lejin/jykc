package org.jesusyouth.jykc.jykcadmin.repository;

import org.jesusyouth.jykc.jykcadmin.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UsersRepo extends CrudRepository<User,Integer> {

     User findFirstByEmail(String email);

     @Modifying
     @Transactional
     @Query(value = "UPDATE users SET member_id=?1, zone = ?2, role= ?3 WHERE id=?3",nativeQuery = true)
     void updateMemberId(Integer memberId,Integer zone,String role, Integer id);

     User findFirstByEmailLike(String email);

     User findFirstByPhoneLike(String phone);

     User findFirstByEmailLikeOrPhoneLike(String email,String phone);

     List<User> findByZoneEqualsAndRoleContainsAndApprovedEquals(Integer zone,String role,Boolean isapproved);

     User findByAuthId(Integer id);

     @Modifying
     @Transactional
     @Query(value = "update users set name=?2 where `member_id`=?1",nativeQuery = true)
     void updateName(Integer member,String name);
}
