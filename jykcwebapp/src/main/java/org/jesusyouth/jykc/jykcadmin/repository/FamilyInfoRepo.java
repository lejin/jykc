package org.jesusyouth.jykc.jykcadmin.repository;

import org.jesusyouth.jykc.jykcadmin.model.FamilyInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FamilyInfoRepo extends CrudRepository<FamilyInfo,Integer> {

    FamilyInfo getFamilyInfoByFamilyElderIdEquals(Integer id);

}
