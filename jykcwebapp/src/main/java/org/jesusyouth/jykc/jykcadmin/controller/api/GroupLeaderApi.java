package org.jesusyouth.jykc.jykcadmin.controller.api;

import org.jesusyouth.jykc.jykcadmin.Constants.ZoneNames;

import org.jesusyouth.jykc.jykcadmin.common.FamilyUtil;
import org.jesusyouth.jykc.jykcadmin.common.GroupFee;
import org.jesusyouth.jykc.jykcadmin.common.GroupValidations;
import org.jesusyouth.jykc.jykcadmin.model.*;
import org.jesusyouth.jykc.jykcadmin.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GroupLeaderApi {

    private static final Logger logger = LoggerFactory.getLogger(GroupLeaderApi.class);

    @Autowired
    private CommittedMembersRepo committedMembersRepo;

    @Autowired
    private GroupInfoRepo groupInfoRepo;

    @Autowired
    private GroupMembersRepo groupMembersRepo;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private FamilyInfoRepo familyInfoRepo;

    @Autowired
    private GroupValidations  groupValidations;

    @Autowired
    private GroupFee groupFee;

    @Autowired
    private FamilyUtil familyUtil;

    @PostMapping("/api/groupleader")
    public AuthUser verifyGroupLeader(@RequestParam String phone,
                                      @RequestParam Integer age ,
                                      @RequestParam Integer zone,
                                      @RequestParam String category,
                                      @RequestParam boolean accomadation,
                                      @RequestParam Integer authId,
                                      @RequestParam Integer userId){

        User user=usersRepo.findByAuthId(authId);
        if(null == user){
            return new AuthUser(-1,-1,"user",-1);

        }

        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setGroupLeader(userId);
        groupInfo.setGroupZone(zone);
        groupInfo.setStatus("pending");
        groupInfo.setEditable(true);
        groupInfo.setGroupFee(groupFee.calculateFee(category));
        groupInfoRepo.save(groupInfo);


        Integer groupId = groupInfo.getGid();
        String groupCode = String.format("%04d", groupId);
        groupCode = ZoneNames.ZONE_NAME_MAP.get(zone).concat(groupCode);
        groupInfo.setGroupID(groupCode);
        groupInfoRepo.save(groupInfo);

        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setMember(userId);
        groupMembers.setGroupId(groupId);
        groupMembers.setAccomadation(accomadation);
        groupMembers.setCategory(category);
        groupMembersRepo.save(groupMembers);

        committedMembersRepo.updateIsGroupLeaderAndAge(1,age,zone,userId);
        String role=user.getRole();
        if(!StringUtils.isEmpty(role) && !role.contains("group_leader")){
            if("member".equals(role)){
                user.setRole("group_leader");
            }
        }else if(!StringUtils.isEmpty(role) && role.contains("group_leader")){
           // do nothong
        }else if(StringUtils.isEmpty(role)){
            user.setRole("group_leader");
        }else if(!StringUtils.isEmpty(role) && role.contains("zonal_admin")){
            role.concat("|group_leader");
            user.setApproved(true);
            user.setRole(role);
        }
        if ("family".equals(category)) {
            familyUtil.createFamily(groupId, userId, zone);
        }
        user.setPhone(phone);
        user.setZone(zone);
        usersRepo.save(user);
        AuthUser authUser=new AuthUser();
        authUser.setAuthid(user.getAuthId());
        authUser.setUserid(userId);
        authUser.setRole(user.getRole());
        authUser.setGroupId(groupId);
        authUser.setZone(zone);
        authUser.setGroupInfo(groupInfo);
        return authUser;
    }

    @PostMapping("/api/groupleader/requests/{zone}")
    public List<User> getLeaderRequests(@PathVariable Integer zone){
        return usersRepo.findByZoneEqualsAndRoleContainsAndApprovedEquals(zone, "group_leader", false);
    }

    @PostMapping("/api/groupleader/requests/accept")
    public ApprovalResponse acceptRequest(@RequestParam Integer authId){
        ApprovalResponse approvalResponse=new ApprovalResponse();
        try {
            User user = usersRepo.findByAuthId(authId);
            user.setApproved(true);
            usersRepo.save(user);
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.error("group leader accept failed auth id "+authId);
            approvalResponse.setMessage("failed");
            return approvalResponse;
        }
        approvalResponse.setMessage("success");
        return approvalResponse;
    }

    @PostMapping("/api/groupleader/requests/reject")
    public ApprovalResponse rejectRequest(@RequestParam Integer authId){
        ApprovalResponse approvalResponse=new ApprovalResponse();
        try {
            User user = usersRepo.findByAuthId(authId);
            committedMembersRepo.updateIsGroupLeader(0, user.getUserId());
            GroupInfo groupInfo=groupInfoRepo.findByGroupLeaderEquals(user.getUserId());
            groupMembersRepo.deleteByGroupIdEquals(groupInfo.getGid());
            groupInfoRepo.delete(groupInfo);
            user.setApproved(false);
            if("group_leader".equals(user.getRole())){
                user.setRole("member");
            }
            usersRepo.save(user);
        }catch (Exception e){
            logger.error("XXXXXXX   validation error"+e.getMessage());
            approvalResponse.setMessage("failed");
            return approvalResponse;
        }
        approvalResponse.setMessage("success");
        return approvalResponse;
    }

}
