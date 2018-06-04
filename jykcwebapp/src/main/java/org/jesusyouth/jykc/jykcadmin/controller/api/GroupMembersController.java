package org.jesusyouth.jykc.jykcadmin.controller.api;

import org.apache.commons.beanutils.BeanUtils;
import org.jesusyouth.jykc.jykcadmin.common.FamilyUtil;
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
import org.springframework.util.StringUtils;
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

    @Autowired
    private FamilyUtil familyUtil;

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
            groupValidations.validateFamilyCount(groupId,category);
        } catch (GroupMemberValidationException e) {
            logger.error("XXXXXXX   validation error"+e.getMessage());
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
            familyUtil.createFamily(groupId, userId, zoneId);
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
                                  @RequestParam(required = false) String userId,
                                  @RequestParam(required = false) String teenId) {

        GroupInfo groupInfo=null;

        if(null!=teenId && !"null".equals(teenId) && !StringUtils.isEmpty(teenId)){
            Integer teen=Integer.valueOf(StringUtils.trimAllWhitespace(teenId));
            teensRepo.deleteTeenByTeenIdEquals(teen);
            groupMembersRepo.deleteGroupMembersByTeenIdEquals(teen);
            groupInfo=groupFeeComponent.reduceGroupFee(groupId, "student");
            groupInfo.setMessage("success");
            return groupInfo;
        }
        Integer userIdInt=Integer.valueOf(StringUtils.trimAllWhitespace(userId));
        GroupMembers groupMembers = groupMembersRepo.findFirstByMemberEquals(userIdInt);
        if (null != groupMembers) {

            groupInfo = groupInfoRepo.findFirstByGidEquals(groupId);
            if(null!=groupInfo && groupInfo.getGroupLeader().equals(userIdInt)){
                groupInfo.setMessage("can't delete group leader");
                return groupInfo;
            }

            String category = groupMembers.getCategory();
            groupMembersRepo.deleteByMemberEquals(userIdInt,groupId );
            committedMembersRepo.updateIsGroupMember(0, userIdInt);

            groupInfo=groupFeeComponent.reduceGroupFee(groupInfo, category);

            if("family".equals(category)){
                FamilyInfo familyInfo=familyInfoRepo.getFamilyInfoByFamilyElderIdEquals(userIdInt);
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
                    membersWithTeensDto.setCategory("student");
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
