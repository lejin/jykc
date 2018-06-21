package org.jesusyouth.jykc.jykcadmin.common;

import org.jesusyouth.jykc.jykcadmin.model.FamilyInfo;
import org.jesusyouth.jykc.jykcadmin.repository.FamilyInfoRepo;
import org.jesusyouth.jykc.jykcadmin.repository.FamilyMemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FamilyUtil {

    @Autowired
    private FamilyInfoRepo familyInfoRepo;

    @Autowired
    private FamilyMemberRepo familyMemberRepo;


    public void createFamily(Integer groupId, Integer userId, Integer zoneId) {

            FamilyInfo familyInfo = new FamilyInfo();
            familyInfo.setFamilyZoneId(zoneId);
            familyInfo.setFamilygroupId(groupId);
            familyInfo.setFamilyElderId(userId);
            familyInfoRepo.save(familyInfo);
    }

    public void deleteFamily(Integer userIdInt) {
        FamilyInfo familyInfo=familyInfoRepo.getFamilyInfoByFamilyElderIdEquals(userIdInt);
        familyMemberRepo.deleteFamilymembersByFamilyInfoIdEquals(familyInfo.getFamilyId());
        familyInfoRepo.delete(familyInfo);
    }
}
