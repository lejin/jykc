package org.jesusyouth.jykc.jykcadmin.controller.web;

import org.apache.commons.collections.CollectionUtils;
import org.jesusyouth.jykc.jykcadmin.Constants.ZoneNames;
import org.jesusyouth.jykc.jykcadmin.common.FamilyUtil;
import org.jesusyouth.jykc.jykcadmin.common.GroupFee;
import org.jesusyouth.jykc.jykcadmin.common.GroupMemberUtil;
import org.jesusyouth.jykc.jykcadmin.dto.GroupMemberDTO;
import org.jesusyouth.jykc.jykcadmin.dto.MembersWithTeensDto;
import org.jesusyouth.jykc.jykcadmin.model.DeleteDisabled;
import org.jesusyouth.jykc.jykcadmin.model.GroupInfo;
import org.jesusyouth.jykc.jykcadmin.model.GroupMembers;
import org.jesusyouth.jykc.jykcadmin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
public class GroupController {

    @Autowired
    private GroupInfoRepo groupInfoRepo;

    @Autowired
    private GroupMembersRepo groupMembersRepo;

    @Autowired
    private CommittedMembersRepo committedMembersRepo;

    @Autowired
    private GroupMemberUtil groupMemberUtil;

    @Autowired
    private GroupFee groupFee;

    @Autowired
    private FamilyUtil familyUtil;

    @Autowired
    private DeleteDisabledRepo deleteDisabledRepo;

    @GetMapping("/zonaladmin/group_info")
    public String getGroupInfo(Model model, HttpSession httpSession) {
        Integer zone = (Integer) httpSession.getAttribute("zone");
        model.addAttribute("group_info", groupInfoRepo.findGroupInfoWithMembersCount(zone));
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
        List<MembersWithTeensDto> membersWithTeensDtos=groupMemberUtil.getMembersWithTeensDtos(groupID);
        if (CollectionUtils.isNotEmpty(membersWithTeensDtos) && !membersWithTeensDtos.get(0).getZone_id().equals(zone)){
            return "redirect:/403";
        }
        model.addAttribute("group_members", membersWithTeensDtos);
        model.addAttribute("group_id", groupID);
        Iterable<DeleteDisabled> deleteDisabledZones= deleteDisabledRepo.findAll();
        model.addAttribute("disable_delete",false);
       deleteDisabledZones.forEach(e->{
           if(e.getZone().intValue()==zone.intValue()){
               model.addAttribute("disable_delete",true);
           }
       });
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

    @PostMapping("/zonaladmin/group_members/change_category")
    public String changeCategory(@RequestParam Integer member,@RequestParam Integer groupID,@RequestParam String oldCategory, String category,HttpSession httpSession) {
        if (!ObjectUtils.nullSafeEquals(category, oldCategory)) {
            Integer zone = (Integer) httpSession.getAttribute("zone");
            groupMembersRepo.updateMemberCategory(category,member );
            groupFee.reduceGroupFee(groupID,oldCategory );
            groupFee.updateGroupFee(groupID,category );
            if("family".equals(oldCategory)){
               familyUtil.deleteFamily(member);
            }
            if ("family".equals(category)) {
                familyUtil.createFamily(groupID, member, zone);
            }
        }
        return "redirect:/zonaladmin/group_members/".concat(String.valueOf(groupID));
    }

    @DeleteMapping("/zonaladmin/group_members")
    public String deleteGroupMember(@RequestParam Integer groupID,@RequestParam(required = false) Integer memberID,
                                    @RequestParam(required = false) Integer teenID){
        if(null!=teenID) {
            groupMemberUtil.removeGroupMember(groupID, null, teenID.toString());
            return "redirect:/zonaladmin/group_members/".concat(String.valueOf(groupID));

        }
        groupMemberUtil.removeGroupMember(groupID,memberID.toString(),null);
        return "redirect:/zonaladmin/group_members/".concat(String.valueOf(groupID));
    }

    @PostMapping("/zonaladmin/unsubmit")
    public String unsubmit(@RequestParam Integer groupID){
        groupInfoRepo.unsubmit(groupID);
        return "redirect:/zonaladmin/group_info";
    }

    @GetMapping("/incorrectfee")
    @ResponseBody
    public String incorrectfee(){
        StringBuffer br=new StringBuffer();
        groupInfoRepo.findAll().forEach(e->{
            List<MembersWithTeensDto> groupMembers=groupMemberUtil.getMembersWithTeensDtos(e.getGid());
            Integer total=0;
            for (MembersWithTeensDto groupMember : groupMembers) {
                int fee =groupFee.calculateFee(groupMember.getCategory());
                total+=fee;
            }
            if(e.getGroupFee().intValue()!=total.intValue()){
                br.append(">>>>>>??????    "+e.getGroupID()+"  correct value == "+total+"<br>");
            }
        });
        return br.toString();
    }
}
