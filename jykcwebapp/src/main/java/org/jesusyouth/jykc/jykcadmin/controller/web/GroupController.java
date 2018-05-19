package org.jesusyouth.jykc.jykcadmin.controller.web;

import org.jesusyouth.jykc.jykcadmin.Constants.ZoneNames;
import org.jesusyouth.jykc.jykcadmin.model.GroupInfo;
import org.jesusyouth.jykc.jykcadmin.model.GroupMembers;
import org.jesusyouth.jykc.jykcadmin.repository.CommittedMembersRepo;
import org.jesusyouth.jykc.jykcadmin.repository.GroupInfoRepo;
import org.jesusyouth.jykc.jykcadmin.repository.GroupMembersRepo;
import org.jesusyouth.jykc.jykcadmin.repository.ZoneRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpSession;

@Controller
public class GroupController {

    @Autowired
    private GroupInfoRepo groupInfoRepo;

    @Autowired
    private GroupMembersRepo groupMembersRepo;

    @Autowired
    private CommittedMembersRepo committedMembersRepo;

    @GetMapping("/zonaladmin/group_info")
    public String getGroupInfo(Model model, HttpSession httpSession) {
        Integer zone = (Integer) httpSession.getAttribute("zone");
        model.addAttribute("group_info", groupInfoRepo.findGroupInfoByZone(zone));
        return "group_info";

    }

    @GetMapping("/zonaladmin/group_signup")
    public String getGroupSignup(Model model, HttpSession httpSession) {
        Integer zone = (Integer) httpSession.getAttribute("zone");
        model.addAttribute("committed_members", committedMembersRepo.findCommittedMemberByZoneIdAndGroupLeaderAndGroupMember(zone, false, false));
        return "group_signup";

    }

    @PostMapping("/zonaladmin/group_signup/group_leader")
    public String addGroupLeader(@RequestParam Integer leaderID, HttpSession httpSession,@RequestParam Integer age,@RequestParam String category,@RequestParam boolean accomadation) {

        Integer zone = (Integer) httpSession.getAttribute("zone");

        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setGroupLeader(leaderID);
        groupInfo.setGroupZone(zone);
        groupInfo.setStatus("pending");
        groupInfoRepo.save(groupInfo);


        Integer groupId = groupInfo.getGid();
        String groupCode = String.format("%04d", groupId);
        groupCode = ZoneNames.ZONE_NAME_MAP.get(zone).concat(groupCode);
        groupInfo.setGroupID(groupCode);
        groupInfoRepo.save(groupInfo);

        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setMember(leaderID);
        groupMembers.setGroupId(groupId);
        groupMembers.setAccomadation(accomadation);
        groupMembers.setCategory(category);
        groupMembersRepo.save(groupMembers);

        committedMembersRepo.updateIsGroupLeaderAndAge(1,age,zone,leaderID);

        return "redirect:/zonaladmin/group_members/".concat(String.valueOf(groupId));
    }

    @GetMapping("zonaladmin/group_members/{groupID}")
    public String getDetailsOfAGroup(Model model, @PathVariable Integer groupID, HttpSession httpSession) {
        Integer zone = (Integer) httpSession.getAttribute("zone");
        model.addAttribute("committed_members", committedMembersRepo.findCommittedMemberByZoneIdAndGroupLeaderAndGroupMember(zone, false, false));
        model.addAttribute("group_members", groupMembersRepo.findGroupMembersByGroupId(groupID));
        model.addAttribute("group_id", groupID);
        return "group_members";
    }

    @PostMapping("/zonaladmin/group_members")
    public String addGroupMember(@RequestParam Integer memberID,@RequestParam Integer groupID,@RequestParam Integer age,@RequestParam String category,@RequestParam boolean accomadation,@RequestParam Integer zone){
        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setMember(memberID);
        groupMembers.setGroupId(groupID);
        groupMembers.setCategory(category);
        groupMembers.setAccomadation(accomadation);
        groupMembersRepo.save(groupMembers);

        committedMembersRepo.updateIsGroupMemberAndAge(1,age,zone,memberID);


        return "redirect:/zonaladmin/group_members/".concat(String.valueOf(groupID));
    }

    @DeleteMapping("/zonaladmin/group_members")
    public String deleteGroupMember(@RequestParam Integer groupMemberID,@RequestParam Integer memberID,@RequestParam Integer groupID){
        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setUid(groupMemberID);
        groupMembersRepo.delete(groupMembers);

        committedMembersRepo.updateIsGroupMember(0,memberID);


        return "redirect:/zonaladmin/group_members/".concat(String.valueOf(groupID));
    }
}
