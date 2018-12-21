package org.jesusyouth.jykc.jykcadmin.controller.api;

import org.jesusyouth.jykc.jykcadmin.common.GroupMemberUtil;
import org.jesusyouth.jykc.jykcadmin.common.GroupMemberValidationException;
import org.jesusyouth.jykc.jykcadmin.common.GroupValidations;
import org.jesusyouth.jykc.jykcadmin.model.CommittedMember;
import org.jesusyouth.jykc.jykcadmin.model.GroupInfo;
import org.jesusyouth.jykc.jykcadmin.model.User;
import org.jesusyouth.jykc.jykcadmin.repository.CommittedMembersRepo;
import org.jesusyouth.jykc.jykcadmin.repository.GroupInfoRepo;
import org.jesusyouth.jykc.jykcadmin.repository.UsersRepo;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupInfoController {
    @Autowired
    private GroupInfoRepo groupInfoRepo;

    @Autowired
    private GroupValidations groupValidations;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private CommittedMembersRepo committedMembersRepo;

    @Autowired
    private GroupMemberUtil groupMemberUtil;

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ApiAccessLogger.class);

    @GetMapping("/api/group/info")
    public GroupInfo getGroupInfo(@RequestParam Integer groupId){
        return groupInfoRepo.findFirstByGidEquals(groupId);
    }

    @PostMapping("/api/group/finalsubmit")
    public GroupInfo finalSubmit(@RequestParam Integer groupId){
        GroupInfo groupInfo=groupInfoRepo.findFirstByGidEquals(groupId);
        if(null!=groupInfo){
            try {
                groupValidations.validateGroupCountForSubmission(groupId);
                groupInfoRepo.updateGroupInfoById(groupId);
                groupInfo.setStatus("submitted");
                groupInfo.setEditable(false);
                groupInfo.setMessage("success");
            } catch (GroupMemberValidationException e) {
                logger.error("XXXXXXX   validation error"+e.getMessage());
                //groupInfo=new GroupInfo();
                groupInfo.setMessage(e.getMessage());
                return groupInfo;
            }

        }else {
            groupInfo=new GroupInfo();
            groupInfo.setMessage("no group found");
        }
        return groupInfo;
    }

    @PostMapping("/api/replace_leader")
    public String replaceGroupLeader(@RequestParam Integer oldLeader, @RequestParam Integer newLeader) {
        GroupInfo groupInfo = groupInfoRepo.findByGroupLeaderEquals(oldLeader);
        CommittedMember oldCommittedMember = committedMembersRepo.findFirstByIdEquals(oldLeader);
        CommittedMember newCommittedMember = committedMembersRepo.findFirstByIdEquals(newLeader);
        User oldUser = usersRepo.findByUserIdEquals(oldLeader);
        User newUser = usersRepo.findByUserIdEquals(oldLeader);
        if (null == groupInfo || null == oldCommittedMember || null == newCommittedMember) {
            return "no group found";
        }
        Integer groupId = groupInfo.getGid();
//        update leader in group
        groupInfo.setGroupLeader(newLeader);
        groupInfoRepo.save(groupInfo);

//        degrade old leader
        committedMembersRepo.updateIsGroupLeader(0, oldCommittedMember.getId());
        committedMembersRepo.updateIsGroupMember(1, oldCommittedMember.getId());
        oldUser.setRole("member");
        usersRepo.save(oldUser);

//        promote new leader
        committedMembersRepo.updateIsGroupLeader(1, newLeader);
        if (null == newUser) {
            newUser = new User();
            newUser.setPhone(newCommittedMember.getPhone());
            newUser.setUserId(newCommittedMember.getId());
            newUser.setZone(newCommittedMember.getZoneId());
            newUser.setRole("group_leader");
            newUser.setEmail(newCommittedMember.getEmail());
            newUser.setName(newCommittedMember.getName());
            newUser.setVip(true);
            usersRepo.save(newUser);
        } else {
            newUser.setRole("group_leader");
            usersRepo.save(newUser);
        }

//        remove old leader from group
        groupMemberUtil.removeGroupMember(groupId, String.valueOf(oldLeader), null);

        return "success";
    }

}
