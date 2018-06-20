package org.jesusyouth.jykc.jykcadmin.controller.web;

import org.jesusyouth.jykc.jykcadmin.repository.GroupInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class StatusController {

    @Autowired
    private GroupInfoRepo groupInfoRepo;

    @GetMapping("/registration_status")
    public String getGroupSignup(Model model, HttpSession httpSession) {
        model.addAttribute("status", groupInfoRepo.getRegistrationStatus());
        return "registration_status";

    }

    @GetMapping("/group_members_status")
    public String getGroupMembersStatus(Model model, HttpSession httpSession) {
        model.addAttribute("status", groupInfoRepo.getGroupMembersCount());
        Integer members=groupInfoRepo.getGroupMembersCount().stream().map(e->e.getCount()).reduce(Integer::sum).get();
        Integer family=groupInfoRepo.getFamilyCount();
        model.addAttribute("members", members);
        model.addAttribute("family", family);
        model.addAttribute("total", family+members);
        return "registration_status_2";

    }
}
