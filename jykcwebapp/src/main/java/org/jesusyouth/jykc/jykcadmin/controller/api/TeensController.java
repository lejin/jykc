package org.jesusyouth.jykc.jykcadmin.controller.api;

import org.jesusyouth.jykc.jykcadmin.common.GroupMemberValidationException;
import org.jesusyouth.jykc.jykcadmin.common.GroupValidations;
import org.jesusyouth.jykc.jykcadmin.model.GroupMembers;
import org.jesusyouth.jykc.jykcadmin.model.Teen;
import org.jesusyouth.jykc.jykcadmin.repository.CommittedMembersRepo;
import org.jesusyouth.jykc.jykcadmin.repository.FamilyInfoRepo;
import org.jesusyouth.jykc.jykcadmin.repository.GroupMembersRepo;
import org.jesusyouth.jykc.jykcadmin.repository.TeensRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeensController {
    private static final Logger logger = LoggerFactory.getLogger(TeensController.class);
    public static final String TEENS = "teens";

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

    @PostMapping("/api/teen/add")
    public GroupMembers addTeen(@RequestParam Integer groupId,
                                @RequestParam boolean accomadation,
                                @RequestParam Integer age,
                                @RequestParam String name,
                                @RequestParam String gender,
                                @RequestParam String phone,
                                @RequestParam(required = false) String email) {

        try {
            groupValidations.validateGroupMemberAge(TEENS, age);
            groupValidations.validateGroupMembersCount(groupId);
        } catch (GroupMemberValidationException e) {
            logger.error("group id = " + groupId + e.getMessage());
            GroupMembers groupMembers = new GroupMembers();
            groupMembers.setMessage(e.getMessage());
            return groupMembers;
        }

        //add fee

        Teen teen = new Teen();
        teen.setTeenGroupId(groupId);
        teen.setTeenName(name);
        teen.setTeenAge(age);
        teen.setTeenGender(gender);
        teen.setTeenPhone(phone);
        teen.setTeenEmail(email);
        teensRepo.save(teen);

        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setGroupId(groupId);
        groupMembers.setAccomadation(accomadation);
        groupMembers.setCategory(TEENS);
        groupMembers.setTeenId(teen.getTeenId());
        groupMembersRepo.save(groupMembers);
        return groupMembers;
    }

    @PostMapping("/api/teen/remove")
    public String removemember(@RequestParam Integer teenId) {
        groupMembersRepo.deleteGroupMembersByTeenIdEquals(teenId);
        teensRepo.deleteTeenByTeenIdEquals(teenId);
        return "success";
    }

}
