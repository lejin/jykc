package org.jesusyouth.jykc.jykcadmin.controller.web;

import org.jesusyouth.jykc.jykcadmin.controller.api.GroupMembersController;
import org.jesusyouth.jykc.jykcadmin.dto.FamilyStatiInterface;
import org.jesusyouth.jykc.jykcadmin.dto.FamilystatiticsDTO;
import org.jesusyouth.jykc.jykcadmin.dto.MembersWithTeensDto;
import org.jesusyouth.jykc.jykcadmin.dto.StatiticsDto;
import org.jesusyouth.jykc.jykcadmin.model.CommittedMember;
import org.jesusyouth.jykc.jykcadmin.model.CommittedMembers;
import org.jesusyouth.jykc.jykcadmin.model.GroupInfo;
import org.jesusyouth.jykc.jykcadmin.model.GroupMembers;
import org.jesusyouth.jykc.jykcadmin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class StatiticsController {
    @Autowired
    private StatiRepo statiRepo;

    @Autowired
    private FamilyMemberRepo familyMemberRepo;

    @Autowired
    private GroupMembersRepo groupMembersRepo;

    @Autowired
    private CommittedMembersRepo committedMembersRepo;

    @Autowired
    private GroupInfoRepo groupInfoRepo;

    @GetMapping("/admin/statitics/{zone}")
    public String getCommittedMembers(Model model, HttpSession httpSession, @PathVariable Integer zone) {
        model.addAttribute("teens", statiRepo.getTeensByZone(zone));
        List<Integer> headList = new ArrayList<>();
        Map<Integer,CommittedMember> percodeMap=new HashMap<>();
        Iterable<CommittedMember> committedMembers = committedMembersRepo.findAll();
        committedMembers.forEach(e->{
            percodeMap.put(e.getId(),e );
        });
        List<FamilyStatiInterface> familyStatiInterfaceList = familyMemberRepo.getallbyzone(zone);
        List<FamilystatiticsDTO> familystatiticsDTOList=new ArrayList<>();
        familyStatiInterfaceList.forEach(e -> {
            if(!headList.contains(e.getHead())){
                FamilystatiticsDTO head=new FamilystatiticsDTO();
                head.setName(percodeMap.get(e.getHead()).getName());
                head.setAge(percodeMap.get(e.getHead()).getAge());
                head.setGender(percodeMap.get(e.getHead()).getGender());
                head.setRelation("Family Head");
                head.setPhone(percodeMap.get(e.getHead()).getPhone());
                head.setGroupLeader(e.getGroupLeader());
                head.setGroupLeaderPhone(e.getGroupLeaderPhone());
                head.setGroupCode(e.getGroupCode());
                familystatiticsDTOList.add(head);
                headList.add(e.getHead());
            }
            FamilystatiticsDTO member=new FamilystatiticsDTO();
            member.setName(e.getName());
            member.setAge(e.getAge());
            member.setGender(e.getGender());
            member.setRelation(e.getRelation());
            member.setGroupCode(e.getGroupCode());
            member.setGroupLeader(e.getGroupLeader());
            member.setGroupLeaderPhone(e.getGroupLeaderPhone());
            member.setHeadName(percodeMap.get(e.getHead()).getName());
            familystatiticsDTOList.add(member);
        }) ;
        Iterable<GroupInfo> groupInfoList=groupInfoRepo.findAll();
        Map<Integer,GroupInfo> groupInfoMap=new HashMap<>();
        groupInfoList.forEach(e->{
            groupInfoMap.put(e.getGid(),e );
        });
        List<GroupMembers> groupMembersList=groupMembersRepo.getAllByCategoryEquals("family");
        groupMembersList.forEach(e->{
            if(groupInfoMap.get(e.getGroupId()).getGroupZone().equals(zone) && !headList.contains(e.getMember())){
                FamilystatiticsDTO head=new FamilystatiticsDTO();
                head.setName(percodeMap.get(e.getMember()).getName());
                head.setAge(percodeMap.get(e.getMember()).getAge());
                head.setGender(percodeMap.get(e.getMember()).getGender());
                head.setRelation("Family Head");
                head.setPhone(percodeMap.get(e.getMember()).getPhone());
                head.setGroupLeader(percodeMap.get(groupInfoMap.get(e.getGroupId()).getGroupLeader()).getName());
                head.setGroupLeaderPhone(percodeMap.get(groupInfoMap.get(e.getGroupId()).getGroupLeader()).getPhone());
                head.setGroupCode(groupInfoMap.get(e.getGroupId()).getGroupID());
                head.setHighLight(true);
                familystatiticsDTOList.add(head);
                headList.add(e.getMember());
            }
        });
        model.addAttribute("families",familystatiticsDTOList);

        List<GroupMembers> others=groupMembersRepo.getAllByCategoryEquals("others");
        others.addAll(groupMembersRepo.getAllByCategoryEquals("student"));
        List<StatiticsDto> otherList=new ArrayList<>();
        List<GroupMembers> withoutTeens=others.stream().filter(e->e.getMember()!=null).collect(Collectors.toList());
        withoutTeens.forEach(e->{
            if(groupInfoMap.get(e.getGroupId())!=null && groupInfoMap.get(e.getGroupId()).getGroupZone().equals(zone)){
                StatiticsDto other=new StatiticsDto();
                other.setName(percodeMap.get(e.getMember()).getName());
                other.setAge(percodeMap.get(e.getMember()).getAge());
                other.setGender(percodeMap.get(e.getMember()).getGender());
                other.setPhone(percodeMap.get(e.getMember()).getPhone());
                System.out.println("????????   "+e.getGroupId());
                other.setGroupLeaderName(percodeMap.get(groupInfoMap.get(e.getGroupId()).getGroupLeader()).getName());
                other.setGroupLeaderPhone(percodeMap.get(groupInfoMap.get(e.getGroupId()).getGroupLeader()).getPhone());
                other.setGroupCode(groupInfoMap.get(e.getGroupId()).getGroupID());
                other.setHighLight(true);
                otherList.add(other);
            }else {
                System.out.println("problematic !!!!!!!!!   "+e.getUid()+"   group id = "+e.getGroupId());
            }
        });
        model.addAttribute("others",otherList);
        return "statitics";
    }
}
