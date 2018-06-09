package org.jesusyouth.jykc.jykcadmin.repository;

import org.jesusyouth.jykc.jykcadmin.model.FamilyInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface FamilyInfoRepo extends CrudRepository<FamilyInfo,Integer> {

    FamilyInfo getFamilyInfoByFamilyElderIdEquals(Integer id);

    @Transactional
    void deleteFamilyInfoByFamilyElderIdEquals(Integer id);

    Integer countByFamilyZoneIdEquals(Integer zoneId);

}
