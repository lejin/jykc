package org.jesusyouth.jykc.jykcadmin.controller.api;

import org.apache.commons.beanutils.BeanUtils;
import org.jesusyouth.jykc.jykcadmin.common.*;
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
    private GroupValidations groupValidations;


    @Autowired
    private FamilyUtil familyUtil;

    @Autowired
    private GroupMemberUtil groupMemberUtil;

    @Autowired
    private GroupFee groupFeeComponent;

    @PostMapping("/api/group/addmember")
    public GroupMembers addmember(@RequestParam Integer groupId,
                                  @RequestParam Integer userId,
                                  @RequestParam boolean accomadation,
                                  @RequestParam String category,
                                  @RequestParam Integer age,
                                  @RequestParam(required = false) Integer zoneId) {

        try {
            //groupValidations.validateGroupMemberAge(category, age);
            //groupValidations.validateGroupMembersCount(groupId);
            //groupValidations.validateFamilyCount(groupId,category);
            groupValidations.validMember(userId);
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

//        return groupMemberUtil.removeGroupMember(groupId, userId, teenId);
        return null;
    }



    @GetMapping("/api/group/members")
    public List<MembersWithTeensDto> getGroupMembers(@RequestParam Integer groupId) {
        return groupMemberUtil.getMembersWithTeensDtos(groupId);
    }



}
