package org.jesusyouth.jykc.jykcadmin.controller.web;

import com.google.gson.Gson;
import org.jesusyouth.jykc.jykcadmin.model.CommittedMember;
import org.jesusyouth.jykc.jykcadmin.model.CommittedMembers;
import org.jesusyouth.jykc.jykcadmin.model.User;
import org.jesusyouth.jykc.jykcadmin.repository.CommittedMembersRepo;
import org.jesusyouth.jykc.jykcadmin.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminCommittedMembersController {

    @Autowired
    private CommittedMembersRepo committedMembersRepo;

    @Autowired
    private UsersRepo usersRepo;

    @GetMapping("/admin/committed_members")
    public String getCommittedMembers(Model model){
        model.addAttribute("committed_members",committedMembersRepo.findAll());
        return "admin_committed_members";
    }

    @GetMapping("/admin/not_members")
    public String getNotRegisteredMembers(Model model){
        model.addAttribute("members",committedMembersRepo.NotRegisteredMembers());
        return "admin_not_members";
    }

    @PostMapping("/admin/member/leader")
    public String setLeader(Integer member){
        committedMembersRepo.updateVIP(member);
        return "redirect:/admin/not_members";
    }

}
