package org.jesusyouth.jykc.jykcadmin.common;

import org.jesusyouth.jykc.jykcadmin.model.FamilyInfo;
import org.jesusyouth.jykc.jykcadmin.repository.FamilyInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FamilyUtil {

    @Autowired
    private FamilyInfoRepo familyInfoRepo;


    public void createFamily(Integer groupId, Integer userId, Integer zoneId) {

            FamilyInfo familyInfo = new FamilyInfo();
            familyInfo.setFamilyZoneId(zoneId);
            familyInfo.setFamilygroupId(groupId);
            familyInfo.setFamilyElderId(userId);
            familyInfoRepo.save(familyInfo);
    }
}
