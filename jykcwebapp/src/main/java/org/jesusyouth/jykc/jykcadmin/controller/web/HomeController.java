package org.jesusyouth.jykc.jykcadmin.controller.web;

import org.jesusyouth.jykc.jykcadmin.dto.HomeStatus;
import org.jesusyouth.jykc.jykcadmin.model.GroupInfo;
import org.jesusyouth.jykc.jykcadmin.repository.CommittedMembersRepo;
import org.jesusyouth.jykc.jykcadmin.repository.FamilyInfoRepo;
import org.jesusyouth.jykc.jykcadmin.repository.GroupInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private GroupInfoRepo groupInfo;

    @Autowired
    private CommittedMembersRepo committedMembersRepo;

    @Autowired
    private FamilyInfoRepo familyInfoRepo;

    @RequestMapping(value = "/zonaladmin/home" )
    public String home(Model modelMap,HttpSession httpSession){
        Integer zone = (Integer) httpSession.getAttribute("zone");
        modelMap.addAttribute("familyCount",familyInfoRepo.countByFamilyZoneIdEquals(zone));
        modelMap.addAttribute("homeStatus",groupInfo.getTotalGroupsCount(zone));
        modelMap.addAttribute("membersCount",committedMembersRepo.countCommittedMemberByZoneIdEquals(zone));
        return "home";
    }
}
