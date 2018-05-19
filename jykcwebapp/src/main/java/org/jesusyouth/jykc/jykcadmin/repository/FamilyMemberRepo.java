package org.jesusyouth.jykc.jykcadmin.repository;

import org.jesusyouth.jykc.jykcadmin.dto.FamilyMembers;
import org.jesusyouth.jykc.jykcadmin.model.Familymember;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FamilyMemberRepo extends CrudRepository<Familymember,Integer> {

    @Query(value = "select * from `family_members`inner join `family` on `family`.`family_uid`=`family_members`.`family_info_id` where `family`.`family_head_id`=?1",nativeQuery = true)
    List<FamilyMembers> getByFamily(Integer familyInfoId);

}
