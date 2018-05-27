package org.jesusyouth.jykc.jykcadmin.controller.api;

import org.apache.commons.beanutils.BeanUtils;
import org.jesusyouth.jykc.jykcadmin.common.GroupFee;
import org.jesusyouth.jykc.jykcadmin.common.GroupMemberValidationException;
import org.jesusyouth.jykc.jykcadmin.common.GroupValidations;
import org.jesusyouth.jykc.jykcadmin.dto.GroupMemberDTO;
import org.jesusyouth.jykc.jykcadmin.dto.MembersWithTeensDto;
import org.jesusyouth.jykc.jykcadmin.model.*;
import org.jesusyouth.jykc.jykcadmin.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GroupMembersController {

    private static final Logger logger = LoggerFactory.getLogger(GroupMembersController.class);

    @Autowired
    private CommittedMembersRepo committedMembersRepo;


    @Autowired
    private GroupMembersRepo groupMembersRepo;

    @Autowired
    private FamilyInfoRepo familyInfoRepo;

    @Autowired
    private GroupValidations groupValidations;

    @Autowired
    private TeensRepo teensRepo;

    @Autowired
    private GroupInfoRepo groupInfoRepo;

    @Autowired
    private GroupFee groupFeeComponent;

    @Autowired
    private FamilyMemberRepo familyMemberRepo;


    @PostMapping("/api/group/addmember")
    public GroupMembers addmember(@RequestParam Integer groupId,
                                  @RequestParam Integer userId,
                                  @RequestParam boolean accomadation,
                                  @RequestParam String category,
                                  @RequestParam Integer age,
                                  @RequestParam(required = false) Integer zoneId) {

        try {
            //groupValidations.validateGroupMemberAge(category, age);
            groupValidations.validateGroupMembersCount(groupId);
            groupValidations.validateFamilyCount(groupId);
        } catch (GroupMemberValidationException e) {
            logger.error("user id = " + userId + e.getMessage());
            GroupMembers groupMembers = new GroupMembers();
            groupMembers.setMessage(e.getMessage());
            return groupMembers;
        }
        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setMember(userId);
        groupMembers.setGroupId(groupId);
        groupMembers.setAccomadation(accomadation);
        groupMembers.setCategory(category);
        groupMembersRepo.save(groupMembers);
        CommittedMember committedMember = committedMembersRepo.findFirstByIdEquals(userId);
        if ("family".equals(category)) {

            FamilyInfo familyInfo = new FamilyInfo();
            familyInfo.setFamilyZoneId(zoneId);
            familyInfo.setFamilygroupId(groupId);
            familyInfo.setFamilyElderId(userId);
            familyInfoRepo.save(familyInfo);
        }
        if (null != committedMember) {
            committedMember.setAge(age);
            committedMember.setGroupMember(true);
            committedMember.setZoneId(zoneId);
            committedMembersRepo.save(committedMember);
        }
        groupMembers.setGroupInfo(groupFeeComponent.updateGroupFee(groupId,category));
        return groupMembers;
    }

    @PostMapping("/api/group/removemember")
    public GroupInfo removemember(@RequestParam Integer groupId,
                                  @RequestParam Integer userId) {
        GroupMembers groupMembers = groupMembersRepo.findFirstByMemberEquals(userId);
        GroupInfo groupInfo=null;

        if(groupMembers.getTeenId()!=null){

            teensRepo.deleteTeenByTeenIdEquals(groupMembers.getTeenId());
            groupMembersRepo.deleteGroupMembersByTeenIdEquals(groupMembers.getTeenId());
            groupInfo=groupFeeComponent.reduceGroupFee(groupId, groupMembers.getCategory());
            groupInfo.setMessage("success");
            return groupInfo;
        }

        if (null != groupMembers) {
            String category = groupMembers.getCategory();
            groupMembersRepo.deleteByMemberEquals(userId,groupId );
            committedMembersRepo.updateIsGroupMember(0, userId);
            groupInfo=groupFeeComponent.reduceGroupFee(groupId, category);
            if("family".equals(category)){
                FamilyInfo familyInfo=familyInfoRepo.getFamilyInfoByFamilyElderIdEquals(userId);
                familyMemberRepo.deleteFamilymembersByFamilyInfoIdEquals(familyInfo.getFamilyId());
                familyInfoRepo.delete(familyInfo);
            }
            groupInfo.setMessage("success");
        }
//        remove family member
        return groupInfo;
    }

    @GetMapping("/api/group/members")
    public List<MembersWithTeensDto> getGroupMembers(@RequestParam Integer groupId) {
        List<GroupMemberDTO> groupMemberDTOS = groupMembersRepo.getAllMembers(groupId);
        List<Teen> teenList = teensRepo.findAllByTeenGroupIdEquals(groupId);
        List<MembersWithTeensDto> membersWithTeensDtos = new ArrayList<>();
        groupMemberDTOS.forEach(e -> {
            MembersWithTeensDto membersWithTeensDto = new MembersWithTeensDto();
            try {
                BeanUtils.copyProperties(membersWithTeensDto, e);
                membersWithTeensDtos.add(membersWithTeensDto);
            } catch (Exception ex) {
                logger.error(ex.getMessage());
            }
        });
        if (!CollectionUtils.isEmpty(teenList)) {
            teenList.forEach(e -> {
                MembersWithTeensDto membersWithTeensDto = new MembersWithTeensDto();
                try {
                    membersWithTeensDto.setAge(e.getTeenAge());
                    membersWithTeensDto.setEmail(e.getTeenEmail());
                    membersWithTeensDto.setGender(e.getTeenGender());
                    membersWithTeensDto.setPhone_number(e.getTeenPhone());
                    membersWithTeensDto.setName(e.getTeenName());
                    membersWithTeensDto.setTeenId(e.getTeenId());
                    membersWithTeensDtos.add(membersWithTeensDto);
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                }
            });
        }

        return membersWithTeensDtos;
    }


}
