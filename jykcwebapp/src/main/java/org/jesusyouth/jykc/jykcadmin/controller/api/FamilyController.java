package org.jesusyouth.jykc.jykcadmin.controller.api;

import org.jesusyouth.jykc.jykcadmin.common.GroupValidations;
import org.jesusyouth.jykc.jykcadmin.dto.FamilyDTO;
import org.jesusyouth.jykc.jykcadmin.dto.FamilyMembers;
import org.jesusyouth.jykc.jykcadmin.model.ApprovalResponse;
import org.jesusyouth.jykc.jykcadmin.model.FamilyInfo;
import org.jesusyouth.jykc.jykcadmin.model.Familymember;
import org.jesusyouth.jykc.jykcadmin.repository.FamilyInfoRepo;
import org.jesusyouth.jykc.jykcadmin.repository.FamilyMemberRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FamilyController {

    @Autowired
    private FamilyInfoRepo familyInfoRepo;

    @Autowired
    private FamilyMemberRepo familyMemberRepo;

    @Autowired
    private GroupValidations groupValidations;

    private static final Logger logger = LoggerFactory.getLogger(FamilyController.class);

    @PostMapping("/api/family/create")
    public FamilyInfo createFamily(@RequestParam Integer userid,
                                   @RequestParam Integer zoneId,
                                   @RequestParam Integer groupId){
        FamilyInfo familyInfo=new FamilyInfo();
        familyInfo.setFamilyElderId(userid);
        familyInfo.setFamilygroupId(groupId);
        familyInfo.setFamilyZoneId(zoneId);
        familyInfoRepo.save(familyInfo);
        return familyInfo;
    }

    @PostMapping("/api/family/addmember")
    public Familymember addMember(@RequestParam Integer familyInfoId,
                                  @RequestParam String familymemberName,
                                  @RequestParam Integer familyMemberAge,
                                  @RequestParam String familyMemberGender,
                                  @RequestParam String familyMemberRelation){
        Familymember familymember=new Familymember();
        if(familyMemberRelation!=null && "kid".equals(familyMemberRelation)){
            try {
                groupValidations.validateGroupMemberAge("kid", familyMemberAge);
            }catch (Exception e){
                logger.error("XXXXXXX   validation error"+e.getMessage());
                familymember.setMessage(e.getMessage());
                return familymember;
            }
        }
        familymember.setFamilyInfoId(familyInfoId);
        familymember.setFamilyMemberName(familymemberName);
        familymember.setFamilymemberAge(familyMemberAge);
        familymember.setFamilyMemberGender(familyMemberGender);
        familymember.setFamilyMemberRelation(familyMemberRelation);
        familyMemberRepo.save(familymember);
        familymember.setMessage("success");
        return familymember;
    }

    @GetMapping("/api/family/members")
    public FamilyDTO getFamilyMembers(@RequestParam Integer userid){

        FamilyInfo familyInfo=familyInfoRepo.getFamilyInfoByFamilyElderIdEquals(userid);
        List<FamilyMembers> familyMembers=familyMemberRepo.getByFamily(userid);
        FamilyDTO familyDTO=new FamilyDTO();
        if(null!=familyInfo){
            familyDTO.setFamilyInfoId(familyInfo.getFamilyId());
        }
        familyDTO.setFamilyMembers(familyMembers);
        return familyDTO;
    }

    @PostMapping("/api/family/removemember")
    public ApprovalResponse removeMember(@RequestParam Integer familyMemberId){
        try {
            Familymember familymember=familyMemberRepo.findById(familyMemberId).get();
            familyMemberRepo.delete(familymember);
            ApprovalResponse approvalResponse=new ApprovalResponse();
            approvalResponse.setMessage("success");
            return approvalResponse;
        }catch (Exception e){
            logger.error("error "+e.getMessage());
        }
        ApprovalResponse approvalResponse=new ApprovalResponse();
        approvalResponse.setMessage("error");
        return approvalResponse;
    }

}
